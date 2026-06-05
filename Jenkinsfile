pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'develop', description: 'Ветка для сборки')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: params.BRANCH_NAME, url: 'https://github.com/sslanss/practice.git'
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
                expression { params.BRANCH_NAME == 'develop' }
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
    }

    post {
        success {
            echo 'Build successful! Publishing artifact...'
            bat 'xcopy /Y shop-app\\target\\*jar-with-dependencies.jar C:\\artifacts\\'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}