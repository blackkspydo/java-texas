public enum Category {
    ELECTRONICS("Electronics"),
    FASHION("Fashion"),
    HOME("Home"),
    SPORTS("Sports"),
    TOYS("Toys"),
    OTHER("Other");

    private String name;

    Category() {
        this.name = this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Category getCategory(String category) {
        return switch (category.toLowerCase()) {
            case "electronics" -> Category.ELECTRONICS;
            case "fashion" -> Category.FASHION;
            case "home" -> Category.HOME;
            case "sports" -> Category.SPORTS;
            case "toys" -> Category.TOYS;
            case "other" -> Category.OTHER;
            default -> Category.OTHER;
        };
    }


}
