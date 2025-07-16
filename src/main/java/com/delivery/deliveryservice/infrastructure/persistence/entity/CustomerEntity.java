package com.delivery.deliveryservice.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    
    @Id
    private Integer id;
    
    private String name;
    
    private String email;
    
    private String phoneNumber;
    
    private String address;
}
