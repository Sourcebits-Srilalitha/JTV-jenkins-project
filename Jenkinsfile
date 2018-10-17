  pipeline {
    agent any    
  
    stages {   
      stage('check'){
         steps {
                script {
                   echo 'Pulling...' + env.BRANCH_NAME
                  
                 //echo 'Pulling complete env...' + env.getEnvironment() 
                  echo 'Pulling... US Branch' + ${env.BRANCH}
                  
                  
                    def mvnHome = tool 'Maven 3.5.4'
                   //bat(/"${mvnHome}\bin\mvn" -Dintegration-tests.skip=true clean package/)
                   bat(/"${mvnHome}\bin\mvn"/)
                        def pom = readMavenPom file: 'pom.xml'
                        print pom.version
                }
         }
       
      }
      
  /*  stage('Build') {      
      steps {
        print 'Before Build...'  
        bat 'mvn clean install'
        print 'After Build...'
      }     
     }
      
      //check status
      
       stage('Development deploy approval and deployment') {
            steps {
                script {
                    if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
                        timeout(time: 3, unit: 'MINUTES') {
                            // you can use the commented line if u have specific user group who CAN ONLY approve
                            //input message:'Approve deployment?', submitter: 'it-ops'
                            input message: 'Approve deployment?'
                        }
                        timeout(time: 2, unit: 'MINUTES') {   
                            if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
                                echo 'the application is deployed !'
                            } else {
                                error 'the application is not  deployed as development version is null!'
                            }

                        }
                    }
                }
            }
        }
      
      stage('Deploy') {      
      steps {
       script {
          print 'Before Copy'          
         
          if (isUnix()) {
                                   sh "'pwd' "
                               } else {
              bat 'xcopy "C:\\Users\\Srilalitha Jana\\.m2\\repository\\JTV\\JTV-Backend\\1.6.8-SNAPSHOT\\*.war"  "C:\\JTV-jenkins\\"'
                                  
                               }

         
          //copy 'C:/Users/Srilalitha Jana\\.m2\\repository\\JTV\\JTV-Backend\\1.6.8-SNAPSHOT\\*.war C:\\JTV-jenkins\\'
          //xcopy 'C:\\Users\\Srilalitha Jana\\.m2\\repository\\JTV\\JTV-Backend\\1.6.8-SNAPSHOT\\*.war'  'C:\\JTV-jenkins\\'
           // sshagent(['tomcat-jenkins']) {
         //       sh 'scp -o StrictHostKeyChecking=no target/*.war ec2-user@34.227.59.124:/var/lib/tomcat8/webapps'
        //    }
          print 'After Copy...'       
       }         
      }     
     }  */ 
      
    } 
  }
