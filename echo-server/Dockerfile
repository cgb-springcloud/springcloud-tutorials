FROM java:8
MAINTAINER dsys
VOLUME /tmp
ADD ECHO_SERVER.jar ECHO_SERVER.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ECHO_SERVER.jar"]