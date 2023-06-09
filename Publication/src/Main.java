public class Main {
    public static void main(String[] args) {
        // Authors
        Author author1 = new Author("J.K. Rowling", 1965);
        Author author2 = new Author("J.R.R. Tolkien", 1892);
        Author author3 = new Author("George R.R. Martin", 1948);

        // Books
        Book book1 = new Book("Harry Potter and the Philosopher's Stone", 223, 1997, author1);
        Book book2 = new Book("Harry Potter and the Chamber of Secrets", 251, 1998, author2);
        Book book3 = new Book("Harry Potter and the Prisoner of Azkaban", 317, 1999, author3);

        // Print out the books
        System.out.println(book1);
        System.out.println(book2);
        System.out.println(book3);
    }

}