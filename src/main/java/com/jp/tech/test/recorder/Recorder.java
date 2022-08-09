package com.jp.tech.test.recorder;

import com.jp.tech.test.entity.SaleMessage;
import com.jp.tech.test.exceptions.RecorderException;

/*
   Class Acts as a template to run the steps to record given message
   Also it logs the statuses as per log processing
 */
public interface Recorder<T,K extends SaleMessage> {

    default void recordIncomingMessage(T message){
        K salesMessage= null;
        try {
            salesMessage = parseAndValidate(message);
            logMessage(salesMessage);
            processMessage(salesMessage);
            displayStatusAfterMessage(salesMessage);
        } catch (RecorderException e) {
            e.printStackTrace();
        }
    }

    void displayStatusAfterMessage(K message);
    K parseAndValidate(T t) throws RecorderException;
    void logMessage(K message) throws RecorderException;
    void processMessage(K message) throws RecorderException;

}
