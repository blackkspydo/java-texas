import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class DbPool {
    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String USER_FILE = ROOT_DIR + "/src/db/user.csv";
    private static final String PRODUCT_FILE = ROOT_DIR + "/src/db/products.csv";
    private static final String ORDER_FILE = ROOT_DIR + "/src/db/orders.csv";
    private static final HashMap<UUID, User> userMap = new HashMap<>();
    private static final HashMap<String, User> usernameMap = new HashMap<>();
    private static final HashMap<UUID, Product> productMap = new HashMap<>();
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
            User user = new User(name, username, password);
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
            writer.write(user.getId() + "," + user.getName() + "," + user.getUsername() + "," + user.getPassword() + "\n");
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

    public static void updateUser(User user) throws IOException {
        if (doesUserExist(user.getUsername())) {
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
            String username = fields[2];
            if (username.equals(user.getUsername())) {
                inputBuffer.append(user.getId()).append(",").append(user.getName()).append(",").append(user.getUsername()).append(",").append(user.getPassword()).append("\n");
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
            UUID productId = UUID.fromString(fields[0]);
            String name = fields[1];
            String description = fields[2];
            double price = Double.parseDouble(fields[3]);
            Condition condition = Condition.valueOf(fields[4]);
            Category category = Category.valueOf(fields[5]);
            UUID sellerId = UUID.fromString(fields[6]);
            Product product = new Product(name, description, price, condition, category, sellerId);
            productMap.put(productId, product);
            line = reader.readLine();
        }
        reader.close();
    }

    public static void saveProduct(@NotNull Product product) throws IOException {
        if (!doesProductExist(product.getId())) {
            System.out.println("Product does not exist");
            putProduct(product);
            FileWriter writer = new FileWriter(PRODUCT_FILE, true);
            writer.write(product.getId() + "," + product.getName() + "," + product.getDescription() + "," + product.getPrice() + "," + product.getCondition() + "," + product.getCategory() + "," + product.getSellerId() + "\n");
            writer.close();
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
            UUID productId = UUID.fromString(fields[0]);
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

    public static boolean doesProductExist(UUID productId) {
        return productMap.containsKey(productId);
    }

    public static HashMap<UUID, Product> getProductMap() {
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
            UUID productId = UUID.fromString(fields[1]);
            UUID buyerId = UUID.fromString(fields[2]);
            UUID sellerId = UUID.fromString(fields[3]);
            double price = Double.parseDouble(fields[4]);
            int quantity = Integer.parseInt(fields[5]);
            double totalPrice = Double.parseDouble(fields[6]);
            OrderStatus status = OrderStatus.valueOf(fields[7]);
            Order order = new Order(productId, buyerId, sellerId, price, quantity);
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
            System.out.println("Order does not exist");
            putOrder(order);
            FileWriter writer = new FileWriter(ORDER_FILE, true);
            String entry = order.getOrderId() + "," +
                    order.getProductId() + "," +
                    order.getBuyerId() + "," +
                    order.getSellerId() + "," +
                    order.getPrice() + "," +
                    order.getQuantity() + "," +
                    order.getTotalPrice() + "," +
                    order.getStatus() + "\n";
            writer.write(entry);
            writer.close();
            return;
        }
        System.out.println("Order already exists");
    }

    public static void updateOrderStatus(String orderId, OrderStatus status) throws IOException {
        if (doesOrderExist(orderId)) {
            System.out.println("Order does not exist");
            return;
        }
        Order order = orderMap.get(orderId);
        order.setStatus(status);
        orderMap.put(orderId, order);
        BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE));
        String line;
        StringBuilder inputBuffer = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String id =fields[0];
            if (id.equals(orderId)) {
                inputBuffer
                        .append(order.getOrderId()).append(",")
                        .append(order.getProductId()).append(",")
                        .append(order.getBuyerId()).append(",")
                        .append(order.getSellerId()).append(",")
                        .append(order.getPrice()).append(",")
                        .append(order.getQuantity()).append(",")
                        .append(order.getTotalPrice()).append(",")
                        .append(order.getStatus()).append("\n");
            } else {
                inputBuffer
                        .append(line).append("\n");
            }
        }
        reader.close();
        FileWriter writer = new FileWriter(ORDER_FILE);
        writer.write(inputBuffer.toString());
        writer.close();
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
        loadOrders();
    }
}
