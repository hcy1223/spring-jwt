package com.huchenyuan.authJwt.auth;

import com.huchenyuan.authJwt.TestsBase;
import com.huchenyuan.authJwt.dto.JwtRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class LoginTests extends TestsBase {

    @Test
    void greeting_test() {
        get("greeting").then().statusCode(401);
    }

    @Test
    void user_login_by_form_data_test() {
        given().
                body(new JwtRequest("xiaoming", "password")).
                contentType(ContentType.JSON).
                when().
                post("/login").
                then().
                statusCode(200);
    }

}
