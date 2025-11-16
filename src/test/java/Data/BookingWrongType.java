package Data;

public class BookingWrongType {

    private int firstname;
    private int lastname;
    private String totalprice;
    private int depositpaid;
    private BookingDateWrongType bookingdates;
    private int additionalneeds;

    public BookingWrongType() {}

    public BookingWrongType(int firstname, int lastname, String totalprice, int depositpaid, BookingDateWrongType bookingdates, int additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    public int getFirstname() { return firstname; }
    public void setFirstname(int firstname) { this.firstname = firstname; }
    public int getLastname() { return lastname; }
    public void setLastname(int lastname) { this.lastname = lastname; }
    public String getTotalprice() { return totalprice; }
    public void setTotalprice(String totalprice) { this.totalprice = totalprice; }
    public int isDepositpaid() { return depositpaid; }
    public void setDepositpaid(int depositpaid) { this.depositpaid = depositpaid; }
    public BookingDateWrongType getBookingdates() { return bookingdates; }
    public void setBookingdates(BookingDateWrongType bookingdates) { this.bookingdates = bookingdates; }
    public int getAdditionalneeds() { return additionalneeds; }
    public void setAdditionalneeds(int additionalneeds) { this.additionalneeds = additionalneeds; }

    @Override
    public String toString() {
        return "Booking{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", totalprice=" + totalprice +
                ", depositpaid=" + depositpaid +
                ", bookingdates=" + bookingdates +
                ", additionalneeds='" + additionalneeds + '\'' +
                '}';
    }
}
