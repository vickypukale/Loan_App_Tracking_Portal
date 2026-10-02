pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK21'
    }

    environment {
        APP_ENV = "${params.ENVIRONMENT ?: 'dev'}"
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'qa', 'prod'], description: 'Deployment Environment')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                // To deliberately introduce a failure, you would commit a failing test.
                // This step runs the unit tests and selenium suite.
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("loanapp:${env.BUILD_ID}")
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying to ${APP_ENV} environment..."
                // Simulation of deployment
                sh 'echo Deploying to Tomcat/Nginx/Docker...'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            echo 'Build and Deployment Successful!'
        }
        failure {
            echo 'Build Failed. Stopping pipeline.'
        }
    }
}
