// Jenkins script simple scripted demo

node('RHEL-SLAVE1') {
    stage('build') {
        // stage1
        echo "Hello Jenkins"
    }
    stage('second stage'){
        //stage2
        sh 'touch test123.txt'
    }
}