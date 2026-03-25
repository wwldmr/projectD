pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t projectd-admin:latest ./admin'
            }
        }
    }
}