pipeline {
    agent any

    tools {
        jdk 'jdk-25.0.4.1+1'
    }

    stages {
        stage('Build & Test') {
            steps {
                dir('inventory-service') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean verify'
                }
            }
        }
    }
}