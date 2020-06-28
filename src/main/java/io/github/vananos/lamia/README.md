## Test project description
The Project consists from two parts:
* test task's implementation
* the micro event processing framework

The test task main part is in the [Main](./Main.java) file.
All classes that exist only for test task's purposes located in the lamia package.

You can read more about the framework's concepts in the separate [README](../../../../../../../README.md)

### Dependencies
* java 8
* php 7 (I've used 7.4.3)

Java project implemented without any frameworks, Jetty used as a Server.
Usually I use [Lombok](https://projectlombok.org/) or Kotlin for avoiding
boilerplate code, but these tools usually require IDE plugins, therefore I have not used them. 

### Build && Run
* Ensure you are staying in a right place

```shell script
 ls | grep build.gradle || echo "Your are staying in a wrong directory"
```
If you're in a wrong directory, you need to set your workdir to project root.

* Run backend 
```shell script
./gradlew run
```

Somewhere in the stdout you should see a substring 
```shell script
oejs.Server:main: Started
```

If you see something like
```shell script
java.net.BindException: Address already in use
```
it means, that you need to kill application that uses 8080 port, or provide another port number by
server.port VM option.
Application is also configurable by VM option api.key, it's the key which we use for omdbapi.

Then
* Switch tmux or open new terminal
* Run php client

```shell script
 ./php/php_client.php 
```

* Play with curl

```shell script
# get the token
TOKEN=$(curl --verbose -XPOST localhost:8080/login 2>&1 | grep -Po 'Authorization: Bearer [\w\.-]*')

# call /getMovie (can use any parameters from their site) 
curl  -H "$TOKEN" -X GET localhost:8080/getMovie?i=tt3896198

curl  -H "$TOKEN" -X GET localhost:8080/getMovie?t=Mile&y=1999

# call /getBook
curl -H "$TOKEN"  -X GET localhost:8080/getBook?isbn=ISBN:0451526538
```