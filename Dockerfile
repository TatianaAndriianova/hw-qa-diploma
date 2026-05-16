FROM eclipse-temurin:11-jre-jammy
WORKDIR /app
COPY artifacts/aqa-shop.jar aqa-shop.jar
ENTRYPOINT ["java", "-jar", "aqa-shop.jar"]