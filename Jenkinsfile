pipeline {
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
    }
    
    agent { 
        docker { 
            image 'maven:alpine'
            reuseNode true 
        } 
    }
    
    triggers {
        cron('H 23 * * *')        
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