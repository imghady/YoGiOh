package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandMatcher {
    public static Matcher getCommandMatcher(String input,String regex){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(input);
        if (matcher.find()){
            return matcher;
        }
        return null;
    }
}
