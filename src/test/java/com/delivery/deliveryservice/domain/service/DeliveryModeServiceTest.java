package com.delivery.deliveryservice.domain.service;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.repository.DeliveryModeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryModeServiceTest {

    @Mock
    private DeliveryModeRepository deliveryModeRepository;

    @InjectMocks
    private DeliveryModeService deliveryModeService;


    @Test
    void getAllDeliveryModes_ShouldReturnAllModes() {
        DeliveryMode drive = new DeliveryMode("DRIVE", "Drive", 7, true);
        DeliveryMode delivery = new DeliveryMode("DELIVERY", "Delivery", 14, true);

        when(deliveryModeRepository.findAll()).thenReturn(Flux.just(drive, delivery));

        StepVerifier.create(deliveryModeService.getAllDeliveryModes())
                .expectNext(drive)
                .expectNext(delivery)
                .verifyComplete();

        verify(deliveryModeRepository, times(1)).findAll();
    }

    @Test
    void getDeliveryModeByName_WhenExists_ShouldReturnMode() {
        DeliveryMode drive = new DeliveryMode("DRIVE", "Drive", 7, true);

        when(deliveryModeRepository.findByName("DRIVE")).thenReturn(Mono.just(drive));

        StepVerifier.create(deliveryModeService.getDeliveryModeByName("DRIVE"))
                .expectNext(drive)
                .verifyComplete();

        verify(deliveryModeRepository, times(1)).findByName("DRIVE");
    }

    @Test
    void getDeliveryModeByName_WhenNotExists_ShouldReturnError() {
        // Arrange
        when(deliveryModeRepository.findByName("UNKNOWN")).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(deliveryModeService.getDeliveryModeByName("UNKNOWN"))
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(deliveryModeRepository, times(1)).findByName("UNKNOWN");
    }
}