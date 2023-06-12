public class ServiceHandlers {
    public  static void loginHandler(String command) {
        String username = Helpers.getOptionValue(command, "-u");
        String password = Helpers.getOptionValue(command, "-p");
        if (username == null || password == null) {
            System.out.println("Invalid command");
            return;
        }
        User user = DbPool.getUserByUsername(username);
        if (user == null) {
            System.out.println("User not found");
            return;
        }
        if (user.getPassword().equals(password)) {
            System.out.println("Login successful");
            User.currentUser = user;
            UI.currentPage = Pages.HOME;
        } else {
            System.out.println("Invalid password");
        }
    }
}
