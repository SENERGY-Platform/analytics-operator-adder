FROM maven:3.5-jdk-8-onbuild-alpine
RUN apk update && apk add --no-cache libc6-compat gcompat
CMD ["java","-jar","/usr/src/app/target/operator-adder-jar-with-dependencies.jar"]
