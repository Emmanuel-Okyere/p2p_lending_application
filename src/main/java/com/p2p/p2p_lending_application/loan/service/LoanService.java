package com.p2p.p2p_lending_application.loan.service;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import com.p2p.p2p_lending_application.loan.model.Loan;
import com.p2p.p2p_lending_application.loan.payload.request.LoanAcceptDTO;
import com.p2p.p2p_lending_application.loan.payload.request.LoanDTO;
import com.p2p.p2p_lending_application.loan.repository.LoanRepository;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import com.p2p.p2p_lending_application.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoanService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    public ResponseEntity<?> requestLoan(LoanDTO loanRequest, HttpHeaders headers) {
        Optional<User> user = getUserFromHeader(headers);
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.get().getId());
        Optional<Loan> borrowerLoanPendingApproval = loanRepository.findByBorrowerAndApprovedIsFalse(user.get());
        if(userProfile.get().getUser().getApproved() && borrowerLoanPendingApproval.isEmpty()){
            Loan loan = new Loan(user.get(),loanRequest.getAmount(),false);
            loanRepository.save(loan);
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status","failure","message","account/loan not approved"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("status","success","message","loan requested successfully"));
    }

    public ResponseEntity<?> getAllUserLoans(HttpHeaders headers) {
        Optional<User> user = getUserFromHeader(headers);
        List<Loan> borrowerLoanRequested = loanRepository.findAllByBorrower(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(borrowerLoanRequested);
    }

    public ResponseEntity<?> getAllLoansWithoutLenders() {
        return ResponseEntity.status(HttpStatus.OK).body(loanRepository.findAllByLenderIsNull());
    }

    public ResponseEntity<?> acceptToLoan(LoanAcceptDTO loanAcceptDTO, Long loanId,HttpHeaders headers) {
        Optional<User> user = getUserFromHeader(headers);
        Optional<Loan> loan  = loanRepository.findById(loanId);
        if(loan.isPresent() && user.isPresent()){
            loan.get().setLender(user.get());
            loan.get().setUpdatedAt(new Date());
            loan.get().setInterestRate(loanAcceptDTO.getInterestRate());
            Loan saved = loanRepository.save(loan.get());
            return ResponseEntity.ok(saved);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status","failure", "message","loan does not exist"));
        }
    }

    public Optional<User> getUserFromHeader(HttpHeaders headers){
        String token  = Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
        String emailAddress = jwtUtils.getEmailFromJwtToken(token);
        return userRepository.findByemailAddress(emailAddress);
    }

    public ResponseEntity<?> getLendersAcceptedLoans(HttpHeaders headers) {
        return ResponseEntity.ok(loanRepository.findAllByLender(getUserFromHeader(headers).get()));
    }
}
