import java.util.UUID;

public class Product {
    private String productId ;
    private String name;
    private String description;
    private double price;

    private Condition condition;
    private  Category category;
    private UUID sellerId;

    public Product(String name, String description, double price, Condition condition, Category category, UUID sellerId) {
        this.productId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = name;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.category = category;
        this.sellerId = sellerId;
    }
    public Product(String id,String name, String description, double price, Condition condition, Category category, UUID sellerId) {
        this.productId = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.category = category;
        this.sellerId = sellerId;
    }

    public Product(Product product) {
        this.productId = product.productId;
        this.name = product.name;
        this.description = product.description;
        this.price = product.price;
        this.condition = product.condition;
        this.category = product.category;
        this.sellerId = product.sellerId;
    }

    public String getId() {
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

    public  void setId(String id) {
        this.productId = id;
    }

    public UUID getSeller() {
        return sellerId;
    }
}
