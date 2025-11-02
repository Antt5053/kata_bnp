package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String token;

    public static String getToken() {
        //Can add token expiration
        if (token == null) {
            generateToken();
        }
        return token;
    }

    private static void generateToken() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + ConfigManager.get("username") + "\", \"password\":\"" + ConfigManager.get("password") + "\"}")
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();

        token = response.path("token");
    }
}