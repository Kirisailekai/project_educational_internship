# Этап сборки с Maven и JDK 23
FROM maven:3.9.6-openjdk-17-slim AS builder

# Устанавливаем рабочую директорию для сборки
WORKDIR /app

# Копируем pom.xml и загружаем зависимости (для кеширования слоев)
COPY pom.xml .

# Скачиваем зависимости (кешируем, если pom.xml не изменен)
RUN mvn dependency:go-offline

# Копируем исходный код в контейнер
COPY src ./src

# Собираем приложение
RUN mvn clean package -DskipTests

# Используем образ OpenJDK 23 для финального приложения
FROM openjdk:23-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR файл из первого этапа сборки
COPY --from=builder /app/target/*.jar app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
