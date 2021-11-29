#/bin/bash
CLUSTER_ADDRESS="localhost:8091"
USER_NAME="Administrator"
PASSWORD="123456"

printf "Wait for Couchbase UI "
until $(curl --output /dev/null --silent --head --fail http://$CLUSTER_ADDRESS); do
    printf '.'
    sleep 5
done

printf '\n'
printf 'UI started\n'

printf "Cluster creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/couchbase-cli cluster-init -c $CLUSTER_ADDRESS --cluster-username $USER_NAME \
                               --cluster-password $PASSWORD --services data,index,query,fts \
                                                              --cluster-ramsize 2048 --cluster-index-ramsize 256 \
                                                              --cluster-eventing-ramsize 256 --cluster-fts-ramsize 256 \
                                                              --cluster-analytics-ramsize 1024 --cluster-fts-ramsize 256 \
                                                              --index-storage-setting default

printf "Bucket IOT creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/couchbase-cli bucket-create -c $CLUSTER_ADDRESS --username $USER_NAME \
                              --password $PASSWORD --bucket IOT --bucket-type couchbase \
                              --bucket-ramsize 256 --bucket-replica 2 --bucket-priority high \
                              --bucket-eviction-policy fullEviction --enable-flush 1 \
                              --enable-index-replica 1

printf "Scope infrastructure creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                              -p $PASSWORD --bucket IOT --create-scope infrastructure

printf "Collection houses creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                                                             -p $PASSWORD --bucket IOT --create-collection infrastructure.houses

printf "Collection metrics creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                                                             -p $PASSWORD --bucket IOT --create-collection infrastructure.metrics
printf "Collection devices creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/couchbase-cli collection-manage -c $CLUSTER_ADDRESS -u $USER_NAME \
                                                             -p $PASSWORD --bucket IOT --create-collection infrastructure.devices
sleep 10
printf "Primary index for houses creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/cbq -u $USER_NAME -p $PASSWORD -e "$CLUSTER_ADDRESS" --script="CREATE PRIMARY INDEX PRIMARY_INDEX_HOUSES on IOT.infrastructure.houses;"

printf "Primary index for devices creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/cbq -u $USER_NAME -p $PASSWORD -e "$CLUSTER_ADDRESS"  --script="CREATE PRIMARY INDEX PRIMARY_INDEX_DEVICES on IOT.infrastructure.devices;"

printf "Index for devices creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/cbq -u $USER_NAME -p $PASSWORD -e "$CLUSTER_ADDRESS" --script="CREATE INDEX HOUSES_ADDRESS_TYPE ON IOT.infrastructure.devices (type, house.address) USING GSI;"

printf "Primary index for metrics creating...\n"
/Applications/Couchbase\ Server.app/Contents/Resources/couchbase-core/bin/cbq -u $USER_NAME -p $PASSWORD -e "$CLUSTER_ADDRESS" --script="CREATE PRIMARY INDEX PRIMARY_INDEX_METRICS on IOT.infrastructure.metrics;"


printf "done(couchbase)\n"
exit 0

