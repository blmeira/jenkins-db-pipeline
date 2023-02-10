pipeline {
    agent {
        label 'docker-host'
    }
    options {
        disableConcurrentBuilds()
        disableResume()
    }

    parameters {
        string(name: 'ENVIRONMENT_NAME', trim: true)
        password(defaultValue: '', description: 'Password to use for MySQL container - root user', name: 'DB_PASSWORD')
        string(name: 'MYSQL_PORT', trim: true)
        choice(name: 'DB_ENGINE', choices: ['mysql', 'postreSQL', 'oracleXE'])

        booleanParam(name: 'SKIP_STEP_1', defaultValue: false, description: 'STEP 1 - RE-CREATE DOCKER IMAGE')
    }

    stages {
        stage('Validate environment') {
            steps {
              if (!params.ENVIRONMENT_NAME?.trim()){
                error "The environment name must not be empty"
              }
              if (params.MYSQL_PORT < 1 && params.MYSQL_PORT > 65535){
                error "The port must be between 1 and 65535"
              }
              if (!params.DB_PASSWORD?.trim()){
                error "The password must not be empty"
              }
            }
        }
        stage('Checkout GIT repository') {
            steps {
              git branch: 'master', credentialsId: '21f01d09-06da9cc35103', url: 'git@mysecret-nonexistent-repo/jenkins.git'
            }
        }
        stage('Create latest Docker image') {
            steps {
              script {
                if (!params.SKIP_STEP_1){
                    echo "Creating docker image with name $params.ENVIRONMENT_NAME using port: $params.MYSQL_PORT"

                    def image = getDbEngineImage(params.DB_ENGINE)

                    sh """
                    docker build pipelines/ -t $params.ENVIRONMENT_NAME:latest --build-arg DB_IMAGE=${image} --build-arg DB_ENGINE=${params.DB_ENGINE}
                    """
                } else{
                    echo "Skipping STEP1"
                }
              }
            }
        }
        stage('Start new container using latest image and create user') {
            steps {
              script {

                def dateTime = (sh(script: "date +%Y%m%d%H%M%S", returnStdout: true).trim())
                def containerName = "${params.ENVIRONMENT_NAME}_${dateTime}"
                def passEnvKey = getPasswordEnvKey(params.DB_ENGINE)
                def dbPort = getDbPort(params.DB_ENGINE)

                sh """
                docker run -d --name ${containerName} --rm -e ${passEnvKey}=${params.DB_PASSWORD} -p ${params.MYSQL_PORT}:${dbPort} ${params.ENVIRONMENT_NAME}:latest
                """

                echo "Docker container created: $containerName"

              }
            }
        }
    }
}

static def getDbEngineImage(dbEngine) {
    switch(dbEngine) {
       case "mysql":
         return "mysql"
       case "postreSQL":
         return "postgres"
       case "oracleXE":
         return "registry.oracle.com/database/express"
    }
}

static def getPasswordEnvKey(dbEngine) {
    switch(dbEngine) {
       case "mysql":
         return "MYSQL_ROOT_PASSWORD"
       case "postreSQL":
         return "POSTGRES_PASSWORD"
       case "oracleXE":
         return "ORACLE_PWD"
    }
}

static def getDbPort(dbEngine) {
    switch(dbEngine) {
       case "mysql":
         return "3306"
       case "postreSQL":
         return "5432"
       case "oracleXE":
         return "5500"
    }
}