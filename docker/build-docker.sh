#!/usr/bin/env bash
path=$(cd `dirname $0`; pwd)
project=$(cd $path/..; pwd)
mvn clean package -Dmaven.test.skip=true -f $project
docker build --build-arg JAR_FILE=./eagle-core/target/*.jar -t eagle:latest -f $path/Dockerfile .
