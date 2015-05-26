# ForceManager RESTful API - Java example

## Introduction

This is a Java example code for using [**ForceManager**](https://forcemanager.net/) RESTful API. You can check the API documentation [here](http://developer.forcemanager.net/).

This example is written in Java using [**Spring Resttemplate**](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html), [**Jackson**](https://github.com/FasterXML/jackson) and [**Maven**](https://maven.apache.org/).



## Getting started

Follow these steps to get started:

#### Step 1: Configuring Eclipse IDE for Java

You need to download the last version of Eclipse IDE for Java EE Developers, for example [Lunar](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr2) version. Make sure that Eclipse has installed the Maven plugin.


#### Step 2: Import Maven Project into Eclipse

To import an existing Maven project into Eclipse, just right-click the Package Explorer and:

`
Import... > Existing Maven Projects > Select root directory > Finish
`

It's possible that you need to update Maven project. To do it, just right-click the project and:

`
Maven > Update Project...
`


#### Step 3: Add public and private key

Before running the examples, you need to add the public key and private key. To inform them, navigate to the file *`src\main\resources\fm-prop.properties`* and change the value of these properties: **`net.forcemanager.ws.sample.publicKey`** and **`net.forcemanager.ws.sample.privateKey`**.



## Examples

The package `net.forcemanager.ws.sample.examples` contains some Java examples. Each example contains a Main class with a public static main method entry point.

#### Example 1

This example shows the top 500 companies and print by console their names.

#### Example 2

In this example:

+ First, add a new company to FM.
+ Then, modifies it: change "city_name", "mobile_phone" and "phone".
+ Finally, deletes it if exists in FM.
