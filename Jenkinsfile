pipeline {
    agent { docker { image 'maven:alpine' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn -B clean install'
            }
        }
    }
}