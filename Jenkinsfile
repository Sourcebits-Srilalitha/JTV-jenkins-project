
node (){
   echo 'I am in jenkins file'
   stage ('Build') {
 
    git url: 'https://github.com/Sourcebits-Srilalitha/JTV-jenkins-project'
 
   
      echo 'Before build'
      // Run the maven build
      //   sh "mvn clean install" //unix
       bat 'mvn clean install'
      
        echo 'After build'
 
    
  }
}
