package com.delivery.deliveryservice.domain.service;

import com.delivery.deliveryservice.domain.model.Booking;
import com.delivery.deliveryservice.domain.model.BookingStatus;
import com.delivery.deliveryservice.domain.repository.BookingRepository;
import com.delivery.deliveryservice.infrastructure.messaging.event.BookingCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotBookedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.service.KafkaMessagingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final TimeSlotService timeSlotService;
    private final CustomerService customerService;
    private final KafkaMessagingService kafkaMessagingService;

    public BookingService(BookingRepository bookingRepository, TimeSlotService timeSlotService, CustomerService customerService, KafkaMessagingService kafkaMessagingService) {
        this.bookingRepository = bookingRepository;
        this.timeSlotService = timeSlotService;
        this.customerService = customerService;
        this.kafkaMessagingService = kafkaMessagingService;
    }


    public Mono<Booking> createBooking(Integer customerId, UUID timeSlotId, String deliveryMode) {
        return customerService.getCustomerById(customerId)
                .flatMap(customer -> timeSlotService.bookTimeSlot(timeSlotId)
                        .flatMap(timeSlot -> {
                            Booking booking = new Booking();
                            booking.setCustomerId(customerId);
                            booking.setTimeSlot(timeSlot);
                            booking.getTimeSlot().setDate(timeSlot.getDate());
                            booking.getTimeSlot().getDeliveryMode().setName(deliveryMode);
                            booking.setDeliveryMode(timeSlot.getDeliveryMode());
                            booking.setBookingTime(LocalDateTime.now().toString());
                            booking.setStatus(BookingStatus.CONFIRMED);

                            return bookingRepository.save(booking)
                                    .flatMap(savedBooking -> {
                                        // Publier l'événement de création de réservation
                                        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent(
                                                savedBooking.getId(),
                                                savedBooking.getCustomerId(),
                                                savedBooking.getTimeSlot().getId(),
                                                savedBooking.getDeliveryMode().getName(),
                                                savedBooking.getTimeSlot().getDate(),
                                                savedBooking.getTimeSlot().getStartTime(),
                                                savedBooking.getTimeSlot().getEndTime(),
                                                savedBooking.getBookingTime()
                                        );

                                        // Publier l'événement de réservation de créneau
                                        TimeSlotBookedEvent timeSlotBookedEvent = new TimeSlotBookedEvent(
                                                savedBooking.getTimeSlot().getId(),
                                                savedBooking.getId(),
                                                savedBooking.getCustomerId(),
                                                savedBooking.getBookingTime()
                                        );

                                        return kafkaMessagingService.publishBookingCreatedEvent(bookingCreatedEvent)
                                                .then(kafkaMessagingService.publishTimeSlotBookedEvent(timeSlotBookedEvent))
                                                .thenReturn(savedBooking);
                                    });
                        }));
    }
    
    public Flux<Booking> getBookingsByCustomerId(Integer customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }
    
    public Mono<Booking> getBookingById(UUID id) {
        return bookingRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Booking not found: " + id)));
    }
}
