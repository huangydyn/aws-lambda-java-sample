# aws-lambda-java-example
## description
this is aws lambda java example， build with:
- jdk8
- gradle

this is used in api gateway proxy api

## build
```
./gradlew clean packageFat
```

## deploy
upload to lambda, and config runtime `com.huangydyn.BookHandler::handleRequest`