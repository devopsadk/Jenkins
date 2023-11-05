node{
    // scripted pipeline  node without any stage
    echo "Hello Jenkins"
}

----------

node {
    // scripted pipeline with stage
    stage('GIT checkout'){
        echo "Hello"
    }
}

-----------

node inside node

node{
    stage('GIT CHECKOUT'){
        node{
            echo "Hello Jenkins"
        }
    }

}

EX :

node('rhel-slave1'){
    stage('build'){
        node('rhel-slave1')
            sh 'mvn -v'
    }

}


----------------------------

Agent any usage

pipeline{
    agent any 
    stages{
        stage('GIT Checkout'){
            steps{
                echo "Hello Jenkins"
            }
        }
    }
}


--------------------

Top level agents and stage level agents

pipeline{
    agent any
    stages{
        stage('build'){
            agent any
            steps{
                echo "Hello Jenkins"
            }
        }
    }
}


Agent none usage
----------

pipeline{
    agent none
    stages{
        stage('test'){
            agent {
                label 'rhel-slave1'
            }
            steps{
                sh 'mvn -v'
            }
        }
    }
}

-----------

stages on different nodes 

-------------

pipeline{
    agent none
    stages{
        stage('INIT'){
            agent {
                label 'rhel-slave1'
                        }
            steps{
                echo " Hello Jenkins"
            }
        }
      stage('build'){
        agent{
            label 'ubunt-slave2'
        }
        steps{
            echo "Hello Jenkins from slave2"
        }
      }
    }
}

------------------------

Define customWorkspace

--------------------------

// I am defining workspace at top level for slave 1

pipeline{
    agent{
        node{
            label 'rhel-slave1'
            customWorkspace '/home/ec2-user/testdir/'
        }
        stages{
            stage('init'){
                steps{
                    echo "Hello Jenkins"
                }
            }
        }
    }
}


// I am defining custom workspace at stage level

pipeline{
    agent none 
    stages{
        stage('init'){
            agent{
                node{
                    label 'rhel-slave1'
                    customWorkspace '/home/ec2-user/test123/'
                }
            }
                steps {
                    sh 'touch f1'
                }
                }
            }
        }
    
-------------------------------------------

Defining dir()

-------------------$_COOKIE

pipeline{
    agent none 
    stages{
        stage('init'){
            agent{
                node{
                    label 'rhel-slave1'
                    customWorkspace '/home/ec2-user/test123/'
                }
            }
                steps{
                    dir("/home/ec2-user/dir1/"){
                    sh 'touch f1'
                }
            }
        }
    }
}

---------------------------------------------$_COOKIE
post
The post section defines one or more additional steps that are run upon the completion of a Pipeline’s or stage’s run
 post can support any of the following post-condition blocks: always, changed, fixed, regression, aborted, failure, success, unstable, unsuccessful, and cleanup.

 1)always
Run the steps in the post section regardless of the completion status of the Pipeline’s or stage’s run
--------------------------------------

pipeline{
    agent any
    stages{
        stage('init'){
            steps{
                echo "Hello Jenkins"
            }
        }
    }
        post{
            always{
                echo "THIS IS POST ALWAYS MESSAGE"
            }
        }
    }

--------------------------
2) Changed 

pipeline{
    agent any
    stages{
        stage('init'){
            steps{
                echo "Hello Jenkins"
                //script{
                    // currentBuild.result='UNSTABLE'
                //}
            }
        }
    }
        post{
            changed{
                echo "POST >> Changed"
            }
        }
    }

    //// Run the code uncommenting script section




    pipeline{
    agent any
    stages{
        stage('init'){
            steps{
                echo "Hello Jenkins"
                script{
                    currentBuild.result='UNSTABLE'
                }
            }
        }
    }
        post{
            changed{
                echo "POST >> Changed"
            }
        }
    }

    --------------------
    Fixed
    ------------
    Only run the steps in post if the current Pipeline’s run is successful and the previous run failed or was unstable


     pipeline{
    agent any
    stages{
        stage('init'){
            steps{
                echo "Hello Jenkins"
                //script{
                  //  currentBuild.result='UNSTABLE'
                //}
            }
        }
    }
        post{
            changed{
                echo "POST >> Fixed"
            }
        }
    }

    ----------------------------------
Regression
Only run the steps in post if the current Pipeline’s or status is failure, unstable, or aborted and the previous run was successful.
---------------


pipeline{
    agent any
    stages{
        stage('init'){
            steps{
                echo "Hello Jenkin"  
                script{
                    currentBuild.result='UNSTABLE'
                }
            }
        }
    }
        post{
            regression{
                echo "POST >> Regression is called"
            }
        }
    }

--------------

Aborted 
Only run the steps in post if the current Pipeline’s run has an "aborted" status, usually due to the Pipeline being manually aborted. This is typically denoted by gray in the web UI

pipeline{
    agent any
    stages{
        stage('init'){
            steps{
                echo "Hello Jenkin"  
                script{
                    currentBuild.result='ABORTED'
                }
            }
        }
    }
        post{
            aborted{
                echo "POST >> Aborted is called"
            }
        }
    }

-----------------------------------------------------------------

Success / Failure / Always

pipeline{
    agent any
    stages{
        stage('init'){
            steps{
                echo "Hello Jenkin"  
                script{
                    currentBuild.result='ABORTED'
                }
            }
        }
    }
        post{
            aborted{
                echo "POST >> Aborted is called"
            }
            success{
                echo "POST >> SUCCESS is called"
            }
            failure{
                echo "POST >> Failure is called"
            }
            always{
                echo "POST >> Always is called"
            }
            unstable{
                echo "POST >> Unstable is called"
            }
            unsuccessful{
                echo "POST>> unsuccessful is called"
            }
        }
    }