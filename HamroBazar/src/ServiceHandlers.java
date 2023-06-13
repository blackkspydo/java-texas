import java.io.IOException;
import java.util.*;

public class ServiceHandlers {
    public  static void loginHandler(String command) {
        String username = Helpers.getOptionValue(command, "-u");
        String password = Helpers.getOptionValue(command, "-p");
        if (username == null || password == null) {
            System.out.println("Invalid command");
            return;
        }
        User user = DbPool.getUserByUsername(username);
        if (user == null) {
            System.out.println("User not found");
            return;
        }
        if (user.getPassword().equals(password)) {
            System.out.println("Login successful");
            User.currentUser = new User(user);
            User.currentUser.setId(user.getId());
            UI.currentPage = Pages.NADA;
        } else {
            System.out.println("Invalid password");
        }
    }

    public  static void registerHandler(String command) throws IOException {
        boolean isInteractive = Helpers.getOptionValue(command, "-i") != null;
        if (isInteractive) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            User user = DbPool.getUserByUsername(username);
            if (user != null) {
                System.out.println("Username already exists");
                return;
            }
            User newUser = new User(name, username, password);
            DbPool.saveUser(newUser);
            System.out.println("User created successfully, please login");
            return;
        }
        String name = Helpers.getOptionValue(command, "-n");
        String username = Helpers.getOptionValue(command, "-u");
        String password = Helpers.getOptionValue(command, "-p");
        if (name == null || username == null || password == null) {
            System.out.println("Invalid command");
            return;
        }
        User user = DbPool.getUserByUsername(username);
        if (user != null) {
            System.out.println("Username already exists");
            return;
        }
        User newUser = new User(name, username, password);
        DbPool.saveUser(newUser);
        System.out.println("User created successfully, please login");
    }

    public static  void editProfileHandler(String command) throws IOException {
        boolean isUserLoggedIn = User.currentUser != null;
        if (!isUserLoggedIn) {
            System.out.println("Please login first");
            return;
        }
        boolean isInteractive = Helpers.getOptionValue(command, "-i") != null;
        if (isInteractive) {
            Scanner scanner = new Scanner(System.in);
               System.out.print("What do you want to change? (name/username/password): ");
                String choice = scanner.nextLine();
                System.out.print("Enter new value: ");
                String value = scanner.nextLine();
                switch (choice) {
                    case "name" -> User.currentUser.setName(value);
                    case "username" -> {
                        User user = DbPool.getUserByUsername(value);
                        if (user != null) {
                            System.out.println("Username already exists");
                            return;
                        }
                        User.currentUser.setUsername(value);
                    }
                    case "password" -> User.currentUser.setPassword(value);
                    default -> {
                        System.out.println("Invalid choice");
                        return;
                    }
                }
        }
        String name = Helpers.getOptionValue(command, "-n");
        String username = Helpers.getOptionValue(command, "-u");
        String password = Helpers.getOptionValue(command, "-p");
        if (name == null && username == null && password == null) {
            System.out.println("Invalid command");
            return;
        }
        if (name != null) {
            User.currentUser.setName(name);
        }
        if (username != null) {
            User user = DbPool.getUserByUsername(username);
            if (user != null) {
                System.out.println("Username already exists");
                return;
            }
            User.currentUser.setUsername(username);
        }
        if (password != null) {
            User.currentUser.setPassword(password);
        }
        DbPool.updateUser(User.currentUser);
        System.out.println("Profile updated successfully");
    }

    public static void logoutHandler() {
        User.currentUser = null;
        UI.currentPage = Pages.HOME_USER_NOT_LOGGED_IN;
        System.out.println("Logged out successfully");
    }

    public static void addProductHandler(String command) throws IOException {
        boolean isInteractive = command.contains("-i");
        boolean isHelp = command.contains("--help");
        UI.currentPage = Pages.NADA;
         if(isInteractive){
             Scanner scanner = new Scanner(System.in);
                System.out.println("Enter product name:");
                String name = scanner.nextLine();
                System.out.println("Enter product description:");
                String description = scanner.nextLine();
                System.out.println("Enter product price:");
                String price = scanner.nextLine();
                System.out.println("Enter product category:");
                System.out.println("(Electronics|Fashion|Home|Sports|Toys|Other)");
                String category = scanner.nextLine();
                System.out.println("Enter product condition (New|Used|Refurbished):");
                String condition = scanner.nextLine();
                Product product = new Product(name, description, Double.parseDouble(price), Condition.getCondition(condition), Category.getCategory(category), User.currentUser.getId());
                DbPool.saveProduct(product);
                System.out.println("Product added successfully");
                return;
         }
        String name = Helpers.getOptionValue(command, "-n");
        String description = Helpers.getOptionValue(command, "-d");
        String price = Helpers.getOptionValue(command, "-p");
        String category = Helpers.getOptionValue(command, "-c");
        String condition = Helpers.getOptionValue(command, "-co");
        if (isHelp) {
            System.out.println("Usage: add-product [-i] [-n <name>] [-d <description>] [-p <price>] [-c <category>] [-co <condition>]");
            return;
        }
        if (name == null || description == null || price == null || category == null || condition == null) {
            System.out.println("Invalid command");
            return;
        }
        if (User.currentUser == null) {
            System.out.println("Please login first");
            return;
        }
        Product product = new Product(name, description, Double.parseDouble(price), Condition.getCondition(condition), Category.getCategory(category), User.currentUser.getId());
        DbPool.saveProduct(product);
        System.out.println("Product added successfully");
    }

    public static void editProductHandler(String command) throws IOException {
        boolean isInteractive = command.contains("-i");
        boolean isHelp = command.contains("--help");
        UI.currentPage = Pages.NADA;
        if(isInteractive){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which product do you want to edit?");
            String id = scanner.nextLine();
            System.out.println("What do you want to edit?");
            System.out.println("(name|description|price|category|condition)");
            String field = scanner.nextLine();
            System.out.println("Enter new value:");
            String value = scanner.nextLine();
            Product product = DbPool.getProductById(id);
            if (product == null) {
                System.out.println("Product not found");
                return;
            }
            switch (field) {
                case "name":
                    product.setName(value);
                    break;
                case "description":
                    product.setDescription(value);
                    break;
                case "price":
                    product.setPrice(Double.parseDouble(value));
                    break;
                case "category":
                    product.setCategory(Category.getCategory(value));
                    break;
                case "condition":
                    product.setCondition(Condition.getCondition(value));
                    break;
                default:
                    System.out.println("Invalid field");
                    return;
            }
            DbPool.updateProduct(product);
            System.out.println("Product updated successfully");
            return;
        }
        String id = Helpers.getOptionValue(command, "-id");
        String name = Helpers.getOptionValue(command, "-n");
        String description = Helpers.getOptionValue(command, "-d");
        String price = Helpers.getOptionValue(command, "-p");
        String category = Helpers.getOptionValue(command, "-c");
        String condition = Helpers.getOptionValue(command, "-co");
        if (isHelp) {
            System.out.println("Usage: edit-product [-i] [-id <id>] [-n <name>] [-d <description>] [-p <price>] [-c <category>] [-co <condition>]");
            return;
        }
        if (id == null || name == null || description == null || price == null || category == null || condition == null) {
            System.out.println("Invalid command");
            return;
        }
        if (User.currentUser == null) {
            System.out.println("Please login first");
            return;
        }
        Product product = new Product(name, description, Double.parseDouble(price), Condition.getCondition(condition), Category.getCategory(category), User.currentUser.getId());
        product.setId(id);
        DbPool.updateProduct(product);
        System.out.println("Product updated successfully");
    }

    public static void deleteProductHandler(String command) throws IOException {
        boolean isInteractive = command.contains("-i");
        boolean isHelp = command.contains("--help");
        UI.currentPage = Pages.NADA;
        if(isInteractive){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which product do you want to delete?");
            String id = scanner.nextLine();
            Product product = DbPool.getProductById(id);
            if (product == null) {
                System.out.println("Product not found");
                return;
            }
            DbPool.deleteProduct(product);
            System.out.println("Product deleted successfully");
            return;
        }
        String id = Helpers.getOptionValue(command, "-id");
        if (isHelp) {
            System.out.println("Usage: delete-product [-i] [-id <id>]");
            return;
        }
        if (id == null) {
            System.out.println("Invalid command");
            return;
        }
        if (User.currentUser == null) {
            System.out.println("Please login first");
            return;
        }
        Product product = DbPool.getProductById(id);
        if (product == null) {
            System.out.println("Product not found");
            return;
        }
        DbPool.deleteProduct(product);
        System.out.println("Product deleted successfully");
    }


    public static void addToCartHandler(String command) throws IOException {
        boolean isInteractive = command.contains("-i");
        boolean isHelp = command.contains("--help");
        UI.currentPage = Pages.NADA;

        if (isInteractive) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which product(s) do you want to add to cart? (comma-separated)");
            String input = scanner.nextLine();
            String[] ids = input.split(",");
            for (String id : ids) {
                Product product = DbPool.getProductById(id);
                if (product == null) {
                    System.out.println("Product with ID " + id + " not found");
                    continue;
                }
                Cart.currentCart.addProduct(product);
                System.out.println("Product with ID " + id + " added to cart successfully");
            }
            return;
        }

        if (isHelp) {
            System.out.println("Usage: add-to-cart [-i] [-id <id>,<id>,...]");
            return;
        }

        // Extract the list of IDs from the command string
        String[] argsArray = command.split(" ");
        ArrayList<String> ids = new ArrayList<>(Arrays.asList(argsArray[2].split(",")));

        if (User.currentUser == null) {
            System.out.println("Please login first");
            return;
        }

        // Add each product to the cart
        for (String id : ids) {
            Product product = new Product(DbPool.getProductById(id));
            if (product == null) {
                System.out.println("Product with ID " + id + " not found");
                continue;
            }
            Cart.currentCart.addProduct(product);
            System.out.println("Product with ID " + id + " added to cart successfully");
        }
    }

    public static void removeProductFromCartHandler(String command) throws IOException {
        boolean isInteractive = command.contains("-i");
        boolean isHelp = command.contains("--help");
        UI.currentPage = Pages.NADA;

        if (isInteractive) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which product(s) do you want to remove from cart? (comma-separated)");
            String input = scanner.nextLine();
            String[] ids = input.split(",");
            for (String id : ids) {
                Product product = new Product(DbPool.getProductById(id));
                if (product == null) {
                    System.out.println("Product with ID " + id + " not found");
                    continue;
                }
                Cart.currentCart.removeProduct(product);
                System.out.println("Product with ID " + id + " removed from cart successfully");
            }
            return;
        }

        if (isHelp) {
            System.out.println("Usage: remove product [-i] [-id <id>,<id>,...]");
            return;
        }

        // Extract the list of IDs from the command string
        String[] argsArray = command.split(" ");
        ArrayList<String> ids = new ArrayList<>(Arrays.asList(argsArray[2].split(",")));

        if (User.currentUser == null) {
            System.out.println("Please login first");
            return;
        }

        // Remove each product from the cart
        for (String id : ids) {
            Product product = new Product(DbPool.getProductById(id));
            if (product == null) {
                System.out.println("Product with ID " + id + " not found");
                continue;
            }
            Cart.currentCart.removeProduct(product);
            System.out.println("Product with ID " + id + " removed from cart successfully");
        }
    }

    public static void checkoutHandler(String command) throws IOException {
        // checkout
        // has no interactive mode and it places Order directly show a ascii art of a shopping cart
        // and then show the total price and ask for confirmation
        // if confirmed then place the order and show a success message
        // if not then show a message that the order is cancelled
        // if the cart is empty then show a message that the cart is empty
        // if the user is not logged in then show a message that the user is not logged in

        if (User.currentUser == null) {
            System.out.println("Please login first");
            return;
        }
        if (Cart.currentCart.getProducts().isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        UI.cartView();
        System.out.println("Total price: " + Cart.currentCart.getTotal());
        System.out.println("Do you want to checkout? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("y")) {
            ArrayList<String> productIds = new ArrayList<>();
            ArrayList<UUID> sellerIds = new ArrayList<UUID>();
            for (Product product : Cart.currentCart.getProducts()) {
                productIds.add(product.getId());
                sellerIds.add(product.getSellerId());
            }
            Order order = new Order(productIds,User.currentUser.getId(),sellerIds, Cart.currentCart.getTotal());
            DbPool.saveOrder(order);
            System.out.println("Order placed successfully");
            Cart.currentCart.emptyCart();
        } else {
            System.out.println("Order cancelled");
        }

    }


}
