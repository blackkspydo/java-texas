import java.io.IOException;
import java.util.Scanner;

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
            User.currentUser = user;
            UI.currentPage = Pages.NADA;
        } else {
            System.out.println("Invalid password");
        }
    }

    public  static void registerHandler(String command) throws IOException {
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
               System.out.print("Name: ");
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
}
