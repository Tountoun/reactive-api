package com.gofar.springreactive.controller;

import com.gofar.springreactive.SpringReactiveApplication;
import com.gofar.springreactive.entity.Transaction;
import com.gofar.springreactive.service.ITransactionService;
import com.gofar.springreactive.service.impl.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransactionControllerITest.TestConfig.class, SpringReactiveApplication.class})
public class TransactionControllerITest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ITransactionService iTransactionService;

    private TransactionService transactionService;

    @BeforeEach
    public void before() {
        this.client.mutate().responseTimeout(Duration.ofSeconds(5)).build();
    }

    @Test
    public void getTransactionByIdShouldReturnTransactionWithSameIdTest() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        Mockito.doReturn(Mono.just(transaction)).when(iTransactionService).getTransaction(anyLong());

        client.get()
                .uri("api/v1/transactions/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Transaction.class)
                .value(t -> {
                    Assertions.assertEquals(t.getId(), 1);
                });
    }

    @Configuration
    static class TestConfig {

        @Primary
        @Bean
        public ITransactionService mock() {
            return Mockito.mock(TransactionService.class);
        }
    }
}
