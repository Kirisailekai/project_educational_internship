# Используем официальный образ OpenJDK с Maven для сборки
FROM maven:3.9.6-openjdk-23-slim AS builder

# Устанавливаем рабочую директорию для сборки
WORKDIR /app

# Копируем pom.xml и файлы зависимостей (для кеширования слоев)
COPY pom.xml .

# Скачиваем зависимости (они будут закешированы, если pom.xml не изменяется)
RUN mvn dependency:go-offline

# Копируем исходный код в контейнер
COPY src ./src

# Собираем приложение
RUN mvn clean package -DskipTests

# Используем более легкий образ для работы приложения
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR файл из первого этапа сборки
COPY --from=builder /app/target/*.jar app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
