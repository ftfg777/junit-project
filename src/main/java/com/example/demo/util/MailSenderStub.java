package com.example.demo.util;

import org.springframework.stereotype.Component;

// 구현 전 가짜
@Component
public class MailSenderStub implements MailSender{

    @Override
    public boolean send() {
        return true;
    }
}
