package com.p2p.p2p_lending_application.loan.repository;

import com.p2p.p2p_lending_application.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
