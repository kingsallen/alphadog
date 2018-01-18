package com.moseeker.chat.service.parser;

import com.moseeker.chat.base.ChatParser;

public class ButtonParser extends AbstractChatParser {
    @Override
    public String fieldName() {
        return "btnContent";
    }
}
