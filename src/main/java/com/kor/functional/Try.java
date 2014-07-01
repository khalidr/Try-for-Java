package com.kor.functional;

import fj.F;
import fj.P1;
import fj.data.Option;

import static com.kor.functional.Success.*;

public abstract class Try<T> {
    public abstract <U> Try<U> map(F<T, U> func);

    public abstract boolean isSuccess();

    public abstract boolean isFailure();

    abstract public T get() throws Exception;

    protected T getUnderlying() {
        return t;
    }

    protected T t;

    public Option<T> toOption() {
        try {
            T t = this.get();
            return Option.some(t);
        } catch (Exception e) {
            return Option.none();
        }
    }

    public <U> U fold(final F<Exception, U> f, F<T, U> t) {
        if (isFailure())
            return f.f(((Failure) this).getException());
        else {
            try {
                return t.f(this.get());
            } catch (Exception e) {
                //will never happen
                throw new RuntimeException(e);
            }
        }
    }

    public static <A, B> Try<B> ttry(P1<B> f) {
        try {
             B result = f._1();
            return success(result);
        } catch (Exception e) {
            return new Failure<>(e);
        }
    }

    public T getOrElse(P1<T> defaultFunc) {
        if (isSuccess())
            return getUnderlying();
        else
            return defaultFunc._1();
    }

    public Try<T> orElse(P1<Try<T>> defaultTry) {
        if (isSuccess())
            return this;
        else
            return defaultTry._1();
    }


}

