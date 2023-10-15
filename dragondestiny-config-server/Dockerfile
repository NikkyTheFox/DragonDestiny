FROM openjdk:17
COPY target/dragondestiny-config-server-0.0.1-SNAPSHOT.jar dragondestiny-config-server.jar
EXPOSE 8888
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh

CMD ["/usr/wait-for-it.sh", "dddiscovery:8761", "--timeout=150", "--", "java","-jar","dragondestiny-config-server.jar"]