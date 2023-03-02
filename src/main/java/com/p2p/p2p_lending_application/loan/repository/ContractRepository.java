package com.p2p.p2p_lending_application.loan.repository;

import com.p2p.p2p_lending_application.loan.model.Contract;
import com.p2p.p2p_lending_application.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByLoanId(Loan loanId);
}
