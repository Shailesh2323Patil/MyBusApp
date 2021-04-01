package com.shailesh.mybusappdagger2.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatCls {
    public static String datePattern = "yyyy-MM-dd";
    public static SimpleDateFormat inputFormat = new SimpleDateFormat(datePattern);

    public static String datePattern_2 = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat inputFormat_2 = new SimpleDateFormat(datePattern_2);

    public static String datePattern_3 = "dd-MMM-yyyy hh:mm aa";
    public static SimpleDateFormat inputFormat_3 = new SimpleDateFormat(datePattern_3);

    public static Integer getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);

        return ageInt;
    }

    public static boolean isDateValid(final String date)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");

        boolean valid = false;
        try
        {
            // why 2008-02-2x, 20-11-02, 12012-04-05 are valid date?
            sdf.parse(date);
            // strict mode - check 30 or 31 days, leap year
            sdf.setLenient(false);
            valid = true;

        }
        catch (ParseException e)
        {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }
}
