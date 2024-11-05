pipeline {
    agent any

    parameters {
        string(name: 'IMG_MAVEN', defaultValue: 'tools/maven3/apache-maven-3.6.3-jdk1.11.34_101:1.1.4', description: 'Maven Image Version')
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
        skipStagesAfterUnstable()
    }

    stages {
        stage('PreBuild: CleanWorkspace') {
            steps {
                cleanWs()
            }
        }
        stage('PreBuild: Clone') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '${branch}']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', noTags: false, reference: '', shallow: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'git_credential', url: 'https://github.com/iamrdb2f/TestAutomationExercise.git']]])
            }
        }
        stage('Build: Launch tests') {
            agent {
                docker {
                    image '$IMG_MAVEN'
                    label 'generic-slave'
                    reuseNode true
                }
            }
            steps {

                sh 'mvn -P ALL test -Denv=bench -Dremote=yes'
                sh 'mvn allure:report'

            }
        }
    }
    post {
        success {
            zip zipFile: 'allure-report.zip', dir: pwd() + "/target/allure-results"
            emailext attachLog: true, attachmentsPattern: 'target/extent-reports/index.html, allure-report.zip',
                    body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                    to: "${recipients}",
                    subject: "[SUCCESSFUL][BENCH][ALL] Automatic tests result"
        }
        unsuccessful {
            zip zipFile: 'allure-report.zip', dir: pwd() + "/target/allure-results"
            emailext attachLog: true, attachmentsPattern: 'target/extent-reports/index.html, allure-report.zip',
                    body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                    to: "${recipients}",
                    subject: "[UNSUCCESSFUL][BENCH][ALL] Automatic tests result"
        }
    }
}