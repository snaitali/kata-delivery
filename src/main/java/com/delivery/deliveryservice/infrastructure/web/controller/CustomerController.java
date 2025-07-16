package com.delivery.deliveryservice.infrastructure.web.controller;

import com.delivery.deliveryservice.application.dto.CustomerDTO;
import com.delivery.deliveryservice.application.dto.mapper.CustomerDTOMapper;
import com.delivery.deliveryservice.domain.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "API for managing customers")
public class CustomerController {
    
    private final CustomerService customerService;
    private final CustomerDTOMapper customerDTOMapper;
    
    @GetMapping
    @Operation(summary = "Get all customers")
    public Flux<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers()
                .map(customerDTOMapper::toDTO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public Mono<ResponseEntity<CustomerDTO>> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id)
                .map(customerDTOMapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Get customer by email")
    public Mono<ResponseEntity<CustomerDTO>> getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email)
                .map(customerDTOMapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new customer")
    public Mono<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTOMapper.toDomain(customerDTO))
                .map(customerDTOMapper::toDTO);
    }
}
