package utils;

public class TestContext {
    private static Integer bookingId;

    public static int getBookingId() {
        return bookingId;
    }

    public static void setBookingId(Integer id) {
        bookingId = id;
    }

    public static void deleteBookingId(){
        bookingId = null;
    }
}