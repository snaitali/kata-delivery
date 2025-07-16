package com.delivery.deliveryservice.domain.repository;


import com.delivery.deliveryservice.domain.model.TimeSlot;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TimeSlotRepository {
    Flux<TimeSlot> finAll();
    Flux<TimeSlot> findAvailableSlotsByModeAndDate(String mode, String date);
    Mono<TimeSlot> findById(UUID id);
    Mono<TimeSlot> save(TimeSlot timeSlot);
    Flux<TimeSlot> findAllByDeliveryMode(String mode);
}
