package com.gofar.springreactive.config;

import com.gofar.springreactive.utils.TransactionStatus;
import org.springframework.core.convert.converter.Converter;

public class TransactionStatusConverter implements Converter<TransactionStatus, String> {

    /**
     * @param source the type to convert
     * @return the converted value
     */
    @Override
    public String convert(TransactionStatus source) {
        return source.name();
    }
}
