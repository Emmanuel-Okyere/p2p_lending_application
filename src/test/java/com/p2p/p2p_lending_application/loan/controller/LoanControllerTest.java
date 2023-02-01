package com.p2p.p2p_lending_application.loan.controller;

import com.p2p.p2p_lending_application.loan.payload.request.LoanDTO;
import com.p2p.p2p_lending_application.loan.service.LoanService;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class LoanControllerTest {
    @Mock
    private HttpHeaders headers;
    @MockBean
    private LoanService loanService;
    @Autowired
    private LoanController loanController;
    @Test
    void userCanRequestForLoanWithAPositiveAmount() {
        LoanDTO loan = new LoanDTO(BigDecimal.valueOf(12.00));
        Mockito.doReturn(ResponseEntity.
                status(HttpStatus.OK)
                .body(Map.of("status","success","message","loan requested successfully")))
                .when(loanService).requestLoan(loan,headers);
        loanController.requestLoan(loan, headers);
        Mockito.verify(loanService, Mockito.times(1)).requestLoan(any(),any());
    }
    @Test
    void userCanGetTheirRequestedLoans() {
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("her")).when(loanService).getAllUserLoans(headers);
        loanService.getAllUserLoans(headers);
        Mockito.verify(loanService,Mockito.times(1)).getAllUserLoans(headers);
    }

    @Test
    void usersCanGetAllLoansThatAreNotApprovedYet() {
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("her")).when(loanService).getAllNonApprovedLoans();
        loanService.getAllNonApprovedLoans();
        Mockito.verify(loanService,Mockito.times(1)).getAllNonApprovedLoans();
    }
}