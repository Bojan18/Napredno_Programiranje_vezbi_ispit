package BooksCollection;

import java.util.*;
import java.util.stream.Collectors;

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

class Book{
    private String title;
    private String category;
    private float price;

    public Book(String title, String category, float number) {
        this.title = title;
        this.category = category;
        this.price = number;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }
}

class BookCollection{

    public List<Book> books;

    public BookCollection(){
        books = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void printByCategory(String category){
        Comparator<Book> comparator = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);
        StringBuilder sb = new StringBuilder();
        books.stream().sorted(comparator).forEach(i -> {
            if(i.getCategory().equals(category)){
                System.out.println(i);
            }
        });
    }

    public List<Book> getCheapestN(int n){
        // враќа листа на најевтините N книги (ако има помалку од N книги во колекцијата, ги враќа сите).
        Comparator<Book> comparator = Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle);
        return books.stream().sorted(comparator).limit(n).collect(Collectors.toList());
    }

}