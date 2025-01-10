package com.example.controller;

import com.example.entity.Loan;
import com.example.entity.LoanInstallment;
import com.example.entity.LoanPaymentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.LoanService;

import java.util.List;


/**
 * @author taner geyhan
 * Loan Controller Operations
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    /**
     * Create Loan Endpoint
     * @param customerId
     * @param loanAmount
     * @param interestRate
     * @param numberOfInstallments
     * @return created loan object with given input parameters
     */
    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@RequestParam Long customerId,
                                           @RequestParam Double loanAmount,
                                           @RequestParam Double interestRate,
                                           @RequestParam Integer numberOfInstallments) {
        Loan loan = loanService.createLoan(customerId, loanAmount, interestRate, numberOfInstallments);
        return ResponseEntity.ok(loan);
    }

    /**
     * List Loans Endpoint
     * @param customerId
     * @return List all loans for customer with given customerId
     */
    @GetMapping("/list")
    public ResponseEntity<List<Loan>> listLoans(@RequestParam Long customerId) {
        List<Loan> loans = loanService.listLoans(customerId);
        return ResponseEntity.ok(loans);
    }

    /**
     * List Installments Endpoint
     * @param loanId
     * @return installments list with given loanId
     */
    @GetMapping("/installments")
    public ResponseEntity<List<LoanInstallment>> listInstallments(@RequestParam Long loanId) {
        List<LoanInstallment> installments = loanService.listInstallments(loanId);
        return ResponseEntity.ok(installments);
    }

    /**
     * Pay Loan Installments Endpoint
     * @param loanId
     * @param paymentAmount
     * @return payment of loan transaction with given loanId and amount
     */
    @PostMapping("/pay")
    public ResponseEntity<LoanPaymentResult> payLoanInstallments(@RequestParam Long loanId,
                                                                 @RequestParam Double paymentAmount) {
        LoanPaymentResult result = loanService.payLoan(loanId, paymentAmount);
        return ResponseEntity.ok(result);
    }

}
