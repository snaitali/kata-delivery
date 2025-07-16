package com.delivery.deliveryservice.infrastructure.persistence.repository.impl;

import com.delivery.deliveryservice.domain.model.Customer;
import com.delivery.deliveryservice.domain.repository.CustomerRepository;
import com.delivery.deliveryservice.infrastructure.persistence.entity.CustomerEntity;
import com.delivery.deliveryservice.infrastructure.persistence.mapper.CustomerMapper;
import com.delivery.deliveryservice.infrastructure.persistence.repository.CustomerR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    
    private final CustomerR2dbcRepository customerR2dbcRepository;
    private final CustomerMapper customerMapper;
    
    public CustomerRepositoryImpl(CustomerR2dbcRepository customerR2dbcRepository, 
                                 CustomerMapper customerMapper) {
        this.customerR2dbcRepository = customerR2dbcRepository;
        this.customerMapper = customerMapper;
    }
    
    @Override
    public Mono<Customer> findById(Integer id) {
        return customerR2dbcRepository.findById(id)
                .map(customerMapper::toDomain);
    }
    
    @Override
    public Mono<Customer> findByEmail(String email) {
        return customerR2dbcRepository.findByEmail(email)
                .map(customerMapper::toDomain);
    }
    
    @Override
    public Flux<Customer> findAll() {
        return customerR2dbcRepository.findAll()
                .map(customerMapper::toDomain);
    }
    
    @Override
    public Mono<Customer> save(Customer customer) {
        CustomerEntity entity = customerMapper.toEntity(customer);
        if (customer.getId() == null) {
            entity.setId(null);
            // Insertion d'un nouvel enregistrement
            return customerR2dbcRepository.save(entity)
                    .map(customerMapper::toDomain);
        } else {
            // Mise à jour d'un enregistrement existant
            return customerR2dbcRepository.findById(customer.getId())
                    .flatMap(existingEntity -> {
                        // Conserver l'ID mais mettre à jour les autres champs
                        entity.setId(existingEntity.getId());
                        return customerR2dbcRepository.save(entity);
                    })
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Customer not found: " + customer.getId())))
                    .map(customerMapper::toDomain);
        }
    }
}
