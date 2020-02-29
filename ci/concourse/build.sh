#!/bin/sh
set -e
repo/gradlew --build-file repo/build.gradle build --warning-mode all --stacktrace
cp repo/Services/build/libs/*.jar out/
