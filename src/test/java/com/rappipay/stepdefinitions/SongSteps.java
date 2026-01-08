package com.rappipay.stepdefinitions;

import com.rappipay.questions.LastResponseContent;
import com.rappipay.questions.ResponseIsValid;
import com.rappipay.tasks.DetectSong;
import com.rappipay.tasks.GetSongDetails;
import com.rappipay.tasks.GetSongDetailsUnauthorized;
import com.rappipay.utils.ApiConfig;
import com.rappipay.utils.SongConstants;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class SongSteps
{

    private Actor user;

    @Before
    public void setUp() {
        user = Actor.named("API User");
        user.can(CallAnApi.at(ApiConfig.BASE_URL));
    }

    @Given("el usuario configura el servicio de Shazam")
    public void configurarServicio() {
        // La configuración base se hereda del @Before y ApiConfig
    }

    @Given("el usuario configura el servicio de Shazam sin credenciales validas")
    public void configurarServicioSinCredenciales() {
        // Preparación lógica para escenario negativo
    }

    @When("consulta los detalles de una canción")
    public void consultarCancion() {
        user.attemptsTo(GetSongDetails.byId(SongConstants.ANOTHER_SONG_ID));
    }

    @When("intenta detectar una canción con el payload {string}")
    public void detectarCancion(String payload) {
        user.attemptsTo(DetectSong.withData(payload));
    }

    @When("consulta los detalles de una canción sin autorización")
    public void consultaCancionSinAutorizacion() {
        user.attemptsTo(GetSongDetailsUnauthorized.byId(SongConstants.VALID_SONG_ID));
    }

    @Then("la respuesta debe ser exitosa")
    public void validarRespuesta() {
        user.should(seeThat(new ResponseIsValid()));
    }

    @Then("la respuesta debe coincidir con el esquema de respuesta de la canción")
    public void validarEsquemaDeCancion() {
        user.should(
                ResponseConsequence.seeThatResponse("El formato JSON cumple con el contrato",
                        response -> response.body(matchesJsonSchemaInClasspath("schemas/song-details-schema.json"))
                )
        );
    }

    @Then("la respuesta debe contener los headers esperados")
    public void validarHeaders() {
        user.should(
                ResponseConsequence.seeThatResponse("Validación de Headers de seguridad y contenido",
                        response -> response.header("Content-Type", containsString("application/json"))
                                .header("Server", notNullValue())
                )
        );
    }

    @Then("el tiempo de respuesta debe ser menor a {int} milisegundos")
    public void validarTiempoRespuesta(int tiempoMaximo) {
        user.should(
                ResponseConsequence.seeThatResponse("Validación de performance",
                        response -> response.time(lessThan((long) tiempoMaximo))
                )
        );
    }

    @Then("el servicio debe responder con codigo {int}")
    public void validarCodigoRespuesta(int codigo) {
        user.should(
                seeThat("El código de estado HTTP esperado",
                        actor -> SerenityRest.lastResponse().statusCode(), is(codigo))
        );
    }

    @Then("el mensaje de error debe indicar acceso no autorizado")
    public void validarMensajeError() {
        user.should(
                seeThat("El mensaje de error informativo",
                        LastResponseContent.field("message"),
                        anyOf(
                                containsString(SongConstants.AUTH_ERROR_MESSAGES[0]),
                                containsString(SongConstants.AUTH_ERROR_MESSAGES[1]),
                                containsString(SongConstants.AUTH_ERROR_MESSAGES[2])
                        )
                )
        );
    }
}