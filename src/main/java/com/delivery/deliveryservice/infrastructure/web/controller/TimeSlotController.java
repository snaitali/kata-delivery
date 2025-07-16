package com.delivery.deliveryservice.infrastructure.web.controller;

import com.delivery.deliveryservice.application.dto.TimeSlotDTO;
import com.delivery.deliveryservice.application.dto.mapper.TimeSlotDTOMapper;
import com.delivery.deliveryservice.domain.service.TimeSlotService;
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
@RequestMapping("/api/time-slots")
@RequiredArgsConstructor
@Tag(name = "Time Slots", description = "API for managing time slots")
public class TimeSlotController {
    
    private final TimeSlotService timeSlotService;
    private final TimeSlotDTOMapper timeSlotDTOMapper;

    @GetMapping
    @Operation(summary = "Get all time slots")
    public Flux<TimeSlotDTO> getAllTimeSlots() {
        return timeSlotService.findAllTimeSlots()
                .map(timeSlotDTOMapper::toDTO);
    }
    
    @GetMapping("/available")
    @Operation(summary = "Get available time slots by delivery mode and date")
    public Flux<TimeSlotDTO> getAvailableTimeSlots(
            @RequestParam String mode,
            @RequestParam String date) {
        return timeSlotService.getAvailableTimeSlots(mode, date)
                .map(timeSlotDTOMapper::toDTO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get time slot by ID")
    public Mono<ResponseEntity<TimeSlotDTO>> getTimeSlotById(@PathVariable UUID id) {
        return timeSlotService.getTimeSlotById(id)
                .map(timeSlotDTOMapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new time slot")
    public Mono<TimeSlotDTO> createTimeSlot(@Valid @RequestBody TimeSlotDTO timeSlotDTO) {
        return timeSlotService.createTimeSlot(timeSlotDTOMapper.toDomain(timeSlotDTO))
                .map(timeSlotDTOMapper::toDTO);
    }
    
    @GetMapping("/by-mode/{mode}")
    @Operation(summary = "Get all time slots by delivery mode")
    public Flux<TimeSlotDTO> getTimeSlotsByDeliveryMode(@PathVariable String mode) {
        return timeSlotService.findAllByDeliveryMode(mode)
                .map(timeSlotDTOMapper::toDTO);
    }
}
