FROM openjdk:17
COPY .\target\player-0.0.1-SNAPSHOT.jar player.jar

# Set the environment variables for connecting to MS SQL Server
ENV DB_HOST=
ENV DB_USER=
ENV DB_PASSWORD=
ENV DB_NAME=


ENV ACCEPT_EULA=Y
ENV MSSQL_SA_PASSWORD=test
ENV MSSQL_PID=Developer
ENV MSSQL_TCP_PORT=1433


CMD ["java","-jar","player.jar"]