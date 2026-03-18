FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN groupadd --system spring \
    && useradd --system --gid spring --create-home spring

COPY target/product-purchase-service-0.0.1-SNAPSHOT.jar app.jar

USER spring
EXPOSE 8337

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
