package com.kor.functional;

import fj.F;

public class Success<T> extends Try<T> {


    private Success(T t){
        this.t=t;
    }

    @Override
    public <U> Try<U> map(F<T, U> func) {
        try{
            U f = func.f(t);
            return new Success<U>(f);
        } catch(Exception e){
           return new Failure<U>(e);
        }
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public T get() {
        return t;
    }



    public static <T> Success<T> success(T t){

        return new Success<T>(t);
    }
}
