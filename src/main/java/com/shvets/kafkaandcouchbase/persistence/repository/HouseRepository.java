package com.shvets.kafkaandcouchbase.persistence.repository;

import java.util.List;

import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.DynamicProxyable;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.query.QueryScanConsistency;
import com.shvets.kafkaandcouchbase.persistence.model.House;

@Repository
@Scope("infrastructure")
@Collection("houses")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
public interface HouseRepository extends CouchbaseRepository<House, String>, DynamicProxyable<HouseRepository> {

    @Override
    List<House> findAll();
}
