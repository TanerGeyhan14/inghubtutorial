package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "loan_installments")
public class LoanInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    @Getter
    @Setter
    private Loan loan;

    @Getter
    @Setter
    private Double amount;

    @Getter
    @Setter
    private Double paidAmount;

    @Getter
    @Setter
    private LocalDate dueDate;

    @Getter
    @Setter
    private LocalDate paymentDate;

    @Getter
    @Setter
    private Boolean isPaid;

}
