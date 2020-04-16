pipeline {
    agent { docker { image 'maven:3.6.3-jdk-11-openj9' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}