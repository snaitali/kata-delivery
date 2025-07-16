package com.delivery.deliveryservice.domain.repository;

import com.delivery.deliveryservice.domain.model.Booking;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BookingRepository {
    Mono<Booking> findById(UUID id);
    Flux<Booking> findByCustomerId(Integer customerId);
    Mono<Booking> save(Booking booking);
}
