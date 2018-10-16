  pipeline {
    agent any
    print 'First Line'
    stages {
       print 'Stages'
    stage('Build') {
     print 'Build...' + env.BRANCH_NAME     
      steps {
        bat 'mvn clean install'
        print 'After Build...'
      }
     
     }
    }  
  }
