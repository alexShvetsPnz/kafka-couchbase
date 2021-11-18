#/bin/bash
CLUSTER_ADDRESS="localhost:8091"
USER_NAME="Administrator"
PASSWORD="123456"
printf 'Start couchbase containers\n'
docker run -d --name db1 couchbase
docker run -d --name db2 couchbase
docker run -d --name db -p 8091-8096:8091-8096 -p 11210-11211:11210-11211 couchbase

printf 'Containers started\n'

printf "Wait for Couchbase UI "
until $(curl --output /dev/null --silent --head --fail http://$CLUSTER_ADDRESS); do
    printf '.'
    sleep 5
done

printf '\n'
printf 'UI started\n'

printf 'detecting nodes IP\n'
DB1=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' db1)
echo $DB1
DB2=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' db2)
echo $DB2
printf "detected nodes IP $DB1, $DB2\n"

printf "Cluster creating...\n"
docker exec  db /bin/bash -c "couchbase-cli cluster-init -c $CLUSTER_ADDRESS --cluster-username $USER_NAME \
                               --cluster-password $PASSWORD --services data,index,query,fts,analytics \
                               --cluster-ramsize 2048 --cluster-index-ramsize 256 \
                               --cluster-eventing-ramsize 256 --cluster-fts-ramsize 256 \
                               --cluster-analytics-ramsize 1024 --cluster-fts-ramsize 256 \
                               --index-storage-setting default"

printf "Adding server $DB1\n"
docker exec db /bin/bash -c "couchbase-cli server-add -c $CLUSTER_ADDRESS --username $USER_NAME \
                              --password $PASSWORD --server-add $DB1 \
                              --server-add-username $USER_NAME --server-add-password $PASSWORD \
                              --services data,index,query,fts,analytics"
printf "Adding server $DB1\n"
docker exec db /bin/bash -c "couchbase-cli server-add -c $CLUSTER_ADDRESS --username $USER_NAME \
                              --password $PASSWORD --server-add $DB2 \
                              --server-add-username $USER_NAME --server-add-password $PASSWORD \
                              --services data,index,query,fts,analytics"
printf "Rebalance...\n"
docker exec db /bin/bash -c "couchbase-cli rebalance -c $CLUSTER_ADDRESS --username $USER_NAME \
                              --password $PASSWORD"

printf "Bucket IOT creating...\n"
docker exec db /bin/bash -c "couchbase-cli bucket-create -c $CLUSTER_ADDRESS --username $USER_NAME \
                              --password $PASSWORD --bucket IOT --bucket-type couchbase \
                              --bucket-ramsize 256 --bucket-replica 2 --bucket-priority high \
                              --bucket-eviction-policy fullEviction --enable-flush 1 \
                              --enable-index-replica 1"
printf "Scope infrastructure creating...\n"
docker exec db /bin/bash -c "couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                              -p $PASSWORD --bucket IOT --create-scope infrastructure"

printf "Collection houses creating...\n"
docker exec db /bin/bash -c "couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                                                             -p $PASSWORD --bucket IOT --create-collection infrastructure.houses"

printf "Collection metrics creating...\n"
docker exec db /bin/bash -c "couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                                                             -p $PASSWORD --bucket IOT --create-collection infrastructure.metrics"
printf "Collection devices creating...\n"
docker exec db /bin/bash -c "couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                                                             -p $PASSWORD --bucket IOT --create-collection infrastructure.devices"
printf "Primary index for houses creating...\n"
docker exec db /bin/bash -c "cbq -u $USER_NAME -p $PASSWORD -e \"$CLUSTER_ADDRESS\" \
                                     --script=\"CREATE PRIMARY INDEX PRIMARY_INDEX_HOUSES on IOT.infrastructure.houses;\""

printf "Primary index for devices creating...\n"
docker exec db /bin/bash -c "cbq -u $USER_NAME -p $PASSWORD -e \"$CLUSTER_ADDRESS\" \
                                     --script=\"CREATE PRIMARY INDEX PRIMARY_INDEX_DEVICES on IOT.infrastructure.devices;\""

printf "Index for devices creating...\n"
docker exec db /bin/bash -c "cbq -u $USER_NAME -p $PASSWORD -e \"$CLUSTER_ADDRESS\" \
                                     --script=\"CREATE INDEX HOUSES_ADDRESS_TYPE ON IOT.infrastructure.devices (type, house.address) USING GSI;\""

printf "Primary index for metrics creating...\n"
docker exec db /bin/bash -c "cbq -u $USER_NAME -p $PASSWORD -e \"$CLUSTER_ADDRESS\" \
                                     --script=\"CREATE PRIMARY INDEX PRIMARY_INDEX_METRICS on IOT.infrastructure.metrics;\""


printf "done(couchbase)\n"
exit 0

