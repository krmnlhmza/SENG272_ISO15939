public class Book {
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private boolean isBorrowed = false;

    public Book(String isbn, String title, String author, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }


    public String getTitle() {
        return title;

    }
    public String getAuthor() {
        return author;
    }
    public int getPublicationYear() {
        return publicationYear;
    }
    public boolean getIsBorrowed() {
        return isBorrowed;
    }


    public void setIsBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }

    //Override olayı nedir araştır hata !!!!!!!!
    public String toString() {
        String status;
        if (isBorrowed) {
            status = "Borrowed";
        } else {
            status = "Available";
        }

        return String.format(
                "ISBN: %s | Title: %s | Author: %s | Year: %d | Status: %s",
                isbn, title, author, publicationYear, status
        );
    }


    public String toFileFormat() {
        return isbn + "," + title + "," + author + "," + publicationYear + "," + isBorrowed;
    }
}