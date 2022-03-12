package com.electronic.cat.springstudydefault;

import javax.annotation.PostConstruct;

public class InjectedTestClass {
    private TestClass testClass;

    public InjectedTestClass(TestClass testClass) {
        this.testClass = testClass;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println( "주입되서 잘 만들어졌습니다 ");
    }
}
