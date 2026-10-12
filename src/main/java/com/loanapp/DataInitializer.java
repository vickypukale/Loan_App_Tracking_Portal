package com.loanapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final LoanApplicationRepository repository;

    public DataInitializer(LoanApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(new LoanApplication("John Doe", 25000.0, "Personal", "SUBMITTED", 720, "Salary slips and KYC verified. Pending credit assessment."));
            repository.save(new LoanApplication("Priya Sharma", 150000.0, "Home Loan", "APPROVED", 785, "High credit score. Property collateral evaluated and approved."));
            repository.save(new LoanApplication("Rajesh Patel", 45000.0, "Auto Loan", "UNDER_REVIEW", 690, "Underwriter review in progress. Awaiting vehicle dealer invoice."));
            repository.save(new LoanApplication("Emily Watson", 15000.0, "Education", "SUBMITTED", 710, "University admission confirmed. Co-applicant income verified."));
            repository.save(new LoanApplication("Michael Chen", 95000.0, "Business", "EXCEPTION", 590, "High loan amount with credit score below 600. Requires committee review."));
            repository.save(new LoanApplication("Vikram Singh", 30000.0, "Personal", "REJECTED", 540, "Debt-to-income ratio exceeds 65%. Recent credit delinquency."));
            System.out.println(">>> Seeded 6 Initial Loan Applications for Loan Application Tracking Portal <<<");
        }
    }
}
