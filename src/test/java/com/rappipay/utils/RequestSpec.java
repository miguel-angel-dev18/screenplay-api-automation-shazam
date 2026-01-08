package com.rappipay.utils;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

public class RequestSpec
{
    public static RequestSpecification defaultRequest() {
        return SerenityRest.given()
                .header("X-RapidAPI-Key", ApiConfig.API_KEY)
                .header("X-RapidAPI-Host", ApiConfig.API_HOST);
    }
}
