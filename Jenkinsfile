#!groovy
@Library('atlas_shared')

service = "springboot-skeleton"
helm_chart_version = "0.1.47"

pipeline {
    agent {
        kubernetes {
            label "build-springboot-skeleton-${BUILD_NUMBER}"
            defaultContainer 'jnlp'
            yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    some-label: "build-springboot-skeleton-${BUILD_NUMBER}"
spec:
  containers:
  - name: tools
    image: 004384079765.dkr.ecr.us-west-2.amazonaws.com/devops/jenkins-slave:v1.19
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket
  - name: deploy
    image: ${KUBECTL_HELM_RUNNER}
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket
  - name: deploy-mx
    image: ${KUBECTL_HELM_RUNNER}
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket
  - name: deploy-ph
    image: ${KUBECTL_HELM_RUNNER}
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket
  - name: terraform-ph
    image: ${TERRAFORM_12_RUNNER}
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket
  - name: trivy
    image: ${TRIVY_SCANNER}
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket
  - name: gradle
    image: gradle:6.5-jdk11
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket
  volumes:
  - name: docker-socket
    hostPath:
      path: /var/run/docker.sock
      type: File
"""
        }
    }
    environment {
        JFROG_ARTIFACTORY_CREDENTIALS = credentials('55bdcd8c-e944-41dc-a5cc-9c3aa5616bdc')
        GITHUB_ACCESS_TOKEN = credentials('3ba79c7b-80de-4765-9f6c-959d0c83c492')
        GITHUB_COMMON_CREDS = credentials('b14ed09f-ca8d-4fa9-853b-8c2e0288d438')
        VERACODE_API_ID = credentials('VERACODE_API_ID')
        VERACODE_SECRET_KEY = credentials('VERACODE_SECRET_KEY')
    }

    stages {
        stage('Abort old builds') {
            steps {
                abort_old_builds()
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run unit tests & Build Image') {
            parallel {
                stage("Run Unit Tests") {
                    when {
                        anyOf {
                            branch 'master'
                            branch 'develop'
                            branch 'PR-*'
                            branch 'release-*'
                        }
                    }
                    steps {
                        container('gradle') {
                            sh "export ORG_GRADLE_PROJECT_artifactory_user=$JFROG_ARTIFACTORY_CREDENTIALS_USR && export ORG_GRADLE_PROJECT_artifactory_password=$JFROG_ARTIFACTORY_CREDENTIALS_PSW && ./gradlew clean check --build-cache bootJar"
                            stash includes: 'build/libs/*.jar', name: 'targetfiles'
                        }
                    }
                    post {
                        failure {
                            container('tools') {
                                slack_notify_test_failures("${service}")
                            }
                        }
                    }
                }

                stage('Build Docker image') {
                    when {
                        anyOf {
                            branch 'master'
                            branch 'develop'
                            branch 'PR-*'
                            branch 'release-*'
                        }
                    }
                    steps {
                        container('tools') {
                            sh 'docker build --build-arg ORG_GRADLE_PROJECT_artifactory_user=$JFROG_ARTIFACTORY_CREDENTIALS_USR --build-arg ORG_GRADLE_PROJECT_artifactory_password=$JFROG_ARTIFACTORY_CREDENTIALS_PSW --build-arg=GIT_REVISION=$(git rev-parse HEAD) --build-arg=APP_VERSION=${BRANCH_NAME} -t="springboot-skeleton-service" .'
                        }
                    }
                    post {
                        failure {
                            container('tools') {
                                slack_notify_test_failures("${service}")
                            }
                        }
                    }
                }
            }
        }

        stage('Scan Docker images for Vulnerabilities') {
            when {
                anyOf {
                branch 'master'
                branch 'develop'
                branch 'PR-*'
                branch 'release-*'
                }
            }
            steps {
                container('trivy') {
                sh "trivy --clear-cache springboot-skeleton-service"
                sh "trivy --severity HIGH,CRITICAL,MEDIUM springboot-skeleton-service"
                }
            }
        }

        stage('Login to ECR') {
            steps {
                container('tools') {
                    sh '$(aws ecr get-login --no-include-email --region us-west-2)'
                }
            }
        }

        stage('Push Docker image to ECR') {
            when {
                anyOf {
                    branch 'master'
                    branch 'develop'
                    branch 'PR-*'
                    branch 'release-*'
                }
            }
            steps {
                container('tools') {
                    sh "docker tag springboot-skeleton-service 004384079765.dkr.ecr.us-west-2.amazonaws.com/springboot-skeleton-service:${BRANCH_NAME}-${GIT_COMMIT.take(10)}"
                    sh "docker push 004384079765.dkr.ecr.us-west-2.amazonaws.com/springboot-skeleton-service:${BRANCH_NAME}-${GIT_COMMIT.take(10)}"
                }
            }
        }

        stage('Terragrunt Apply in PH ') {
            when {
                anyOf {
                    // branch 'master'
                    branch 'develop'
                    branch 'PR-*'
                    // branch 'release-*'
                }
            }
            steps {
                container('terraform-ph') {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        run_terragrunt_all("terraform/ph/", "dist/ph/", env.BRANCH_NAME, "ph", "ap-southeast-1")
                    }
                }
            }
        }

        stage('Merge release branch') {
            when {
                expression {
                    env.BRANCH_NAME ==~ /release-\d+.\d+.\d+/
                }
            }
            steps {
                container('tools') {
                    script {
                        timeout(time: 24, unit: 'HOURS') {
                            input "Confirm merge ${env.BRANCH_NAME} to master"
                        }

                        repo_url = "${env.GIT_URL}"
                        repo_name = repo_url.replace("https://", "")

                        git url: "${repo_url}", credentialsId: 'b14ed09f-ca8d-4fa9-853b-8c2e0288d438', branch: 'master'

                        try {
                            sh "git config --global user.email 'gitbotuser@tala.com'"
                            sh "git config --global user.name 'talagitbotuser'"
                            sh "git merge origin/${env.BRANCH_NAME}"
                            sh "git push --repo https://$GITHUB_COMMON_CREDS_USR:$GITHUB_ACCESS_TOKEN@$repo_name"
                        } catch (Exception e) {
                            slack_notify_merge_failure("$service")
                            error "Merging release branch to master failed with error $e"
                        }

                        try {
                            sh "git remote set-url origin https://$GITHUB_COMMON_CREDS_USR:$GITHUB_ACCESS_TOKEN@$repo_name"
                            sh "git push origin --repo https://$GITHUB_COMMON_CREDS_USR:$GITHUB_ACCESS_TOKEN@$repo_name --delete ${env.BRANCH_NAME}"
                        } catch (Exception e) {
                            echo "Deleting release branch after merge to master failed"
                            slack_notify_merge_failure("$service")
                        }
                    }
                }
            }
        }

        stage('Submit Veracode Scan') {
            when {
                expression {
                    env.BRANCH_NAME == 'master'
                }
            }
            steps {
                container('gradle') {
                    submit_veracode_scan("${service}")
                }
            }
        }
    }

    post {
        success {
            container('tools') {
                slack_notify()
            }
        }
    }
}
