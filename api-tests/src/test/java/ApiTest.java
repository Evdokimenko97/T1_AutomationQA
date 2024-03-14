import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.Cart;
import pojo.Product;
import pojo.Register;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTest {
    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
        RestAssured.filters(RequestLoggingFilter.logRequestTo(System.out), ResponseLoggingFilter.logResponseTo(System.out));
    }

    @Test
    public void postRegister() {
        Register register = new Register("drusss123", "vilezzz");

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body(register)
                .when()
                .post("/register").getStatusCode();

        assertEquals(201, statusCode, "Ошибка post /register!");
    }

    @Test
    public void postLogin() {
        Register auth = new Register("drus123", "vilezzz");

        int statusCode = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post("/login")
                .getStatusCode();

        assertEquals(200, statusCode, "Ошибка post /login!");
    }

    @Test
    public void getProducts() {

        int statusCode = given().get("/products").getStatusCode();

        assertEquals(200, statusCode, "Ошибка get /products!");
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
                .post("/products").getStatusCode();

        assertEquals(201, statusCode, "Ошибка post /products!");
    }

    @Test
    public void getProductId() {

        List<Product> products = given()
                .when()
                .get("/products")
                .then().statusCode(200)
                .extract().body().jsonPath().getList("", Product.class);

        // Проверка, что есть id в списке
        int expectedId = 1;
        assertTrue(products.stream().anyMatch(product -> product.getId() == expectedId), "Не найден в списке id = " + expectedId);
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
                .body(product)
                .when()
                .put("/products/" + productIdToUpdate).getStatusCode();

        assertEquals(200, statusCode, "Ошибка put /products/" + productIdToUpdate + "!");
    }

    @Test
    public void deleteProduct() {
        int productIdToDelete = 1;

        int statusCode = given()
                .when()
                .delete("/products/" + productIdToDelete)
                .getStatusCode();

        assertEquals(200, statusCode, "Ошибка delete /products/" + productIdToDelete + "!");
    }

    @Test
    public void getCartWithToken() {
        String token = getToken("drusss123", "vilezzz");

        int statusCode = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/cart").statusCode();

        assertEquals(200, statusCode, "Ошибка get /cart!");
    }

    @Test
    public void postCartWithToken() {
        String token = getToken("drusss123","vilezzz");

        Cart cart = new Cart();
        cart.setProduct_id(1);
        cart.setQuantity(2);

        int statusCode = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(cart)
                .when()
                .post("/cart").getStatusCode();

        assertEquals(201, statusCode, "Ошибка post /cart!");
    }

    @Test
    public void deleteCartWithToken() {
        String token = getToken("drusss123","vilezzz");

        int productIdToDelete = 1;

        int statusCode = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/cart/" + productIdToDelete)
                .getStatusCode();

        assertEquals(200, statusCode, "Ошибка delete /cart/" + productIdToDelete + "!");
    }


    public String getToken(String username, String password) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new Register(username, password))
                .when()
                .post("/login");

        String jsonString = response.getBody().asString();
        return JsonPath.from(jsonString).get("access_token");
    }
}
