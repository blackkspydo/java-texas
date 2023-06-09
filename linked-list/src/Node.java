public class Node {
    private Integer data;
    private Node next;

    public Node(Integer data) {
        this.data = data;
        this.next = null;
    }

    // Getters and setters
    public Integer getData() {
        return this.data;
    }

    public Node getNext() {
        return this.next;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    // Other methods
    public void printNode() {
        System.out.println(this.data);
    }
}
