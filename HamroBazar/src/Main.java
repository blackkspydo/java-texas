import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DbPool.loadDataFromCSV();
        User user1 = new User("John Doe", "jdoe", "password");
        User user2 = new User("John Doe", "jmoe", "password");
        try {
            DbPool.saveUser(user2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printUsers();

    }
    public static void printUsers() {
        for (User user : DbPool.getAllUsers()) {
            System.out.println(user);
        }
    }
}