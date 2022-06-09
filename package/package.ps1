if(!(Test-Path .\jdk-11.0.7.zip)) {
    Invoke-WebRequest -Uri http://dublin.zhaw.ch/~tsengmar/jdk-11.0.7.zip -OutFile .\jdk-11.0.7.zip
}

java -jar packr.jar --platform windows64 --jdk .\jdk-11.0.7.zip --executable RaceTrack --classpath ..\target\RaceTrack-0.0.0-jar-with-dependencies.jar --mainclass com.pathfinder.racetrack.Main --minimizejre soft --output RaceTrack_Windows64