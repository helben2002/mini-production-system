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
                }
            }
            post {
                always {
                    junit 'inventory-service/target/surfire-reports/*.xml'
                }
            }
        }
    }
}