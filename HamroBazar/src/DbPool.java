import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class DbPool {
    private static final String USER_FILE = "db/user.csv";
    private static final String PRODUCT_FILE = "db/products.csv";
    private static final String ORDER_FILE = "db/orders.csv";

    private static final HashMap<UUID, User> userMap = new HashMap<>();
    private static final HashMap<UUID, Product> productMap = new HashMap<>();
    private static final HashMap<UUID, Order> orderMap = new HashMap<>();

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
            line = reader.readLine();
        }
        reader.close();
    }

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

    private static void loadOrders() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE));
        String line = reader.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            UUID id = UUID.fromString(fields[0]);
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

    public static void load() throws IOException {
        loadUsers();
        loadProducts();
        loadOrders();
    }

    public static void saveUser(User user) throws IOException {
        // Add or update user in the HashMap
        putUser(user);

        // Write user to the file if it doesn't already exist
        BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
        String line;
        boolean userExists = false;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            UUID id = UUID.fromString(fields[0]);
            if (id == user.getId()) {
                userExists = true;
                break;
            }
        }
        reader.close();

        if (!userExists) {
            FileWriter writer = new FileWriter(USER_FILE, true);
            writer.write(user.getId() + "," + user.getName() + "," + user.getUsername() + "," + user.getPassword() + "\n");
            writer.close();
        }else{
            BufferedReader reader2 = new BufferedReader(new FileReader(USER_FILE));
            String line2;
            StringBuilder inputBuffer = new StringBuilder();
            while ((line2 = reader2.readLine()) != null) {
                String[] fields = line2.split(",");
                UUID id = UUID.fromString(fields[0]);
                if (id == user.getId()) {
                    inputBuffer.append(user.getId()).append(",").append(user.getName()).append(",").append(user.getUsername()).append(",").append(user.getPassword()).append("\n");
                }else{
                    inputBuffer.append(line2).append("\n");
                }
            }
            reader2.close();
            FileWriter writer2 = new FileWriter(USER_FILE);
            writer2.write(inputBuffer.toString());
            writer2.close();
        }
    }

    public static HashMap<UUID, User> getUserMap() {
        return userMap;
    }

    public static HashMap<UUID, Product> getProductMap() {
        return productMap;
    }

    public static HashMap<UUID, Order> getOrderMap() {
        return orderMap;
    }

    public static void putUser(User user) {
        userMap.put(user.getId(), user);


    }

    public static void putProduct(Product product) {
        productMap.put(product.getId(), product);
    }

    public static void putOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }
}
