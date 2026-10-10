pipeline {
    agent any

    tools {
        jdk 'jdk25'
    }

    stages {
        stage('Build & Test') {
            steps {
                dir('inventory-service') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean verify'
                    sh 'ls -la target/surefire-reports/'
                }
            }
        }
    }
    post {
        always {
            dir('inventory-service') {
                junit 'target/surfire-reports/TEST-*.xml'
            }
        } 
    }
}