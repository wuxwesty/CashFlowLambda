#!/bin/sh
set -e
./gradlew --build-file repo/build.gradle build
cp repo/Services/build/libs/*.jar out/
