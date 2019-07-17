FROM alpine:latest

RUN apk add --no-cache --update openjdk8-jre

WORKDIR /app

ENTRYPOINT exec java -XX:+UseG1GC -XX:NewRatio=1 -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 \
    -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap \
    -Djava.security.egd=file:/dev/./urandom \
    -cp .:agent.jar \
    -Dcom.sun.management.jmxremote.port=9999 \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Djava.awt.headless=true me.rotemfo.jmx.JMXApplication

EXPOSE 9999

ADD ./target/java-jmx-example-1.0.0-jar-with-dependencies.jar /app/agent.jar
