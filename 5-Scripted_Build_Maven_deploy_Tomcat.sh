// Pipeline script to build and deploy maven project -- Scripted

node("RHEL-SLAVE1") {
stage('GIT INIT'){
    checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'github_credentials', url: 'https://github.com/devopsadk/mavenproj.git']])
}
stage('build')
{
    sh 'mvn package'
}
stage('deploy')
{
    deploy adapters: [tomcat9(credentialsId: 'tomcat_credentials', path: '', url: 'http://3.90.26.107:8080')], contextPath: 'mytest1', onFailure: false, war: '**/*.war'
}
}