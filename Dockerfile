# Base container with amazon corretto
FROM amazoncorretto:11.0.8

# Copy sabis executable jar. You need to replace the jar from the assets dir after a new build
# copyjars.sh will do it for you
COPY assets/ /

# http port
EXPOSE 8042
# hazelcast communication
EXPOSE 5701

WORKDIR /opt
ENTRYPOINT ["/bin/java", "-jar", "/opt/marvin-bot-1.0-SNAPSHOT.jar"]

