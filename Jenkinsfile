
node {
 
   stage('SCM Checkout') {
      git 'https://github.com/Sourcebits-Srilalitha/JTV-jenkins-project.git'
     
   }
   stage('Compile-Package') {  
     if (isUnix()) {
         sh 'mvn package'
     } else {
         bat (mvn package)
     }
   }
   
}
