import PojoClass.Token;
import PojoClass.UserCred;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.given;

class BaseTest {
    private static final String BASE_URI = "http://9b142cdd34e.vps.myjino.ru:49268";

    static final String PRODUCTS_ENDPOINT = "/products";
    static final String CART_ENDPOINT = "/cart";

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
