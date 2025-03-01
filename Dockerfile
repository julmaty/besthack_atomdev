# Stage 1: Сборка приложения с помощью Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
# Копируем файлы проекта
COPY pom.xml .
COPY src ./src
# Собираем приложение (JAR файл будет создан в target/)
RUN mvn clean package -DskipTests

# Stage 2: Запуск приложения
FROM openjdk:17-jdk
WORKDIR /app
# Копируем собранный JAR файл из builder'а
COPY --from=builder /app/target/besthack_atomdev.jar app.jar
# Открываем порт, на котором будет работать приложение
EXPOSE 8080
# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]