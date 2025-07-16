package com.delivery.deliveryservice.domain.service;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.repository.DeliveryModeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeliveryModeService {
    
    private final DeliveryModeRepository deliveryModeRepository;

    public DeliveryModeService(DeliveryModeRepository deliveryModeRepository) {
        this.deliveryModeRepository = deliveryModeRepository;
    }

    public Flux<DeliveryMode> getAllDeliveryModes() {
        return deliveryModeRepository.findAll();
    }
    
    public Mono<DeliveryMode> getDeliveryModeByName(String name) {
        return deliveryModeRepository.findByName(name)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Delivery mode not found: " + name)));
    }
}
