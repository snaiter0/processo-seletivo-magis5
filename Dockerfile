#build
FROM maven:3.9.9-amazoncorretto-21-al2023 AS build

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

#run
FROM amazoncorretto:21.0.5
WORKDIR /app


COPY --from=build ./build/target/*.jar ./logisticaapi.jar

EXPOSE 8080

ENV DATASOURCE_URL='jdbc:mysql://mysql-logistica:3306/logistica'
ENV DATASOURCE_USERNAME='test'
ENV DATASOURCE_PASSWORD='test'

ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar logisticaapi.jar