# Architecture Utils

## Introduction

Below we present a module that encompasses a series of functionalities that could be useful throughout the development of microservices. This module helps the user to simplify project structure, shortening programming time that is usually spent on frequent functionalities. In this way, by importing an external library, the user is provided with these functionalities implemented in the application, and also import technologies that are commonly used in the Onesait Technology architecture field. In the components and utilities section, all those utilities that are currently available in the library are listed.

## Technologies

For the development of the project, the following technologies are used:

Maven: For dependency resolution.
Spring-boot: For implementation of the application module.

## Components and utilities

- Exception component and automatic generation of HTTP errors for REST services: For the use of custom exceptions in order to unify their use between different architecture products. In addition to exceptions, a handler has been defined for exceptions that, depending on the type of exception thrown, returns the correct http code. Below are the available exceptions:
  - OnesaitException: for generic exceptions
  - BadRequestException (extends from OnseaitException): For erroneous or corrupt requests
  - NotFoundException : Exception for access to ontologies or databases when the resource is not found
  - UserNotFoundException : Case where the user with exists in Identity manager. Returns an HttpStatus. UNAUTHORIZED (code 401)
- Date formatting and validation component: provides functions for converting dates between different formats and time zones, generating dates in real time, copying dates...
- JSON object manipulation component: it consists of a series of functions that allow the parsing and manipulation of the attributes of a JSON object in a simple way.
- Ontology Component: Query Builder for handling very common queries that cross any ontology. Example: Sorting, Filtering, Search, etc...
- Implementation of the WebMvcConfigurer interface to configure the web part of the product.
- Swagger: Internal import of this tool for API documentation. Requires some configuration in the app.
- Lombok: Internal import for the simplification of the use of class methods.
- Apache commons: Apache libraries focused on all reusable aspects of Java components.

## General instructions for use the library

This project has a usable version uploaded to the Architecture nexus. To have access to the architecture nexus we will have to configure maven to use it. To use the functionality that the application service provides, we must follow the following steps:

Add repositories to pom.xml:

```xml
<repositories>
    <repository>
        <id>onesait-platform</id>
        <url>https://nexus.onesaitplatform.com/nexus/content/repositories/releases/</url>
    </repository>
</repositories>
```

Add to our pom.xml the nexus library dependency:

```xml
<dependency>
  <groupId>com.minsait.architecture</groupId>
  <artifactId>architecture-utils</artifactId>
  <version>1.1.0</version>
</dependency>
```