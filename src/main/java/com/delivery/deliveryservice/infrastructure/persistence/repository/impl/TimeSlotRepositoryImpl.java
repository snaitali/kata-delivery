package com.delivery.deliveryservice.infrastructure.persistence.repository.impl;

import com.delivery.deliveryservice.domain.model.TimeSlot;
import com.delivery.deliveryservice.domain.repository.TimeSlotRepository;
import com.delivery.deliveryservice.infrastructure.persistence.entity.TimeSlotEntity;
import com.delivery.deliveryservice.infrastructure.persistence.mapper.TimeSlotMapper;
import com.delivery.deliveryservice.infrastructure.persistence.repository.TimeSlotR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TimeSlotRepositoryImpl implements TimeSlotRepository {
    
    private final TimeSlotR2dbcRepository timeSlotR2dbcRepository;
    private final TimeSlotMapper timeSlotMapper;
    
    public TimeSlotRepositoryImpl(TimeSlotR2dbcRepository timeSlotR2dbcRepository, 
                                 TimeSlotMapper timeSlotMapper) {
        this.timeSlotR2dbcRepository = timeSlotR2dbcRepository;
        this.timeSlotMapper = timeSlotMapper;
    }

    @Override
    public Flux<TimeSlot> finAll() {
        return timeSlotR2dbcRepository.findAll().map(timeSlotMapper::toDomain);
    }

    @Override
    public Flux<TimeSlot> findAvailableSlotsByModeAndDate(String mode, String date) {
        return timeSlotR2dbcRepository.findAvailableSlotsByModeAndDate(mode, date)
                .map(timeSlotMapper::toDomain);
    }
    
    @Override
    public Mono<TimeSlot> findById(UUID id) {
        return timeSlotR2dbcRepository.findById(id)
                .map(timeSlotMapper::toDomain);
    }
    
    @Override
    public Mono<TimeSlot> save(TimeSlot timeSlot) {
        TimeSlotEntity entity = timeSlotMapper.toEntity(timeSlot);
        if (timeSlot.getId() == null) {
            return timeSlotR2dbcRepository.save(entity)
                    .map(timeSlotMapper::toDomain);
        } else {
            // Mise à jour d'un enregistrement existant
            return timeSlotR2dbcRepository.findById(timeSlot.getId())
                    .flatMap(existingEntity -> {
                        // Conserver l'ID mais mettre à jour les autres champs
                        entity.setId(existingEntity.getId());
                        return timeSlotR2dbcRepository.save(entity);
                    })
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Time slot not found: " + timeSlot.getId())))
                    .map(timeSlotMapper::toDomain);
        }
    }
    
    @Override
    public Flux<TimeSlot> findAllByDeliveryMode(String mode) {
        return timeSlotR2dbcRepository.findAllByDeliveryMode(mode)
                .map(timeSlotMapper::toDomain);
    }
}
