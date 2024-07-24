pipeline {
    agent any

    tools {
        // Maven va JDK o'rnatilganligini bildiradi
        maven 'Maven 3.8.1'
        jdk 'jdk-21'
    }

    environment {
        JAVA_HOME = "${tool 'jdk-21'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Kodni Git'dan yuklab olish
                git 'https://github.com/KomilovAzizbekk/barterly-backend.git'
            }
        }
        stage('Build') {
            steps {
                // Maven orqali build qilish
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                // Maven orqali testlarni ishga tushirish
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Ilovani joylashtirish (deploy) jarayonlari
                sh 'tmux new-session -d -s barterly "java -jar target/barterly-backend-0.0.1-SNAPSHOT.jar"'
            }
        }
    }

    post {
        success {
            echo 'Build and Test Successful'
        }
        failure {
            echo 'Build or Test Failed'
        }
    }
}
