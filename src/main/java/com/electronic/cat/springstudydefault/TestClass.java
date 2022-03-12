package com.electronic.cat.springstudydefault;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestClass {
    @PostConstruct
    public void postConstuct() {
        System.out.println("빈이 떴습니다.");
    }
}
