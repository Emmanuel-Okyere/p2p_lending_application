package com.p2p.p2p_lending_application.loan.service;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import com.p2p.p2p_lending_application.loan.model.Loan;
import com.p2p.p2p_lending_application.loan.payload.request.LoanDTO;
import com.p2p.p2p_lending_application.loan.repository.LoanRepository;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import com.p2p.p2p_lending_application.profile.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class LoanServiceTest {
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private LoanRepository loanRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserProfileRepository userProfileRepository;
    @Autowired
    private LoanService loanService;
    @MockBean
    private UserProfile userProfile;

    @Mock
    private HttpHeaders headers;

    @Test
    void usersWhoseAccountHasNotBeenApprovedCanNotCreateLoanRequest() {
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        user.get().setApproved(false);
        Optional<UserProfile> profile = Optional.of(new UserProfile(user.get()));
        LoanDTO loan = new LoanDTO(BigDecimal.valueOf(12.00));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMyNEBnbWFpbC5jb20iLCJleHAiOjE2NzUyNTQxOTksImlhdCI6MTY3NTI1MDU5OX0.WjjFbG2vyMzud2rWum7aroagyENTfOZbV0d_OkLcsAM0CKbRHORTtutO0QBqxat_egSPKRpPGBjnlnX30jK-dA";
        Mockito.when(headers.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        Mockito.doReturn("gyateng94@gmail.com").when(jwtUtils).getEmailFromJwtToken(any());
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(profile).when(userProfileRepository).findByUserId(user.get().getId());
        Mockito.doReturn(Optional.of(new Loan())).when(loanRepository).findByBorrowerAndApprovedIsFalse(user.get());
        ResponseEntity<?> response = loanService.requestLoan(loan,headers);
        Mockito.verify(loanRepository,Mockito.times(0)).save(any());
        assertEquals(response.getStatusCode(),HttpStatus.BAD_REQUEST);
    }
    @Test
    void usersWhoseAccountHasBeenApprovedCanNCreateLoanRequests() {
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        user.get().setApproved(true);
        Optional<UserProfile> profile = Optional.of(new UserProfile(user.get()));
        LoanDTO loan = new LoanDTO(BigDecimal.valueOf(12.00));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMyNEBnbWFpbC5jb20iLCJleHAiOjE2NzUyNTQxOTksImlhdCI6MTY3NTI1MDU5OX0.WjjFbG2vyMzud2rWum7aroagyENTfOZbV0d_OkLcsAM0CKbRHORTtutO0QBqxat_egSPKRpPGBjnlnX30jK-dA";
        Mockito.when(headers.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        Mockito.doReturn("gyateng94@gmail.com").when(jwtUtils).getEmailFromJwtToken(any());
        Mockito.doReturn(profile).when(userProfileRepository).findByUserId(user.get().getId());
        Mockito.doReturn(Optional.empty()).when(loanRepository).findByBorrowerAndApprovedIsFalse(user.get());
        ResponseEntity<?> response = loanService.requestLoan(loan,headers);
        Mockito.verify(loanRepository,Mockito.times(1)).save(any());
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }

    @Test
    void userCanGetTheirLoans() {
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        Optional<UserProfile> profile = Optional.of(new UserProfile(user.get()));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMyNEBnbWFpbC5jb20iLCJleHAiOjE2NzUyNTQxOTksImlhdCI6MTY3NTI1MDU5OX0.WjjFbG2vyMzud2rWum7aroagyENTfOZbV0d_OkLcsAM0CKbRHORTtutO0QBqxat_egSPKRpPGBjnlnX30jK-dA";
        Mockito.when(headers.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        Mockito.doReturn("gyateng94@gmail.com").when(jwtUtils).getEmailFromJwtToken(any());
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(profile).when(userProfileRepository).findByUserId(user.get().getId());
        Mockito.doReturn(List.of(new Loan())).when(loanRepository).findAllByBorrower(user.get());
        loanService.getAllUserLoans(headers);
        Mockito.verify(jwtUtils, Mockito.times(1)).getEmailFromJwtToken(any());
        Mockito.verify(userRepository, Mockito.times(1)).findByemailAddress(any());
        Mockito.verify(userProfileRepository, Mockito.times(1)).findByUserId(any());
        Mockito.verify(loanRepository, Mockito.times(1)).findAllByBorrower(any());
    }

    @Test
    void lenderUsersCanGetAllLoansWithoutLendersAndNotApproved() {
        //given
        Mockito.doReturn(List.of(new Loan())).when(loanRepository).findAllByLenderIsNull();
        //when
        loanService.getAllLoansWithoutLenders();
        //then
        Mockito.verify(loanRepository,Mockito.times(1)).findAllByLenderIsNull();
    }
}