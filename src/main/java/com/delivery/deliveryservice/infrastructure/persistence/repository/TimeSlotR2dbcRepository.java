package com.delivery.deliveryservice.infrastructure.persistence.repository;

import com.delivery.deliveryservice.infrastructure.persistence.entity.TimeSlotEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface TimeSlotR2dbcRepository extends ReactiveCrudRepository<TimeSlotEntity, UUID> {
    
    @Query("SELECT * FROM time_slots WHERE delivery_mode = :mode AND date = :date AND booked = false")
    Flux<TimeSlotEntity> findAvailableSlotsByModeAndDate(
            @Param("mode") String mode, 
            @Param("date") String date);
    
    @Query("SELECT * FROM time_slots WHERE delivery_mode = :mode")
    Flux<TimeSlotEntity> findAllByDeliveryMode(@Param("mode") String mode);
}
