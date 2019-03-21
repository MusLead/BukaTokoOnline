package com.lazday.bukatokoonline.utils;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class Converter {

    public static String rupiah(int number){
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(number);
    }
    public static boolean isValidEmailId(String email){
        return isValidEmail(email);
    }
    private static boolean isValidEmail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
