FROM openjdk:8-jdk-alpine
COPY target/kg-extract-1.0.jar /opt
ENV LANG C.UTF-8
EXPOSE 6215
WORKDIR /opt
CMD ["java","-jar","-Duser.timezone=GMT+08","kg-extract-1.0.jar"]
