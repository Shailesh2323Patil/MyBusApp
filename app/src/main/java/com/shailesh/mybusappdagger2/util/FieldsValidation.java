package com.shailesh.mybusappdagger2.util;

import android.util.Patterns;

public class FieldsValidation
{
    public static boolean isEmailValid(String emailAddress)
    {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public static boolean isBuisnessEmailValid(String email)
    {
        if (email == null)
        {
            return false;
        }
        else if(email.matches(".*\\b@gmail\\b.*"))
        {
            return false;
        }
        else if(email.matches(".*\\b@google\\b.*"))
        {
            return false;
        }
        else if(email.matches(".*\\b@yahoo\\b.*"))
        {
            return false;
        }
        else if(email.matches(".*\\b@outlook\\b.*"))
        {
            return false;
        }
        else if(email.matches(".*\\b@hotmail\\b.*"))
        {
            return false;
        }
        else if(email.matches(".*\\b@msn\\b.*"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        if(phoneNumber.length() == 10)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isTextAlphanumeric(String text)
    {
        return text.matches("[A-Za-z0-9]+");
    }

    public static boolean isTextIsLetter(String text)
    {
        return ((!text.equals(""))
                && (text != null)
                && (text.matches("^[a-zA-Z]*$")));
    }

    public static boolean isTextIsNumber(String text)
    {
        try {
            Double number = Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPasswordValid(String password)
    {
        return password.length() > 3;
    }

    public static boolean isPasswordMatch(String password,String cofirmPassword)
    {
        if(password.equals(cofirmPassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
