package com.p2p.p2p_lending_application.loan.service;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import com.p2p.p2p_lending_application.loan.model.Loan;
import com.p2p.p2p_lending_application.loan.payload.request.LoanDTO;
import com.p2p.p2p_lending_application.loan.repository.LoanRepository;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import com.p2p.p2p_lending_application.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
        String token  = Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
        String emailAddress = jwtUtils.getEmailFromJwtToken(token);
        Optional<User> user = userRepository.findByemailAddress(emailAddress);
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.get().getId());
        Optional<Loan> borrowerLoanPendingApproval = loanRepository.findByBorrowerAndApprovedIsFalse(user.get());
        if(userProfile.get().getUser().getApproved() && borrowerLoanPendingApproval.isEmpty()){
            Loan loan = new Loan(user.get(),loanRequest.getAmount(),false);
            loanRepository.save(loan);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status","failure","message","account/loan not approved"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status","success","message","loan requested successfully"));
    }

    public ResponseEntity<?> getAllUserLoans(HttpHeaders headers) {
        String token  = Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
        String emailAddress = jwtUtils.getEmailFromJwtToken(token);
        Optional<User> user = userRepository.findByemailAddress(emailAddress);
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.get().getId());
        List<Loan> borrowerLoanRequested = loanRepository.findAllByBorrower(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(borrowerLoanRequested);
    }

    public ResponseEntity<?> getAllNonApprovedLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanRepository.findAllByApprovedFalse());
    }
}
