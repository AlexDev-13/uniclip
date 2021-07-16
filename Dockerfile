FROM openjdk:11

EXPOSE 8085

COPY ./build/libs/uniclip.jar /home/uniclip.jar

CMD ["java", "-jar", "/home/uniclip.jar"]