package com.p2p.p2p_lending_application.loan.repository;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByBorrowerAndApprovedIsFalse(User borrower);
    List<Loan> findAllByBorrower(User borrower);
    List<Loan> findAllByApprovedFalse();
}
