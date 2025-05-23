package com.example.api;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetTodos() {
        long responseTime = given()
                .log().all()
                .when()
                .get("/todos")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].userId", equalTo(1))
                .body("[0].title", equalTo("delectus aut autem"))
                .body("[0].completed", equalTo(false))
                .extract()
                .time();

        System.out.println("GET /todos response time: " + responseTime + " ms");
        assert responseTime < 1000 : "GET isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }

    @Test
    public void testCreateTodo() {
        String requestBody = "{"
                + "\"userId\": 1,"
                + "\"title\": \"Learn REST Assured\","
                + "\"completed\": false"
                + "}";

        long responseTime = given()
                .header("Content-type", "application/json; charset=UTF-8")
                .body(requestBody)
                .when()
                .post("/todos")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("userId", equalTo(1))
                .body("title", equalTo("Learn REST Assured"))
                .body("completed", equalTo(false))
                .extract()
                .time();

        System.out.println("POST /todos response time: " + responseTime + " ms");
        assert responseTime < 1000 : "POST isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }
}
