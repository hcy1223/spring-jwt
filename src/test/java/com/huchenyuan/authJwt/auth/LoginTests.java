package com.huchenyuan.authJwt.auth;

import com.huchenyuan.authJwt.TestsBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

@DisplayName("login test")
public class LoginTests extends TestsBase {

    @Test
    void user_login_by_form_data_test() {
                given()
                        .contentType(ContentType.URLENC)
                        .formParam("username", "user")
                        .formParam("password", "password")
                .when()
                        .post("/login")
                .then()
                        .statusCode(200);

        get("greeting").then().statusCode(200);
    }
}
