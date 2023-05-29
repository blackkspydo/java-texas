import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        printMenu();

    }

    public static void printMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please choose an option:
                1) Buy Item
                2) Sell Item
                3) Edit Item
                4) Exit""");
        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Buying item...");
                printMenu();
            }
            case 2 -> {
                System.out.println("Selling item...");
                printMenu();
            }
            case 3 -> {
                System.out.println("Editing item...");
                printMenu();
            }
            case 4 -> {
                System.out.println("Exiting...");
            }
            default -> {
                System.out.println("Invalid choice.");
                printMenu();
            }
        }
    }
}
