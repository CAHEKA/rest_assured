import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.*;


public class ApiTests extends BaseTest {
    
    @Test
    public void testRegister() {

        Map<String, String> creds = Map.of(
                "username", UUID.randomUUID().toString(),
                "password", "12345");

        given()
                .body(creds)
                .when()
                .post("/register")
                .then()
                .statusCode(201);
    }

    @Test
    public void testLogin() {
        Map<String, String> creds = Map.of(
                "username", "Qwerty",
                "password", "12345");
        
        given()
                .body(creds)
                .when()
                .post("/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetProducts() {
        when()
                .get("/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAddProduct() {
        Map<String, String> product = Map.of(
                "name", "New Product",
                "category", "Electronics",
                "price", "12.99",
                "discount","5"
        );

        given()
                .body(product)
                .when()
                .post("/products")
                .then()
                .statusCode(201);
    }

    @Test
    public void testGetProductById() {
        when()
                .get("/products/{product_id}", 1)
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateProduct() {
        Map<String, String> product = Map.of(
                "name", "Update Product",
                "category", "Electronics",
                "price", "12.99",
                "discount","5"
        );
        
        given()
                .body(product)
                .when()
                .put("/products/{product_id}", 1)
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteProduct() {
        when()
                .delete("/products/{product_id}", 1)
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetShoppingCart() {
        when()
                .get("/cart")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAddToCart() {
        Map<String, Integer> cart = Map.of(
                "product_id", 1,
                "quantity", 2);
        
        given()
                .body(cart)
                .when()
                .post("/cart")
                .then()
                .statusCode(201);
    }

    @Test
    public void testRemoveFromCart() {
        when()
                .delete("/cart/{product_id}", 1)
                .then()
                .statusCode(200);
    }
}
