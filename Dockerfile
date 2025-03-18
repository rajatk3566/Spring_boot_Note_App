FROM openjdk:23
LABEL authors="consultadd"
COPY target/Spring_boot_Training-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "Spring_boot_Training-0.0.1-SNAPSHOT.jar"]