package com.geekbrains.backend.test.imgur;

import java.util.Properties;
import com.geekbrains.backend.test.FunctionalTest;
import io.restassured.RestAssured;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.AuthenticationSpecificationImpl;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class ImgurApiAbstractTest extends FunctionalTest {

    protected static RequestSpecification requestSpecification;

    static {
        try {
            Properties properties = readProperties();
            RestAssured.baseURI = properties.getProperty("imgur-api-url");
            String TOKEN = properties.getProperty("imgur-api-token");
            requestSpecification = new RequestSpecBuilder()
                    .setAuth(new OAuth2Scheme())
                    .build();
            AuthenticationSpecification auth = new AuthenticationSpecificationImpl(requestSpecification);
            requestSpecification = auth.oauth2(TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Спецификация ответа дз 4
    protected static ResponseSpecification responseSpecification;

    static {
        try {
            responseSpecification = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectResponseTime(Matchers.lessThan(2000L))
                    .expectContentType("application/json")
                    .expectBody("status", is(200))
                    .expectBody("success", is(true))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
