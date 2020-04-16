pipeline {
    agent { 
        docker { 
            image 'maven:alpine'
            args '-v /home/maven:/var/maven'
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