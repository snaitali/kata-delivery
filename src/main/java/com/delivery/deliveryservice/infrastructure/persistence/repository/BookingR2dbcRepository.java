package com.delivery.deliveryservice.infrastructure.persistence.repository;

import com.delivery.deliveryservice.infrastructure.persistence.entity.BookingEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Repository
public interface BookingR2dbcRepository extends ReactiveCrudRepository<BookingEntity, UUID> {
    
    Flux<BookingEntity> findByCustomerId(Integer customerId);
}
