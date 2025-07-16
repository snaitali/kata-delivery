package com.delivery.deliveryservice.domain.service;

import com.delivery.deliveryservice.domain.model.Customer;
import com.delivery.deliveryservice.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Integer customerId;
    private String email;
    private Customer customer;

    @BeforeEach
    void setUp() {
        
        customerId = 1;
        email = "souf.nait@example.com";
        
        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Soufiane NAIT");
        customer.setEmail(email);
        customer.setPhoneNumber("0123456789");
        customer.setAddress("123 Test Avenue");
    }

    @Test
    void createCustomer_ShouldSaveCustomer() {
        // Arrange
        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(customer));

        // Act & Assert
        StepVerifier.create(customerService.createCustomer(customer))
                .expectNext(customer)
                .verifyComplete();
        
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void getAllCustomers_ShouldReturnAllCustomers() {
        Customer anotherCustomer = new Customer();
        anotherCustomer.setId(2);
        anotherCustomer.setName("Another Name");
        anotherCustomer.setEmail("another.name@example.com");
        
        when(customerRepository.findAll()).thenReturn(Flux.just(customer, anotherCustomer));

        StepVerifier.create(customerService.getAllCustomers())
                .expectNext(customer)
                .expectNext(anotherCustomer)
                .verifyComplete();
        
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById_WhenExists_ShouldReturnCustomer() {
        when(customerRepository.findById(customerId)).thenReturn(Mono.just(customer));

        StepVerifier.create(customerService.getCustomerById(customerId))
                .expectNext(customer)
                .verifyComplete();
        
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void getCustomerById_WhenNotExists_ShouldReturnError() {
        Integer nonExistentId = 6;
        when(customerRepository.findById(nonExistentId)).thenReturn(Mono.empty());

        StepVerifier.create(customerService.getCustomerById(nonExistentId))
                .expectError(IllegalArgumentException.class)
                .verify();
        
        verify(customerRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void getCustomerByEmail_WhenExists_ShouldReturnCustomer() {
        when(customerRepository.findByEmail(email)).thenReturn(Mono.just(customer));

        StepVerifier.create(customerService.getCustomerByEmail(email))
                .expectNext(customer)
                .verifyComplete();

        verify(customerRepository, times(1)).findByEmail(email);
    }

    @Test
    void getCustomerByEmail_WhenNotExists_ShouldReturnError() {
        String nonExistentEmail = "nonexistantemail@test.com";
        when(customerRepository.findByEmail(nonExistentEmail)).thenReturn(Mono.empty());

        StepVerifier.create(customerService.getCustomerByEmail(nonExistentEmail))
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(customerRepository, times(1)).findByEmail(nonExistentEmail);
    }

    @Test
    void createCustomer_WhenNotExists_ShouldCreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setId(1);
        newCustomer.setName("New Souf");
        newCustomer.setEmail("new.souf@example.com");
        newCustomer.setPhoneNumber("9876543210");
        newCustomer.setAddress("456 New Street");

        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(newCustomer));

        StepVerifier.create(customerService.createCustomer(customer))
                .expectNext(newCustomer)
                .verifyComplete();

        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
