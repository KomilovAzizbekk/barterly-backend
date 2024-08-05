# Asosiy image sifatida openjdk ni tanlaymiz
FROM openjdk

# Ishchi katalogni belgilaymiz
WORKDIR /app-barterly-backend

# Jar fayllarimizni Docker image ichiga nusxalaymiz
COPY ./target/barterly-backend-0.0.1-SNAPSHOT.jar /app-barterly-backend

# Jar faylimizni ishga tushirish buyrug'i
CMD ["java", "-jar", "barterly-backend-0.0.1-SNAPSHOT.jar"]
