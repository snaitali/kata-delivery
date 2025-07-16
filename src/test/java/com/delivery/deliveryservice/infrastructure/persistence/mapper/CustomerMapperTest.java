package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.Customer;
import com.delivery.deliveryservice.infrastructure.persistence.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerMapper mapper;
    private Customer customer;
    private CustomerEntity customerEntity;
    private Integer customerId;

    @BeforeEach
    void setUp() {
        mapper = new CustomerMapper();
        
        customerId = 1;
        
        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Souf NAIT");
        customer.setEmail("souf.nait@example.com");
        customer.setPhoneNumber("0123456789");
        customer.setAddress("123 Main Street");
        
        customerEntity = new CustomerEntity();
        customerEntity.setId(customerId);
        customerEntity.setName("Souf NAIT");
        customerEntity.setEmail("souf.nait@example.com");
        customerEntity.setPhoneNumber("0123456789");
        customerEntity.setAddress("123 Main Street");
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        // Act
        CustomerEntity result = mapper.toEntity(customer);
        
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
        Customer result = mapper.toDomain(customerEntity);
        
        // Assert
        assertEquals(customerId, result.getId());
        assertEquals("Souf NAIT", result.getName());
        assertEquals("souf.nait@example.com", result.getEmail());
        assertEquals("0123456789", result.getPhoneNumber());
        assertEquals("123 Main Street", result.getAddress());
    }
}
