package com.example.inghubtutorial;
import com.example.entity.Customer;
import com.example.entity.Loan;
import com.example.entity.LoanInstallment;
import com.example.entity.LoanPaymentResult;
import com.example.repository.CustomerRepository;
import com.example.repository.LoanInstallmentRepository;
import com.example.repository.LoanRepository;
import com.example.service.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private LoanService loanService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_createLoan(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setCreditLimit(75000.00);
        customer.setUsedCreditLimit(1000.00);
        Long customerId = 1L;

        Double loanAmount = 20000.00;
        Double interestRate = 0.5;
        Integer numberOfInstallments = 6;

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Loan actual = loanService.createLoan(customerId,loanAmount,interestRate,numberOfInstallments);
        Assertions.assertEquals(numberOfInstallments,actual.getNumberOfInstallments());
    }

    @Test
    void test_createLoan_throwException_invalidInstallments(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setCreditLimit(75000.00);
        customer.setUsedCreditLimit(1000.00);
        Long customerId = 1L;

        Double loanAmount = 20000.00;
        Double interestRate = 0.5;
        Integer numberOfInstallments = 7;

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Throwable throwable =  Assertions.assertThrows(Throwable.class, () -> {
            loanService.createLoan(customerId,loanAmount,interestRate,numberOfInstallments);
        });
        Assertions.assertEquals(IllegalArgumentException.class, throwable.getClass());
    }

    @Test
    void test_createLoan_throwException_invalidLimit(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setCreditLimit(5000.00);
        customer.setUsedCreditLimit(10000.00);
        Long customerId = 1L;

        Double loanAmount = 20000.00;
        Double interestRate = 0.5;
        Integer numberOfInstallments = 6;

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Throwable throwable =  Assertions.assertThrows(Throwable.class, () -> {
            loanService.createLoan(customerId,loanAmount,interestRate,numberOfInstallments);
        });
        Assertions.assertEquals(IllegalArgumentException.class, throwable.getClass());
    }

    @Test
    void test_payLoan(){

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setCreditLimit(75000.00);
        customer.setUsedCreditLimit(1000.00);
        Long customerId = 1L;

        Double loanAmount = 20000.00;
        Double interestRate = 0.5;
        Integer numberOfInstallments = 6;

        Loan loan = new Loan();
        loan.setLoanAmount(10000.00);
        loan.setCustomer(customer);
        loan.setCreateDate(LocalDate.now().toString());
        loan.setId(1L);
        loan.setNumberOfInstallments(numberOfInstallments);
        loan.setIsPaid(false);

        LoanInstallment loanInstallment = new LoanInstallment();
        loanInstallment.setLoan(loan);
        loanInstallment.setId(1L);
        loanInstallment.setAmount(5000.00);
        loanInstallment.setDueDate(LocalDate.MAX);
        loanInstallment.setPaymentDate(LocalDate.now());
        loanInstallment.setPaidAmount(5000.00);
        loanInstallment.setIsPaid(true);

        LoanInstallment loanInstallment2 = new LoanInstallment();
        loanInstallment2.setLoan(loan);
        loanInstallment2.setId(2L);
        loanInstallment2.setAmount(5000.00);
        loanInstallment2.setDueDate(LocalDate.MAX);
        loanInstallment2.setPaymentDate(LocalDate.now());
        loanInstallment2.setPaidAmount(5000.00);
        loanInstallment2.setIsPaid(false);

        LoanInstallment loanInstallment3 = new LoanInstallment();
        loanInstallment3.setLoan(loan);
        loanInstallment3.setId(3L);
        loanInstallment3.setAmount(5000.00);
        loanInstallment3.setDueDate(LocalDate.MAX);
        loanInstallment3.setPaymentDate(LocalDate.now());
        loanInstallment3.setPaidAmount(5000.00);
        loanInstallment3.setIsPaid(false);

        LoanInstallment loanInstallment4 = new LoanInstallment();
        loanInstallment4.setLoan(loan);
        loanInstallment4.setId(4L);
        loanInstallment4.setAmount(5000.00);
        loanInstallment4.setDueDate(LocalDate.MAX);
        loanInstallment4.setPaymentDate(LocalDate.now());
        loanInstallment4.setPaidAmount(5000.00);
        loanInstallment4.setIsPaid(false);

        LoanInstallment loanInstallment5 = new LoanInstallment();
        loanInstallment5.setLoan(loan);
        loanInstallment5.setId(5L);
        loanInstallment5.setAmount(5000.00);
        loanInstallment5.setDueDate(LocalDate.MAX);
        loanInstallment5.setPaymentDate(LocalDate.now());
        loanInstallment5.setPaidAmount(5000.00);
        loanInstallment5.setIsPaid(false);

        LoanInstallment loanInstallment6 = new LoanInstallment();
        loanInstallment6.setLoan(loan);
        loanInstallment6.setId(6L);
        loanInstallment6.setAmount(5000.00);
        loanInstallment6.setDueDate(LocalDate.MAX);
        loanInstallment6.setPaymentDate(LocalDate.now());
        loanInstallment6.setPaidAmount(5000.00);
        loanInstallment6.setIsPaid(false);

        List<LoanInstallment> installments = new ArrayList<>();
        installments.add(loanInstallment);
        installments.add(loanInstallment2);
        installments.add(loanInstallment3);
        installments.add(loanInstallment4);
        installments.add(loanInstallment5);
        installments.add(loanInstallment6);


        Mockito.when(loanRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(loan));
        Mockito.when(loanInstallmentRepository.findByLoanId(Mockito.anyLong())).thenReturn(installments);
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        LoanPaymentResult actual = loanService.payLoan(1L,10000.00);
        LoanPaymentResult result = new LoanPaymentResult();
        result.setIsLoanPaid(actual.getIsLoanPaid());
        result.setCustomer(actual.getCustomer());
        result.setTotalPaid(actual.getTotalPaid());
        result.setPaidInstallments(actual.getPaidInstallments());
        Assertions.assertEquals(result.getCustomer(),customer);
    }

    @Test
    void test_listLoans(){

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setCreditLimit(75000.00);
        customer.setUsedCreditLimit(1000.00);
        Long customerId = 1L;

        Double loanAmount = 20000.00;
        Double interestRate = 0.5;
        Integer numberOfInstallments = 6;

        Loan loan = new Loan();
        loan.setLoanAmount(10000.00);
        loan.setCustomer(customer);
        loan.setCreateDate(LocalDate.now().toString());
        loan.setId(1L);
        loan.setNumberOfInstallments(numberOfInstallments);
        loan.setIsPaid(false);

        Loan loanOther = new Loan();
        loan.setLoanAmount(10000.00);
        loan.setCustomer(customer);
        loan.setCreateDate(LocalDate.now().toString());
        loan.setId(2L);
        loan.setNumberOfInstallments(numberOfInstallments);
        loan.setIsPaid(false);

        List<Loan> loanList = new ArrayList<>();

        loanList.add(loan);
        loanList.add(loanOther);

        Mockito.when(loanRepository.findByCustomerId(Mockito.anyLong())).thenReturn(loanList);

        Assertions.assertEquals(loanList.size(),loanService.listLoans(1L).size());
    }

}
