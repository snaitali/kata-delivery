package com.delivery.deliveryservice.domain.service;


import com.delivery.deliveryservice.domain.model.TimeSlot;
import com.delivery.deliveryservice.domain.repository.TimeSlotRepository;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.service.KafkaMessagingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class TimeSlotService {
    
    private final TimeSlotRepository timeSlotRepository;
    private final DeliveryModeService deliveryModeService;
    private final KafkaMessagingService kafkaMessagingService;

    public TimeSlotService(TimeSlotRepository timeSlotRepository, DeliveryModeService deliveryModeService, KafkaMessagingService kafkaMessagingService) {
        this.timeSlotRepository = timeSlotRepository;
        this.deliveryModeService = deliveryModeService;
        this.kafkaMessagingService = kafkaMessagingService;
    }

    public Flux<TimeSlot> findAllTimeSlots() {
        return timeSlotRepository.finAll();
    }

    public Flux<TimeSlot> getAvailableTimeSlots(String mode, String date) {
        return deliveryModeService.getDeliveryModeByName(mode)
                .flatMapMany(deliveryMode -> {
                    if (!deliveryMode.isValidForDate(date)) {
                        return Flux.error(new IllegalArgumentException("Invalid date for delivery mode: " + mode));
                    }
                    return timeSlotRepository.findAvailableSlotsByModeAndDate(mode, date);
                });
    }
    
    public Mono<TimeSlot> getTimeSlotById(UUID id) {
        return timeSlotRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Time slot not found: " + id)));
    }

    public Mono<TimeSlot> createTimeSlot(TimeSlot timeSlot) {
        return deliveryModeService.getDeliveryModeByName(timeSlot.getDeliveryMode().getName())
                .flatMap(deliveryMode -> {
                    timeSlot.setDeliveryMode(deliveryMode);
                    timeSlot.setBooked(false);
                    return timeSlotRepository.save(timeSlot)
                            .flatMap(savedTimeSlot -> {
                                // Publier l'événement de création de créneau
                                TimeSlotCreatedEvent timeSlotCreatedEvent = new TimeSlotCreatedEvent(
                                        savedTimeSlot.getId(),
                                        savedTimeSlot.getDate(),
                                        savedTimeSlot.getStartTime(),
                                        savedTimeSlot.getEndTime(),
                                        savedTimeSlot.getDeliveryMode().getName()
                                );

                                return kafkaMessagingService.publishTimeSlotCreatedEvent(timeSlotCreatedEvent)
                                        .thenReturn(savedTimeSlot);
                            });
                });
    }
    
    public Mono<TimeSlot> bookTimeSlot(UUID timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Time slot not found: " + timeSlotId)))
                .flatMap(timeSlot -> {
                    if (timeSlot.isBooked()) {
                        return Mono.error(new IllegalStateException("Time slot already booked: " + timeSlotId));
                    }
                    timeSlot.setBooked(true);
                    return timeSlotRepository.save(timeSlot);
                });
    }

    public Flux<TimeSlot> findAllByDeliveryMode(String mode) {
        return timeSlotRepository.findAllByDeliveryMode(mode);
    }
}
