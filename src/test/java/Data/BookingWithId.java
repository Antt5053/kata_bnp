package Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingWithId {
    @JsonProperty("bookingid")
    private Integer bookingId;
    private Booking booking;

    public BookingWithId(){}

    public BookingWithId(int id,Booking booking){
        this.bookingId=id;
        this.booking=booking;
    }

    public int getBookingId(){
        return this.bookingId;
    }
    public void setBookingId(int id){
        this.bookingId=id;
    }
    public Booking getBooking(){
        return this.booking;
    }
    public void setBooking(Booking booking){
        this.booking=booking;
    }
}
