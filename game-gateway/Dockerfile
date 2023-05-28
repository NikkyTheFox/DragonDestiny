FROM openjdk:17


# Set the environment variables for connecting to MS SQL Server
ENV DB_HOST=
ENV DB_USER=
ENV DB_PASSWORD=
ENV DB_NAME=


ENV ACCEPT_EULA=Y
ENV MSSQL_SA_PASSWORD=test
ENV MSSQL_PID=Developer
ENV MSSQL_TCP_PORT=1433

COPY .\target\played_character-0.0.1-SNAPSHOT.jar played_character.jar
CMD ["java","-jar","played_character.jar"]