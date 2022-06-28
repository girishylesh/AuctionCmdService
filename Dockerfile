FROM openjdk:11.0.7-jre-slim
EXPOSE 8080
ADD target/auction-cmd-service-0.0.1-SNAPSHOT.jar auction-cmd-service.jar 
ENTRYPOINT ["java","-jar","/auction-cmd-service.jar"]