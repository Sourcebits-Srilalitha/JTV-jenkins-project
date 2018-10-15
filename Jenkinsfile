
node {
 echo I am in jenkins file
 
   stage('SCM Checkout') {
    echo maven Package
      git 'https://github.com/Sourcebits-Srilalitha/JTV-jenkins-project.git'
     
   }
   stage('Compile-Package') {  
     if (isUnix()) {
         sh 'mvn package'
     } else {
        call mvn clean
      echo mvnClean
         bat (call mvn package)
       echo mavenPackage
     }
   }
   
}
