#!/usr/bin/env bash
path=$(cd `dirname $0`; pwd)
project=$(cd $path/..; pwd)
docker=$project/docker
sh $docker/build-docker.sh
docker-compose -f $docker/docker-compose.dev.yml up -d eagle
