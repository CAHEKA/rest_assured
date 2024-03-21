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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Named.named;


public class ApiTestsAuthorizedUser extends BaseTest {

    
    private static Stream<Arguments> provideSpecs() {
        return Stream.of(
                Arguments.of(named("New User", new Spec().requestNewUser())),
                Arguments.of(named("Old User", new Spec().requestOldUser()))
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
                .price(12.99)
                .discount(5.0)
                .build();

        given()
                .spec(userSpec)
                .body(product)
                .when()
                .post(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(201)
                .extract().as(ResponseМessage.class)
                .getMessage().equals("Product added successfully");
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddNewProductWithoutName(RequestSpecification userSpec) {
        Product product = Product.builder()
                .price(12.99)
                .discount(5.0)
                .build();

        given()
                .spec(userSpec)
                .body(product)
                .when()
                .post(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(400)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddNewProductWithoutBody(RequestSpecification userSpec) {
        given()
                .spec(userSpec)
                .when()
                .post(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(400)
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
                .statusCode(400)
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
    public void testUpdateWithoutProduct(RequestSpecification userSpec) {
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
                .put(PRODUCTS_ENDPOINT + "/{id}", 20000000)
                .then()
                .statusCode(404)
                .extract().as(ResponseМessage.class)
                .getMessage().equals("Product not found");
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
                .statusCode(200)
                .extract().as(ResponseМessage.class)
                .getMessage().equals("Product updated successfully");
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testDeleteProduct(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .delete(PRODUCTS_ENDPOINT + "/{id}", 3)
                .then()
                .statusCode(200)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testDeleteWithoutProduct(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .delete(PRODUCTS_ENDPOINT + "/{id}", 3)
                .then()
                .statusCode(404)
                .extract().as(ResponseМessage.class)
                .getMessage().equals("Product not found");
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testGetShoppingCart(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .get(CART_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().as(ListCart.class);
        
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
                .statusCode(201)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddNonExistentProductToCart(RequestSpecification userSpec) {
        ProductCard productCard = new ProductCard(999999999, 2);

        given()
                .spec(userSpec)
                .body(productCard)
                .when()
                .post(CART_ENDPOINT)
                .then()
                .statusCode(404)
                .extract().as(ResponseМessage.class)
                .getMessage().equals("Product not found");
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddNegativeQuantityProductToCart(RequestSpecification userSpec) {
        ProductCard productCard = new ProductCard(1, -20);

        given()
                .spec(userSpec)
                .body(productCard)
                .when()
                .post(CART_ENDPOINT)
                .then()
                .statusCode(400)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddProductWithoutQuantityToCart(RequestSpecification userSpec) {
        given()
                .spec(userSpec)
                .body(Map.of("product_id", 1))
                .when()
                .post(CART_ENDPOINT)
                .then()
                .statusCode(400)
                .extract().as(ResponseМessage.class);
    }
    
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testAddProductWithoutBodyToCart(RequestSpecification userSpec) {
        given()
                .spec(userSpec)
                .when()
                .post(CART_ENDPOINT)
                .then()
                .statusCode(400)
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
                .statusCode(200)
                .extract().as(ResponseМessage.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideSpecs")
    public void testRemoveMissingItem(RequestSpecification userSpec) {
        with()
                .spec(userSpec)
                .delete(CART_ENDPOINT + "/{id}", 1000000)
                .then()
                .statusCode(404)
                .extract().as(ResponseМessage.class)
                .getMessage().equals("Product not found");
    }
}
