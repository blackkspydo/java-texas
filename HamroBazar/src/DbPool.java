

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class DbPool {
    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String USER_FILE = ROOT_DIR + "/src/db/user.csv";
    private static final String PRODUCT_FILE = ROOT_DIR + "/src/db/products.csv";
    private static final String ORDER_FILE = ROOT_DIR + "/src/db/orders.csv";
    private static final HashMap<UUID, User> userMap = new HashMap<>();
    private static final HashMap<String, User> usernameMap = new HashMap<>();
    private static final HashMap<String, Product> productMap = new HashMap<>();
    private static final HashMap<String, Order> orderMap = new HashMap<>();

    /**
     * =============================================
     * ========= User Related Operations ===========
     * =============================================
     */
    private static void loadUsers() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
        String line = reader.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            UUID id = UUID.fromString(fields[0]);
            String name = fields[1];
            String username = fields[2];
            String password = fields[3];

            ArrayList<String> productsPosted = new ArrayList<>();
            ArrayList<String> productsBought = new ArrayList<>();
            ArrayList<String> productsSold = new ArrayList<>();

            // Check if there are product lists to read
            if (fields.length > 4) {
                productsPosted = new ArrayList<>(Arrays.asList(fields[4].split(";")));
            }
            if (fields.length > 5) {
                productsBought = new ArrayList<>(Arrays.asList(fields[5].split(";")));
            }
            if (fields.length > 6) {
                productsSold = new ArrayList<>(Arrays.asList(fields[6].split(";")));
            }

            User user = new User(id, name, username, password);
            user.setProductsPosted(productsPosted);
            user.setProductsBought(productsBought);
            user.setProductsSold(productsSold);
            userMap.put(id, user);
            usernameMap.put(user.getUsername(), user);
            line = reader.readLine();
        }
        reader.close();
    }

    public static void saveUser(@NotNull User user) throws IOException {
        if (!doesUserExist((user.getUsername()))) {
            putUser(user);
            FileWriter writer = new FileWriter(USER_FILE, true);
            String csvString = user.getId() + "," +
                    user.getName() + "," +
                    user.getUsername() + "," +
                    user.getPassword() + "," +
                    String.join(";", user.getProductsPosted()) + "," +
                    String.join(";", user.getProductsBought()) + "," +
                    String.join(";", user.getProductsSold()) + "\n";
            writer.write(csvString);
            writer.close();
            return;
        }
        System.out.println("User already exists");
    }


    public static HashMap<UUID, User> getUserMap() {
        return userMap;
    }

    public static void putUser(User user) {
        userMap.put(user.getId(), user);
        usernameMap.put(user.getUsername(), user);
    }

    public static User getUserByUsername(String username) {
        return usernameMap.get(username);
    }
    public  static User getUserById(UUID id) {
        return userMap.get(id);
    }

    public static void updateUser(User user) throws IOException {
        if (!doesUserExist(user.getId())) {
            System.out.println("User does not exist");
            return;
        }
        userMap.put(user.getId(), user);
        usernameMap.put(user.getUsername(), user);
        BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
        String line;
        StringBuilder inputBuffer = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            UUID userId = UUID.fromString(fields[0]);
            String username = fields[2];

            if (userId.equals(user.getId())) {
                inputBuffer
                        .append(user.getId()).append(",")
                        .append(user.getName()).append(",")
                        .append(user.getUsername()).append(",")
                        .append(user.getPassword()).append(",")
                        .append(String.join(";", user.getProductsPosted())).append(",")
                        .append(String.join(";", user.getProductsBought())).append(",")
                        .append(String.join(";", user.getProductsSold())).append("\n");
            } else {
                inputBuffer.append(line).append("\n");
            }
        }
        reader.close();
        FileWriter writer = new FileWriter(USER_FILE);
        writer.write(inputBuffer.toString());
        writer.close();
    }

    public static boolean doesUserExist(String username) {
        return usernameMap.containsKey(username);
    }

    public  static boolean doesUserExist(UUID id) {
        return userMap.containsKey(id);
    }
    public static User[] getAllUsers() {
        return userMap.values().toArray(new User[0]);
    }

    /**
     * =============================================
     * ========= Product Related Operations ========
     * =============================================
     */
    private static void loadProducts() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE));
        String line = reader.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            String productId = fields[0];
            String name = fields[1];
            String description = fields[2];
            double price = Double.parseDouble(fields[3]);
            Condition condition = Condition.valueOf(fields[4]);
            Category category = Category.valueOf(fields[5]);
            UUID sellerId = UUID.fromString(fields[6]);
            Product product = new Product(productId,name, description, price, condition, category, sellerId);
            productMap.put(productId, product);
            line = reader.readLine();
        }
        reader.close();
    }

    public static void saveProduct(@NotNull Product product) throws IOException {
        if (!doesProductExist(product.getId())) {
            putProduct(product);
            FileWriter writer = new FileWriter(PRODUCT_FILE, true);
            writer.write(product.getId() + "," + product.getName() + "," + product.getDescription() + "," + product.getPrice() + "," + product.getCondition() + "," + product.getCategory() + "," + product.getSellerId() + "\n");
            writer.close();
            User.currentUser.addProductPosted(product.getId());
            updateUser(User.currentUser);
            return;
        }
        System.out.println("Product already exists");
    }

    public static void updateProduct(@NotNull Product product) throws IOException {
        if (doesProductExist(product.getId())) {
            System.out.println("Product does not exist");
            return;
        }
        productMap.put(product.getId(), product);
        BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE));
        String line;
        StringBuilder inputBuffer = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String productId = fields[0];
            if (productId.equals(product.getId())) {
                inputBuffer
                        .append(product.getId()).append(",")
                        .append(product.getName()).append(",")
                        .append(product.getDescription()).append(",")
                        .append(product.getPrice()).append(",")
                        .append(product.getCondition()).append(",")
                        .append(product.getCategory()).append(",")
                        .append(product.getSellerId()).append("\n");
            } else {
                inputBuffer
                        .append(line).append("\n");
            }
        }
        reader.close();
        FileWriter writer = new FileWriter(PRODUCT_FILE);
        writer.write(inputBuffer.toString());
        writer.close();
    }

    public static void deleteProduct(@NotNull Product product) throws IOException {
        if (!doesProductExist(product.getId())) {
            System.out.println("Product does not exist");
            return;
        }
        productMap.remove(product.getId());
        BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE));
        String line;
        StringBuilder inputBuffer = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String productId = fields[0];
            if (!productId.equals(product.getId())) {
                inputBuffer
                        .append(line).append("\n");
            }
        }
        reader.close();
        FileWriter writer = new FileWriter(PRODUCT_FILE);
        writer.write(inputBuffer.toString());
        writer.close();
    }
    public static boolean doesProductExist(String productId) {
        return productMap.containsKey(productId);
    }

    public static HashMap<String, Product> getProductMap() {
        return productMap;
    }

    public static void putProduct(Product product) {
        productMap.put(product.getId(), product);
    }


    /**
     * =============================================
     * ========= Order Related Operations ==========
     * =============================================
     */
    private static void loadOrders() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE));
        String line = reader.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            String id = fields[0];
            ArrayList<String> productIds = new ArrayList<String>();
             productIds.addAll(Arrays.asList(fields[1].split(";")));
            UUID buyerId = UUID.fromString(fields[2]);
            ArrayList<UUID> sellerIds = new ArrayList<>();
            String[] sellerIdsString = fields[3].split(";");
            for (String sellerId : sellerIdsString) {
                sellerIds.add(UUID.fromString(sellerId));
            }
            double totalPrice = Double.parseDouble(fields[4]);
            Order order = new Order(id,productIds, buyerId, sellerIds, totalPrice);
            orderMap.put(id, order);
            line = reader.readLine();
        }
        reader.close();
    }


    public static HashMap<String, Order> getOrderMap() {
        return orderMap;
    }

    public static void putOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    public static void saveOrder(@NotNull Order order) throws IOException {
        if (!doesOrderExist(order.getOrderId())) {
            putOrder(order);
            FileWriter writer = new FileWriter(ORDER_FILE, true);
            String products = String.join(";", order.getProducts());
            ArrayList<String> sellerIdsArr = new ArrayList<>();
            for (UUID sellerId : order.getSellerIds()) {
                sellerIdsArr.add(sellerId.toString());
            }
            String sellerIds = String.join(";", sellerIdsArr);
            String entry = order.getOrderId() + "," +
                    products + "," +
                    order.getBuyerId() + "," +
                    sellerIds + "," +
                    order.getTotalPrice() + "\n";
            writer.write(entry);
            writer.close();

            //update current user orders
            User.currentUser.addProductBought(order.getProducts());
            updateUser(User.currentUser);

            // update seller orders
            for (UUID sellerId : order.getSellerIds()) {
                User seller = new User(getUserById(sellerId));
                seller.addProductSold(order.getProducts());
                updateUser(seller);
            }

            return;
        }
        System.out.println("Order already exists");
    }




    public static Order getOrderById(UUID id) {
        return orderMap.get(id);
    }

    public static boolean doesOrderExist(String orderId) {
        return orderMap.containsKey(orderId);
    }

    /**
     * =============================================
     * ========= Load Data from CSV ================
     * =============================================
     */

    public static void loadDataFromCSV() throws IOException {

        loadUsers();
        loadProducts();
        loadOrders(); //print all hashmap

    }

    public static Product getProductById(String id) {
        return productMap.get(id);
    }

    public static void printHash() {
        for (User user : userMap.values()) {
            System.out.println(user);
        }
        for (Product product : productMap.values()) {
            System.out.println(product);
        }
        for (Order order : orderMap.values()) {
            System.out.println(order);
        }
    }
}
