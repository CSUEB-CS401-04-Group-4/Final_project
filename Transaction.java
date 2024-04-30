// Transaction class representing a borrowing transaction

import java.util.Date;


public class Transaction {
    private Book book;
    private Patron patron;
    private Date checkoutDate;
    private Date returnDate;
    private boolean returned;

    // Constructor
    public Transaction(Book book, Patron patron, Date checkoutDate) {
        this.book = book;
        this.patron = patron;
        this.checkoutDate = checkoutDate;
        this.returned = false;
    }

    // Getters and setters



public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

}