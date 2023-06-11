public enum Pages {
    HOME_USER_NOT_LOGGED_IN("Home User Not Logged In"),
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
    ADMIN("Admin"),
    ADMIN_USERS("Admin Users"),
    ADMIN_PRODUCTS("Admin Products"),
    ADMIN_ORDERS("Admin Orders"),
    ADMIN_USER("Admin User"),
    ADMIN_PRODUCT("Admin Product"),
    ADMIN_ORDER("Admin Order");

    private final String value;

    Pages(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
