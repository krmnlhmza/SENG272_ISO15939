import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private ArrayList<Book> books;
    private static final String FILE_NAME = "books.txt";

    public Library() {
        books = new ArrayList<>();
        loadBooksFromFile();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book add succesfully.");
        saveToFile();
    }

    public void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No book in the library.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, books.get(i).toString());
        }
    }


    public void listAvailableBooks() {
        int cound = 0;
        for (Book b : books) {
            if (!b.getIsBorrowed()) {
                System.out.println(b.toString());
                cound++;
            }
        }
        if (cound == 0) {
            System.out.println("No available books.");
        }
    }


    public Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                return b;
            }
        }
        return null;
    }

    public void borrowBook(String isbn) {
        Book b = findBook(isbn);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        if (b.getIsBorrowed()) {
            System.out.println("This book is already borrowed.");
            return;
        }
        b.setIsBorrowed(true);
        System.out.println("Book borrowed successfully.");
        saveToFile();
    }

    public void returnBook(String isbn) {
        Book b = findBook(isbn);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!b.getIsBorrowed()) {
            System.out.println("This book is already available.");
            return;
        }
        b.setIsBorrowed(false);
        System.out.println("Book returned successfully.");
        saveToFile();
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                bw.write(b.toFileFormat());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving to file: " + e.getMessage());
        }
    }

    private void loadBooksFromFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;

        List<Book> loaded = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;

                String isbn = parts[0];
                String title = parts[1];
                String author = parts[2];
                int year;
                try {
                    year = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {

                    continue;
                }
                boolean borrowed = Boolean.parseBoolean(parts[4]);

                Book b = new Book(isbn, title, author, year);
                b.setIsBorrowed(borrowed);
                loaded.add(b);
            }
            books.clear();
            books.addAll(loaded);
        } catch (IOException e) {
            System.out.println("Error while loading from fle: " + e.getMessage());
        }
    }
}