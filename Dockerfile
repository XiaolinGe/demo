FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER zhouleib1412@gmail.com

ENV APP_ROOT /opt/demo
ENV SPRING_BOOT_PROFILE --spring.profiles.active=test
ENV JAVA_OPTS -server -Xmx512m -Xms256m

RUN mkdir -p ${APP_ROOT}/etc ${APP_ROOT}/lib ${APP_ROOT}/bin
ADD build/libs/demo-anz-0.0.1.jar ${APP_ROOT}/lib/app.jar
WORKDIR $APP_ROOT

ENTRYPOINT java $JAVA_OPTS -jar /opt/demo/lib/app.jar $SPRING_BOOT_PROFILE

EXPOSE 8080 8080

