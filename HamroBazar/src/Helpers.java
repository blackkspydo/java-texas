import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {
    public static String getOptionValue(String command, String option) {
        List<String> args = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while (m.find())
            args.add(m.group(1).replace("\"", ""));
        int index = args.indexOf(option);
        if (index >= 0 && index < args.size() - 1) {
            return args.get(index + 1);
        } else {
            return null;
        }
    }


    public static String getInitials(String name) {
        String[] args = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String arg : args) {
            initials.append(arg.substring(0, 1).toUpperCase());
        }
        return initials.toString();
    }

}
