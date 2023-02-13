package com.example.springboot.validation;

import com.github.mfathi91.time.PersianDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Validation {


    //section valid date
    public static boolean validDate(String dateUser) {
        while (true) {
            PersianDate systemDate = PersianDate.now();
            String fromSystem = String.valueOf(systemDate);
            try {
                String[] system = fromSystem.split("-");
                String[] user = dateUser.split("-");
                String systemYear = system[0];
                String systemMonth = system[1];
                String systemDay = system[2];
                String userYear = user[0];
                String userMonth = user[1];
                String userDay = user[2];
                try {
                    if (Integer.parseInt(systemYear) == Integer.parseInt(userYear)) {
                        if (Integer.parseInt(systemMonth) == Integer.parseInt(userMonth)) {
                            if (Integer.parseInt(systemDay) < Integer.parseInt(userDay)) {
                                return true;
                            }
                        } else if (Integer.parseInt(systemMonth) < Integer.parseInt(userMonth)) {
                            return true;
                        }
                    } else if (Integer.parseInt(systemYear) < Integer.parseInt(userYear)) {
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }

    //section valid email
    public static boolean validateEmail(String email) {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (email.matches(regex))
            return true;
        return false;
    }


    //section valid Password
    public static boolean validPassword(String string) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (string.matches(regex))
            return true;
        return false;
    }


    //section Number
    public static boolean noNumber(String string) {
        AtomicBoolean flag = new AtomicBoolean(true);
        Arrays.stream(string.split("")).forEach(s -> {
            if (s.matches("[0-9]"))
                flag.set(false);
        });
        return flag.get();
    }


    //section String
    public static boolean validString(String string) {
        boolean result = Validation.noNumber(string);
        InternationalValidation international = new InternationalValidation();
        boolean result2 = international.checkEmpty(string);
        String returnResult = string;
        if (!result || !result2)
            return false;
        return true;
    }

    //section valid number
    public static boolean validNumber(String number) {
        InternationalValidation validateInternation = new InternationalValidation();
        boolean b = validateInternation.validNumber(number);
        String result = number;
        while (!b)
            return false;
        return true;
    }

}
