package com.rappipay.tasks;

import com.rappipay.utils.RequestSpec;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DetectSong implements Task {

    private final String rawData;

    public DetectSong(String rawData) {
        this.rawData = rawData;
    }

    public static DetectSong withData(String rawData) {
        return instrumented(DetectSong.class, rawData);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/songs/v3/detect")
                        .with(request -> RequestSpec.defaultRequest()
                                .header("Content-Type", "text/plain")
                                .queryParam("timezone", "America/Chicago")
                                .queryParam("locale", "en-US")
                                .body(rawData)
                        )
        );
    }
}