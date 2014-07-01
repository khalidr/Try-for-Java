package com.kor.functional;

import fj.F;

public class Failure<T> extends Try<T> {

    private Exception e;

    public Failure(Exception e){
        this.e=e;
    }

    @Override
    public <U> Try<U> map(F<T, U> func) {
        return new Failure<U>(e);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public T get() throws Exception{
        throw e;
    }

    public static <T> Failure<T> failure(T e ){
        if(!(e instanceof Exception)) throw new IllegalArgumentException();

        return new Failure<T>((Exception)e);
    }

    public Exception getException(){
        return e;
    }
}
