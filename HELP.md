if docker is not work

sudo chmod 666 /var/run/docker.sock

To run kafka

docker network create kafkanet
docker network ls
docker run -d --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper
docker run -d --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka

docker exec -it kafka bash

TO create topic with 10 partitions

kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 10 --topic topic-10p 
