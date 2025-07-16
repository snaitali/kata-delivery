package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import com.delivery.deliveryservice.infrastructure.persistence.entity.TimeSlotEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TimeSlotMapper {
    
    public TimeSlot toDomain(TimeSlotEntity entity) {
        if (entity == null) {
            return null;
        }
        
        // Créer un DeliveryMode simple à partir du nom
        DeliveryMode deliveryMode = new DeliveryMode();
        deliveryMode.setName(entity.getDeliveryMode());
        
        return new TimeSlot(
                entity.getId(),
                entity.getDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                deliveryMode,
                entity.isBooked(),
                entity.getVersion()
        );
    }
    
    public TimeSlotEntity toEntity(TimeSlot domain) {
        if (domain == null) {
            return null;
        }
        
        return new TimeSlotEntity(
                domain.getId() != null ? domain.getId() : UUID.randomUUID(),
                domain.getDate(),
                domain.getStartTime(),
                domain.getEndTime(),
                domain.getDeliveryMode().getName(),
                domain.isBooked(),
                domain.getVersion()
        );
    }
}
