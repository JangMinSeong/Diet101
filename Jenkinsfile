def backendImage
def frontendImage
def modelImage

pipeline {
    agent any

    // environment {
    //     /*
    //     // 환경 변수 설정
    //     GIT_REGISTRY_CREDENTIALS = credentials('gitlab')
    //     DOCKER_REGISTRY_CREDENTIALS = credentials('docker')
    //     // GCP_SERVICE_ACCOUNT_JSON = credentials('GCP_SERVICE_ACCOUNT_JSON')
    //     BACK_IMAGE_NAME = "${env.BACK_IMAGE_NAME}"
    //     FRONT_IMAGE_NAME = "${env.FRONT_IMAGE_NAME}"
    //     MODEL_IMAGE_NAME = "${env.MODEL_IMAGE_NAME}"

    //     DATABASE_URL = "${env.DATABASE_URL}"
    //     HTTPS = "${env.HTTPS}"

        
    //     GOOGLE_CLIENT_ID = "${env.GOOGLE_CLIENT_ID}"
    //     GOOGLE_CLIENT_SECRET = "${env.GOOGLE_CLIENT_SECRET}"
    //     NAVER_CLIENT_ID = "${env.NAVER_CLIENT_ID}"
    //     NAVER_CLIENT_SECRET = "${env.NAVER_CLIENT_SECRET}"
    //     KAKAO_CLIENT_ID = "${env.KAKAO_CLIENT_ID}"
    //     KAKAO_CLIENT_SECRET = "${env.KAKAO_CLIENT_SECRET}"

    //     SECRET_KEY = "${env.SECRET_KEY}"
    //     SESSION_SECRET_KEY = "${env.SESSION_SECRET_KEY}"
    //     SMTP_PASSWORD = "${env.SMTP_PASSWORD}"
    //     SMTP_PORT = "${env.SMTP_PORT}"
    //     SMTP_SERVER = "${env.SMTP_SERVER}"
    //     SMTP_USERNAME = "${env.SMTP_USERNAME}"
    //     SSL_CRT_FILE = "${env.SSL_CRT_FILE}"
    //     SSL_KEY_FILE = "${env.SSL_KEY_FILE}"
        
    //     REACT_APP_API_BASE_URL = "${env.REACT_APP_API_BASE_URL}"
    //     AI_SERVER_URL = "${env.AI_SERVER_URL}"
    //     DOCKER_COMPOSE_FILE = "docker-compose.yml"
        
    //     *///
    // }
    
    stages {
        
        stage('Checkout') {
            steps {
                script {
                    checkout([$class: 'GitSCM', 
                        branches: [[name: '*/deploy']], 
                        userRemoteConfigs: [[
                            url: 'https://lab.ssafy.com/s10-ai-image-sub2/S10P22D101.git',
                            credentialsId: 'gitlab'
                        ]]
                    ])
                    //asdfasdfadsfasdf
                    // git credentialsId: 'gitlab', url: 'https://lab.ssafy.com/s10-ai-image-sub2/S10P22D101.git'
                }
            }
        }

        // stage('Build and Push the Back-end Docker Image') {
        //     steps {
        //         script {
        //             sh 'echo "Starting Build Back Docker Image"'
        //             dir('back') {
        //                 withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com') {

        //                     withCredentials([file(credentialsId: 'GCP_SERVICE_ACCOUNT_JSON', variable: 'GCP_SERVICE_ACCOUNT_JSON')]) {
        //                         sh 'cp $GCP_SERVICE_ACCOUNT_JSON ./google_service_key.json'
                               
        //                         backendImage = docker.build("${BACK_IMAGE_NAME}:${env.BUILD_NUMBER}", 
        //                             "--build-arg DATABASE_URL=${env.DATABASE_URL} " +
        //                             "--build-arg HTTPS=${env.HTTPS} " +
        //                             "--build-arg NAVER_CLIENT_ID=${env.NAVER_CLIENT_ID} " +
        //                             "--build-arg NAVER_CLIENT_SECRET=${env.NAVER_CLIENT_SECRET} " +
        //                             "--build-arg KAKAO_CLIENT_ID=${env.KAKAO_CLIENT_ID} " +
        //                             "--build-arg KAKAO_CLIENT_SECRET=${env.KAKAO_CLIENT_SECRET} " +
        //                             "--build-arg GOOGLE_CLIENT_ID=${env.GOOGLE_CLIENT_ID} " +
        //                             "--build-arg GOOGLE_CLIENT_SECRET=${env.GOOGLE_CLIENT_SECRET} " +
        //                             "--build-arg SMTP_PASSWORD=${env.SMTP_PASSWORD} " +
        //                             "--build-arg SMTP_PORT=${env.SMTP_PORT} " +
        //                             "--build-arg SMTP_SERVER=${env.SMTP_SERVER} " +
        //                             "--build-arg AI_SERVER_URL=${env.AI_SERVER_URL} " +
        //                             "--build-arg SMTP_USERNAME=${env.SMTP_USERNAME} " +
        //                             "--build-arg SSL_CRT_FILE=${env.SSL_CRT_FILE} " +
        //                             "--build-arg SSL_KEY_FILE=${env.SSL_KEY_FILE} .")

        //                         sh 'rm -f ./google_service_key.json' // 빌드 후 파일 삭제
                                

        //                         // Docker 빌드 결과 출력
        //                         if (backendImage != 0) {
        //                             echo "Docker build succeeded: ${BACK_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //                             docker.withRegistry('https://registry.hub.docker.com', 'docker') {
        //                                 backendImage.push()
        //                         }
        //                         // sh "docker run -p 8000:8000 ${BACK_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //                         } else {
        //                             error "Docker build failed"
        //                         }
                                
        //                     }
                            
                             
        //                 }
        //             }
        //         }
        //     }
            
        // }    
        // stage('Build and Push the Front-end Docker Image') {
        //     steps {
        //         script {
        //             sh 'echo "Starting Build Front Docker Image"'
        //             dir('front') {
        //                 withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com') {
                            
        //                      frontendImage = docker.build("${FRONT_IMAGE_NAME}:${env.BUILD_NUMBER}",
        //                       "--build-arg REACT_APP_API_BASE_URL=${env.REACT_APP_API_BASE_URL} .")
        //                     // Docker 빌드 결과 출력
        //                     if (frontendImage != 0) {
        //                         echo "Docker build succeeded: ${FRONT_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //                         docker.withRegistry('https://registry.hub.docker.com', 'docker') {
        //                             frontendImage.push()
        //                     }
        //                     } else {
        //                         error "Docker build failed"
        //                     }
        //                 }
        //             }
        //         }
        //     }
            
        // }
        // stage('Build and Push the Ai-model Image') {
        //     steps {
        //         script {
        //             sh 'echo "Starting Build Ai-model Docker Image"'
        //             dir('back/app/ai_models/face') {
        //                 withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com') {
                            
        //                      modelImage = docker.build("${MODEL_IMAGE_NAME}:${env.BUILD_NUMBER}")
        //                     // Docker 빌드 결과 출력
        //                     if (modelImage != 0) {
        //                         echo "Docker build succeeded: ${MODEL_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //                         docker.withRegistry('https://registry.hub.docker.com', 'docker') {
        //                             modelImage.push()
        //                     }
        //                     } else {
        //                         error "Docker build failed"
        //                     }
        //                 }
        //             }
        //         }
        //     }
            
        // }     
    
        // stage('Deploy') {
        //     steps {
        //         script {
        //             sh "docker-compose -f ${DOCKER_COMPOSE_FILE} pull"
        //             sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
        //         }
        //     }
        // }
    }
}
