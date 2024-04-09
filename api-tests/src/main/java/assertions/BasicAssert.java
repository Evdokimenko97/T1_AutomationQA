package assertions;

import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class BasicAssert extends AbstractAssert<BasicAssert, Response> {

    public BasicAssert(Response actual) {
        super(actual, BasicAssert.class);
    }

    public static BasicAssert assertThat(Response actual) {
        return new BasicAssert(actual);
    }

    public BasicAssert statusCodeIsEqual(int expectedCode) {
        Assertions.assertThat(actual.getStatusCode())
                .as("Код ответа не равен " + expectedCode)
                .isEqualTo(expectedCode);

        return this;
    }

    public BasicAssert responseContainsField(String path) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as("Поле " + path + " не найдено в теле ответа")
                .isNotNull();

        return this;
    }

    public BasicAssert responseFieldIsEqual(String path, String value) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as(String.format("Поле '%s' не равно '%s'", path, value))
                .isEqualTo(value);

        return this;
    }

    public BasicAssert responseFieldIsEqual(String path, int value) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as(String.format("Поле '%s' не равно '%s'", path, value))
                .isEqualTo(value);

        return this;
    }
}
