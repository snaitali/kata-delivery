package com.delivery.deliveryservice.domain.repository;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryModeRepository {
    Flux<DeliveryMode> findAll();
    Mono<DeliveryMode> findByName(String name);
    Mono<DeliveryMode> save(DeliveryMode deliveryMode);
}
