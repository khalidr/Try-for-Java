package com.kor.functional;

import fj.F;
import fj.P;
import fj.P1;
import org.junit.Test;

import static org.junit.Assert.*;
import static com.kor.functional.Try.*;

public class TestTry {

    @Test
    public void testNewTryFailure(){

        final Integer toCloseOver = 12;

        Try ttry = Try.ttry(new P1<Integer>() {
            @Override
            public Integer _1() {
                return toCloseOver / 0;
            }
        });

        assertTrue(ttry.isFailure());

        assertTrue(((Failure) ttry).getException() instanceof ArithmeticException);

    }

    @Test
    public void testNewTrySuccess(){


        Try<Integer> ttry = Try.ttry(new P1<Integer>() {
            @Override
            public Integer _1() {
                return 100 / 2;
            }
        });

        assertTrue(ttry.isSuccess());

        try{
            Integer integer = ttry.get();
            assertTrue(integer == 50);
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testTryMap(){
        Try<String> map = Try.ttry(new P1<Integer>(){
            @Override
            public Integer _1() {
                return 50 / 2;
            }
        }).map(new F<Integer, String>() {
            @Override
            public String f(Integer integer) {
                return "The value is " + integer.toString();
            }
        });

        assertTrue(map.isSuccess());
        try {
            assertTrue(map.get().equals("The value is 25"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testTryFold(){

        assertTrue(fold(mod(5, 2)).equals("Successful results 1"));
        assertTrue(fold(mod(5,0)).equals("Whoops there was an exception"));
    }

    @Test
    public void testGetOrElse(){
        Integer orElse = mod(5, 0).getOrElse(new P1<Integer>() {
            @Override
            public Integer _1() {
                return 1;
            }
        });

        assertEquals(orElse.intValue(),1);
    }

    @Test
    public void testOrElse() throws Exception {

        Try<Integer> integerTry = mod(4, 0).orElse(new P1<Try<Integer>>() {
            @Override
            public Try<Integer> _1() {
                return ttry(P.p(4));
            }
        });

        assertEquals(integerTry.get().intValue(), 4);
    }

    private String fold(Try<Integer> t){
        return t.fold(
                new F<Exception,String>(){
                    @Override
                    public String f(Exception e) {
                        return "Whoops there was an exception";
                    }
                },
                new F<Integer,String>(){
                    @Override
                    public String f(Integer i) {
                        return "Successful results "+i;
                    }
                }
        );
    }


    private Try<Integer> mod(final Integer p1, final Integer p2){
        return ttry(new P1<Integer>() {
            @Override
            public Integer _1() {
                return p1 % p2;
            }
        });
    }

}
