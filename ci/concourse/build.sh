#!/bin/sh
set -e
./gradlew --build-file CashFlowLambda/build.gradle build
cp CashFlowLambda/Services/build/libs/*.jar build-output/
