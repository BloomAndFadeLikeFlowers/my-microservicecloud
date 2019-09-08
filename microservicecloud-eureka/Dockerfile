FROM java:8
ADD ./target/*.jar app.jar
RUN touch app.jar
ENTRYPOINT ["java","-jar","app.jar"]
