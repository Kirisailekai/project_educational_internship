# Используем образ OpenJDK 23 для финального приложения
FROM openjdk:23-jdk-slim

# Устанавливаем рабочую директорию для финального приложения
WORKDIR /app

# Копируем собранный JAR файл в контейнер
COPY target/*.jar app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
