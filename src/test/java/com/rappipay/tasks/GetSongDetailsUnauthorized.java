package com.rappipay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import com.rappipay.utils.ApiConfig;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetSongDetailsUnauthorized implements Task {

    private final String songId;

    public GetSongDetailsUnauthorized(String songId) {
        this.songId = songId;
    }

    public static GetSongDetailsUnauthorized byId(String songId) {
        return instrumented(GetSongDetailsUnauthorized.class, songId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Get.resource("/songs/v2/get-details")
                        .with(request -> request
                                .header("X-RapidAPI-Key", "")
                                .header("X-RapidAPI-Host", ApiConfig.API_HOST)
                                .queryParam("id", songId)
                                .relaxedHTTPSValidation() // Importante: evita problemas de certificados en redes corporativas
                        )
        );
    }
}