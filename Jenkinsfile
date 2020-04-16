pipeline {
    agent { 
        docker { 
            image 'maven:alpine'
            args '-v /home/maven:/root/.m2'
            reuseNode true 
        } 
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn -B clean install'
            }
        }
    }
}