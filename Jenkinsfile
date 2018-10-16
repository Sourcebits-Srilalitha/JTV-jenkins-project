  pipeline {
    agent any    
    stages {    
    stage('Build') {      
      steps {
        print 'Build...' + env.BRANCH_NAME    
        bat 'mvn clean install'
        print 'After Build...'
      }
     
     }
    }  
  }
