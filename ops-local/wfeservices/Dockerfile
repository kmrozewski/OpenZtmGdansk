FROM openjdk:8-jre-alpine

ADD wfeservices.jar wfeservices.jar
ADD config.yml config.yml

CMD java -jar wfeservices.jar --spring.config.location="file:/config.yml"

EXPOSE 8080
