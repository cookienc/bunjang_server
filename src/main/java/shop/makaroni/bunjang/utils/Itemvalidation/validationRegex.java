package shop.makaroni.bunjang.utils.Itemvalidation;

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
    public static boolean isRegexReportType(String type){
        String itemRegex = "^I[A-E][1-3]$";
        String reviewRegex = "^R[A-C]$";
        Pattern itemPattern = Pattern.compile(itemRegex, Pattern.CASE_INSENSITIVE);
        Matcher itemMatcher = itemPattern.matcher(type);
        Pattern reviewPattern = Pattern.compile(reviewRegex, Pattern.CASE_INSENSITIVE);
        Matcher reviewMatcher = reviewPattern.matcher(type);
        return (itemMatcher.find() || reviewMatcher.find());
    }
    public static boolean isRegexPhoneNumber(String phoneNum){
        String regex = "^(01[01]\\d{7,8})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.find();
    }

}
