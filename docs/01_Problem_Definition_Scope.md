# Problem Definition and Scope: Loan Application Tracking Portal

## Problem Statement
Current loan application processes lack transparency, leading to borrower frustration and increased support overhead. Internal teams struggle to track applications across different stages (submission, verification, underwriting, approval/rejection) efficiently due to disparate systems. A centralized, real-time portal is needed to track the lifecycle of a loan application.

## Target Users
1. **Borrowers/Applicants:** Need to submit applications and check real-time status.
2. **Loan Officers:** Need to review applications, request documents, and update statuses.
3. **Underwriters:** Need to assess risk and approve/reject applications.
4. **Management:** Need dashboard summaries and KPIs (turnaround time, approval rates).

## Stakeholders
- Retail Banking Head
- Risk and Compliance Team
- Customer Support Team
- IT Infrastructure & DevOps Team

## Objectives
- Reduce application processing time by 20%.
- Decrease customer status inquiry calls by 40%.
- Provide a single source of truth for all loan applications.
- Ensure secure and audited access to applicant data.

## Constraints
- **Time:** MVP must be delivered within the current release cycle (15-Task scope).
- **Budget:** Use open-source technologies (Java, Spring Boot/Maven, PostgreSQL, Docker, Jenkins).
- **Security:** Applicant data must be protected (OWASP standards).

## Measurable Success Criteria
- 100% of applications tracked digitally from Day 1 of launch.
- Sub-2 second response time for dashboard queries.
- Zero PII data leaks.

## Approved MVP Scope (15 Tasks)
1. **User Auth:** Basic Login/Logout.
2. **Application Submission:** Form to submit basic loan details (Name, Amount, Type).
3. **Dashboard:** View all submitted applications (Searchable).
4. **Status Update:** Ability to move application (Submitted -> Review -> Approved/Rejected).
5. **Details View:** Drill-down into a specific application's details.
6. **Exception/Alert View:** Flag incomplete applications.
7. **Summary Indicators:** Count of total, pending, and completed applications.
8. **Git Repository Setup:** Version control initialization.
9. **CI Pipeline:** Jenkins automated build (Maven).
10. **Test Automation:** Basic Selenium test for Login and Dashboard.
11. **Containerization:** Dockerfile for the application.
12. **CD Pipeline:** Automated deployment via Jenkins to Docker/Tomcat.
13. **Infrastructure as Code:** Ansible playbook/Puppet manifest for server setup.
14. **Idempotent Deployment:** Automated provisioning and rollback testing.
15. **Documentation:** Architecture and troubleshooting guides.
