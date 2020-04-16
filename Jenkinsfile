pipeline {
    agent { 
        label 'docker'
    }  
    stages {
        stage('build') {
            agent { 
                docker {
                    label 'docker'
                    image 'maven:3.3.3'
                } 
            }          
            steps {
                sh 'mvn --version'
            }
        }
    }
}