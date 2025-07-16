package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.CustomerDTO;
import com.delivery.deliveryservice.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOMapperTest {

    private CustomerDTOMapper mapper;
    private Customer customer;
    private CustomerDTO customerDTO;
    private Integer customerId;

    @BeforeEach
    void setUp() {
        mapper = new CustomerDTOMapper();
        
        customerId = 1;
        
        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Souf NAIT");
        customer.setEmail("souf.nait@example.com");
        customer.setPhoneNumber("0123456789");
        customer.setAddress("123 Main Street");
        
        customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setName("Souf NAIT");
        customerDTO.setEmail("souf.nait@example.com");
        customerDTO.setPhoneNumber("0123456789");
        customerDTO.setAddress("123 Main Street");
    }

    @Test
    void toDTO_ShouldMapCorrectly() {
        // Act
        CustomerDTO result = mapper.toDTO(customer);
        
        // Assert
        assertEquals(customerId, result.getId());
        assertEquals("Souf NAIT", result.getName());
        assertEquals("souf.nait@example.com", result.getEmail());
        assertEquals("0123456789", result.getPhoneNumber());
        assertEquals("123 Main Street", result.getAddress());
    }

    @Test
    void toDomain_ShouldMapCorrectly() {
        // Act
        Customer result = mapper.toDomain(customerDTO);
        
        // Assert
        assertEquals(customerId, result.getId());
        assertEquals("Souf NAIT", result.getName());
        assertEquals("souf.nait@example.com", result.getEmail());
        assertEquals("0123456789", result.getPhoneNumber());
        assertEquals("123 Main Street", result.getAddress());
    }
}
