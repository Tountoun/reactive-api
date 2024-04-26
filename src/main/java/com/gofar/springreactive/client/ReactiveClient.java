package com.gofar.springreactive.client;

import com.gofar.springreactive.entity.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class ReactiveClient {

    private final WebClient webClient;

    public ReactiveClient() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8085/api/v1/transactions").build();
    }

    public Mono<String> getTransactionReferenceById(int id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Transaction.class)
                .map(Transaction::getReference);
    }
}
