FROM openjdk:11


ADD target/node-image-editor-backend-0.0.1-SNAPSHOT.jar .

EXPOSE 8880
CMD java -jar node-image-editor-backend-0.0.1-SNAPSHOT.jar