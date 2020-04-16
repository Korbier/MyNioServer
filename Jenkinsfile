pipeline {
    agent { 
        docker { 
            image 'maven:alpine'
            reuseNode true 
        } 
    }
    stages {
        stage('dependencies') {
            steps {
                sh 'mvn dependency:go-offline'
            }
        }
        stage('build') {
            steps {
                sh 'mvn -B clean install'
            }
        }
    }
}