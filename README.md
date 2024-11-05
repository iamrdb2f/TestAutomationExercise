# Automation Exercise Framework Documentation

### Objectives of this project

This project is a framework aimed to demonstrate how to develop automation testing project of web applications, based on the [Automation Exercises](https://automationexercise.com/) website.

### Technologies used

- JDK 8 or above
- Maven 3.6.5 minimum

## How to build the project

```bash
mvn clean install
```

### How to run the TESTS in local for all the scenarios to be executed
```bash
mvn -P ALL test -Denv=dev -Dremote=no
```

### TESTS run parameters:
```bash
env: is needed to specify the environment on which you need to launch the tests
 
remote: is needed to know if the tests are launched on your local or in remote ( Jenkins for Example)
```

## How to serve the report

```bash
mvn allure:serve
```
## How to generate a report

```bash
mvn allure:report
```