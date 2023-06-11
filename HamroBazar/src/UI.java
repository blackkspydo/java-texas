import java.util.Scanner;

public class UI {
    public static void getHeaderNewUser() {
        String line = """
                +----------------------------------------------------------------------------------+
                                                                                                     
                                           Welcome to HamroBazar MarketPlace                        \s
                                                                                         1. Login   \s
                                                                                         2. Signup  \s
                +----------------------------------------------------------------------------------+
                """;
        System.out.println(line);
    }

    public static void getHeaderExistingUser(User user) {
        String line = """
                +----------------------------------------------------------------------------------+
                                                                                                  
                                          Welcome to HamroBazar MarketPlace                      \s
                                                                                     1. %s       \s
                +----------------------------------------------------------------------------------+
                """.formatted(user.getUsername());
        System.out.printf(line);
    }

    public static void main(String[] args) {
        getHeaderNewUser();
    }
    public static void getFooter(Pages page) {
        String not_logged_in = """
                1.Home         2.Signup
                3.Exit
                """;
        String home = """
                1.Profile       2.Post Ad
                3.View Ads      4.Logout
                """;
        switch (page) {
            case HOME_USER_NOT_LOGGED_IN -> System.out.println(not_logged_in);
            case HOME -> System.out.println(home);
        }
    }

    public static void parseCommand() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        String[] commandArray = command.split(" ");
        String action = commandArray[0];
        String page = commandArray[1];
        System.out.println(action);
        System.out.println(page);
        if (action.equals("go") && page.equals("home")) {
            System.out.println("You are in home page");
        } else if (action.equals("go") && page.equals("profile")) {
            System.out.println("You are in profile page");
        } else if (action.equals("go") && page.equals("post")) {
            System.out.println("You are in post page");
        } else if (action.equals("go") && page.equals("view")) {
            System.out.println("You are in view page");
        } else if (action.equals("go") && page.equals("logout")) {
            System.out.println("You are in logout page");
        } else {
            System.out.println("Err: Invalid command");
        }
    }


}
