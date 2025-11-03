package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.APIUtils;
import utils.TestContext;
import utils.ReadJSON;
import org.json.JSONObject;
import utils.TokenManager;

import static org.junit.Assert.assertEquals;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class StepDefinitions {

    private String endpoint;
    private Response response;
    private String requestBody;
    private String token;

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

    @Given("I create a new booking {string}")
    public void i_create_a_new_booking(String payload) {
        requestBody = ReadJSON.readJsonFromFile(payload);
        response = APIUtils.postRequest("/booking", requestBody);
        if(response.statusCode()==200) {
            TestContext.setBookingId(response.path("bookingid"));
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
        response = APIUtils.getRequest("/booking/"+TestContext.getBookingId());
        response.then().statusCode(404);
        TestContext.deleteBookingId();
    }

    @When("I retrieve the booking by ID")
    public void iRetrieveTheBookingByID() {
        response = APIUtils.getRequest("/booking/"+TestContext.getBookingId());
    }

    @Then("the booking details should match the created data")
    public void theBookingDetailsShouldMatchTheCreatedData() {
        JSONObject sent = new JSONObject(ReadJSON.readJsonFromFile("createBooking.json"));
        System.out.println(response.getBody().asString());
        JSONObject received = new JSONObject(response.getBody().asString());
        if(received.has("bookingid")) {
            received = received.getJSONObject("booking");
        }

        assertEquals(sent.getString("firstname"), received.getString("firstname"));
        assertEquals(sent.getString("lastname"), received.getString("lastname"));
        assertEquals(sent.getInt("totalprice"), received.getInt("totalprice"));
        assertEquals(sent.getBoolean("depositpaid"), received.getBoolean("depositpaid"));
        assertEquals(sent.getString("additionalneeds"), received.getString("additionalneeds"));

        JSONObject sentDates = sent.getJSONObject("bookingdates");
        JSONObject receivedDates = received.getJSONObject("bookingdates");
        assertEquals(sentDates.getString("checkin"), receivedDates.getString("checkin"));
        assertEquals(sentDates.getString("checkout"), receivedDates.getString("checkout"));
    }

    @When("I update the booking with new data")
    public void iUpdateTheBookingWithNewData() {
        requestBody = ReadJSON.readJsonFromFile("updateBooking.json");
        response = APIUtils.putRequest("/booking/"+TestContext.getBookingId(), requestBody);
        response.then().statusCode(200);
    }

    @Then("the booking details should reflect the update")
    public void theBookingDetailsShouldReflectTheUpdate() {
        JSONObject sent = new JSONObject(ReadJSON.readJsonFromFile("updateBooking.json"));
        JSONObject received = new JSONObject(response.getBody().asString());

        assertEquals(sent.getString("firstname"), received.getString("firstname"));
        assertEquals(sent.getString("lastname"), received.getString("lastname"));
        assertEquals(sent.getInt("totalprice"), received.getInt("totalprice"));
        assertEquals(sent.getBoolean("depositpaid"), received.getBoolean("depositpaid"));
        assertEquals(sent.getString("additionalneeds"), received.getString("additionalneeds"));

        JSONObject sentDates = sent.getJSONObject("bookingdates");
        JSONObject receivedDates = received.getJSONObject("bookingdates");
        assertEquals(sentDates.getString("checkin"), receivedDates.getString("checkin"));
        assertEquals(sentDates.getString("checkout"), receivedDates.getString("checkout"));
    }

    @When("I partialy update the booking with new data")
    public void iPartialyUpdateTheBookingWithNewData() {
        requestBody = ReadJSON.readJsonFromFile("partialUpdateBooking.json");
        response = APIUtils.patchRequest("/booking/"+TestContext.getBookingId(), requestBody);
        response.then().statusCode(200);
    }

    @Then("the booking details should reflect the partial update")
    public void theBookingDetailsShouldReflectThePartialUpdate() {
        JSONObject sent = new JSONObject(ReadJSON.readJsonFromFile("partialUpdateBooking.json"));
        JSONObject received = new JSONObject(response.getBody().asString());

        assertEquals(sent.getString("firstname"), received.getString("firstname"));
        assertEquals(sent.getString("lastname"), received.getString("lastname"));
    }

    @When("I delete the booking")
    public void iDeleteTheBooking() {
        response = APIUtils.deleteRequest("/booking/"+TestContext.getBookingId());
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
            int id = TestContext.getBookingId();
        } catch (Exception e) {
            i_create_a_new_booking("createBooking.json");
        }
    }
}
