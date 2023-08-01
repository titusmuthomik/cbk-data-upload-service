# Changes to make to customize the project

This project has been build as a template so you will have to change a few things to customize it to the intended service. 
Below is a list of the required changes:

1. in [settings.gradle](../settings.gradle), update the application name on line 4
2. rename the package name in src/main and src/test from `co.tala.skeleton` to `co.tala.<your-service-name>`
3. update base package on [Application](../src/main/kotlin/co/tala/skeleton/Application.kt) file on line 16
4. update all necessary values in [application.yml](../src/main/resources/application.yml) file.
   * work with DevOps to generate auth certificates for your service and update the `atlas.auth` configs on line 45 - 50
5. update [logback.xml](../src/main/resources/logback.xml) file on line 9
6. on [Jenkinsfile](../Jenkinsfile) replace `springboot-skeleton` with your service name
7. make necessary changes to [Dockerfile](../Dockerfile) 
   * update jarfile name on line 10 and 41  
   * replace container name on line 25, 33, 35 - 37
8. update [./docker-compose](../docker-compose.yaml) file to replace `skeleton-service` with your service name
