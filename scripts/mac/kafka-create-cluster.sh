printf 'Start kafka containers\n'
docker network create kafkanet
docker network ls
docker run -d --platform linux/x86_64/v8 --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper
docker run -d --platform linux/x86_64/v8 --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka

printf 'Containers started\n'


printf "Creating topic ...\n"
docker exec kafka /bin/bash -c "kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 10 --topic metrics"
docker exec kafka /bin/bash -c "kafka-topics --bootstrap-server kafka:9092 --list"



printf "done(kafka)\n"
exit 0

