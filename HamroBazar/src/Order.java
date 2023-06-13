import java.util.ArrayList;
import java.util.UUID;

public class Order {
    private String orderId ;
    private ArrayList<String> products = new ArrayList<String>();
    private UUID buyerId;
    private ArrayList<UUID> sellerIds = new ArrayList<UUID>();
    private final double totalPrice;

    public Order(ArrayList<String> products, UUID buyerId,ArrayList<UUID> sellerIds, double totalPrice) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();;
        this.products = products;
        this.buyerId = buyerId;
        this.sellerIds = sellerIds;
        this.totalPrice = totalPrice;
    }
    public Order(String orderId,ArrayList<String> products, UUID buyerId, ArrayList<UUID> sellerIds, double totalPrice) {
        this.orderId = orderId;
        this.products = products;
        this.buyerId = buyerId;
        this.sellerIds = sellerIds;
        this.totalPrice = totalPrice;
    }

    public Order(Order order) {
        this.orderId = order.orderId;
        this.products = order.products;
        this.buyerId = order.buyerId;
        this.sellerIds = order.sellerIds;
        this.totalPrice = order.totalPrice;
    }
    public String getOrderId() {
        return orderId;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public ArrayList<UUID> getSellerIds() {
        return sellerIds;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public void setBuyerId(UUID buyerId) {
        this.buyerId = buyerId;
    }

    public void setSellerIds(ArrayList<UUID> sellerIds) {
        this.sellerIds = sellerIds;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + products.toString() +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerIds.toString() +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
