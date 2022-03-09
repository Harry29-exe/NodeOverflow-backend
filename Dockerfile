FROM openjdk:11


ADD target/NodeOverflow-backend-0.0.1-SNAPSHOT.jar .

EXPOSE 8080
CMD java -jar NodeOverflow-backend-0.0.1-SNAPSHOT.jar