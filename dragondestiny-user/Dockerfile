FROM openjdk:17 as builder
EXPOSE 8083
COPY target/dragondestiny-user-0.0.1-SNAPSHOT.jar dragondestiny-user.jar
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh

CMD ["/usr/wait-for-it.sh", "sqlserver:1433", "--timeout=350", "--", "java","-jar","dragondestiny-user.jar"]