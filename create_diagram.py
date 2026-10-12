import matplotlib.pyplot as plt
import matplotlib.patches as patches

def generate_architecture_diagram(output_path="docs/architecture_diagram.png"):
    fig, ax = plt.subplots(figsize=(10, 5.2), dpi=300)
    ax.set_xlim(0, 100)
    ax.set_ylim(0, 52)
    ax.axis('off')

    # Background canvas
    fig.patch.set_facecolor('#F8FAFC')
    ax.set_facecolor('#F8FAFC')

    # Color palette
    c_git = '#E2E8F0'
    c_ci = '#DBEAFE'
    c_test = '#FEF3C7'
    c_docker = '#E0E7FF'
    c_ansible = '#DCFCE7'
    c_prod = '#FCE7F3'

    b_git = '#64748B'
    b_ci = '#2563EB'
    b_test = '#D97706'
    b_docker = '#4F46E5'
    b_ansible = '#16A34A'
    b_prod = '#DB2777'

    def draw_box(x, y, w, h, title, items, fill_c, border_c):
        rect = patches.FancyBboxPatch((x, y), w, h, boxstyle="round,pad=0.6,rounding_size=1.2",
                                     facecolor=fill_c, edgecolor=border_c, linewidth=1.8)
        ax.add_patch(rect)
        ax.text(x + w/2, y + h - 2.5, title, ha='center', va='center',
                fontsize=10.5, fontweight='bold', color=border_c, family='sans-serif')
        for i, item in enumerate(items):
            ax.text(x + w/2, y + h - 5.5 - (i * 2.5), item, ha='center', va='center',
                    fontsize=8.5, color='#334155', family='sans-serif')

    def draw_arrow(x1, y1, x2, y2, label=""):
        ax.annotate('', xy=(x2, y2), xytext=(x1, y1),
                    arrowprops=dict(facecolor='#475569', edgecolor='#475569', arrowstyle='->', lw=1.6, mutation_scale=14))
        if label:
            ax.text((x1 + x2)/2, (y1 + y2)/2 + 1.2, label, ha='center', va='bottom',
                    fontsize=7.5, fontweight='bold', color='#475569', family='sans-serif')

    # Diagram Title
    ax.text(50, 49.5, "Figure 1: End-to-End Secure CI/CD & Deployment Architecture",
            ha='center', va='center', fontsize=12, fontweight='bold', color='#0F172A', family='sans-serif')

    # Stage 1: SCM
    draw_box(2, 22, 14, 22, "1. SCM & Branching", 
             ["Developer IDE", "Feature Branch", "Pull Request", "GitHub Repo"], c_git, b_git)

    # Stage 2: CI Orchestrator
    draw_box(20, 22, 16, 22, "2. Continuous Integration", 
             ["Jenkins Server", "Webhook Polling", "Maven 3.9 Build", "JDK 21 Compile"], c_ci, b_ci)

    # Stage 3: Automated Quality Gate
    draw_box(40, 22, 16, 22, "3. Testing Quality Gate", 
             ["JUnit Unit Tests", "Selenium WebDriver", "Headless Chrome", "Surefire XML"], c_test, b_test)

    # Stage 4: Containerization
    draw_box(60, 22, 16, 22, "4. Containerization", 
             ["Dockerfile Packaging", "Temurin JRE Alpine", "Docker Build", "Local/Hub Registry"], c_docker, b_docker)

    # Stage 5: Configuration Mgmt
    draw_box(80, 22, 17, 22, "5. Automated CD / IaC", 
             ["Ansible Controller", "Idempotent Playbook", "systemd Daemon", "Zero-Downtime Rollout"], c_ansible, b_ansible)

    # Bottom Node: Target Server & Portal
    draw_box(25, 4, 50, 12, "Production Target Node (Tomcat / Docker Host: Port 8085)",
             ["Loan Application Tracking Portal (Spring Boot REST + Thymeleaf Dashboard)",
              "Health Checks | H2 In-Memory / PostgreSQL | Rollback Recovery Strategy"], c_prod, b_prod)

    # Horizontal Flow Arrows
    draw_arrow(16.5, 33, 19.5, 33, "git push")
    draw_arrow(36.5, 33, 39.5, 33, "triggers")
    draw_arrow(56.5, 33, 59.5, 33, "gates passed")
    draw_arrow(76.5, 33, 79.5, 33, "package jar")

    # Downward Arrow to Target Node
    draw_arrow(88, 21.5, 68, 16.5, "Ansible Deploy")
    draw_arrow(68, 21.5, 52, 16.5, "Container Run")

    plt.tight_layout()
    plt.savefig(output_path, dpi=300, bbox_inches='tight')
    plt.close()
    print(f"Diagram successfully saved to {output_path}")

if __name__ == "__main__":
    generate_architecture_diagram()
