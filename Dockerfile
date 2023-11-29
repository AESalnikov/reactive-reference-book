FROM openjdk:21
ADD /target/*.jar reactive-reference-book.jar
ENTRYPOINT ["java", "-jar", "reactive-reference-book.jar"]