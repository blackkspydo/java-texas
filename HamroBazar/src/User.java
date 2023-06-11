import java.util.ArrayList;
import java.util.UUID;

public class User {

    private UUID id =  UUID.randomUUID();
    private String name;
    private String username;
    private String password;

    private ArrayList<UUID> productsPosted = new ArrayList<UUID>();
    private ArrayList<UUID> productsBought = new ArrayList<UUID>();
    private ArrayList<UUID> productsSold = new ArrayList<UUID>();

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", productsPosted=" + productsPosted +
                ", productsBought=" + productsBought +
                ", productsSold=" + productsSold +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<UUID> getProductsPosted() {
        return productsPosted;
    }

    public void setProductsPosted(ArrayList<UUID> productsPosted) {
        this.productsPosted = productsPosted;
    }

    public ArrayList<UUID> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(ArrayList<UUID> productsBought) {
        this.productsBought = productsBought;
    }

    public ArrayList<UUID> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(ArrayList<UUID> productsSold) {
        this.productsSold = productsSold;
    }

    public void addProductPosted(UUID productId) {
        this.productsPosted.add(productId);
    }

    public void addProductBought(UUID productId) {
        this.productsBought.add(productId);
    }

    public void addProductSold(UUID productId) {
        this.productsSold.add(productId);
    }

    public void removeProductPosted(UUID productId) {
        this.productsPosted.remove(productId);
    }



}
