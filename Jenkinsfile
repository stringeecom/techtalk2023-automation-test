pipeline {
    agent { label 'automation-test' }
    stages {
        stage('Automation Test') {
            steps {
//                 bat "mvn -D clean test"
                sh "mvn -D clean test"
            }
            post {
                always {
                   publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/surefire-reports', reportFiles: 'emailable-report.html', reportName: 'Automation Test Analyzer', reportTitles: 'Automation Test Analyzer', useWrapperFileDirectly: true])
                }
            }
        }
    }
}