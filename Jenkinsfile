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
            steps {
                bat 'mvn test -pl shop-core'
            }
        }

        stage('Checkstyle') {
            when {
                branch 'develop'
            }
            steps {
                bat 'mvn checkstyle:check -pl shop-core -Dcheckstyle.failOnViolation=false'
            }
        }

        stage('Coverage') {
            steps {
                bat 'mvn org.jacoco:jacoco-maven-plugin:report -pl shop-core'
            }
        }

        stage('Coverage Check') {
            steps {
                bat 'mvn verify -pl shop-core -DskipTests'
            }
        }

        stage('Install') {
            steps {
                bat 'mvn install -DskipTests'
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