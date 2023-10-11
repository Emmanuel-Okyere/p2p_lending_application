package com.p2p.p2p_lending_application.controllers;
import com.p2p.p2p_lending_application.dto.requestDTO.*;
import com.p2p.p2p_lending_application.services.implementations.LoanServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/loan")
public class LoanController {
    @Autowired
    private LoanServiceImpl loanServiceImpl;
    @PostMapping("/create")
    public ResponseEntity<?> requestLoan(@RequestBody @Valid LoanDTO loanRequest, @RequestHeader HttpHeaders headers){
        return loanServiceImpl.requestLoan(loanRequest,headers);
    }
    @GetMapping("/user-loans")
    public ResponseEntity<?> getAllRequestedLoan(@RequestHeader HttpHeaders headers){
        return loanServiceImpl.getAllUserLoans(headers);
    }
    @GetMapping("/lender")
    public ResponseEntity<?> getAllLoansWithoutLenders(){
        return loanServiceImpl.getAllLoansWithoutLenders();
    }
    @PostMapping("/{loanId}")
    public ResponseEntity<?> lenderAcceptToLoan(@RequestBody @Valid LoanAcceptDTO loanAcceptDTO, @PathVariable Long loanId, @RequestHeader HttpHeaders headers){
        return loanServiceImpl.lenderAcceptToLoan(loanAcceptDTO,loanId,headers);
    }
    @GetMapping("/lender-loans")
    public ResponseEntity<?> getAllLendersAcceptedLoans(@RequestHeader HttpHeaders headers){
        return loanServiceImpl.getLendersAcceptedLoans(headers);
    }
    @GetMapping("/admin")
    public ResponseEntity<?> getAllAcceptedLoansButUnApproved(){
        return loanServiceImpl.getAllAcceptedLoansButUnApproved();
    }
    @PostMapping("/admin/{loanId}")
    public ResponseEntity<?> adminApproveLoan(@RequestBody LoanApproval loanApproval, @PathVariable Long loanId){
        return loanServiceImpl.adminApproveLoan(loanApproval,loanId);
    }

    @PostMapping("lender/{loanId}/contract")
    public ResponseEntity<?> lenderSignsTheContract(@PathVariable Long loanId,
                                                    @RequestBody @Valid LenderContractSignRequest lenderContractSignRequest){
        return loanServiceImpl.lenderSignsContract(loanId,lenderContractSignRequest);
    }
    @PostMapping("borrower/{loanId}/contract")
    public ResponseEntity<?> borrowerSignsTheContract(@PathVariable Long loanId,
                                                    @RequestBody @Valid BorrowerContractSignRequest borrowerContractSignRequest){
        return loanServiceImpl.borrowerSignsContract(loanId,borrowerContractSignRequest);
    }
    @GetMapping("{loanId}/contract")
    public ResponseEntity<?> getBorrowerContract(@PathVariable Long loanId){
        return loanServiceImpl.getAContract(loanId);
    }
}
