package in.reqres.tests;

import in.reqres.models.*;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.Spec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresExtendedTests {

    @Test
    void getModelTest() {

        TestsBodyPojoModel authData = new TestsBodyPojoModel();

        authData.setName("morpheus");
        authData.setJob("zion resident");

        TestsResponseLombokModel modelResponse = step("Make request", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/get-test.json")))
                        .extract().as(TestsResponseLombokModel.class);

        step("Check response", () -> {
            assertEquals("morpheus", modelResponse.getName());
            assertEquals("zion resident", modelResponse.getJob());
        });
    }

    @Test
    void testUser() {

        TestsBodyPojoModel authData = new TestsBodyPojoModel();
        authData.setName("morpheus");
        authData.setJob("leader");

        TestsResponseLombokModel responseLombokModel = step("Make request", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/user-test.json")))
                        .extract().as(TestsResponseLombokModel.class);

        step("Check response", () -> {

            assertEquals("morpheus", responseLombokModel.getName());
            assertEquals("leader", responseLombokModel.getJob());
        });
    }


    @Test
    void testLogin() {

        LoginEmailTest authData = new LoginEmailTest();
        authData.setEmail("peter@klaven");

        LoginEmailResponseTest responseError = step("Make request", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseError400)
                        .body(matchesJsonSchemaInClasspath("schemas/login-test.json")))
                        .extract().as(LoginEmailResponseTest.class);

        step("Verify that the response generated an error", () -> {
            assertEquals("Missing password", responseError.getError());
        });
    }

    @Test
    void testRegister() {

        RegisterRequestTest requestData = new RegisterRequestTest();
        requestData.setEmail("eve.holt@reqres.in");
        requestData.setPassword("pistol");

        RegisterTokenTest tokenTest = step("Make request", () ->
                given(requestSpec)
                        .body(requestData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseToken)
                       .body(matchesJsonSchemaInClasspath("schemas/register-test.json")))
                        .extract().as(RegisterTokenTest.class);

                        step("Check response token", () -> {
                        assertEquals("QpwL5tke4Pnpja7X4", tokenTest.getToken());
    });
    }

    @Test
    void testStatus() {

        step("Make request", () ->
                given(pageSpec)
                        .delete("users/2")
                        .then()
                        .spec(responsePage));
    }
}
