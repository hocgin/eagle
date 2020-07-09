#!/usr/bin/env bash
path=$(cd `dirname $0`; pwd)
project=$path/..
mvn clean package -Dmaven.test.skip=true -f $project
docker build --build-arg JAR_FILE=$project/eagle-core/target/*.jar  . -t eagle:v2.0
