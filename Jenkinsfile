  pipeline {   
    agent any
    // global env variables
    environment {
        EMAIL_RECIPIENTS = 'srilalitha.jana@sourcebits.com'
    }
  
    stages {   
      stage('check'){
         steps {
                script {
                       //Works only with multi branch
                       echo 'Pulling...' + env.BRANCH_NAME
                       echo  'versionNumber ...' + env.BUILD_NUMBER;
                       //check Unix env
                       echo 'Unix env ... ' + isUnix()
                  
                        //read pom file
                        def pom = readMavenPom file: 'pom.xml'
                        print pom.version
                        echo 'Env:: ' + env.getEnvironment()
                        //maven
                        def mvn_version = tool 'M3'
                        print 'maven version ...' + mvn_version
                       }
                }       
         }   
/*      
    // Build a war file
      stage('Build') {      
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
                  }
                    print 'After Copy...'       
              }         
           }     
        }   */     
     } 
  }
