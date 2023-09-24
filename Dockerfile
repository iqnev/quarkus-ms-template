## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:23.0.1.2-Final-java17 AS build

ARG GITHUB_READ_TOKEN
ARG GITHUB_USERNAME

ENV GITHUB_READ_TOKEN=$GITHUB_READ_TOKEN
ENV GITHUB_USERNAME=$GITHUB_USERNAME

COPY --chown=quarkus:quarkus mvnw /code/mvnw
COPY --chown=quarkus:quarkus .mvn /code/.mvn
COPY --chown=quarkus:quarkus pom.xml /code/
COPY --chown=quarkus:quarkus api-dtos/pom.xml /code/api-dtos/pom.xml
COPY --chown=quarkus:quarkus application/pom.xml /code/application/pom.xml
COPY --chown=quarkus:quarkus commons/pom.xml /code/commons/pom.xml
COPY --chown=quarkus:quarkus configuration/pom.xml /code/configuration/pom.xml
COPY --chown=quarkus:quarkus db-entities/pom.xml /code/db-entities/pom.xml
COPY --chown=quarkus:quarkus db-migration/pom.xml /code/db-migration/pom.xml
COPY --chown=quarkus:quarkus services/pom.xml /code/services/pom.xml
COPY --chown=quarkus:quarkus resources/pom.xml /code/resources/pom.xml

USER quarkus

WORKDIR /code

RUN ./mvnw --s "/code/.mvn/settings.xml" -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline

COPY api-dtos /code/api-dtos
COPY application /code/application
COPY commons /code/commons
COPY configuration /code/configuration
COPY db-entities /code/db-entities
COPY db-migration /code/db-migration
COPY services /code/services
COPY resources /code/resources

RUN ./mvnw --s "/code/.mvn/settings.xml" package -Pnative -DskipTests -Dquarkus.native.native-image-xmx=8g

## Stage 2 : create the docker final image
FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=build /code/application/target/*-runner /work/application

# set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
