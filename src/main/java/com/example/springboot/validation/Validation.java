package com.example.springboot.validation;

import com.example.springboot.entity.Time;
import com.example.springboot.exeption.OrderException;
import com.github.mfathi91.time.PersianDate;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


public class Validation {


    //section valid date
    public static boolean validDate(String dateUser) {
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

    //section compare date
    public static int compareDate(String dateUser, Time userTime) {
        PersianDate systemDate = PersianDate.now();
        LocalTime time = LocalTime.now();
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
                int mDelay = (60 - userTime.getMinute() + time.getMinute());
                int month = (Integer.parseInt(systemMonth) - (Integer.parseInt(userMonth) + 1)) * 43800;
                int year = (Integer.parseInt(systemYear) - (Integer.parseInt(userYear) + 1)) * 525600;
                final int findDay = Integer.parseInt(systemDay) - (Integer.parseInt(userDay) + 1);
                int day = findDay * 1440;
                if (day < 0)
                    day = 0;
                // اگه سال بزگتر بود پس یوزر زودتر کار رو تحویل داده
                if (Integer.parseInt(systemYear) < Integer.parseInt(userMonth))
                    return 0;
                    // اگه سال مساوی بود باید چک بشه ببینم وضعیت ماه و روز به چه صورتی هست
                else {
                    final boolean ifDay = Integer.parseInt(systemDay) <= Integer.parseInt(userDay);
                    final int monthBig = ((30 - (Integer.parseInt(userDay) + 1)) + Integer.parseInt(systemDay)) * 1440;
                    if (Integer.parseInt(systemYear) == Integer.parseInt(userYear)) {
                        // اگه روز بزرگتر باشه پس یوزر حداقل یک روز زودتر کار رو تحویل داده
                        if (Integer.parseInt(systemMonth) < Integer.parseInt(userMonth))
                            return 0;
                        // اگه سال یکی بود و ماه هم یکی بود
                        if (Integer.parseInt(systemMonth) == Integer.parseInt(userMonth)) {
                            // اگه روز یوزر بزگتر بود پس یوزر زودتر کار رو تحویل داده
                            if (ifDay) {
                                return 0;
                            } else {
                                if (time.getHour() < userTime.getHour() && Integer.parseInt(systemDay) == Integer.parseInt(userDay))
                                    return 0;
                                if (time.getHour() >= userTime.getHour()) {
                                    day = (Integer.parseInt(systemDay) - (Integer.parseInt(userDay))) * 1440;
                                    if (day < 0)
                                        day = 0;
                                    int hour = ((time.getHour() - (userTime.getHour() + 1)) * 60);
                                    return mDelay + day + hour;
                                } else{
                                    int hour = (((24 - (userTime.getHour() + 1)) + time.getHour()) * 60);
                                    return mDelay + day + hour;
                                }
                            }
                        }
                        // اگه ماه یوزر بزرگتر از ماه جاری بود
                        else {
                            if (ifDay) {
                                day = monthBig;
                                if (time.getHour() >= userTime.getHour()) {
                                    int hour = ((time.getHour() - (userTime.getHour() + 1)) * 60);
                                    return day + month + mDelay + hour;
                                } else {
                                    int hour = (((24 - (userTime.getHour() + 1)) + time.getHour()) * 60);
                                    return day + month + mDelay + hour;
                                }
                            } else {
                                day = (findDay * 1440);
                                if (time.getHour() >= userTime.getHour()) {
                                    int hour = ((time.getHour() - (userTime.getHour() + 1)) * 60);
                                    return day + month + mDelay + hour;
                                } else {
                                    int hour = (((24 - (userTime.getHour() + 1)) + time.getHour()) * 60);
                                    return day + month + mDelay + hour;
                                }
                            }
                        }

                    } else {
                        if (Integer.parseInt(systemMonth) <= Integer.parseInt(userMonth)) {
                            month = ((12 - ((Integer.parseInt(userMonth) + 1) - Integer.parseInt(systemMonth)))) * 43800;
                            if (ifDay) {
                                day = monthBig;
                                if (time.getHour() >= userTime.getHour()) {
                                    int hour = ((time.getHour() - (userTime.getHour() + 1)) * 60);
                                    return day + month + mDelay + hour + year;
                                } else {
                                    int hour = (((24 - (userTime.getHour() + 1)) + time.getHour()) * 60);
                                    return day + month + mDelay + hour + year;
                                }
                            }
                        }else{
                            day = (findDay * 1440);
                            if (time.getHour() >= userTime.getHour()) {
                                int hour = ((time.getHour() - (userTime.getHour() + 1)) * 60);
                                return day + month + mDelay + hour + year;
                            } else {
                                int hour = (((24 - (userTime.getHour() + 1)) + time.getHour()) * 60);
                                return day + month + mDelay + hour + year;
                            }
                        }
                    }
                }
                throw new OrderException("cant find out the result in validation");
            } catch (Exception e) {
                throw new OrderException("something wrong");
            }
        } catch (Exception e) {
            return 1;
        }
    }

}
