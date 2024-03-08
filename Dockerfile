
FROM openjdk:16-jdk-alpine as build
#Workaround for obnoxiously long builds, not something cheeky/in scope -MG
COPY .m2 /root/m2
COPY . /app
WORKDIR /app
RUN ./mvnw install

FROM openjdk:17-jdk-alpine as run
RUN addgroup -S spring && adduser -S spring -G spring
COPY --from=build /app/target /run
RUN mkdir /run/lib
COPY --from=build /app/lib/HFilter.jar /run/lib/HFilter.jar
COPY --from=build /app/lib/HFilter.jar /lib/HFilter.jar
WORKDIR /run
#Commented out for testing bug no 293, TODO: Fix me! -MG
#USER spring:spring
EXPOSE 5003
ENTRYPOINT ["java","-jar","/run/sensor-data-0.0.1-SNAPSHOT.jar","-cp","/run;/run/lib/HFilter.jar"]

