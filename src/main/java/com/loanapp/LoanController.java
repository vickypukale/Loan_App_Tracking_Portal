package com.loanapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/loans") // Using the UI mapping
public class LoanController {

    @Autowired
    private LoanApplicationRepository repository;

    @GetMapping
    public String dashboard(Model model) {
        List<LoanApplication> applications = repository.findAll();
        model.addAttribute("applications", applications);
        // Dashboard feature edit
        model.addAttribute("totalCount", applications.size());
        return "dashboard";
    }

    @PostMapping("/submit")
    public String submitApplication(@ModelAttribute LoanApplication application) {
        application.setStatus("SUBMITTED");
        // Develop hotfix edit kept
        System.out.println("Submitting application...");
        application.setSubmissionDate(new java.util.Date());
        repository.save(application);
        return "redirect:/loans";
    }
}
