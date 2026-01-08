package com.rappipay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseIsValid implements Question<Boolean>
{
    @Override
    public Boolean answeredBy(Actor actor) {
        // Solo valida que el código sea de éxito (2xx)
        int statusCode = SerenityRest.lastResponse().statusCode();
        // Retorna true si es cualquier código de éxito (200, 201, 204, etc.)
        return statusCode >= 200 && statusCode < 300;
    }
}