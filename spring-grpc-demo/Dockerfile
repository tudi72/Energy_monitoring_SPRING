FROM maven:3.8.6-jdk-11 AS builder

COPY ./spring-demo/src/ /root/src
COPY ./spring-demo/pom.xml /root/
COPY ./spring-demo/checkstyle.xml /root/


WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/ds-2020-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/ds-2020-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.16-jre
ENV TZ=UTC
ENV DB_IP=172.15.0.52
ENV DB_PORT=3306
ENV DB_USER=htoh
ENV DB_PASSWORD=htoh
ENV DB_DBNAME=ds_project_1


COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]
