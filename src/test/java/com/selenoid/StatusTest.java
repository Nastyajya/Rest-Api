package com.selenoid;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
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
    void checkTotalWithBrowser() {
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
                .body("total", is(20))
                .body("browser.chrome", hasKey("99.0"));
    }
}
