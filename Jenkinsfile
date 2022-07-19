pipeline {
    agent any
    stages {
        stage ('Build Backend'){
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests'){
            steps {
                sh 'mvn test'
            }
        }
        stage ('Sonar Analysis'){
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL'){
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=d1f8656904f8a2cd6f7d4db624b62962d88ae4b5 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/TaskBackendApplication.java"
                }
            }
        }
        stage ('Quality Gate'){
            steps {
                sleep(5)
                timeout(time: 1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }                
            }
        }
        stage ('Deploy Backend'){
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'                
            }
        }
        stage ('API Test'){
            steps {
                dir('api-test') {
                    git 'https://github.com/Rtrojack/tasks-api-test.git'
                    sh 'mvn test'
                }                
            }
        }
        stage ('Deploy Frontend'){
            steps {
                dir('frontend'){
                    git 'https://github.com/Rtrojack/tasks-frontend.git'
                    sh 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'                
                }
            }
        }
        stage ('Functional Test'){
            steps {
                dir('functional-test') {
                    git 'https://github.com/Rtrojack/tasks-functional-test.git'
                    sh 'mvn test'
                }                
            }
        }
    }
}
