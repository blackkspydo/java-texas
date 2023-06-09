public class Book {
    public String title;
    public Integer pages;
    public Author author;
    public Integer publishedYear;
    public static Integer totalBooks = 0;

    Book(String title, Integer pages, Integer publishedYear, Author author) {
        this.title = title;
        this.pages = pages;
        this.author = author;
        this.publishedYear = publishedYear;
        Book.totalBooks++;
    }

    public String toString() {
        return this.title + " by " + this.author.name + " (born in " + this.author.birthYear + "), " + this.pages + " pages, published in " + this.publishedYear;
    }
}
