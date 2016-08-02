FROM tomcat:7-jre7
#FROM consol/tomcat-7.0 
MAINTAINER "Shadab Khan <shadabkhaniet@gmail.com>"

COPY target/maven-war-websocket-example.war /usr/local/tomcat/webapps/myapp.war
