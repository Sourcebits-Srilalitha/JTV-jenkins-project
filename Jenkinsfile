  pipeline {
    agent any
    echo 'First Line'
    stages {
       echo 'Stages'
    stage('Build') {
     echo 'Build...' + env.BRANCH_NAME     
     bat 'mvn clean install'
     echo 'After Build...'
     }
    }  
  }
