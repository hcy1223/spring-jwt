package com.huchenyuan.authJwt.auth;

import com.huchenyuan.authJwt.TestsBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.authentication.FormAuthConfig.formAuthConfig;

public class LoginTests extends TestsBase {

    @Test
    void greeting_test() {
        get("greeting").then().statusCode(200);
    }

    @Test
    void user_login_by_form_data_test() {
        given().
                auth().form("user", "password", formAuthConfig().withAutoDetectionOfCsrf()).
                when().
                post("/login").
                then().statusCode(200);
    }

}
