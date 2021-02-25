pipeline {
    agent any
    stages {
        stage ('Install Stage') {
            steps {
                withMaven(maven : 'Maven 3.6.3') {
                    sh'mvn clean install -DskipTests'
                }
            }
        }
        stage ('Testing Stage') {
            steps {
                withMaven(maven : 'Maven 3.6.3') {
                    sh'mvn test -DsuiteXmlFile=testng.xml -Dbrowser=$browser'
                }
            }
            post {
                        always {
                            publishHTML ([
                                    allowMissing: false,
                                    alwaysLinkToLastBuild: true,
                                    keepAll: false,
                                    reportDir: '/Users/anastasia/.jenkins/workspace/Selenium/target/surefire-reports',
                                    reportFiles: 'index.html',
                                    reportName: 'HTML Report',
                                    reportTitles: ''
                                ])
                        }
                    }
        }

    }
}