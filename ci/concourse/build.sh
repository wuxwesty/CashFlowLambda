#!/bin/sh
set -e
./gradlew --build-file ./build.gradle build
cp ./Services/build/libs/*.jar build-output/
