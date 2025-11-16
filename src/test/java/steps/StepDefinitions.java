package steps;

import Data.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.APIUtils;
import utils.TestContext;
import utils.TokenManager;

import static org.junit.Assert.assertEquals;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinitions {

    private TestContext testContext;
    private String endpoint;
    private Response response;
    private String requestBody;
    private String token;
    private Booking booking = new Booking();
    private BookingDates bookingDates = new BookingDates();
    private BookingWithId bookingWithId = new BookingWithId();
    private ObjectMapper mapper = new ObjectMapper();

    public StepDefinitions(TestContext testContext){
        this.testContext=testContext;
    }

    @Given("the API endpoint is {string}")
    public void the_api_endpoint_is(String url) {
        endpoint = url;
    }

    @When("I send a GET request")
    public void i_send_a_get_request() {
        response = given()
                .when()
                .get(endpoint);
    }

    @Given("I create a new booking")
    public void i_create_a_new_booking() {
        bookingDates = new BookingDates("2005-01-01","2005-01-05");
        booking = new Booking("Wilson","doudodidon",100,true,bookingDates,"cake");

        try {
            requestBody = mapper.writeValueAsString(booking);
            response = APIUtils.postRequest("/booking", requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if(response.statusCode()==200) {
            testContext.setBookingId(response.path("bookingid"));
        }
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should match the booking schema")
    public void the_response_should_match_the_booking_schema() {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schema/booking.json"));
    }

    @Then("the booking should no longer exist")
    public void theBookingShouldNoLongerExist() {
        response = APIUtils.getRequest("/booking/"+testContext.getBookingId());
        response.then().statusCode(404);
        testContext.deleteBookingId();
    }

    @When("I retrieve the booking by ID")
    public void iRetrieveTheBookingByID() {
        response = APIUtils.getRequest("/booking/"+testContext.getBookingId());
    }

    @Then("the booking details should match the created data")
    public void theBookingDetailsShouldMatchTheCreatedData() {
        System.out.println("debug1:"+response.getBody().asString());
        bookingWithId = response.getBody().as(BookingWithId.class);
        Booking receivedBooking = bookingWithId.getBooking();

        assertEquals(booking.toString(), receivedBooking.toString());
    }

    @When("I update the booking with new data")
    public void iUpdateTheBookingWithNewData() {
        booking.setFirstname("w");
        booking.setLastname("test");
        booking.setTotalprice(0);
        booking.setDepositpaid(false);
        booking.setBookingdates(new BookingDates("2010-01-01","2010-01-05"));
        try {
            requestBody = mapper.writeValueAsString(booking);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response = APIUtils.putRequest("/booking/"+testContext.getBookingId(), requestBody);
        response.then().statusCode(200);
    }

    @Then("the booking details should reflect the update")
    public void theBookingDetailsShouldReflectTheUpdate() {
        Booking bookingReceived = response.getBody().as(Booking.class);
        assertEquals(booking.toString(), bookingReceived.toString());
    }

    @When("I partialy update the booking with new data")
    public void iPartialyUpdateTheBookingWithNewData() {

        booking.setFirstname("w");
        try {
            requestBody = mapper.writeValueAsString(booking);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response = APIUtils.patchRequest("/booking/"+testContext.getBookingId(), requestBody);
        response.then().statusCode(200);
    }

    @Then("the booking details should reflect the partial update")
    public void theBookingDetailsShouldReflectThePartialUpdate() {
        Booking bookingReceived = response.getBody().as(Booking.class);
        assertEquals(booking.toString(), bookingReceived.toString());
    }

    @When("I delete the booking")
    public void iDeleteTheBooking() {
        response = APIUtils.deleteRequest("/booking/"+testContext.getBookingId());
        response.then().statusCode(201);
    }

    @When("I create an auth token")
    public void i_create_an_auth_token() {
        token = TokenManager.getToken();
    }

    @Then("the token should not be empty")
    public void the_token_should_not_be_empty() {
        if (token == null || token.isEmpty()) {
            throw new AssertionError("Token is empty");
        }
    }

    @Given("a booking already exist")
    public void aBookingAlreadyExist() {
        try {
            int id = testContext.getBookingId();
        } catch (Exception e) {
            i_create_a_new_booking();
        }
    }

    @Given("The firstname of the person booking is {string}")
    public void theFirstnameOfThePersonBookingIs(String firstname) {
        booking.setFirstname(firstname);
    }

    @Then("The lastname of the person booking is {string}")
    public void theLastnameOfThePersonBookingIs(String lastname) {
        booking.setLastname(lastname);
    }

    @Then("The price of the booking is {int}")
    public void thePriceOfTheBookingIs(int price) {
        booking.setTotalprice(price);
    }

    @Then("The the deposit of the booking is payed")
    public void theTheDepositOfTheBookingIsPayed() {
        booking.setDepositpaid(true);
    }

    @Then("the booking date start the {string}")
    public void theBookingDateStartTheAndEndThe(String checkin) {
        bookingDates.setCheckin(checkin);
    }

    @Then("the booking date end the {string}")
    public void theBookingDateEndThe(String checkout) {
        bookingDates.setCheckout(checkout);
        booking.setBookingdates(bookingDates);
    }

    @Then("The additional needs is {string}")
    public void theAdditionlNeedsIs(String additionalNeed) {
        booking.setAdditionalneeds(additionalNeed);
    }

    @Then("I create the booking")
    public void iCreateTheBooking() {
        try {
            requestBody = mapper.writeValueAsString(booking);
            response = APIUtils.postRequest("/booking", requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Then("the booking details should match booking")
    public void theBookingDetailsShouldMatchBooking() {
        Booking receivedBooking = response.as(Booking.class);
        assertEquals(booking.toString(), receivedBooking.toString());
    }

    @Given("I create a new booking with checking before checkout")
    public void iCreateANewBookingWithCheckingBeforeCheckout() {
        bookingDates = new BookingDates("2005-01-05","2005-01-01");
        booking = new Booking("Wilson","doudodidon",100,true,bookingDates,"cake");

        try {
            requestBody = mapper.writeValueAsString(booking);
            response = APIUtils.postRequest("/booking", requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("the booking details should match")
    public void theBookingDetailsShouldMatch() {
        Booking bookingReceived = response.getBody().as(Booking.class);
        assertEquals(booking.toString(), bookingReceived.toString());
    }

    @Given("I create a new booking with wrong values")
    public void iCreateANewBookingWithWrongValues() {
        BookingWrongType wrongBooking = new BookingWrongType(10,11,"trets",12,new BookingDateWrongType(true,false),10);
        try {
            requestBody = mapper.writeValueAsString(wrongBooking);
            response = APIUtils.postRequest("/booking", requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
