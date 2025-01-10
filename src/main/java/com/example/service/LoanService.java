package com.example.service;

import com.example.entity.Customer;
import com.example.entity.Loan;
import com.example.entity.LoanInstallment;
import com.example.entity.LoanPaymentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.CustomerRepository;
import com.example.repository.LoanInstallmentRepository;
import com.example.repository.LoanRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author taner geyhan
 * LoanService
 */

@Service
public class LoanService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanInstallmentRepository loanInstallmentRepository;

    private final Double INTEREST_RATE_MIN = 0.1;
    private final Double INTEREST_RATE_MAX = 0.5;

    /**
     * Create Loan Method
     * @param customerId
     * @param loanAmount
     * @param interestRate
     * @param numberOfInstallments
     * @return created loan object with input parameters
     * @throws IllegalArgumentException error state
     */
    public Loan createLoan(Long customerId, Double loanAmount, Double interestRate, Integer numberOfInstallments) {

        // Validate inputs
        if (interestRate < INTEREST_RATE_MIN || interestRate > INTEREST_RATE_MAX) {
            throw new IllegalArgumentException("Interest rate must be between 0.1 and 0.5");
        }
        if (!Arrays.asList(6, 9, 12, 24).contains(numberOfInstallments)) {
            throw new IllegalArgumentException("Number of installments must be 6, 9, 12, or 24");
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        if (customer.getUsedCreditLimit() + loanAmount > customer.getCreditLimit()) {
            throw new IllegalArgumentException("Customer does not have enough credit limit for this loan");
        }

        // Create Installments
        Double totalAmount = loanAmount * (1 + interestRate);
        Double installmentAmount = totalAmount / numberOfInstallments;
        customer.setUsedCreditLimit(customer.getUsedCreditLimit() + loanAmount); // Update customer used credit limit

        // Create Loan
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanAmount(totalAmount);
        loan.setNumberOfInstallments(numberOfInstallments);
        loan.setCreateDate(LocalDate.now().toString());
        loan.setIsPaid(false);
        loanRepository.save(loan);



        LocalDate dueDate = LocalDate.now().plusMonths(1).withDayOfMonth(1); // Installment’s due date should be the first day of next month.

        for (int i = 0; i < numberOfInstallments; i++) {
            LoanInstallment installment = new LoanInstallment();
            installment.setLoan(loan);
            installment.setAmount(installmentAmount);
            installment.setPaidAmount(0.0);
            installment.setDueDate(dueDate);
            installment.setIsPaid(false);
            loanInstallmentRepository.save(installment);

            dueDate = dueDate.plusMonths(1); // Next installment due date
        }

        return loan;
    }

    /**
     * List Loan Method
     * @param customerId
     * @return loan list with given customerId
     */
    public List<Loan> listLoans(Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    /**
     * List Installments Method
     * @param loanId
     * @return loan installments list with given loanId
     */
    public List<LoanInstallment> listInstallments(Long loanId) {
        return loanInstallmentRepository.findByLoanId(loanId);
    }

    /**
     * Pay Loan Installments
     * @param loanId
     * @param paymentAmount
     * @return information payment of installments
     */
    public LoanPaymentResult payLoan(Long loanId, Double paymentAmount) {
        // Get Loan and installment information
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));

        // Get customer by id
        Customer customer = customerRepository.findById(loan.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // If loan is already closed
        if (loan.getIsPaid()) {
            throw new RuntimeException("Loan is already fully paid");
        }

        // Get loan installments information
        List<LoanInstallment> installments = loanInstallmentRepository.findByLoanId(loanId);

        //Paid Installment Calculation
        int paidInstallments = 0;
        Double totalPaid = 0.0;
        Double remainingPayment = 0.0;
        for (LoanInstallment installment : installments){
            if(installment.getIsPaid()){
                ++paidInstallments;
                totalPaid +=installment.getPaidAmount();
            }
        }
        remainingPayment = loan.getLoanAmount() - totalPaid;
        LocalDate currentDate = LocalDate.now();
        // 3 Months Calculate
        LocalDate threeMonthsAgo = getThreeMonthsAgo(currentDate);

        // Step by step pay installments
        for (LoanInstallment installment : installments) {
            // if 3 calendar months valid
            if (!installment.getIsPaid() && installment.getDueDate().isAfter(threeMonthsAgo)) {
                // if payment is equal or greater than installment amount
                if (paymentAmount >= installment.getAmount()) {
                    // update paid amount
                    installment.setPaidAmount(installment.getAmount());
                    installment.setPaymentDate(currentDate);
                    installment.setIsPaid(true);

                    // update remaining payment
                    paymentAmount -= installment.getAmount();
                    paidInstallments++;
                    totalPaid += installment.getAmount();
                    remainingPayment -= paymentAmount;

                } else {
                    // if installment amount is greater than payment
                    break;
                }
                loanInstallmentRepository.save(installment);
            }

            // if all loan installments paid, paid status set TRUE
            if (remainingPayment == 0) {
                loan.setIsPaid(true);
                loanRepository.save(loan);
                break;
            }
        }

        // if all loan installments paid, paid status set TRUE
        if (remainingPayment == 0) {
            loan.setIsPaid(true);
            loanRepository.save(loan);
        }

        // Return loan payment result
        return new LoanPaymentResult(paidInstallments, totalPaid, loan.getIsPaid(),customer);
    }

    /**
     * Get Three Months Ago
     * @param currentDate
     * @return Local Date three months ago
     */
    private LocalDate getThreeMonthsAgo(LocalDate currentDate) {
        return currentDate.minusMonths(3);
    }

}
