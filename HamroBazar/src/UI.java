import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    public static Pages currentPage = Pages.HOME_USER_NOT_LOGGED_IN;

    public static void getHeader(Pages page) {
        String line = """
                +----------------------------------------------------------------------------------+
                                                                                                  
                                          Welcome to HamroBazar MarketPlace                      \s
                                                                                        %s       \s
                +----------------------------------------------------------------------------------+
                """.formatted(User.currentUser != null ? User.currentUser.getUsername() : "");
        System.out.printf(line);
        switch (page) {
            case NADA -> System.out.println();
            case HOME -> System.out.println(line);
        }
    }

    public static void getBody(Pages page) {
        String home = User.currentUser != null ? """
                HamroBazar MarketPlace is a platform where you can buy and sell products.
                You can buy products from the following categories:
                +----------------------------------------------------------------------------------+
                | Electronics | Fashion | Home | Sports | Toys | Other |
                +----------------------------------------------------------------------------------+
                You can sell products in the following categories:
                +----------------------------------------------------------------------------------+
                | Electronics | Fashion | Home | Sports | Toys | Other |
                +----------------------------------------------------------------------------------+
                                
                +----------------------------------------------------------------------------------+
                Suggested commands:
                login -u <username> -p <password>
                signup -n <name> -u <username> -p <password>
                help | exit
                +----------------------------------------------------------------------------------+
                """ : "";

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
            case PRODUCT_VIEW -> getProductViewPage();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DbPool.loadDataFromCSV();
        while (true) {
            Thread.sleep(1000);
            System.out.println(Cart.currentCart);
            if (User.currentUser != null) {
                System.out.println(User.currentUser.getId());
            }
            getHeader(UI.currentPage);
            getBody(UI.currentPage);
            getFooter(UI.currentPage);
            parseCommand();
        }
    }

    public static void productView(Product product) {
        if (User.currentUser == null) {
            System.out.println("Please login to view products");
            return;
        }
        User seller = new User(DbPool.getUserById(product.getSeller()));
        String line = """
                +----------------------------------------------------------------------------------+
                | ID : %s | Name : %s | Price : %.2f | Condition : %s |
                +----------------------------------------------------------------------------------+
                | Description : %s
                +----------------------------------------------------------------------------------+
                | Seller : %s  | Category : %s
                +----------------------------------------------------------------------------------+
                """.formatted(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCondition(),
                product.getCategory().getName(),
                product.getDescription(),
                seller.getName()
        );
        System.out.println(line);
    }

    public static void cartView() {
        // show list of products in cart with their prices and total price and let user know to checkout
        // if user wants to remove a product from cart, he can do so by typing remove <product_id>
        UI.currentPage = Pages.CART;
        if (User.currentUser == null) {
            System.out.println("Please login to view products");
            return;
        }

        StringBuffer line = new StringBuffer("""
                +----------------------------------------------------------------------------------+
                |     ID    |        Name       | Price |       Seller      |       Category       |
                +----------------------------------------------------------------------------------+
                """);
        for (Product product : Cart.currentCart.getProducts()) {
            line.append("""
                    | %9s | %17s | %.2f | %17s | %20s |
                    +----------------------------------------------------------------------------------+
                    """.formatted(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    DbPool.getUserById(product.getSeller()).getName(),
                    product.getCategory().getName()
            ));
        }
        line.append("""
                | Total Price : %.2f                                                              |
                +----------------------------------------------------------------------------------+
                """.formatted(Cart.currentCart.getTotal()));
        System.out.println(line);
        System.out.println("""
                +----------------------------------------------------------------------------------+
                Suggested commands:
                remove <product_id>
                checkout
                goto <page> (home, profile, post, view, cart)
                help | exit
                +----------------------------------------------------------------------------------+
                """);
    }

    public static void getProductViewPage() {

        ArrayList<Product> products = new ArrayList<>();
        for (Product product : DbPool.getProductMap().values()) {
            if (product.getSeller() != User.currentUser.getId()) {
                products.add(product);
            }
        }
        for (Product product : products) {
            productView(product);
        }
        System.out.println("""
                +----------------------------------------------------------------------------------+
                Suggested commands:
                buy <product_id>
                goto <page> (home, profile, post, view, cart)
                help | exit
                +----------------------------------------------------------------------------------+
                """);
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

    public static void parseCommand() throws IOException {
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
                    case "shop" -> currentPage = Pages.PRODUCT_VIEW;
                    case "cart" -> cartView();
                    default -> System.out.println("Err: Invalid command");
                }
            }
            case "back" -> currentPage = Pages.HOME;
            case "add" -> {
                switch (argument) {
                    case "product" -> ServiceHandlers.addProductHandler(command);
                    case "cart" -> ServiceHandlers.addToCartHandler(command);
                    default -> System.out.println("Err: Invalid command");
                }
            }
            case "edit" -> {
                switch (argument) {
                    case "profile" -> ServiceHandlers.editProfileHandler(command);
                    case "product" -> ServiceHandlers.editProductHandler(command);
                    default -> System.out.println("Err: Invalid command");
                }
            }
            case "delete" -> {
                if (argument.equals("product")) {
                    ServiceHandlers.deleteProductHandler(command);
                } else {
                    System.out.println("Err: Invalid command");
                }
            }
            case "remove" -> {
                if (argument.equals("product")) {
                    ServiceHandlers.removeProductFromCartHandler(command);
                } else {
                    System.out.println("Err: Invalid command");
                }
            }
            case "next" -> System.out.println("Next page");
            case "prev" -> System.out.println("Previous page");
            case "login" -> ServiceHandlers.loginHandler(command);
            case "signup" -> ServiceHandlers.registerHandler(command);
            case "help" -> currentPage = Pages.HELP;
            case "logout" -> ServiceHandlers.logoutHandler();
            case "exit" -> System.exit(0);
            default -> System.out.println("Err: Invalid command");
        }

    }


}
