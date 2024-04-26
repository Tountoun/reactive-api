package com.gofar.springreactive.service.impl;

import com.gofar.springreactive.entity.Transaction;
import com.gofar.springreactive.repository.TransactionRepository;
import com.gofar.springreactive.service.ITransactionService;
import com.gofar.springreactive.utils.TransactionFilter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * @param transaction the transaction to save
     * @return a mono of the saved transaction
     */
    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * @param transactionId the input id
     * @return a mono of the transaction with the input id
     */
    @Override
    public Mono<Transaction> getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    /**
     * @param reference the input reference
     * @return a mono of the transaction with the input reference
     */
    @Override
    public Mono<Transaction> getTransactionByReference(String reference) {
        return transactionRepository.findByReference(reference);
    }

    /**
     * @return a stream of all transactions
     */
    @Override
    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * @param mode the mode of the transactions
     * @return a stream of transactions with a given mode
     */
    @Override
    public Flux<Transaction> getTransactionsByMode(String mode) {
        return transactionRepository.findAllByMode(mode);
    }

    /**
     * @param status the status of the transactions
     * @return a stream of transactions with a given status
     */
    @Override
    public Flux<Transaction> getTransactionsByStatus(String status) {
        return transactionRepository.findAllByStatus(status);
    }

    /**
     * @param date the date of the transactions
     * @return a stream of transactions of a given day
     */
    @Override
    public Flux<Transaction> getTransactionsByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return transactionRepository.findAllByTransactionDate(localDate);
    }

    /**
     * @param startDate the start of the interval
     * @param endDate the end of the interval
     * @return a stream of transactions between @startDate and @endDate
     */
    @Override
    public Flux<Transaction> getTransactionsByDate(String startDate, String endDate) {
        // TODO: Not implemented yet
        return null;
    }

    /**
     * @param filter object used to filter transactions
     * @return a stream of transactions that match the filter values
     */
    @Override
    public Flux<Transaction> filter(TransactionFilter filter) {
        // TODO: Not implemented yet
        return null;
    }


}
