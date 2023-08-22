package com.haulmont.propsdemo;


public class PropDependencyBean {

    private final String rand;

    public PropDependencyBean(String rand) {
        this.rand = rand;
    }

    public String getLogin() {
        return rand;
    }
}
