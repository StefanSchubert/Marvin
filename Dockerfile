# Base container with amazon corretto
FROM amazoncorretto:17.0.2-alpine3.15

# Copy executable jar. You need to replace the jar from the assets dir after a new build
# buildAndLaunchConatiner.sh will do it for you

COPY assets/ /

# nobody (99)
RUN chown -R 99:99 /opt ; \
    chmod 644 /opt/marvin-bot-1.0-SNAPSHOT.jar

# http port
EXPOSE 8042
# hazelcast communication
EXPOSE 5701

# Run as Nobody
USER nobody

WORKDIR /opt
ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/marvin-bot-1.0-SNAPSHOT.jar"]
