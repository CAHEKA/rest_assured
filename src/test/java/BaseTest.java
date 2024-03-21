import PojoClass.Product;
import PojoClass.Token;
import PojoClass.UserCred;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

class BaseTest {
    private static final String BASE_URI = "http://9b142cdd34e.vps.myjino.ru:49268";

    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter());
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    public static String getUserToken(UserCred credBody) {
        return given()
                .body(credBody)
                .when()
                .post("/login")
                .then()
                .extract().as(Token.class).getAccessToken();
    }

    public static void userRegister(UserCred creds) {
        given()
                .body(creds)
                .when()
                .post("/register")
                .then()
                .statusCode(201);
    }
    
}
