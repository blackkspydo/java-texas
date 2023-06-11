import java.util.Scanner;

public class UI {
    public static Pages currentPage = Pages.HOME_USER_NOT_LOGGED_IN;
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
        while (true){
            System.out.println(currentPage+" is the current page");
            parseCommand();
        }
    }

    public static void getFooter(Pages page) {
        String not_logged_in = """
                +---------------------------------------------------------------------------------+
                Suggested commands:
                login | signup | help | exit
                +---------------------------------------------------------------------------------+
                """;
        String home = """
                +---------------------------------------------------------------------------------+
                Suggested commands:
                goto profile | goto post | goto view | goto cart | help | exit
                +---------------------------------------------------------------------------------+
                """;
        String profile = """
                +---------------------------------------------------------------------------------_+
                Suggested commands:
                goto home | goto cart | help | exit
                edit profile
                +----------------------------------------------------------------------------------
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
        int length = commandArray.length;
        String action = commandArray[0];
        String argument = length > 1 ? commandArray[1] : "";
        switch (action) {
            case "goto" -> {
                switch (argument) {
                    case "home" -> currentPage = Pages.HOME;
                    case "profile" -> currentPage = Pages.PROFILE;
                    case "post" -> currentPage = Pages.POST_PRODUCT;
                    case "view" -> currentPage = Pages.PRODUCT;
                    case "cart" -> currentPage = Pages.CART;
                    default -> System.out.println("Err: Invalid command");
                }
            }
            case "back" -> currentPage = Pages.HOME;
            case "add" -> {
                switch (argument) {
                    case "product" -> System.out.println("Product added");
                    case "cart" -> System.out.println("Product added to cart");
                    default -> System.out.println("Err: Invalid command");
                }
            }
            case "edit" -> {
                switch (argument) {
                    case "profile" -> System.out.println("Profile edited");
                    case "product" -> System.out.println("Product edited");
                    default -> System.out.println("Err: Invalid command");
                }
            }
            case "next" -> System.out.println("Next page");
            case "prev" -> System.out.println("Previous page");
            case "login" -> currentPage = Pages.LOGIN;
            case "signup" -> currentPage = Pages.REGISTER;
            case "help"-> currentPage = Pages.HELP;
            case "exit" -> System.exit(0);
            default -> System.out.println("Err: Invalid command");
        }

    }


}
