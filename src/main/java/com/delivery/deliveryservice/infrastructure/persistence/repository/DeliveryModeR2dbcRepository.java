package com.delivery.deliveryservice.infrastructure.persistence.repository;

import com.delivery.deliveryservice.infrastructure.persistence.entity.DeliveryModeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DeliveryModeR2dbcRepository extends ReactiveCrudRepository<DeliveryModeEntity, String> {
    
    @Query("SELECT * FROM delivery_modes WHERE mode = :mode")
    Mono<DeliveryModeEntity> findByMode(String mode);
}
