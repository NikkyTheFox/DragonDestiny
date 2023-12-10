
FROM openjdk:17 as builder
EXPOSE 8086
COPY target/dragondestiny-engine-0.0.1-SNAPSHOT.jar dragondestiny-engine.jar
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh
CMD ["/usr/wait-for-it.sh", "sqlserver:1433", "--timeout=350", "--", "java", "-jar", "dragondestiny-engine.jar"]
ENV SQL_SERVER_URL localhost:1433