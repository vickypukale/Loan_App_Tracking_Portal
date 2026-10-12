package com.loanapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanApplicationRepository repository;

    @GetMapping
    public String dashboard(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "status", required = false) String statusFilter,
            Model model) {
        
        List<LoanApplication> all = repository.findAll();

        // Calculate Summary Indicators
        long totalCount = all.size();
        long submittedCount = all.stream().filter(a -> "SUBMITTED".equalsIgnoreCase(a.getStatus())).count();
        long reviewCount = all.stream().filter(a -> "UNDER_REVIEW".equalsIgnoreCase(a.getStatus())).count();
        long approvedCount = all.stream().filter(a -> "APPROVED".equalsIgnoreCase(a.getStatus())).count();
        long rejectedCount = all.stream().filter(a -> "REJECTED".equalsIgnoreCase(a.getStatus())).count();
        long exceptionCount = all.stream().filter(a -> "EXCEPTION".equalsIgnoreCase(a.getStatus())).count();
        
        double totalRequested = all.stream().mapToDouble(a -> a.getAmount() != null ? a.getAmount() : 0.0).sum();
        double totalApproved = all.stream()
                .filter(a -> "APPROVED".equalsIgnoreCase(a.getStatus()))
                .mapToDouble(a -> a.getAmount() != null ? a.getAmount() : 0.0).sum();

        // Apply Search and Status Filters
        List<LoanApplication> filtered = all;
        if (statusFilter != null && !statusFilter.trim().isEmpty() && !"ALL".equalsIgnoreCase(statusFilter)) {
            filtered = filtered.stream()
                    .filter(a -> statusFilter.equalsIgnoreCase(a.getStatus()))
                    .collect(Collectors.toList());
        }
        if (search != null && !search.trim().isEmpty()) {
            String q = search.toLowerCase().trim();
            filtered = filtered.stream()
                    .filter(a -> (a.getApplicantName() != null && a.getApplicantName().toLowerCase().contains(q))
                            || (a.getLoanType() != null && a.getLoanType().toLowerCase().contains(q))
                            || String.valueOf(a.getId()).equals(q))
                    .collect(Collectors.toList());
        }

        // Add attributes to view
        model.addAttribute("applications", filtered);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("submittedCount", submittedCount);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("rejectedCount", rejectedCount);
        model.addAttribute("exceptionCount", exceptionCount);
        model.addAttribute("totalRequested", String.format("%.2f", totalRequested));
        model.addAttribute("totalApproved", String.format("%.2f", totalApproved));
        model.addAttribute("currentSearch", search != null ? search : "");
        model.addAttribute("currentStatus", statusFilter != null ? statusFilter : "ALL");

        return "dashboard";
    }

    @PostMapping("/submit")
    public String submitApplication(
            @RequestParam("applicantName") String applicantName,
            @RequestParam("amount") Double amount,
            @RequestParam("loanType") String loanType,
            @RequestParam(value = "creditScore", defaultValue = "700") Integer creditScore,
            @RequestParam(value = "riskRemarks", required = false) String riskRemarks) {
        
        LoanApplication app = new LoanApplication();
        app.setApplicantName(applicantName);
        app.setAmount(amount);
        app.setLoanType(loanType);
        app.setCreditScore(creditScore);
        app.setSubmissionDate(new Date());

        // Auto-flag high risk exceptions
        if (creditScore != null && creditScore < 600 && amount > 50000.0) {
            app.setStatus("EXCEPTION");
            app.setRiskRemarks("High-value loan with low credit score (" + creditScore + "). Flagged for committee review.");
        } else {
            app.setStatus("SUBMITTED");
            app.setRiskRemarks(riskRemarks != null && !riskRemarks.trim().isEmpty() ? riskRemarks : "Standard application submitted.");
        }

        repository.save(app);
        return "redirect:/loans";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") String status,
            @RequestParam(value = "remarks", required = false) String remarks) {
        
        repository.findById(id).ifPresent(app -> {
            app.setStatus(status.toUpperCase());
            if (remarks != null && !remarks.trim().isEmpty()) {
                app.setRiskRemarks(remarks);
            }
            repository.save(app);
        });
        return "redirect:/loans";
    }

    // REST endpoint for API automation
    @GetMapping("/api")
    @ResponseBody
    public List<LoanApplication> getApplicationsApi() {
        return repository.findAll();
    }
}
