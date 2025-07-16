package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.Booking;
import com.delivery.deliveryservice.domain.model.BookingStatus;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import com.delivery.deliveryservice.infrastructure.persistence.entity.BookingEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingMapper {
    
    public Booking toDomain(BookingEntity entity) {
        if (entity == null) {
            return null;
        }

        DeliveryMode deliveryMode = new DeliveryMode();
        deliveryMode.setName(entity.getDeliveryMode());
        
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(entity.getTimeSlotId());
        timeSlot.setDate(entity.getDate());
        timeSlot.setStartTime(entity.getStartTime());
        timeSlot.setEndTime(entity.getEndTime());
        timeSlot.setDeliveryMode(deliveryMode);
        
        return new Booking(
                entity.getId(),
                entity.getCustomerId(),
                timeSlot,
                deliveryMode,
                entity.getBookingTime(),
                BookingStatus.valueOf(entity.getStatus()),
                entity.getVersion()
        );
    }
    
    public BookingEntity toEntity(Booking domain) {
        if (domain == null) {
            return null;
        }
        
        return new BookingEntity(
                domain.getId() != null ? domain.getId() : UUID.randomUUID(),
                domain.getCustomerId(),
                domain.getTimeSlot().getId(),
                domain.getTimeSlot().getDate(),
                domain.getTimeSlot().getStartTime(),
                domain.getTimeSlot().getEndTime(),
                domain.getDeliveryMode().getName(),
                domain.getBookingTime(),
                domain.getStatus().name(),
                domain.getVersion()
        );
    }
}
