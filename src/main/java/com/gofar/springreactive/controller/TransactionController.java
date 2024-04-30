package com.gofar.springreactive.controller;

import com.gofar.springreactive.entity.Transaction;
import com.gofar.springreactive.service.ITransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final ITransactionService transactionService;

    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    public Mono<Transaction> save(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping
    public Flux<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Mono<Transaction> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping("/ref/{reference}")
    public Mono<Transaction> getTransactionByReference(@PathVariable("reference") String reference) {
        return transactionService.getTransactionByReference(reference);
    }

    @GetMapping("/mode/{mode}")
    public Flux<Transaction> getTransactionByMode(@PathVariable("mode") String mode) {
        return transactionService.getTransactionsByMode(mode);
    }

    @GetMapping("/status/{status}")
    public Flux<Transaction> getTransactionByStatus(@PathVariable("status") String status) {
        return transactionService.getTransactionsByStatus(status);
    }

    @DeleteMapping("/{reference}")
    public Mono<Void> deleteTransaction(@PathVariable("reference") String reference) {
        return transactionService.deleteTransactionByReference(reference);
    }
}
