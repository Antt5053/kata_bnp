package Data;

public class BookingDateWrongType {
    private boolean checkin;
    private boolean checkout;

    public BookingDateWrongType() {}

    public BookingDateWrongType(boolean checkin, boolean checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public boolean getCheckin() { return checkin; }
    public void setCheckin(boolean checkin) { this.checkin = checkin; }
    public boolean getCheckout() { return checkout; }
    public void setCheckout(boolean checkout) { this.checkout = checkout; }

    @Override
    public String toString() {
        return "BookingDates{" +
                "checkin='" + checkin + '\'' +
                ", checkout='" + checkout + '\'' +
                '}';
    }
}
