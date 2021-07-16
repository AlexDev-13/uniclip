FROM openjdk:11

COPY ./build/libs/uniclip.jar uniclip.jar
ENTRYPOINT ["java","-jar","/uniclip.jar"]
