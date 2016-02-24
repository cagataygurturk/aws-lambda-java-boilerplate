# AWS Lambda Boilerplate for JAVA and Spring IoC

Do you miss JAVA, its great features and all Spring framework sugar in AWS Lambda? Use this boilerplate code to author JAVA functions in Lambda and enjoy all features of Spring IoC. It also supports local debugging of JAVA functions.

## Usage

The code is already documented. **MainHandler** is the main entrance point of your lambda function. **services** package includes Spring Beans. For default, MainHandler fetches **Service** Bean from IoC container but once you have Service instance you can use Autowiring features.
 
### Local running

In root folder fire
 
```
mvn exec:java
```

You can configure your IDE to run com.example.lambda.local.LocalRunner as Main Class and com.example.lambda.MainHandler as program argument to debug locally your function.

### Deployment

In root folder fire

```
mvn package
```

this creates a JAR package in target folder. You can upload this JAR folder to AWS Lambda console. Handler function should be configured to com.example.MainHandler.
 
## Planned features

- Automated deployment
- JUnit template