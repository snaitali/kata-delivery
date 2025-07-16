package com.delivery.deliveryservice.application.dto.mapper;

import com.delivery.deliveryservice.application.dto.CustomerDTO;
import com.delivery.deliveryservice.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOMapper {
    
    public CustomerDTO toDTO(Customer domain) {
        if (domain == null) {
            return null;
        }
        
        return new CustomerDTO(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                domain.getPhoneNumber(),
                domain.getAddress()
        );
    }
    
    public Customer toDomain(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new Customer(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getAddress()
        );
    }
}
