package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.TimeSlotDTO;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import org.springframework.stereotype.Component;


@Component
public class TimeSlotDTOMapper {
    
    public TimeSlotDTO toDTO(TimeSlot domain) {
        if (domain == null) {
            return null;
        }
        
        return new TimeSlotDTO(
                domain.getId(),
                domain.getDate(),
                domain.getStartTime(),
                domain.getEndTime(),
                domain.getDeliveryMode().getName(),
                domain.isBooked()
        );
    }
    
    public TimeSlot toDomain(TimeSlotDTO dto) {
        if (dto == null) {
            return null;
        }
        
        DeliveryMode deliveryMode = new DeliveryMode();
        deliveryMode.setName(dto.getDeliveryMode());
        
        return new TimeSlot(
                dto.getId(),
                dto.getDate(),
                dto.getStartTime(),
                dto.getEndTime(),
                deliveryMode,
                dto.isBooked(),
                null
        );
    }
}
