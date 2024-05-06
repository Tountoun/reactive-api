package com.gofar.springreactive.config;

import com.gofar.springreactive.utils.TransactionMode;
import org.springframework.core.convert.converter.Converter;

public class TransactionModeConverter implements Converter<TransactionMode, String> {

    /**
     * @param source the type to convert
     * @return the converted value
     */
    @Override
    public String convert(TransactionMode source) {
        return source.name();
    }
}
