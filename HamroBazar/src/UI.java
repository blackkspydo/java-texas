
import java.io.IOException;
import java.util.Scanner;
public class UI {
    public static Pages currentPage = Pages.HOME_USER_NOT_LOGGED_IN;

    public static void getHeaderExistingUser(User user) {
        String line = """
                +----------------------------------------------------------------------------------+
                                                                                                  
                                          Welcome to HamroBazar MarketPlace                      \s
                                                                                        %s       \s
                +----------------------------------------------------------------------------------+
                """.formatted(user.getUsername());
        System.out.printf(line);
    }

    public static void getHeader(Pages page) {
        String line = """
                +----------------------------------------------------------------------------------+
                                                                                                  
                                          Welcome to HamroBazar MarketPlace                      \s
                                                                                        %s       \s
                +----------------------------------------------------------------------------------+
                """.formatted(User.currentUser != null ? User.currentUser.getUsername() : "");
        System.out.printf(line);
    }

    public static void getBody(Pages page) {
        String home = """
                                
                THIS IS HOME PAGE
                                
                                
                """;
        String profile = User.currentUser != null ? """
                \n
                +----------------------------------------------------------------------------------+
                           | Name : %s | Username : %s | Password : %s
                     %s    +----------------------+----------------------+-------------------------+
                           | BoughtProducts : %d | SoldProducts : %d | Balance : %d
                +----------------------------------------------------------------------------------+
                """.formatted(
                User.currentUser.getName(),
                User.currentUser.getUsername(),
                "********",
                Helpers.getInitials(User.currentUser.getName()),
                User.currentUser.getProductsBought().size(),
                User.currentUser.getProductsSold().size(),
                User.currentUser.getBalance()) : """
                \n
                \n
                +----------------------------------------------------------------------------------+
                
                                           Please login to view your profile
                
                +----------------------------------------------------------------------------------+
                """;


        switch (page) {
            case HOME_USER_NOT_LOGGED_IN, HOME -> System.out.println(home);
            case PROFILE -> System.out.println(profile);
            }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DbPool.loadDataFromCSV();
        while (true) {
            Thread.sleep(1000);
            getHeader(UI.currentPage);
            getBody(UI.currentPage);
            getFooter(UI.currentPage);
            parseCommand();
        }
    }

    public static void getFooter(Pages page) {
        String not_logged_in = """
                +----------------------------------------------------------------------------------+
                Suggested commands:
                login -u <username> -p <password>
                signup -n <name> -u <username> -p <password>
                help | exit
                +----------------------------------------------------------------------------------+
                """;
        String home = """
                +----------------------------------------------------------------------------------+
                Suggested commands:
                goto profile | goto post | goto view | goto cart | help | exit
                +----------------------------------------------------------------------------------+
                """;
        String profile = """
                +----------------------------------------------------------------------------------+
                Suggested commands:
                goto <page> (home, profile, post, view, cart)
                edit profile -n <name> -u <username> -p <password>
                help | exit
                +----------------------------------------------------------------------------------+
                """;
        switch (page) {
            case HOME_USER_NOT_LOGGED_IN -> System.out.println(not_logged_in);
            case HOME -> System.out.println(home);
            case PROFILE -> System.out.println(profile);
        }
    }

    public static void parseCommand() {
        System.out.print("HAMROCLI _> ");
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
            case "login" -> ServiceHandlers.loginHandler(command);
            case "signup" -> currentPage = Pages.REGISTER;
            case "help" -> currentPage = Pages.HELP;
            case "exit" -> System.exit(0);
            default -> System.out.println("Err: Invalid command");
        }

    }


}
