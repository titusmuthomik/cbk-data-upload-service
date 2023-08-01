# https://hub.docker.com/_/gradle. If updating this version, update Jenkinsfile as well.
FROM gradle:7.3.1-jdk11 as clean_build
ARG ORG_GRADLE_PROJECT_artifactory_user
ARG ORG_GRADLE_PROJECT_artifactory_password
ARG ORG_GRADLE_PROJECT_artifactory_contextUrl=https://tala.jfrog.io/tala
ARG GRADLE_ARG
ARG GIT_REVISION=""
ARG APP_VERSION=""
ARG GRADLE_TARGETS="clean assemble -Dorg.gradle.jvmargs=-Xmx8g -Dkotlin.daemon.jvm.options=-Xmx6g"
ARG JAR_FILE="Springboot_Skeleton_Service-0.0.1-SNAPSHOT.jar"
COPY --chown=1000:1000 build.gradle settings.gradle ./
COPY --chown=1000:1000 ./src ./src
RUN gradle ${GRADLE_ARGS} ${GRADLE_TARGETS} --info

ENV VERSION_FILE=/version.env
RUN echo "GIT_REVISION=$GIT_REVISION APP_VERSION=$APP_VERSION" > "$VERSION_FILE" && \
    cat "$VERSION_FILE"

FROM openjdk:11-jre-slim

RUN apt update
RUN apt -y install zip curl procps

# Declare contain user variables
ARG CONTAINER_USER_NAME=springboot-skeleton
ARG CONTAINER_USER_ID=10001

# Create application user
RUN id -u $CONTAINER_USER_NAME 2>/dev/null || useradd --system --create-home --uid $CONTAINER_USER_ID \
    --gid 0 $CONTAINER_USER_NAME

WORKDIR /app
RUN chown -R springboot-skeleton:root /app/
ADD src/main/resources /app/resources
COPY --from=clean_build --chown=springboot-skeleton:root /version.env /version.env
COPY --from=clean_build --chown=springboot-skeleton:root /home/gradle/build/libs/${JAR_FILE} /app/${JAR_FILE}
COPY --from=clean_build --chown=springboot-skeleton:root /home/gradle/build/libs/${JAR_FILE} /home/gradle/${CONFIG_FILE} /home/gradle/src/main/resources/certs/ ./
EXPOSE 8080
# Set application user as default user
USER $CONTAINER_USER_NAME
ENTRYPOINT java -jar "Springboot_Skeleton_Service-0.0.1-SNAPSHOT.jar" $JAVA_OPTS
