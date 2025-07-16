package com.delivery.deliveryservice.domain.service;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import com.delivery.deliveryservice.domain.repository.TimeSlotRepository;
import com.delivery.deliveryservice.infrastructure.messaging.event.TimeSlotCreatedEvent;
import com.delivery.deliveryservice.infrastructure.messaging.service.KafkaMessagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeSlotServiceTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private DeliveryModeService deliveryModeService;

    @Mock
    private KafkaMessagingService kafkaMessagingService;

    @InjectMocks
    private TimeSlotService timeSlotService;

    private DeliveryMode driveMode;
    private TimeSlot timeSlot;
    private UUID timeSlotId;
    private String date;

    @BeforeEach
    void setUp() {
        timeSlotId = UUID.randomUUID();
        date = String.valueOf(LocalDate.now().plusDays(1));
        driveMode = new DeliveryMode("DRIVE", "Drive", 7, true);
        
        timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotId);
        timeSlot.setDate(date);
        timeSlot.setStartTime(String.valueOf(LocalTime.of(10, 0)));
        timeSlot.setEndTime(String.valueOf(LocalTime.of(11, 0)));
        timeSlot.setDeliveryMode(driveMode);
        timeSlot.setBooked(false);
    }

    @Test
    void getAvailableTimeSlots_WhenValidDateAndMode_ShouldReturnSlots() {
        when(deliveryModeService.getDeliveryModeByName("DRIVE")).thenReturn(Mono.just(driveMode));
        when(timeSlotRepository.findAvailableSlotsByModeAndDate("DRIVE", date)).thenReturn(Flux.just(timeSlot));

        StepVerifier.create(timeSlotService.getAvailableTimeSlots("DRIVE", date))
                .expectNext(timeSlot)
                .verifyComplete();
        
        verify(deliveryModeService, times(1)).getDeliveryModeByName("DRIVE");
        verify(timeSlotRepository, times(1)).findAvailableSlotsByModeAndDate("DRIVE", date);
    }

    @Test
    void getAvailableTimeSlots_WhenInvalidDate_ShouldReturnError() {
        String pastDate = String.valueOf(LocalDate.now().minusDays(1));
        driveMode.setMaxDaysInAdvance(7);
        
        when(deliveryModeService.getDeliveryModeByName("DRIVE")).thenReturn(Mono.just(driveMode));

        StepVerifier.create(timeSlotService.getAvailableTimeSlots("DRIVE", pastDate))
                .expectError(IllegalArgumentException.class)
                .verify();
        
        verify(deliveryModeService, times(1)).getDeliveryModeByName("DRIVE");
        verify(timeSlotRepository, never()).findAvailableSlotsByModeAndDate(anyString(), any(String.class));
    }

    @Test
    void getTimeSlotById_WhenExists_ShouldReturnSlot() {
        when(timeSlotRepository.findById(timeSlotId)).thenReturn(Mono.just(timeSlot));

        StepVerifier.create(timeSlotService.getTimeSlotById(timeSlotId))
                .expectNext(timeSlot)
                .verifyComplete();
        
        verify(timeSlotRepository, times(1)).findById(timeSlotId);
    }

    @Test
    void getTimeSlotById_WhenNotExists_ShouldReturnError() {
        UUID nonExistentId = UUID.randomUUID();
        when(timeSlotRepository.findById(nonExistentId)).thenReturn(Mono.empty());

        StepVerifier.create(timeSlotService.getTimeSlotById(nonExistentId))
                .expectError(IllegalArgumentException.class)
                .verify();
        
        verify(timeSlotRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void createTimeSlot_ShouldSaveAndPublishEvent() {
        when(deliveryModeService.getDeliveryModeByName("DRIVE")).thenReturn(Mono.just(driveMode));
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(Mono.just(timeSlot));
        when(kafkaMessagingService.publishTimeSlotCreatedEvent(any(TimeSlotCreatedEvent.class)))
                .thenReturn(Mono.just(mock(SenderResult.class)));

        StepVerifier.create(timeSlotService.createTimeSlot(timeSlot))
                .expectNext(timeSlot)
                .verifyComplete();
        
        verify(deliveryModeService, times(1)).getDeliveryModeByName("DRIVE");
        verify(timeSlotRepository, times(1)).save(any(TimeSlot.class));
        verify(kafkaMessagingService, times(1)).publishTimeSlotCreatedEvent(any(TimeSlotCreatedEvent.class));
    }

    @Test
    void bookTimeSlot_WhenAvailable_ShouldBookSlot() {
        when(timeSlotRepository.findById(timeSlotId)).thenReturn(Mono.just(timeSlot));
        
        TimeSlot bookedSlot = new TimeSlot();
        bookedSlot.setId(timeSlotId);
        bookedSlot.setDate(date);
        bookedSlot.setStartTime(String.valueOf(LocalTime.of(10, 0)));
        bookedSlot.setEndTime(String.valueOf(LocalTime.of(11, 0)));
        bookedSlot.setDeliveryMode(driveMode);
        bookedSlot.setBooked(true);
        
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(Mono.just(bookedSlot));

        StepVerifier.create(timeSlotService.bookTimeSlot(timeSlotId))
                .expectNext(bookedSlot)
                .verifyComplete();
        
        verify(timeSlotRepository, times(1)).findById(timeSlotId);
        verify(timeSlotRepository, times(1)).save(any(TimeSlot.class));
    }

    @Test
    void bookTimeSlot_WhenAlreadyBooked_ShouldReturnError() {
        TimeSlot bookedSlot = new TimeSlot();
        bookedSlot.setId(timeSlotId);
        bookedSlot.setDate(date);
        bookedSlot.setStartTime(String.valueOf(LocalTime.of(10, 0)));
        bookedSlot.setEndTime(String.valueOf(LocalTime.of(11, 0)));
        bookedSlot.setDeliveryMode(driveMode);
        bookedSlot.setBooked(true);
        
        when(timeSlotRepository.findById(timeSlotId)).thenReturn(Mono.just(bookedSlot));

        StepVerifier.create(timeSlotService.bookTimeSlot(timeSlotId))
                .expectError(IllegalStateException.class)
                .verify();
        
        verify(timeSlotRepository, times(1)).findById(timeSlotId);
        verify(timeSlotRepository, never()).save(any(TimeSlot.class));
    }

    @Test
    void findAllByDeliveryMode_ShouldReturnSlots() {
        when(timeSlotRepository.findAllByDeliveryMode("DRIVE")).thenReturn(Flux.just(timeSlot));

        StepVerifier.create(timeSlotService.findAllByDeliveryMode("DRIVE"))
                .expectNext(timeSlot)
                .verifyComplete();
        
        verify(timeSlotRepository, times(1)).findAllByDeliveryMode("DRIVE");
    }
}
