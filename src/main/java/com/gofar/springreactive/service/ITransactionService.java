package com.gofar.springreactive.service;

import com.gofar.springreactive.entity.Transaction;
import com.gofar.springreactive.utils.TransactionFilter;
import com.gofar.springreactive.utils.TransactionMode;
import com.gofar.springreactive.utils.TransactionStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {

    Mono<Transaction> saveTransaction(Transaction transaction);

    Mono<Transaction> getTransaction(Long transactionId);

    Mono<Transaction> getTransactionByReference(String reference);

    Flux<Transaction> getAllTransactions();

    Flux<Transaction> getTransactionsByMode(TransactionMode mode);

    Flux<Transaction> getTransactionsByStatus(TransactionStatus status);

    Flux<Transaction> getTransactionsByDate(String date);

    Flux<Transaction> getTransactionsByDate(String startDate, String endDate);

    Flux<Transaction> filter(TransactionFilter filter);

    Mono<Void> deleteTransactionByReference(String reference);
}
