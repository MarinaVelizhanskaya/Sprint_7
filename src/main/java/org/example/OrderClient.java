package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();

    }

    public ValidatableResponse get() {
        return given()
                .spec(getSpec())
                .when()
                .get("/api/v1/orders")
                .then();

    }
}
