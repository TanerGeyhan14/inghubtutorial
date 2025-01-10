package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private Double loanAmount;

    @Getter
    @Setter
    private Integer numberOfInstallments;

    @Getter
    @Setter
    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Getter
    @Setter
    @Column(name = "create_date", nullable = false)
    private String createDate;


}
