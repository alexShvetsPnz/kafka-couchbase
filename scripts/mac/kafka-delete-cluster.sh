#/bin/bash
docker stop kafka zookeeper
docker rm kafka zookeeper
docker network rm kafkanet