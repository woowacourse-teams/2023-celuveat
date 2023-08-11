FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY ./build/libs/celuveat-0.0.1-SNAPSHOT.jar /app/celuveat.jar

ENV TZ=Asia/Seoul

CMD ["java", "-jar", "celuveat.jar"]
