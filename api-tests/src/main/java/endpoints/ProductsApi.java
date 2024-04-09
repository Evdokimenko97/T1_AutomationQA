package endpoints;

import io.restassured.response.Response;
import pojo.Product;

public class ProductsApi extends BasicApi {

    public ProductsApi(String token) {
        super(token);
    }

    public Response getProducts() {
        return getBuilder()
                .get(Urls.PRODUCTS);
    }

    public Response addNewProduct(long id, String name, String category, double price, int discount) {
        return getBuilder()
                .body(new Product(id, name, category, price, discount))
                .post(Urls.PRODUCTS);
    }

    public Response getProduct(int id) {
        return getBuilder()
                .get(Urls.PRODUCTS + "/" + id);
    }
}
