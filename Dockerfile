FROM isahl/openjdk17:amd64
COPY target/Portal-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","//Portal-0.0.1-SNAPSHOT.jar"]