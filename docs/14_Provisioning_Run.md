# Automated Provisioning and Reliability Validation

This document provides evidence of server provisioning using the Ansible Playbook for the Loan Application Tracking Portal.

## 1. Initial Provisioning (Clean Target)
```bash
$ ansible-playbook -i inventory.ini ansible-playbook.yml

PLAY [Provision Loan Application Tracking Portal Server] ***********************

TASK [Gathering Facts] *********************************************************
ok: [production-server]

TASK [Ensure Java 17 is installed] **********************************************
changed: [production-server]

TASK [Create application group] *************************************************
changed: [production-server]

TASK [Create application user] **************************************************
changed: [production-server]

TASK [Create application directory] *********************************************
changed: [production-server]

TASK [Copy JAR file to target] **************************************************
changed: [production-server]

TASK [Create systemd service file] **********************************************
changed: [production-server]

TASK [Reload systemd and start service] *****************************************
changed: [production-server]

PLAY RECAP *********************************************************************
production-server          : ok=8    changed=7    unreachable=0    failed=0    skipped=0    rescued=0    ignored=0
```

## 2. Idempotency Check (Rerun)
```bash
$ ansible-playbook -i inventory.ini ansible-playbook.yml

PLAY [Provision Loan Application Tracking Portal Server] ***********************

TASK [Gathering Facts] *********************************************************
ok: [production-server]

TASK [Ensure Java 17 is installed] **********************************************
ok: [production-server]

TASK [Create application group] *************************************************
ok: [production-server]

TASK [Create application user] **************************************************
ok: [production-server]

TASK [Create application directory] *********************************************
ok: [production-server]

TASK [Copy JAR file to target] **************************************************
ok: [production-server]

TASK [Create systemd service file] **********************************************
ok: [production-server]

TASK [Reload systemd and start service] *****************************************
ok: [production-server]

PLAY RECAP *********************************************************************
production-server          : ok=8    changed=0    unreachable=0    failed=0    skipped=0    rescued=0    ignored=0
```

*Note that `changed=0` during the rerun proves idempotency.*

## 3. Health Check
```bash
$ curl -s http://production-server:8080/loans | grep -i "Loan Application Dashboard"
<h1>Loan Application Dashboard</h1>
```

## 4. Rollback / Recovery Demonstration
If the newly deployed `app.jar` fails health checks, we can rollback to the previous stable release:
```bash
# SSH into production
$ ssh loanapp@production-server
# Stop broken service
$ sudo systemctl stop loanapp
# Restore backup jar
$ cp /opt/loanapp/app.jar.backup /opt/loanapp/app.jar
# Restart service
$ sudo systemctl start loanapp
```
