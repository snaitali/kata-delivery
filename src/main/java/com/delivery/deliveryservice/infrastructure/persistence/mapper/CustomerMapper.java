package com.delivery.deliveryservice.infrastructure.persistence.mapper;

import com.delivery.deliveryservice.domain.model.Customer;
import com.delivery.deliveryservice.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {
    
    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getAddress()
        );
    }
    
    public CustomerEntity toEntity(Customer domain) {
        if (domain == null) {
            return null;
        }
        
        return new CustomerEntity(
                domain.getId() != null ? domain.getId() : null,
                domain.getName(),
                domain.getEmail(),
                domain.getPhoneNumber(),
                domain.getAddress()
        );
    }
}
