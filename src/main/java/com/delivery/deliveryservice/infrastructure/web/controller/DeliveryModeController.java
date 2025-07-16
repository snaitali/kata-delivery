package com.delivery.deliveryservice.infrastructure.web.controller;

import com.delivery.deliveryservice.application.dto.DeliveryModeDTO;
import com.delivery.deliveryservice.application.dto.mapper.DeliveryModeDTOMapper;
import com.delivery.deliveryservice.domain.service.DeliveryModeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/delivery-modes")
@RequiredArgsConstructor
@Tag(name = "Delivery Modes", description = "API for managing delivery modes")
public class DeliveryModeController {
    
    private final DeliveryModeService deliveryModeService;
    private final DeliveryModeDTOMapper deliveryModeDTOMapper;
    
    @GetMapping
    @Operation(summary = "Get all delivery modes")
    public Flux<DeliveryModeDTO> getAllDeliveryModes() {
        return deliveryModeService.getAllDeliveryModes()
                .map(deliveryModeDTOMapper::toDTO);
    }
    
    @GetMapping("/{name}")
    @Operation(summary = "Get delivery mode by name")
    public Mono<ResponseEntity<DeliveryModeDTO>> getDeliveryModeByName(@PathVariable String name) {
        return deliveryModeService.getDeliveryModeByName(name)
                .map(deliveryModeDTOMapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
