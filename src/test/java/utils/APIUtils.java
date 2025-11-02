package utils;

import io.restassured.response.Response;
import utils.TokenManager;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class APIUtils {

    public static Response getRequest(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response postRequest(String endpoint, String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response putRequest(String endpoint, String body) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token= "+TokenManager.getToken())
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response patchRequest(String endpoint, String body) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token= "+TokenManager.getToken())
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response deleteRequest(String endpoint) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token= "+TokenManager.getToken())
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}

