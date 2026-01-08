package com.rappipay.tasks;
import com.rappipay.utils.RequestSpec;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetSongDetails implements Task
{

    private final String songId;

    public GetSongDetails(String songId)
    {
        this.songId = songId;
    }

    public static GetSongDetails byId(String songId) {
        return new GetSongDetails(songId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/songs/v2/get-details")
                        .with(request -> RequestSpec.defaultRequest()
                                .queryParam("id", songId)
                                .queryParam("l", "en-US").log().all()
                        )

        );

    }
}
