import assertions.ProductsAssert;
import dto.Token;
import endpoints.AuthApi;
import endpoints.BasicApi;
import endpoints.ProductsApi;
import endpoints.Urls;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Cart;
import pojo.Product;
import pojo.Register;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GetProductsTestsApi extends BasicApi {
    ProductsApi productApi;

    @BeforeEach
    void getAuthToken() {
        String token = AuthApi.loginUser("drusss123", "vilezzz").jsonPath().getString("access_token");
        productApi = new ProductsApi(token);
    }

    @Test
    void testGetProduct() {
        Response response = productApi.getProduct(1);
        ProductsAssert.assertThat(response)
                .checkProductResponse("Electronics", 10, 1, "HP Pavilion Laptop", 10);
    }
    

    Register auth = new Register("drusss123", "vilezzz");

    @Test
    public void postRegister() {

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post(Urls.REGISTER)
                .getStatusCode();

        assertEquals(201, statusCode, "Ошибка post " + Urls.REGISTER + "!");
    }

    @Test
    public void postLogin() {

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post(Urls.LOGIN)
                .getStatusCode();

        assertEquals(200, statusCode, "Ошибка post " + Urls.LOGIN + "!");
    }

    @Test
    public void getProducts() {

        given()
                .get(Urls.PRODUCTS)
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
                .post(Urls.PRODUCTS)
                .getStatusCode();

        assertEquals(201, statusCode, "Ошибка post " + Urls.PRODUCTS + "!");
    }

    @Test
    public void getProductId() {

        List<Product> products = given()
                .when()
                .get(Urls.PRODUCTS)
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat().body(matchesJsonSchemaInClasspath("product_schema.json"))
                .extract().body().jsonPath().getList("", Product.class);

        // Проверка, что есть id в списке
        int expectedId = 1;
        assertTrue(products.stream().anyMatch(product -> product.getId() == expectedId), "Не найден в списке get " + Urls.PRODUCTS + " id = " + expectedId);
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
                .put(Urls.PRODUCTS + "/" + productIdToUpdate)
                .getStatusCode();

        assertEquals(200, statusCode, "Ошибка put " + Urls.PRODUCTS + "/" + productIdToUpdate + "!");
    }

    @Test
    public void putProductNegativeId() {
        int productIdToUpdate = 10000100;

        Response response = given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .put(Urls.PRODUCTS + "/" + productIdToUpdate);

        assertEquals(404, response.getStatusCode(), "Ошибка! Обновление put " + Urls.PRODUCTS + "/" + productIdToUpdate + " произошло!");
        assertTrue(response.getBody().asString().contains("Product not found"));
    }


    @Test
    public void deleteProduct() {
        int productIdToDelete = 1;

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(Urls.PRODUCTS + "/" + productIdToDelete);

        assertEquals(200, response.getStatusCode(), "Ошибка delete " + Urls.PRODUCTS + "/" + productIdToDelete + "!");

        Response negativeResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(Urls.PRODUCTS + "/" + productIdToDelete);

        int badStatusCode = negativeResponse.getStatusCode();

        assertEquals(404, badStatusCode, "При повторном удалении статус код не 404");
        assertTrue("Product not found" .contains(negativeResponse.getBody().asString()), "Ошибка! Не найдено сообщение 'Product not found'");
    }

    @Test
    public void getCart() {
        String token = Token.getToken(Urls.LOGIN, "drusss123", "vilezzz");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(Urls.CART)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat().body(matchesJsonSchemaInClasspath("cart_schema.json"));
    }

    @Test
    public void postCart() {
        String token = Token.getToken(Urls.LOGIN, "drusss123", "vilezzz");

        Cart cart = new Cart();
        cart.setProduct_id(1);
        cart.setQuantity(2);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(cart)
                .when()
                .post(Urls.CART);

        assertEquals(201, response.getStatusCode(), "При добавлении продукта в корзину произошла ошибка!");
        assertTrue(response.getBody().asString().contains("Product added to cart successfully"));
    }

    @Test
    public void postCartWithoutProductId() {
        String token = Token.getToken(Urls.LOGIN, "drusss123", "vilezzz");

        int statusCode = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{ \"quantity\": 2 }")
                .when()
                .post(Urls.CART)
                .getStatusCode();

        assertEquals(400, statusCode, "Ошибка post " + Urls.CART + " отсутствует product_id!");
    }

    @Test
    public void postCartWithoutToken() {

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .post(Urls.CART)
                .getStatusCode();

        assertEquals(401, statusCode, "Ошибка post " + Urls.CART + " без токена!");
    }

    @Test
    public void deleteCart() {
        String token = Token.getToken(Urls.LOGIN, "drusss123", "vilezzz");

        int productIdToDelete = 1;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(Urls.CART + "/" + productIdToDelete);

        assertEquals(200, response.getStatusCode(), "Ошибка delete " + Urls.CART + "/" + productIdToDelete + "!");
    }

    @Test
    public void deleteCartNegative() {
        String token = Token.getToken(Urls.LOGIN, "drusss123", "vilezzz");

        int productIdToDelete = 1;
        Response negativeResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(Urls.CART + "/" + productIdToDelete);

        assertEquals(404, negativeResponse.getStatusCode(), "При повторном удалении статус код не 404");
        assertTrue(negativeResponse.getBody().asString().contains("Product not found"));
    }
}