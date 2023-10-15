
FROM openjdk:17 as builder
EXPOSE 8086
COPY target/dragondestiny-engine-0.0.1-SNAPSHOT.jar dragondestiny-engine.jar
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh
#RUN echo 'service mssql-server start & java -jar dragondestiny.jar' > entrypoint.sh
#RUN chmod +x entrypoint.sh
CMD ["/usr/wait-for-it.sh", "sqlserver:1433", "--timeout=350", "--", "java", "-jar", "dragondestiny-engine.jar"]

#CMD ["java","-jar","dragondestiny-engine.jar"]

#FROM mcr.microsoft.com/mssql/server:latest   sqlserver:1433
ENV SQL_SERVER_URL localhost:1433
#ENV MSSQL_SA_PASSWORD=test

#ENV MSSQL_PID=Developer
#ENV MSSQL_TCP_PORT=1433

#COPY --from=builder entrypoint.sh /entrypoint.sh

#CMD ["/bin/bash", "/entrypoint.sh"]