package com.delivery.deliveryservice.infrastructure.persistence.repository.impl;

import com.delivery.deliveryservice.domain.model.Booking;
import com.delivery.deliveryservice.domain.repository.BookingRepository;
import com.delivery.deliveryservice.infrastructure.persistence.entity.BookingEntity;
import com.delivery.deliveryservice.infrastructure.persistence.mapper.BookingMapper;
import com.delivery.deliveryservice.infrastructure.persistence.repository.BookingR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingR2dbcRepository bookingR2dbcRepository;
    private final BookingMapper bookingMapper;

    public BookingRepositoryImpl(BookingR2dbcRepository bookingR2dbcRepository,
                                 BookingMapper bookingMapper) {
        this.bookingR2dbcRepository = bookingR2dbcRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public Mono<Booking> findById(UUID id) {
        return bookingR2dbcRepository.findById(id)
                .map(bookingMapper::toDomain);
    }

    @Override
    public Flux<Booking> findByCustomerId(Integer customerId) {
        return bookingR2dbcRepository.findByCustomerId(customerId)
                .map(bookingMapper::toDomain);
    }

    @Override
    public Mono<Booking> save(Booking booking) {
        BookingEntity entity = bookingMapper.toEntity(booking);
        return bookingR2dbcRepository.save(entity)
                .map(bookingMapper::toDomain);
    }
}
