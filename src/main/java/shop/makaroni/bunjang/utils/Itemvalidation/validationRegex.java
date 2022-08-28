package shop.makaroni.bunjang.src.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validationRegex {
    public static boolean isRegexContent(String content) {
        String regex = "^.{10,200}$";
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
        String regex = "^.{2,40}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }



}
