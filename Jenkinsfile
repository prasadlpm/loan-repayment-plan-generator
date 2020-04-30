pipeline {
    agent any
    
    stages {
        stage("Git clone") {
            steps {
                //git clone  
                deleteDir()
                echo 'Clone the latest code from the code-base'
                sh 'git clone https://github.com/prasadlpm/loan-repayment-plan-generator.git'       
            }
            
        }
        stage("Testcases") {
            steps {
                //Execute testcases 
                echo 'Execute test cases'
                dir("loan-repayment-plan-generator"){
                    sh 'mvn clean test' 
                }               
            }
            
        }
        stage("Maven Build") {
            steps {
                echo 'Execute Maven Build'
                dir("loan-repayment-plan-generator"){
                    sh 'mvn clean package'
                }
            }
            
        }
        stage("Docker Build") {            
            steps {
                echo 'Execute Docker Build'
                dir("loan-repayment-plan-generator"){
                    sh "docker build -t loan-repayment-plan-generator:\"${env.BUILD_NUMBER}\" . "
                    docker push prasadlpm/lendico:version1
                    echo "Check the Docker image"
                    sh 'docker images'
                }
            }
            
        }
        stage("Deployment") {
            steps {
                dir("loan-repayment-plan-generator"){
                    sh "docker run -d -p 80:8080 loan-repayment-plan-generator:\"${env.BUILD_NUMBER}\""
                }
            }            
        }
      
    }//end stages
}