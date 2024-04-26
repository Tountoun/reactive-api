package com.gofar.springreactive.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction")
public class Transaction {

    @Id
    private Long id;
    private String reference;
    private String debtorAccount;
    private String creditorAccount;
    private LocalDate transactionDate;
    private Double amount;
    private String description;
    private String status;
    private String mode;
}
