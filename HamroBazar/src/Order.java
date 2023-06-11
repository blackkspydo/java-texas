import java.util.UUID;

public class Order {
    private String orderId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    private UUID productId;
    private UUID buyerId;
    private UUID sellerId;
    private double price;
    private int quantity;
    private final double totalPrice;
    private OrderStatus status;

    public Order(UUID productId, UUID buyerId, UUID sellerId, double price, int quantity) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
        this.status = OrderStatus.PENDING;
    }

    public static void main(String[] args) {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 100, 2);
        System.out.println(order);
    }
    public String getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setBuyerId(UUID buyerId) {
        this.buyerId = buyerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
