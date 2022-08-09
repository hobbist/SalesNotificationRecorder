package com.jp.tech.test.recorder;

import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.exceptions.ParserException;
import com.jp.tech.test.exceptions.ProcessorException;
import com.jp.tech.test.exceptions.RecorderException;
import com.jp.tech.test.parser.SalesMessageParser;
import com.jp.tech.test.processor.ProcessorFactory;

public class SaleRecorder implements Recorder<String,AbstractSaleMessage> {
    SalesMessageParser parser=new SalesMessageParser();

    @Override
    public void displayStatusAfterMessage(AbstractSaleMessage message) {
        ProcessorFactory.getLogProcessorForMessage(message).diplayRecordsStatus();
    }

    @Override
    public AbstractSaleMessage parseAndValidate(String s) throws RecorderException {
        try {
            return parser.parse(s);
        } catch (ParserException e) {
            e.printStackTrace();
            throw new RecorderException(e.getMessage());
        }
    }

    @Override
    public void logMessage(AbstractSaleMessage message) throws RecorderException {
        try {
            ProcessorFactory.getLogProcessorForMessage(message).log(message);
        } catch (ProcessorException e) {
            e.printStackTrace();
            throw new RecorderException(e.getMessage());
        }
    }

    @Override
    public void processMessage(AbstractSaleMessage message) throws RecorderException {
        try {
            ProcessorFactory.getProcessorforMessage(message).process(message);
        } catch (ProcessorException e) {
            e.printStackTrace();
            throw new RecorderException(e.getMessage());
        }
    }
}
