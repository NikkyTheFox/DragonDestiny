FROM nginx:alpine
ENV API_URL http://localhost:8080/api
ENV API_URL_WS ws://localhost:8080
EXPOSE 80
ADD /dist/angular-app /usr/share/nginx/html/
ADD ./default.conf.template /etc/nginx/templates/