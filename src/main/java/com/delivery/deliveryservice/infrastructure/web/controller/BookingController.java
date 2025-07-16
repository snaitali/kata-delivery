package com.delivery.deliveryservice.infrastructure.web.controller;

import com.delivery.deliveryservice.application.dto.BookingRequestDTO;
import com.delivery.deliveryservice.application.dto.BookingResponseDTO;
import com.delivery.deliveryservice.application.dto.mapper.BookingDTOMapper;
import com.delivery.deliveryservice.domain.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "API for managing bookings")
public class BookingController {
    
    private final BookingService bookingService;
    private final BookingDTOMapper bookingDTOMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new booking")
    public Mono<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.createBooking(
                bookingRequestDTO.getCustomerId(),
                bookingRequestDTO.getTimeSlotId(),
                bookingRequestDTO.getDeliveryMode()
        ).map(bookingDTOMapper::toDTO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public Mono<ResponseEntity<BookingResponseDTO>> getBookingById(@PathVariable UUID id) {
        return bookingService.getBookingById(id)
                .map(bookingDTOMapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get bookings by customer ID")
    public Flux<BookingResponseDTO> getBookingsByCustomerId(@PathVariable Integer customerId) {
        return bookingService.getBookingsByCustomerId(customerId)
                .map(bookingDTOMapper::toDTO);
    }
}
