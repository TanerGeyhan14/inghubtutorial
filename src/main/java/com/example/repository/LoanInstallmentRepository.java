package com.example.repository;

import com.example.entity.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {
    /**
     * @param loanId
     * @return list loan installments with given loanId
     */
    List<LoanInstallment> findByLoanId(Long loanId);
}
