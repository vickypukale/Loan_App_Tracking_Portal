package com.loanapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanApplicationRepository repository;

    @PostMapping
    public LoanApplication submitApplication(@RequestBody LoanApplication application) {
        application.setStatus("SUBMITTED");
        application.setSubmissionDate(new java.util.Date());
        return repository.save(application);
    }

    @GetMapping
    public List<LoanApplication> getAllApplications() {
        return repository.findAll();
    }
}
