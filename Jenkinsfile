pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                bat 'mvn compile test-compile'
            }
        }

        stage('Test') {
            when {
                branch 'feature/*'
            }
            steps {
                bat 'mvn test'
            }
        }

        stage('Checkstyle') {
            when {
                branch 'develop'
            }
            steps {
                bat 'mvn checkstyle:check'
            }
        }

        stage('Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }

        stage('Install') {
            steps {
                bat 'mvn install -DskipTests'
            }
        }

        stage('Coverage Check') {
            steps {
                bat 'mvn jacoco:check'
            }
        }

        stage('Publish') {
            steps {
                bat 'xcopy /Y shop-app\\target\\*jar-with-dependencies.jar C:\\artifacts\\'
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed!'
        }
        success {
            echo 'Build successful!'
        }
    }
}