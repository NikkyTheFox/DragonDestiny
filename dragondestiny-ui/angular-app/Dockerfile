#FROM node:14-alpine as build
#FROM alpine:3.18
#ENV NODE_VERSION 20.9.0
#RUN mkdir -p /usr/src/app
#FROM node:alpine  as build
# Install Angular CLI globally
#RUN npm install -g @angular/cli
##
#WORKDIR /usr/src/app/dragondestiny-ui/angular-app

#COPY package.json package-lock.json  ./
#RUN npm install
##COPY . .
#RUN npm run build --prod
##RUN npm install && \
##    npm run build
#RUN ng build --configuration=production

FROM nginx:alpine
ENV API_URL http://localhost:8080/api
ENV API_URL_WS ws://localhost:8080
##WORKDIR /usr/src/app
EXPOSE 80
ADD /dist/angular-app /usr/share/nginx/html/
ADD ./default.conf.template /etc/nginx/templates/

##COPY --from=build /usr/src/app/dist/* /usr/share/nginx/html/
#COPY nginx.conf /etc/nginx/nginx.conf
##COPY nginx.conf  /etc/nginx/conf.d/default.conf
#COPY --from=build /usr/src/app/dist/angular-app /usr/share/nginx/html

#EXPOSE 80
# Start nginx
#CMD ["nginx", "-g", "daemon off;"]

#CMD ["nginx", "-c", "/etc/nginx/nginx.conf"]