package com.example.entity;

import lombok.Getter;
import lombok.Setter;

public class LoanPaymentResult {
    @Getter
    @Setter
    private Integer paidInstallments;

    @Getter
    @Setter
    private Double totalPaid;

    @Getter
    @Setter
    private Boolean isLoanPaid;

    @Getter
    @Setter
    private Customer customer;

    public LoanPaymentResult(Integer paidInstallments, Double totalPaid, Boolean isLoanPaid) {
        this.paidInstallments = paidInstallments;
        this.totalPaid = totalPaid;
        this.isLoanPaid = isLoanPaid;
    }

    public LoanPaymentResult(Integer paidInstallments, Double totalPaid, Boolean isLoanPaid, Customer customer) {
        this.paidInstallments = paidInstallments;
        this.totalPaid = totalPaid;
        this.isLoanPaid = isLoanPaid;
        this.customer = customer;
    }

    public LoanPaymentResult() {
    }
}
