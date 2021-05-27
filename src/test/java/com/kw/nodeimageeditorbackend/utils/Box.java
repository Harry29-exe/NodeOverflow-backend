package com.kw.nodeimageeditorbackend.utils;

public class Box<T> {
    private T innerObject;

    public T getObject() {
        return innerObject;
    }

    public void setObject(T innerObject) {
        this.innerObject = innerObject;
    }
}
