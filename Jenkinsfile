pipeline {
    agent none

    stages {
        stage('Build & Test') {
            agent { label 'linux && maven' }

            tools {
                jdk 'jdk25'
            }

            steps {
                checkout scm

                dir('inventory-service') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean verify'
                }

                stash name: 'inventory-artifacts',
                      includes: 'inventory-service/Dockerfile,inventory-service/target/*.jar'
            }

            post {
                always {
                    dir('inventory-service') {
                        junit testResults: 'target/surefire-reports/TEST-*.xml',
                              allowEmptyResults: true
                    }
                }
            }
        }

        stage('Docker Build') {
            agent { label 'windows && docker' }

            steps {
                unstash 'inventory-artifacts'

                dir('inventory-service') {
                    bat 'docker build -t inventory-service:1.0 .'
                }
            }
        }
    }
}