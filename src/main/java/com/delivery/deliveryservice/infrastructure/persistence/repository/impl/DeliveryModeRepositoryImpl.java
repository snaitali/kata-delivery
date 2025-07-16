package com.delivery.deliveryservice.infrastructure.persistence.repository.impl;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.repository.DeliveryModeRepository;
import com.delivery.deliveryservice.infrastructure.persistence.entity.DeliveryModeEntity;
import com.delivery.deliveryservice.infrastructure.persistence.mapper.DeliveryModeMapper;
import com.delivery.deliveryservice.infrastructure.persistence.repository.DeliveryModeR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DeliveryModeRepositoryImpl implements DeliveryModeRepository {
    
    private final DeliveryModeR2dbcRepository deliveryModeR2dbcRepository;
    private final DeliveryModeMapper deliveryModeMapper;
    
    public DeliveryModeRepositoryImpl(DeliveryModeR2dbcRepository deliveryModeR2dbcRepository, 
                                     DeliveryModeMapper deliveryModeMapper) {
        this.deliveryModeR2dbcRepository = deliveryModeR2dbcRepository;
        this.deliveryModeMapper = deliveryModeMapper;
    }
    
    @Override
    public Flux<DeliveryMode> findAll() {
        return deliveryModeR2dbcRepository.findAll()
                .map(deliveryModeMapper::toDomain);
    }
    
    @Override
    public Mono<DeliveryMode> findByName(String name) {
        return deliveryModeR2dbcRepository.findByMode(name)
                .map(deliveryModeMapper::toDomain);
    }

    @Override
    public Mono<DeliveryMode> save(DeliveryMode deliveryMode) {
        DeliveryModeEntity entity = deliveryModeMapper.toEntity(deliveryMode);
        return deliveryModeR2dbcRepository.save(entity)
                .map(deliveryModeMapper::toDomain);
    }
}
