def call() {

    pipeline {
        agent any

        parameters {
            booleanParam(
                name: 'RUN_TESTS',
                defaultValue: false,
                description: 'Run Tests'
            )
        }

        stages {

            stage('Clone Project') {
                steps {
                    git branch: 'main',
                        url: 'https://github.com/nithinbalu96/Amazon-Jenkins.git'
                }
            }

            stage('Clean') {
                steps {
                    sh 'mvn clean'
                }
            }

            stage('Compile') {
                steps {
                    sh 'mvn compile'
                }
            }

            stage('Test') {
                when {
                    expression {
                        return params.RUN_TESTS
                    }
                }
                steps {
                    sh 'mvn test'
                }
            }

            stage('Build and Install') {
                steps {
                    sh 'mvn install -DskipTests'
                }
            }
        }

        post {
            success {
                echo 'Amazon build successful!'
            }
            failure {
                echo 'Amazon build failed!'
            }
        }
    }
}
