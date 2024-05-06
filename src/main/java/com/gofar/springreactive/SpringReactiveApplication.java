package com.gofar.springreactive;

import com.gofar.springreactive.client.ReactiveClient;
import com.gofar.springreactive.entity.Transaction;
import com.gofar.springreactive.service.ITransactionService;
import com.gofar.springreactive.utils.TransactionMode;
import com.gofar.springreactive.utils.TransactionStatus;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SpringReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}

	@Bean
	@ConditionalOnProperty(name = "beans.runner", havingValue = "true")
	ApplicationRunner runner(ITransactionService transactionService, ReactiveClient reactiveClient) {
		return args -> {
			List<TransactionMode> modes = List.of(TransactionMode.values());
			List<TransactionStatus> statusList = List.of(TransactionStatus.values());
			for (int i = 0; i < 20; i++) {
				int randomInt = new Random().nextInt(0, 3);
				transactionService.saveTransaction(
						Transaction.builder()
								.reference(UUID.randomUUID().toString().substring(1, 8))
								.mode(modes.get(randomInt))
								.status(statusList.get(randomInt))
								.amount(5333D)
								.creditorAccount(UUID.randomUUID().toString().substring(4, 12))
								.debtorAccount(UUID.randomUUID().toString().substring(3, 11))
								.build()
				).subscribe();
			}
			System.out.println("Reference of the transaction with id 5" + reactiveClient.getTransactionReferenceById(5).block());
		};
	}
}
