package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.BookingRequestDTO;
import com.delivery.deliveryservice.application.dto.BookingResponseDTO;
import com.delivery.deliveryservice.domain.model.Booking;
import com.delivery.deliveryservice.domain.model.DeliveryMode;
import com.delivery.deliveryservice.domain.model.TimeSlot;
import org.springframework.stereotype.Component;


@Component
public class BookingDTOMapper {
    
    public BookingResponseDTO toDTO(Booking domain) {
        if (domain == null) {
            return null;
        }
        
        return new BookingResponseDTO(
                domain.getId(),
                domain.getCustomerId(),
                domain.getTimeSlot().getId(),
                domain.getDeliveryMode().getName(),
                domain.getTimeSlot().getDate(),
                domain.getTimeSlot().getStartTime(),
                domain.getTimeSlot().getEndTime(),
                domain.getBookingTime(),
                domain.getStatus().name()
        );
    }
    
    public Booking toDomain(BookingRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        DeliveryMode deliveryMode = new DeliveryMode();
        deliveryMode.setName(dto.getDeliveryMode());
        
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(dto.getTimeSlotId());
        
        Booking booking = new Booking();
        booking.setCustomerId(dto.getCustomerId());
        booking.setTimeSlot(timeSlot);
        booking.setDeliveryMode(deliveryMode);
        
        return booking;
    }
}
