package com.gofar.springreactive.controller;

import com.gofar.springreactive.SpringReactiveApplication;
import com.gofar.springreactive.entity.Transaction;
import com.gofar.springreactive.service.ITransactionService;
import com.gofar.springreactive.service.impl.TransactionService;
import com.gofar.springreactive.utils.TransactionMode;
import com.gofar.springreactive.utils.TransactionStatus;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

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
        Transaction transaction = getTransaction(1L, "5021", "VALID", "TRANSFER", 450D, "", "", LocalDate.now());
        Mockito.doReturn(Mono.just(transaction)).when(iTransactionService).getTransaction(anyLong());

        client.get()
                .uri("api/v1/transactions/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Transaction.class)
                .value(t -> {
                    Assertions.assertEquals(transaction.getId(), t.getId());
                    Assertions.assertEquals(transaction.getReference(), t.getReference());
                });
    }

    @Test
    public void getTransactionByReferenceTest() {
        Transaction transaction = getTransaction(4L, "5321", "VALID", "TRANSFER", 4500D, "", "", LocalDate.now());
        Mockito.doReturn(Mono.just(transaction)).when(iTransactionService).getTransactionByReference(anyString());

        client.get()
                .uri("api/v1/transactions/ref/" + transaction.getReference())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Transaction.class)
                .value(t -> {
                    Assertions.assertEquals(transaction.getId(), t.getId());
                    Assertions.assertEquals(transaction.getReference(), t.getReference());
                    Assertions.assertEquals(transaction.getTransactionDate(), t.getTransactionDate());
                });
    }

    @Test
    public void getTransactionByStatusTest() {
        Transaction transaction = getTransaction(7L, "5321", "INVALID", "CHECK", 4500D, "6332225253", "4832225253", LocalDate.now());
        Mockito.doReturn(Flux.just(transaction)).when(iTransactionService).getTransactionsByStatus(any(TransactionStatus.class));

        client.get()
                .uri("api/v1/transactions/status/" + transaction.getStatus())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(list -> {
                    Assertions.assertEquals(1, list.size());
                    LinkedHashMap<String, Object> res = (LinkedHashMap<String, Object>) list.stream().findFirst().get();

                    Assertions.assertEquals(transaction.getId().toString(), res.get("id").toString());
                    Assertions.assertEquals(transaction.getReference(), res.get("reference"));
                    Assertions.assertEquals(transaction.getDebtorAccount(), res.get("debtorAccount"));
                    Assertions.assertEquals(transaction.getCreditorAccount(), res.get("creditorAccount"));
                });
    }

    @Test
    public void getTransactionByModeTest() {
        Transaction transaction = getTransaction(7L, "5321", "INVALID", "CASH", 450000D, "6332225253", "4832225253", LocalDate.now());
        Mockito.doReturn(Flux.just(transaction)).when(iTransactionService).getTransactionsByMode(any(TransactionMode.class));

        client.get()
                .uri("api/v1/transactions/mode/" + transaction.getMode())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(list -> {
                    Assertions.assertEquals(1, list.size());
                    LinkedHashMap<String, Object> res = (LinkedHashMap<String, Object>) list.stream().findFirst().get();

                    Assertions.assertEquals(transaction.getId().toString(), res.get("id").toString());
                    Assertions.assertEquals(transaction.getReference(), res.get("reference"));
                    Assertions.assertEquals(transaction.getMode().name(), res.get("mode"));
                    Assertions.assertEquals(transaction.getAmount().toString(), res.get("amount").toString());
                });
    }

    @Test
    public void saveTransactionTest() {
        Transaction transaction = getTransaction(7L, "5321", "INVALID", "CASH", 450000D, "6332225253", "4832225253", LocalDate.now());
        Mockito.doReturn(Mono.just(transaction)).when(iTransactionService).saveTransaction(any(Transaction.class));

        client.post()
                .uri("api/v1/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transaction)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Transaction.class)
                .value(t -> {
                        Assertions.assertEquals(transaction.getId(), t.getId());
                        Assertions.assertEquals(transaction.getReference(), t.getReference());
                });
    }

    @Test
    public void getAllTransactionsTest() {
        Transaction transaction = getTransaction(7L, "5321", "INVALID", "CASH", 450000D, "6332225253", "4832225253", LocalDate.now());
        Mockito.doReturn(Flux.just(transaction)).when(iTransactionService).getAllTransactions();

        client.get()
                .uri("api/v1/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(list -> {
                    Assertions.assertEquals(1, list.size());
                });
    }




    private Transaction getTransaction(Long id,
                                       String reference,
                                       String status,
                                       String mode,
                                       Double amount,
                                       String fromAccount,
                                       String toAccount,
                                       LocalDate date) {
        return Transaction.builder()
                .id(id)
                .reference(reference)
                .status(TransactionStatus.valueOf(status))
                .mode(TransactionMode.valueOf(mode))
                .amount(amount)
                .creditorAccount(toAccount)
                .transactionDate(date)
                .debtorAccount(fromAccount)
                .build();
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
