import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products = new ArrayList<Product>();
    private double total = 0;

    public static Cart currentCart = new Cart(new ArrayList<Product>());


    public Cart(ArrayList<Product> products) {
        this.products = products;
        this.total = calculateTotal();
    }

    public Cart(Cart cart) {
        this.products = cart.products;
        this.total = cart.total;
    }

    public void emptyCart() {
        this.products = new ArrayList<Product>();
        this.total = 0;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }


    public double getTotal() {
        return total;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        this.total = calculateTotal();
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        this.total = calculateTotal();
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                ", total=" + total +
                '}';
    }
}
