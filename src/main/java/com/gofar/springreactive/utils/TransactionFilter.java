package com.gofar.springreactive.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionFilter {
    private String mode;
    private String status;
    private String date;
}
