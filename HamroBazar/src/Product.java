import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Product {
    private final UUID productId = UUID.randomUUID();
    private String name;
    private String description;
    private double price;

    private Condition condition;
    private  Category category;
    private UUID sellerId;

    public Product(String name, String description, double price, Condition condition, Category category, UUID sellerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.category = category;
        this.sellerId = sellerId;
    }

    public UUID getId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return """
                Product {
                    id: %s,
                    name: %s,
                    description: %s,
                    price: %s,
                    condition: %s,
                    category: %s,
                    sellerId: %s
                }
                """.formatted(productId, name, description, price, condition, category, sellerId);
    }

    // addProduct
    public void addProduct(Product product) {
        // TODO: add product to csv file and update the productsPosted list of the seller

        // Get the file path for the products CSV file
        String filePath = "./db/products.csv";

        // Create a new file object for the products CSV file
        File file = new File(filePath);

        try {
            // Create a new FileWriter object to write to the CSV file
            FileWriter writer = new FileWriter(file, true);

            // Write the product details to the CSV file
            writer.write(product.getId() + "," + product.getSellerId() + "," + product.getName() + "," + product.getDescription() + "," + product.getPrice() + "\n");

            // Close the FileWriter object
            writer.close();

            // Update the productsPosted list of the seller
//            User seller = User.getSellerById(product.getSellerId());
//            seller.addProductPosted(product);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
