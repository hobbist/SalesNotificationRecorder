package com.jp.tech.test.processor;

import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.exceptions.ProcessorException;

public interface LogProcessor<T extends AbstractSaleMessage> {
    void log(T message) throws ProcessorException;
    void diplayRecordsStatus();
}
