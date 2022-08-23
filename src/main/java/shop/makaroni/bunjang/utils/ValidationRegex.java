package shop.makaroni.bunjang.utils;

import shop.makaroni.bunjang.src.response.exception.NotRightPasswordEx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static shop.makaroni.bunjang.src.response.ErrorCode.NOT_RIGHT_PASSWORD_EXCEPTION;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static void isRegexPassword(String target) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);

        if (notMatch(matcher)) {
            throw new NotRightPasswordEx(NOT_RIGHT_PASSWORD_EXCEPTION.getMessages());
        }
    }

    private static boolean notMatch(Matcher matcher) {
        return !matcher.find();
    }
}

