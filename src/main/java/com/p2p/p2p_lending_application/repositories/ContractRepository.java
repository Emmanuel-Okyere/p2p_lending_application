package com.p2p.p2p_lending_application.repositories;

import com.p2p.p2p_lending_application.models.Contract;
import com.p2p.p2p_lending_application.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByLoanId(Loan loanId);
}
