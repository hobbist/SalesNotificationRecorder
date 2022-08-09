package com.jp.tech.test.processor;

import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.exceptions.ProcessorException;

public interface Processor<T extends AbstractSaleMessage> {
    Boolean process(T message) throws ProcessorException;
    void stopProcessing();
}
