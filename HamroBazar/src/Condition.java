public enum Condition {
    NEW("New"),
    USED("Used"),
    REFURBISHED("Refurbished");

    private String name;

    Condition() {
        this.name = this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }

    Condition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Condition getCondition(String condition) {
        switch (condition.toLowerCase()) {
            case "new":
                return Condition.NEW;
            case "used":
                return Condition.USED;
            case "refurbished":
                return Condition.REFURBISHED;
            default:
                return null;
        }
    }
}
