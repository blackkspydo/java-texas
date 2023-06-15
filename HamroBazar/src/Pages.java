public enum Pages {
    HOME_USER_NOT_LOGGED_IN("Home User Not Logged In"),
    NADA("Nada"),
    HOME("Home"),
    LOGIN("Login"),
    REGISTER("Register"),
    PROFILE("Profile"),
    HELP("Help"),
    POST_PRODUCT("Post Product"),
    PRODUCT("Product"),
    CART("Cart"),
    CHECKOUT("Checkout"),
    ORDERS("Orders"),
    ORDER("Order"),
    PRODUCT_VIEW("Product View"), POSTS("Posts");

    private final String value;

    Pages(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
