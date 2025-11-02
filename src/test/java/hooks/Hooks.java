package hooks;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import utils.TokenManager;
import utils.ConfigManager;


public class Hooks {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ConfigManager.get("baseUrl");
        System.out.println("Base URI set to: " + RestAssured.baseURI);
        TokenManager.getToken();
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("API test completed.");
    }
}