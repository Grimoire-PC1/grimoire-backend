FROM amazoncorretto:21-alpine-jdk

RUN apk update \
 && apk add --update ca-certificates \
 && apk add --update -t deps curl \
 && apk add --no-cache bash gawk sed grep bc coreutils vim wget curl tar tzdata \
 && apk del --purge deps \
 && rm /var/cache/apk/*

COPY target/grimoire-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-Xmx256m", "-jar", "/app.jar"]
