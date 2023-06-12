import java.util.Arrays;
import java.util.List;

public class Helpers {
    public static String getOptionValue(String command, String option) {
        List<String> args = Arrays.asList(command.split(" "));
        int index = args.indexOf(option);
        if (index >= 0 && index < args.size() - 1) {
            return args.get(index + 1);
        } else {
            return null;
        }
    }

    public static String getInitials(String name){
        String[] args = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String arg : args) {
            initials.append(arg.substring(0, 1).toUpperCase());
        }
        return initials.toString();
    }

}
