if docker is not work

sudo chmod 666 /var/run/docker.sock

To run kafka

docker network create kafkanet
docker network ls
docker run -d --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper
docker run -d --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka

docker exec -it kafka bash

TO create topic with 10 partitions

kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 10 --topic metrics 

TO describe groups (get offsets)

kafka-consumer-groups --bootstrap-server kafka:9092 --all-groups --describe

to run Couchbase
docker run -d --name db1 couchbase
docker run -d --name db2 couchbase
docker run -d --name db3 -p 8091-8096:8091-8096 -p 11210-11211:11210-11211 couchbase

docker inspect --format '{{ .NetworkSettings.IPAddress }}' db1
docker inspect --format '{{ .NetworkSettings.IPAddress }}' db2


to Run on Mac need install couchbase sever
https://docs.couchbase.com/server/current/install/macos-install.html

============================================================
to Remove cluster on Mac
https://docs.couchbase.com/server/current/install/install-uninstalling.html
1. Quit Couchbase Server if it’s running.
Click Couchbase Server menu bar icon and select Quit Couchbase Server. The menu bar icon will disappear when Couchbase Server shuts down.

2. Remove the application.
Go to the Applications folder and drag Couchbase Server.app to the Trash.

3. Remove the data and log directories.
Open Finder and go to ~/Library/Application Support/, and drag both the Couchbase and Membase folders (if present) to the Trash.

Next, go to ~/Library/Python/, and drag the couchbase-py folder to the Trash.