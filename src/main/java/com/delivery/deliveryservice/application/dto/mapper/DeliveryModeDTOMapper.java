package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.DeliveryModeDTO;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import org.springframework.stereotype.Component;

@Component
public class DeliveryModeDTOMapper {
    
    public DeliveryModeDTO toDTO(DeliveryMode domain) {
        if (domain == null) {
            return null;
        }
        
        return new DeliveryModeDTO(
                domain.getName(),
                domain.getDescription(),
                domain.getMaxDaysInAdvance(),
                domain.isAllowsPreBooking()
        );
    }
    
    public DeliveryMode toDomain(DeliveryModeDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new DeliveryMode(
                dto.getName(),
                dto.getDescription(),
                dto.getMaxDaysInAdvance(),
                dto.isAllowsPreBooking()
        );
    }
}
