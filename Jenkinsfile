
node {
 
   stage('SCM Checkout') {
      git 'https://github.com/Sourcebits-Srilalitha/JTV-jenkins-project'
     
   }
   stage('Compile-Package') {      
         sh 'mvn package'
   }
   
}
