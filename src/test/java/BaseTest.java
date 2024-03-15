import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    
    private static final String BASE_URI = "http://9b142cdd34e.vps.myjino.ru:49268";

    private static final Map<String, Object> CRED_BODY = Map.of(
            "username", "Qwerty",
            "password", "12345");

    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.requestSpecification = given()
                .header("Authorization", "Bearer " + getToken())
                .contentType("application/json");
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter());
    }

    private static String getToken() {
            return given()
                    .baseUri(BASE_URI)
                    .contentType("application/json")
                    .body(CRED_BODY)
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .extract()
                    .jsonPath().getString("access_token");
    }
}
