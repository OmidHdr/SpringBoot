package com.example.springboot.validation;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class InternationalValidation {

    public boolean checkEmpty(String nationalCode) {
        return !nationalCode.isEmpty();
    }

    private boolean len(String nationalCode) {
        return nationalCode.length() == 10;
    }
    public boolean lenCart(String cartNumber){
        return cartNumber.length() == 16;
    }

    public boolean isNumeric(String nationalCode){
        try {
            Double.parseDouble(nationalCode);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    private boolean validMeli(String nationalCode) {
        final String[] split = nationalCode.split("");
        AtomicInteger count = new AtomicInteger(10);
        AtomicInteger max = new AtomicInteger();
        Arrays.stream(split).forEach(s -> {
            final int i = Integer.parseInt(s);
            int meli = i * count.get();
            count.addAndGet(-1);
            max.addAndGet(meli);
        });
        return max.get() % 11 == 0;
    }

    public boolean checkMeli(String nationalCode) {
        return checkEmpty(nationalCode) && len(nationalCode) && isNumeric(nationalCode)
                && validMeli(nationalCode);
    }
    public boolean validNumber(String number){
        return checkEmpty(number) && isNumeric(number);
    }

}
