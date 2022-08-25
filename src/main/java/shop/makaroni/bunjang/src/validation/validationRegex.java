package shop.makaroni.bunjang.src.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validationRegex {
    public static boolean isRegexPhoneNum(String target) {
        String regex = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexContent(String content) {
        String regex = "^\\S{10,200}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
    public static boolean isRegexCategory(String category) {
        String regex = "^E[A-Z0-9]*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(category);
        return matcher.find();
    }
    public static boolean isRegexItemName(String name){
        String regex = "^\\S{2,40}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }



}
