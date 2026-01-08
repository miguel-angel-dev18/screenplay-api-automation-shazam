package com.rappipay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class LastResponseContent implements Question<String> {

    private final String path;

    public LastResponseContent(String path) {
        this.path = path;
    }

    public static LastResponseContent field(String path) {
        return new LastResponseContent(path);
    }

    @Override
    public String answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getString(path);
    }
}