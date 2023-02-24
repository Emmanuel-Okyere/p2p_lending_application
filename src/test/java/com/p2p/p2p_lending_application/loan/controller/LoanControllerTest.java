package com.p2p.p2p_lending_application.loan.controller;

import com.p2p.p2p_lending_application.loan.payload.request.LenderContractSignRequest;
import com.p2p.p2p_lending_application.loan.payload.request.LoanAcceptDTO;
import com.p2p.p2p_lending_application.loan.payload.request.LoanApproval;
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
import java.util.Map;

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
        loanController.getAllRequestedLoan(headers);
        Mockito.verify(loanService,Mockito.times(1)).getAllUserLoans(headers);
    }

    @Test
    void usersCanGetAllLoansThatAreNotApprovedYet() {
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("her")).when(loanService).getAllLoansWithoutLenders();
        loanController.getAllLoansWithoutLenders();
        Mockito.verify(loanService,Mockito.times(1)).getAllLoansWithoutLenders();
    }
    @Test
    void aLenderCanAcceptToLoan(){
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("her")).when(loanService).lenderAcceptToLoan(any(),any(),any());
        loanController.lenderAcceptToLoan(new LoanAcceptDTO(BigDecimal.valueOf(12.45)),1L,headers);
        Mockito.verify(loanService,Mockito.times(1)).lenderAcceptToLoan(any(),any(),any());
    }

    @Test
    void adminCanApproveLoan(){
        LoanApproval loanApproval = new LoanApproval();
        loanApproval.setIsApproved(true);
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("her")).when((loanService)).lenderAcceptToLoan(any(),any(),any());
        loanController.adminApproveLoan(loanApproval,1L);
        Mockito.verify(loanService,Mockito.times(1)).adminApproveLoan(any(),any());
    }
    @Test
    void lenderSignsTheContract(){
        LenderContractSignRequest lenderContractSignRequest = new LenderContractSignRequest();
        lenderContractSignRequest.setLenderSignature("Emmanuel Okyere");
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("her")).when(loanService).lenderSignsContract(any(),any());
        loanController.lenderSignsTheContract(1L,lenderContractSignRequest);
        Mockito.verify(loanService,Mockito.times(1)).lenderSignsContract(any(),any());
    }
}
