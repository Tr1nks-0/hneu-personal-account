#!groovy

pipeline {
    agent none
    stages {
        stage('Compile and Test') {
            agent {
                docker {
                    image 'fengzhou/java-node'
                }
            }
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Docker Build') {
            agent any
            steps {
                sh 'docker build ' +
                        '-t hneu-personal-account ' +
                        '-f ./hneu-personal-account-webapp/Dockerfile ' +
                        '--build-arg WORKDIR=/var/lib/jenkins/workspace/test .'
            }
        }

        stage('Docker Compose Down') {
            agent {
                docker {
                    image 'docker/compose'
                }
            }
            steps {
                sh 'docker-compose -f ./hneu-personal-account-deployment/docker/docker-compose.yml down'
            }
        }

        stage('Docker Compose Up') {
            agent any
            steps {
                sh 'docker-compose -f ./hneu-personal-account-deployment/docker/docker-compose.yml up -d'
            }
        }
    }
}