FROM maven:3.5-jdk-8-onbuild-alpine
RUN apk update && apk add libc6-compat
CMD ["java","-jar","/usr/src/app/target/operator-adder-jar-with-dependencies.jar"]
