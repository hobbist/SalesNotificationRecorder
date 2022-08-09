package com.jp.tech.test.parser;

import com.jp.tech.test.entity.SaleMessage;
import com.jp.tech.test.exceptions.ParserException;

public interface MessageParser<T,K extends SaleMessage> {
    K parse(T rawMessage) throws ParserException;
}
