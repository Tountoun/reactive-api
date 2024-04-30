package com.gofar.springreactive.repository;

import com.gofar.springreactive.entity.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {


    Mono<Transaction> findByReference(String reference);

    Flux<Transaction> findAllByStatus(String status);

    Flux<Transaction> findAllByMode(String mode);

    Flux<Transaction> findAllByTransactionDate(LocalDate date);

    Mono<Void> deleteByReference(String reference);

    Mono<Boolean> existsByReference(String reference);
}
