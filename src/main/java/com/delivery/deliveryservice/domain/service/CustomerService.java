package com.delivery.deliveryservice.domain.service;

import com.delivery.deliveryservice.domain.model.Customer;
import com.delivery.deliveryservice.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CustomerService {
    
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Mono<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Customer not found: " + id)));
    }
    
    public Mono<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Customer not found with email: " + email)));
    }
    
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
