package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.infrastructure.persistence.entity.DeliveryModeEntity;
import org.springframework.stereotype.Component;

@Component
public class DeliveryModeMapper {
    
    public DeliveryMode toDomain(DeliveryModeEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new DeliveryMode(
                entity.getMode(),
                entity.getDescription(),
                entity.getMaxDaysInAdvance(),
                entity.isAllowsPreBooking()
        );
    }
    
    public DeliveryModeEntity toEntity(DeliveryMode domain) {
        if (domain == null) {
            return null;
        }
        
        return new DeliveryModeEntity(
                domain.getName(),
                domain.getDescription(),
                domain.getMaxDaysInAdvance(),
                domain.isAllowsPreBooking()
        );
    }
}
