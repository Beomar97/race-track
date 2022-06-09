#!/bin/bash

cd ..

mvn clean
mvn clean package -DskipTests

cd package/

[ ! -f ./adoptopenjdk-me-11.jdk.zip ] && curl http://dublin.zhaw.ch/~tsengmar/adoptopenjdk-me-11.jdk.zip --output adoptopenjdk-me-11.jdk.zip

#MacOS
java -jar packr.jar --platform mac --jdk ./adoptopenjdk-me-11.jdk.zip --executable RaceTrack --classpath ../target/RaceTrack-0.0.0-jar-with-dependencies.jar --mainclass com.pathfinder.racetrack.Main --minimizejre soft --output RaceTrack.app --icon ../asset/favicon_io/mac-app.icns --bundle com.pathfinder.racetrack
