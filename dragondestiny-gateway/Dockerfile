
FROM openjdk:17 as builder
EXPOSE 8080
COPY target/dragondestiny-gateway-0.0.1-SNAPSHOT.jar dragondestiny-gateway.jar
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh

CMD ["/usr/wait-for-it.sh", "dddiscovery:8761", "--timeout=150", "--", "java","-jar","dragondestiny-gateway.jar"]