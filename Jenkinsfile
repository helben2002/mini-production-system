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
        stage('Deploy Candidate & Health Check') {
            agent { label 'windows && docker' }

            steps {
                bat '''
                    docker rm -f inventory-candidate 2>NUL
                    docker run -d --name inventory-candidate -p 8083:8081 inventory-service:1.0
                '''

                retry(10) {
                    sleep time: 3, unit: 'SECONDS'

                    bat '''
                        curl.exe --fail --silent --show-error http://localhost:8083/actuator/health/readiness
                    '''
                }
            }
        }
    }
}