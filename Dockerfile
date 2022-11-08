FROM --platform=linux/arm64 arm64v8/adoptopenjdk:15-jre-hotspot as stage-arm64
FROM --platform=linux/amd64 adoptopenjdk:15-jre-hotspot as stage-amd64

ARG TARGETARCH
# Select final stage based on TARGETARCH ARG
FROM stage-${TARGETARCH} as final

RUN mkdir /app

WORKDIR /app

ADD ./api/target/kosarica-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "kosarica-api-1.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "image-catalog-api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar image-catalog-api-1.0.0-SNAPSHOT.jar