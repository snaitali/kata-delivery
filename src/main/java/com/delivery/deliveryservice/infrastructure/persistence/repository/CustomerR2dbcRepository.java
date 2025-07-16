package com.delivery.deliveryservice.infrastructure.persistence.repository;

import com.delivery.deliveryservice.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface CustomerR2dbcRepository extends ReactiveCrudRepository<CustomerEntity, Integer> {
    
    Mono<CustomerEntity> findByEmail(String email);

}
