package com.p2p.p2p_lending_application.services.interfaces;

import com.p2p.p2p_lending_application.dto.requestDTO.*;
import com.p2p.p2p_lending_application.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface LoanService {
    ResponseEntity<?> requestLoan(LoanDTO loanRequest, HttpHeaders headers);
    ResponseEntity<?> getAllUserLoans(HttpHeaders headers);
    ResponseEntity<?> getAllLoansWithoutLenders();
    ResponseEntity<?> lenderAcceptToLoan(LoanAcceptDTO loanAcceptDTO, Long loanId, HttpHeaders headers);
    Optional<User> getUserFromHeader(HttpHeaders headers);
    ResponseEntity<?> getLendersAcceptedLoans(HttpHeaders headers);
    ResponseEntity<?> getAllAcceptedLoansButUnApproved();
    ResponseEntity<?> adminApproveLoan(LoanApproval loanApproval, Long loanId);
    ResponseEntity<?> lenderSignsContract(Long loanId, LenderContractSignRequest lenderContractSignRequest);
    ResponseEntity<?> borrowerSignsContract(Long loanId, BorrowerContractSignRequest borrowerContractSignRequest);
    ResponseEntity<?> getAContract(Long loanId);
}
