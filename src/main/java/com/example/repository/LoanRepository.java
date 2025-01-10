package com.example.repository;


import com.example.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanRepository extends JpaRepository<Loan, Long> {
    /**
     * @param customerId
     * @return List loan with given customerId
     */
    List<Loan> findByCustomerId(Long customerId);
}
