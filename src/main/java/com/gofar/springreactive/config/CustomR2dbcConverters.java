package com.gofar.springreactive.config;

import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.List;

public class CustomR2dbcConverters extends R2dbcCustomConversions {

    public CustomR2dbcConverters() {
        super((ConverterConfiguration) List.of(new TransactionStatusConverter(), new TransactionModeConverter()));
    }
}
