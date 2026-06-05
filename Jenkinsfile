pipeline {
    agent any

    tools {
        maven 'Maven'
    }

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
                bat 'mvn org.jacoco:jacoco-maven-plugin:report'
            }
        }

        stage('Install') {
            steps {
                bat 'mvn install -DskipTests'
            }
        }

        stage('Coverage Check') {
            steps {
                bat 'mvn org.jacoco:jacoco-maven-plugin:check -pl shop-core'
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