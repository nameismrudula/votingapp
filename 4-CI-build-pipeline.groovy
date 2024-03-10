pipeline {   
  agent any
  environment {     
    DOCKERHUB_CREDENTIALS= credentials('docker')     
  }    
  stages {         
    stage("Git Checkout"){           
      steps{                
      //  git branch: 'main', credentialsId: 'github', url: 'https://github.com/vinayprakash893/docker-voting-aws-ec2-k8s.git'
        git branch: 'main', credentialsId: 'e3d5a50c-9cef-4db2-906f-a3e5bbae37b3', url: 'https://github.com/nameismrudula/votingapp.git'
        echo 'Git Checkout Completed'            
      }        
    }
    stage('Build Docker Image') {         
      steps{                
        sh 'docker build -t mruduladocker13/vote:latest vote/'
        sh 'docker build -t mruduladocker13/result:latest result/' 
        sh 'docker build -t mruduladocker13/worker:latest worker/'            
        echo 'Build Image Completed'                
      }           
    }
    stage('Login to Docker Hub') {         
      steps{                            
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'                 
        echo 'Login Completed'                
      }           
    }               
    stage('Push Image to Docker Hub') {         
      steps{                            
        sh 'docker push mruduladocker13/vote:latest'
        sh 'docker push mruduladocker13/result:latest'
        sh 'docker push mruduladocker13/worker:latest'
        echo 'Push Image Completed'       
      }           
    }      
  } 
  post{
    always {  
      sh 'docker logout'           
    }      
  }  
} 
