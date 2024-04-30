
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


// Library class managing overall library operations
public class Library {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Transaction> transactions;

    public Library() {
        this.books = new ArrayList<>();
        this.patrons = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    // Methods for adding/removing books, registering patrons, handling transactions
    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public List<Patron> getPatrons() {
        return patrons;
    }

    public void registerPatron(Patron patron) {
        patrons.add(patron);
    }

    public void removePatron(Patron patron) {
        patrons.remove(patron);
    }

    public void checkoutBook(Book book, Patron patron, Date checkoutDate) {
        if (!book.isAvailable()) {
            System.out.println("Book is not available for checkout.");
            return;
        }
        Transaction transaction = new Transaction(book, patron, checkoutDate);
        transactions.add(transaction);
        book.setAvailable(false);
        System.out.println("Book checked out successfully.");
    }

    public void returnBook(Book book, Date returnDate) {
        for (Transaction transaction : transactions) {
            if (transaction.getBook().equals(book) && !transaction.isReturned()) {
                transaction.setReturned(true);
                transaction.setReturnDate(returnDate);
                book.setAvailable(true);
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Book not found in transactions or already returned.");
    }

    // Other methods as needed
}