import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    public static Pages currentPage = Pages.HOME_USER_NOT_LOGGED_IN;

    public static void getHeader(Pages page) {
        String line = """
                +----------------------------------------------------------------------------------+
                                                                                                  
                                          Welcome to HamroBazar MarketPlace                      \s
                   Cart: %d ($%.2f)                                                     %s    \s
                +----------------------------------------------------------------------------------+
                """.formatted(
                User.currentUser != null ? Cart.currentCart.getProducts().size() : 0,
                User.currentUser != null ? Cart.currentCart.getTotal() : 0,
                User.currentUser != null ? User.currentUser.getUsername() : "");
        String lineNotLoggedin = """
                +----------------------------------------------------------------------------------+
                                                                                                  
                                          Welcome to HamroBazar MarketPlace                      \s
                                   
                +----------------------------------------------------------------------------------+
                """;
        System.out.println(User.currentUser != null ? line : lineNotLoggedin);
    }

    public static void getBody(Pages page) {
        String home = User.currentUser == null ? """
                HamroBazar MarketPlace is a platform where you can buy and sell products.
                You can buy/sell products from the following categories:
                +----------------------------------------------------------------------------------+
                | Electronics | Fashion | Home | Sports | Toys | Other |
                +----------------------------------------------------------------------------------+
                Login or Signup to get started.
                +----------------------------------------------------------------------------------+
                Suggested commands:
                login -u <username> -p <password>
                signup -n <name> -u <username> -p <password>
                help | exit
                +----------------------------------------------------------------------------------+
                """ : """
                
                
                HamroBazar MarketPlace is a platform where you can buy and sell products.
                Use "help" command to see the list of available commands.
                
                
                """;

        String profile = User.currentUser != null ? """
                \n
                +----------------------------------------------------------------------------------+
                           | Name : %s | Username : %s | Password : %s
                     %s    +----------------------+----------------------+-------------------------+
                           | BoughtProducts : %d | SoldProducts : %d | Income : %f
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
            case HELP -> help();
            case CART -> cartView();
            case POSTS -> getMyProductPosts();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DbPool.loadDataFromCSV();
        while (true) {
            Thread.sleep(300);
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

    }

    public static void getProductViewPage() {

        ArrayList<Product> products = new ArrayList<>();
        for (Product product : DbPool.getProductMap().values()) {
            if (!product.getSeller().equals(User.currentUser.getId())) {
                products.add(product);
            }
        }
        for (Product product : products) {
            productView(product);
        }
        System.out.println("""
                +----------------------------------------------------------------------------------+
                Suggested commands:
                add cart [-i] [-id <id>,<id>,...] | checkout
                goto <page> (home, profile, marketplace, cart)
                help | exit
                +----------------------------------------------------------------------------------+
                """);
    }

    public static void getMyProductPosts(){
        UI.currentPage = Pages.POSTS;
        if (User.currentUser == null) {
            System.out.println("Please login to view products");
            return;
        }
        StringBuffer line = new StringBuffer("""
                +----------------------------------------------------------------------------------+
                |     ID    |        Name       | Price |       Seller      |       Category       |
                +----------------------------------------------------------------------------------+
                """);
        for (Product product : DbPool.getProductMap().values()) {
            if(product.getSeller().equals(User.currentUser.getId())){
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
        }
        line.append("""
                +----------------------------------------------------------------------------------+
                Suggested commands:
                edit product [-i] [-id <id>] [-n <name>] [-d <description>] [-p <price>] [-c <category>]
                [-co <condition>]
                delete product [-i] [-id <id>]
                goto <page> (home, profile, marketplace, cart)
                help | exit
                +----------------------------------------------------------------------------------+
                """);
        System.out.println(line);
    }

    public static void getFooter(Pages page) {

        String home = """
                +----------------------------------------------------------------------------------+
                Suggested commands:
                goto profile | goto marketplace | goto cart | help | exit
                +----------------------------------------------------------------------------------+
                """;
        String profile = """
                +----------------------------------------------------------------------------------+
                Suggested commands:
                goto <page> (home, profile, marketplace, cart)
                edit profile -n <name> -u <username> -p <password>
                help | exit
                +----------------------------------------------------------------------------------+
                """;
        switch (page) {
            case  HOME,NADA -> System.out.println(home);
            case PROFILE -> System.out.println(profile);
            case POSTS -> System.out.println();
        }
    }

    public  static  void help(){
        UI.currentPage = Pages.NADA;
        String line = """
                +----------------------------------------------------------------------------------+
                | * login -u <username> -p <password>                                              |
                | * signup -n <name> -u <username> -p <password>                                   |
                | * goto <page> (home, profile, post, view, cart)                                  |
                | * edit profile -n <name> -u <username> -p <password>                             |
                | * add product [-i] [-n <name>] [-d <description>] [-p <price>] [-c <category>]   |
                |      [-co <condition>]                                                           |
                | * edit product [-i] [-id <id>] [-n <name>] [-d <description>] [-p <price>]       |
                |      [-c <category>] [-co <condition>] [-s <status>] [-q <quantity>]             |
                | * remove product [-i] [-id <id>]                                                 |
                | * add cart [-i] [-id <id>,<id>,...]                                              |
                | * remove product [-i] [-id <id>,<id>,...]                                        |
                | * checkout                                                                       |
                | * help | exit                                                                    |
                +----------------------------------------------------------------------------------+
                """;
                
        System.out.println(line);
    }

    public static void parseCommand() throws IOException {
        System.out.print("HAMROCLI _> ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        String[] commandArray = command.split(" ");
        int length = commandArray.length;
        String action = commandArray[0];
        String argument = length > 1 ? commandArray[1] : "";
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        switch (action) {
            case "goto" -> {
                switch (argument) {
                    case "home" -> currentPage = Pages.HOME;
                    case "profile" -> currentPage = Pages.PROFILE;
                    case "marketplace" -> currentPage = Pages.PRODUCT_VIEW;
                    case "cart" -> currentPage = Pages.CART;
                    case "posts" -> currentPage = Pages.POSTS;
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
            case "checkout" -> ServiceHandlers.checkoutHandler();
            case "login" -> ServiceHandlers.loginHandler(command);
            case "signup" -> ServiceHandlers.registerHandler(command);
            case "help" -> UI.currentPage = Pages.HELP;
            case "logout" -> ServiceHandlers.logoutHandler();
            case "exit" -> System.exit(0);
            default -> System.out.println("Err: Invalid command");
        }
        System.out.println("\n");

    }


}
