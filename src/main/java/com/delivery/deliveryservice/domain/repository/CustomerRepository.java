package com.delivery.deliveryservice.domain.repository;

import com.delivery.deliveryservice.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerRepository {
    Mono<Customer> findById(Integer id);
    Mono<Customer> findByEmail(String email);
    Flux<Customer> findAll();
    Mono<Customer> save(Customer customer);
}
