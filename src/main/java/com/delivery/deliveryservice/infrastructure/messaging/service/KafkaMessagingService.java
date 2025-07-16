package com.delivery.deliveryservice.infrastructure.messaging.service;

import com.delivery.deliveryservice.infrastructure.messaging.event.BookingCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotBookedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotReleasedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessagingService {

    private final ReactiveKafkaProducerTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topics.booking-created}")
    private String bookingCreatedTopic;
    
    @Value("${kafka.topics.time-slot-created}")
    private String timeSlotCreatedTopic;
    
    @Value("${kafka.topics.time-slot-booked}")
    private String timeSlotBookedTopic;
    
    @Value("${kafka.topics.time-slot-released}")
    private String timeSlotReleasedTopic;
    
    public Mono<SenderResult<Void>> publishBookingCreatedEvent(BookingCreatedEvent event) {
        log.info("Publishing booking created event: {}", event);
        return kafkaTemplate.send(bookingCreatedTopic, event.getBookingId().toString(), event)
                .doOnSuccess(result -> log.info("Successfully published booking created event"))
                .doOnError(e -> log.error("Failed to publish booking created event", e));
    }
    
    public Mono<SenderResult<Void>> publishTimeSlotCreatedEvent(TimeSlotCreatedEvent event) {
        log.info("Publishing time slot created event: {}", event);
        return kafkaTemplate.send(timeSlotCreatedTopic, event.getTimeSlotId().toString(), event)
                .doOnSuccess(result -> log.info("Successfully published time slot created event"))
                .doOnError(e -> log.error("Failed to publish time slot created event", e));
    }
    
    public Mono<SenderResult<Void>> publishTimeSlotBookedEvent(TimeSlotBookedEvent event) {
        log.info("Publishing time slot booked event: {}", event);
        return kafkaTemplate.send(timeSlotBookedTopic, event.getTimeSlotId().toString(), event)
                .doOnSuccess(result -> log.info("Successfully published time slot booked event"))
                .doOnError(e -> log.error("Failed to publish time slot booked event", e));
    }
}
