package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.infrastructure.persistence.entity.DeliveryModeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryModeMapperTest {

    private DeliveryModeMapper mapper;
    private DeliveryMode deliveryMode;
    private DeliveryModeEntity deliveryModeEntity;

    @BeforeEach
    void setUp() {
        mapper = new DeliveryModeMapper();
        
        deliveryMode = new DeliveryMode("DRIVE", "Drive", 7, true);
        
        deliveryModeEntity = new DeliveryModeEntity();
        deliveryModeEntity.setMode("DRIVE");
        deliveryModeEntity.setDescription("Drive");
        deliveryModeEntity.setMaxDaysInAdvance(7);
        deliveryModeEntity.setAllowsPreBooking(true);
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        // Act
        DeliveryModeEntity result = mapper.toEntity(deliveryMode);
        
        // Assert
        assertEquals("DRIVE", result.getMode());
        assertEquals("Drive", result.getDescription());
        assertEquals(7, result.getMaxDaysInAdvance());
        assertTrue(result.isAllowsPreBooking());
    }

    @Test
    void toDomain_ShouldMapCorrectly() {
        // Act
        DeliveryMode result = mapper.toDomain(deliveryModeEntity);
        
        // Assert
        assertEquals("DRIVE", result.getName());
        assertEquals("Drive", result.getDescription());
        assertEquals(7, result.getMaxDaysInAdvance());
        assertTrue(result.isAllowsPreBooking());
    }
}
