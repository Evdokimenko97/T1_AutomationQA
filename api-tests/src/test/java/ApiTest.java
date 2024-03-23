import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pojo.Cart;
import pojo.Product;
import pojo.Register;
import util.Token;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTest extends BaseApiTest {
    public static final String REGISTER_ENDPOINT = "/register";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String PRODUCTS_ENDPOINT = "/products";
    public static final String CART_ENDPOINT = "/cart";

    Register auth = new Register("drusss123", "vilezzz");

    @Test
    public void postRegister() {

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post(REGISTER_ENDPOINT)
                .getStatusCode();

        assertEquals(201, statusCode, "Ошибка post " + REGISTER_ENDPOINT + "!");
    }

    @Test
    public void postLogin() {

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post(LOGIN_ENDPOINT)
                .getStatusCode();

        assertEquals(200, statusCode, "Ошибка post " + LOGIN_ENDPOINT + "!");
    }

    @Test
    public void getProducts() {

        given()
                .get(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat().body(matchesJsonSchemaInClasspath("product_schema.json"));
    }

    @Test
    public void postProduct() {
        Product product = new Product();
        product.setName("New Product");
        product.setCategory("Electronics");
        product.setPrice(12.99);
        product.setDiscount(5);

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(PRODUCTS_ENDPOINT)
                .getStatusCode();

        assertEquals(201, statusCode, "Ошибка post " + PRODUCTS_ENDPOINT + "!");
    }

    @Test
    public void getProductId() {

        List<Product> products = given()
                .when()
                .get(PRODUCTS_ENDPOINT)
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat().body(matchesJsonSchemaInClasspath("product_schema.json"))
                .extract().body().jsonPath().getList("", Product.class);

        // Проверка, что есть id в списке
        int expectedId = 1;
        assertTrue(products.stream().anyMatch(product -> product.getId() == expectedId), "Не найден в списке get " + PRODUCTS_ENDPOINT + " id = " + expectedId);
    }

    @Test
    public void putProduct() {
        Product product = new Product();
        product.setName("Simple");
        product.setCategory("Dimple");
        product.setPrice(1150.50);
        product.setDiscount(0);

        int productIdToUpdate = 1;
        int statusCode = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(product)
                .when()
                .put(PRODUCTS_ENDPOINT + "/" + productIdToUpdate)
                .getStatusCode();

        assertEquals(200, statusCode, "Ошибка put " + PRODUCTS_ENDPOINT + "/" + productIdToUpdate + "!");
    }

    @Test
    public void putProductNegativeId() {
        int productIdToUpdate = 10000100;

        Response response = given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .put(PRODUCTS_ENDPOINT + "/" + productIdToUpdate);

        assertEquals(404, response.getStatusCode(), "Ошибка! Обновление put " + PRODUCTS_ENDPOINT + "/" + productIdToUpdate + " произошло!");
        assertTrue(response.getBody().asString().contains("Product not found"));
    }


    @Test
    public void deleteProduct() {
        int productIdToDelete = 1;

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(PRODUCTS_ENDPOINT + "/" + productIdToDelete);

        assertEquals(200, response.getStatusCode(), "Ошибка delete " + PRODUCTS_ENDPOINT + "/" + productIdToDelete + "!");

        Response negativeResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(PRODUCTS_ENDPOINT + "/" + productIdToDelete);

        int badStatusCode = negativeResponse.getStatusCode();

        assertEquals(404, badStatusCode, "При повторном удалении статус код не 404");
        assertTrue("Product not found" .contains(negativeResponse.getBody().asString()), "Ошибка! Не найдено сообщение 'Product not found'");
    }

    @Test
    public void getCart() {
        String token = Token.getToken(LOGIN_ENDPOINT, "drusss123", "vilezzz");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(CART_ENDPOINT)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat().body(matchesJsonSchemaInClasspath("cart_schema.json"));
    }

    @Test
    public void postCart() {
        String token = Token.getToken(LOGIN_ENDPOINT, "drusss123", "vilezzz");

        Cart cart = new Cart();
        cart.setProduct_id(1);
        cart.setQuantity(2);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(cart)
                .when()
                .post(CART_ENDPOINT);

        assertEquals(201, response.getStatusCode(), "При добавлении продукта в корзину произошла ошибка!");
        assertTrue(response.getBody().asString().contains("Product added to cart successfully"));
    }

    @Test
    public void postCartWithoutProductId() {
        String token = Token.getToken(LOGIN_ENDPOINT, "drusss123", "vilezzz");

        int statusCode = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{ \"quantity\": 2 }")
                .when()
                .post(CART_ENDPOINT)
                .getStatusCode();

        assertEquals(400, statusCode, "Ошибка post " + CART_ENDPOINT + " отсутствует product_id!");
    }

    @Test
    public void postCartWithoutToken() {

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .post(CART_ENDPOINT)
                .getStatusCode();

        assertEquals(401, statusCode, "Ошибка post " + CART_ENDPOINT + " без токена!");
    }

    @Test
    public void deleteCart() {
        String token = Token.getToken(LOGIN_ENDPOINT, "drusss123", "vilezzz");

        int productIdToDelete = 1;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(CART_ENDPOINT + "/" + productIdToDelete);

        assertEquals(200, response.getStatusCode(), "Ошибка delete " + PRODUCTS_ENDPOINT + "/" + productIdToDelete + "!");
    }

    @Test
    public void deleteCartNegative() {
        String token = Token.getToken(LOGIN_ENDPOINT, "drusss123", "vilezzz");

        int productIdToDelete = 1;
        Response negativeResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(CART_ENDPOINT + "/" + productIdToDelete);

        assertEquals(404, negativeResponse.getStatusCode(), "При повторном удалении статус код не 404");
        assertTrue(negativeResponse.getBody().asString().contains("Product not found"));
    }
}