package com.jp.tech.test.recorder;

import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.exceptions.ParserException;
import com.jp.tech.test.exceptions.ProcessorException;
import com.jp.tech.test.exceptions.RecorderException;
import com.jp.tech.test.parser.SalesMessageParser;
import com.jp.tech.test.processor.ProcessorFactory;

public class SaleRecorder implements Recorder<String,AbstractSaleMessage> {
    SalesMessageParser parser=new SalesMessageParser();
    private Boolean receive=true;
    @Override
    public void displayStatusAfterMessage(AbstractSaleMessage message) {
        String status=ProcessorFactory.getLogProcessorForMessage(message).diplayRecordsStatus();
        String stopCount=System.getProperty("stopCount");
        if(status.equalsIgnoreCase(stopCount)) {
            receive=!receive;
        }
    }

    @Override
    public AbstractSaleMessage parseAndValidate(String s) throws RecorderException {
        try {
            return parser.parse(s);
        } catch (ParserException e) {
            throw new RecorderException(e.getMessage());
        }
    }

    @Override
    public void logMessage(AbstractSaleMessage message) throws RecorderException {
        try {
            ProcessorFactory.getLogProcessorForMessage(message).log(message);
        } catch (ProcessorException e) {
            throw new RecorderException(e.getMessage());
        }
    }

    @Override
    public void processMessage(AbstractSaleMessage message) throws RecorderException {
        try {
            ProcessorFactory.getProcessorforMessage(message).process(message);
        } catch (ProcessorException e) {
            throw new RecorderException(e.getMessage());
        }
    }

    @Override
    public Boolean doReceive() {
        return receive;
    }
}
