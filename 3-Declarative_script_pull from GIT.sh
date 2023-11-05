// Scripted pulling from GIT used the snippet generator

pipeline{
    agent{label 'RHEL-SLAVE1'}
    stages{
        stage('GIT INIT'){
            steps{
            checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'github_credentials', url: 'https://github.com/devopsadk/Jenkins.git']])
        }
    }
}
}