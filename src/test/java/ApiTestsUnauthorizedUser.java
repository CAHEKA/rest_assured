import PojoClass.ListCart;
import PojoClass.Product;
import PojoClass.ProductCard;
import PojoClass.ResponseМessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Named.named;


public class ApiTestsUnauthorizedUser extends BaseTest {

    private static Stream<Arguments> provideSpecs() {
        return Stream.of(
                Arguments.of(named("User without token", new Spec().requestUserWithoutToken())),
                Arguments.of(named("User with wrong token", new Spec().requestUserWithWrongToken()))
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testGetProducts(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .get(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(200)
                .and().extract().as(new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Product.class));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddNewProduct(RequestSpecification userSpec) {
        Product product = Product.builder()
                .name(UUID.randomUUID().toString())
                .category("Electronics")
                .price(10.99)
                .discount(10.0)
                .build();

        given()
                .spec(userSpec)
                .body(product)
                .when()
                .post(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddExistingProduct(RequestSpecification userSpec) {
        Product product = Product.builder()
                .name("HP Pavilion Laptop")
                .category("Electronics")
                .price(10.99)
                .discount(10.0)
                .build();

        given()
                .spec(userSpec)
                .body(product)
                .when()
                .post(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testGetProductById(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .get(PRODUCTS_ENDPOINT + "/{id}", 1)
                .then()
                .statusCode(200)
                .extract().as(Product.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testUpdateProduct(RequestSpecification userSpec) {
        Product product = Product.builder()
                .name(UUID.randomUUID().toString())
                .category("Electronics")
                .price(10.99)
                .discount(10.0)
                .build();

        given()
                .spec(userSpec)
                .body(product)
                .when()
                .put(PRODUCTS_ENDPOINT + "/{id}", 2)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testDeleteProduct(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .delete(PRODUCTS_ENDPOINT + "/{id}", 3)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testGetShoppingCart(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .get(CART_ENDPOINT)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddToCart(RequestSpecification userSpec) {
        ProductCard productCard = new ProductCard(1, 2);
        given()
                .spec(userSpec)
                .body(productCard)
                .when()
                .post(CART_ENDPOINT)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testRemoveFromCart(RequestSpecification userSpec) {
        testAddToCart(userSpec);
        with()
                .spec(userSpec)
                .delete(CART_ENDPOINT + "/{id}", 1)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testRemoveMissingItem(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .delete(CART_ENDPOINT + "/{id}", 100)
                .then()
                .statusCode(401)
                .extract().as(ResponseМessage.class);
    }
}
