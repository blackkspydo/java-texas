import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        User user1 = new User("John Doe", "jdoe", "password");
        try {
            DbPool.saveUser(user1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}