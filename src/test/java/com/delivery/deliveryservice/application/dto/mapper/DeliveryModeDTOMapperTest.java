package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.DeliveryModeDTO;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryModeDTOMapperTest {

    private DeliveryModeDTOMapper mapper;
    private DeliveryMode deliveryMode;
    private DeliveryModeDTO deliveryModeDTO;

    @BeforeEach
    void setUp() {
        mapper = new DeliveryModeDTOMapper();
        
        deliveryMode = new DeliveryMode("DRIVE", "Drive", 7, true);
        
        deliveryModeDTO = new DeliveryModeDTO();
        deliveryModeDTO.setName("DRIVE");
        deliveryModeDTO.setDescription("Drive");
        deliveryModeDTO.setMaxDaysInAdvance(7);
        deliveryModeDTO.setAllowsPreBooking(true);
    }

    @Test
    void toDTO_ShouldMapCorrectly() {
        // Act
        DeliveryModeDTO result = mapper.toDTO(deliveryMode);
        
        // Assert
        assertEquals("DRIVE", result.getName());
        assertEquals("Drive", result.getDescription());
        assertEquals(7, result.getMaxDaysInAdvance());
        assertTrue(result.isAllowsPreBooking());
    }

    @Test
    void toDomain_ShouldMapCorrectly() {
        // Act
        DeliveryMode result = mapper.toDomain(deliveryModeDTO);
        
        // Assert
        assertEquals("DRIVE", result.getName());
        assertEquals("Drive", result.getDescription());
        assertEquals(7, result.getMaxDaysInAdvance());
        assertTrue(result.isAllowsPreBooking());
    }
}
