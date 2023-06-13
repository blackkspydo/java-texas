import java.util.ArrayList;
import java.util.UUID;

public class User {

    private UUID id ;
    private String name;
    private String username;
    private String password;

    private ArrayList<String> productsPosted = new ArrayList<String>();
    private ArrayList<String> productsBought = new ArrayList<String>();
    private ArrayList<String> productsSold = new ArrayList<String>();

    public static User currentUser;

    public User(String name, String username, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public User(UUID id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.username = user.username;
        this.password = user.password;
        this.productsPosted = user.productsPosted;
        this.productsBought = user.productsBought;
        this.productsSold = user.productsSold;
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

    public ArrayList<String> getProductsPosted() {
        return productsPosted;
    }

    public void setProductsPosted(ArrayList<String> productsPosted) {
        this.productsPosted = productsPosted;
    }

    public ArrayList<String> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(ArrayList<String> productsBought) {
        this.productsBought = productsBought;
    }

    public ArrayList<String> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(ArrayList<String> productsSold) {
        this.productsSold = productsSold;
    }

    public void addProductPosted(String productId) {
        this.productsPosted.add(productId);
    }

    public void addProductBought(String productId) {
        this.productsBought.add(productId);
    }

    public void addProductSold(String productId) {
        this.productsSold.add(productId);
    }

    public void removeProductPosted(String productId) {
        this.productsPosted.remove(productId);
    }

    //TODO: Implement this method
    public Integer getBalance() {
        Integer balance = 0;

        return balance;
    }

}
