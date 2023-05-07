FROM openjdk:17
FROM microsoft/mssql-server-linux:latest

ENV ACCEPT_EULA=Y
ENV MSSQL_SA_PASSWORD=test
ENV MSSQL_PID=Developer
ENV MSSQL_TCP_PORT=1433

COPY .\target\game-0.0.1-SNAPSHOT.jar game.jar
CMD ["java","-jar","game.jar"]

#docker run -e ACCEPT_EULA=Y -e MSSQL_PID="Developer" -e MSSQL_SA_PASSWORD="<YourStrong!Passw0rd>" -e MSSQL_TCP_PORT=1234 -p 1234:1234 -d mcr.microsoft.com/mssql/server:2019-GA-ubuntu-16.04