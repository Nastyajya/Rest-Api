package com.selenoid;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class StatusTest {

    @Test
    void checkTotalMini() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }
    @Test
    void checkTotal() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }
    @Test
    void checkTotalWithSomeLogs() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .body("total", is(20));
    }
    @Test
    void checkTotalWithStatus() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }
    @Test
    void checkTotalWithJsonSchema() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                //.body(matchesJsonSchemaInClasspath("schemas/status-response-schema.json"))
                .body("total", is(20))
                .body("browsers.chrome", hasKey("100.0"));
    }
}
