pipeline {
    agent any
    stages {
        stage ('Install Stage') {
            steps {
                maven(maven : 'Maven 3.6.3') {
                    bat'mvn clean install -DskipTests'
                }
            }
        }
        stage ('Testing Stage') {
            steps {
                maven(maven : 'Maven 3.6.3') {
                    bat'mvn test -DsuiteXmlFile=testng.xml -Dbrowser=$browser'
                }
            }
        }
    }
}