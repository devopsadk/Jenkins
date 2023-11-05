// Jenkins declarative pipeline with 2 stages executing on slave node demo

pipeline {
    agent { label 'RHEL-SLAVE1' }
    stages{
        stage('build') {
            steps{
                echo "HELLO JENKINS"
            }
        }
        stage('stage 2') {
            steps{
                sh 'touch test123.txt'
            }
        }
    }
    
}
