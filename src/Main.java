import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("\n=== Library Menu ==");
            System.out.println("1) Add Book");
            System.out.println("2) List All Books");
            System.out.println("3) List Availabla Books");
            System.out.println("4) Borrow Book");
            System.out.println("5) Return Book");
            System.out.println("0) Exit");
            System.out.print("Select: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("ISBN ");
                    String isbn = scanner.nextLine();
                    System.out.print("Title ");
                    String title = scanner.nextLine();
                    System.out.print("Author ");
                    String author = scanner.nextLine();
                    System.out.print("Year ");
                    int year;
                    try {
                        year = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year.");
                        break;
                    }
                    Book b = new Book(isbn, title, author, year);
                    library.addBook(b);
                }
                case 2 -> library.listAllBooks();
                case 3 -> library.listAvailableBooks();
                case 4 -> {
                    System.out.print("ISBN to borrow ");
                    String isbn = scanner.nextLine();
                    library.borrowBook(isbn);
                }
                case 5 -> {
                    System.out.print("ISBN to return ");
                    String isbn = scanner.nextLine();
                    library.returnBook(isbn);
                }
                case 0 -> {
                    System.out.println("by");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid selection.");
            }
        }
    }
    //merge deneme1
}