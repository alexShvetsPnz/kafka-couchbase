package com.shvets.kafkaandcouchbase.persistence.repository;

import java.util.List;

import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.DynamicProxyable;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.query.QueryScanConsistency;
import com.shvets.kafkaandcouchbase.persistence.model.Device;

@Repository
@Scope("infrastructure")
@Collection("devices")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
public interface DeviceRepository extends CouchbaseRepository<Device, String>, DynamicProxyable<DeviceRepository> {

    @Override
    @Query("#{#n1ql.selectEntity};")
    List<Device> findAll();

    @Query("#{#n1ql.selectEntity} WHERE type = $1 and house.address = $2;")
    Device findByTypeAndHouseAddress(String deviceType, String deviceAddress);
}
