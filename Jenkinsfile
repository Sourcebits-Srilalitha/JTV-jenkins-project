  pipeline {
    agent any    
    def developmentArtifactVersion = ''
    stages {    
    stage('Build') {      
      steps {
        print 'Build...' + env.BRANCH_NAME    
        bat 'mvn clean install'
        print 'After Build...'
        def pom = readMavenPom file: 'pom.xml'
           print pom.version
        
         // get the current development version
                        developmentArtifactVersion = "${pom.version}-${targetVersion}"
                        print pom.version
        
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
                            //
                            if (developmentArtifactVersion != null && !developmentArtifactVersion.isEmpty()) {
                                // replace it with your application name or make it easily loaded from pom.xml
                               // def jarName = "application-${developmentArtifactVersion}.war"
                                echo "the application is deploying" //${jarName}"
                                // NOTE : CREATE your deployemnt JOB, where it can take parameters whoch is the jar name to fetch from jenkins workspace
                              //  build job: 'ApplicationToDev', parameters: [[$class: 'StringParameterValue', name: 'jarName', value: jarName]]
                                echo 'the application is deployed !'
                            } else {
                                error 'the application is not  deployed as development version is null!'
                            }

                        }
                    }
                }
            }
        }
      
      
    }  
  }
