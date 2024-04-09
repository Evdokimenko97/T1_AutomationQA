package assertions;

import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;

public class ProductsAssert extends AbstractAssert<ProductsAssert, Response> {

    protected ProductsAssert(Response actual) {
        super(actual, ProductsAssert.class);
    }

    public static ProductsAssert assertThat(Response actual) {
        return new ProductsAssert(actual);
    }

    public ProductsAssert checkProductResponse(String category, int discount, int id, String name, int price) {
        BasicAssert.assertThat(actual)
                .statusCodeIsEqual(200)
                .responseFieldIsEqual("category", category)
                .responseFieldIsEqual("discount", discount)
                .responseFieldIsEqual("id", id)
                .responseFieldIsEqual("name", name)
                .responseFieldIsEqual("price", price);

        return this;
    }
}
