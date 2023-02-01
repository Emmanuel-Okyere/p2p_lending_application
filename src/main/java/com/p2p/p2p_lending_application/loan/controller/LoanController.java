package com.p2p.p2p_lending_application.loan.controller;

import com.p2p.p2p_lending_application.loan.payload.request.LoanDTO;
import com.p2p.p2p_lending_application.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @PostMapping
    public ResponseEntity<?> requestLoan(@RequestBody @Valid LoanDTO loanRequest, @RequestHeader HttpHeaders headers){
        return loanService.requestLoan(loanRequest,headers);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getAllRequestedLoan(@RequestHeader HttpHeaders headers){
        return loanService.getAllUserLoans(headers);
    }
    @GetMapping
    public ResponseEntity<?> getAllLoansThatAreNotApprovedYet(){
        return loanService.getAllNonApprovedLoans();
    }
}
