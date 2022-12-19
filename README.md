# Game of Life

![Pictue of the GUI](./docs/img.png)
<br>
This is a conway's game of life simulation with a gui that's made with SWT. In the game you can set the speed of the
game reset it and stop/play the simulation.
<br>

## Running a jar from the releases:
To run a precompiled jar download the jar for your platform and just run the jar with Java 11 or higher.
```shell
java -jar game_of_life-1.0_linux_x86_64.jar
```

## Building and starting the project:

If you want to build and start your project you have to look that you use the correct library for SWT in the pom.xml
file. For building just use the ```mvn install``` command

## Building a standalone jar

For compiling a standalone jar use the following command:

```shell
mvn clean compile assembly:single
```

## Building a standalone jar for a different target os

For compiling a standalone jar for a different os than the one you are compiling you have to change the profile so that
maven uses the correct dependencies. Of course the profile with the correct dependencies has to exist in the pom.xml 
file. You can read more about maven profiles [here](https://maven.apache.org/guides/introduction/introduction-to-profiles.html).

```shell
mvn clean compile assembly:single -P windows_x86_64,-linux_x86_64
```

