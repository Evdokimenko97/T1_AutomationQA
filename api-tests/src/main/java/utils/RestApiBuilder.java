package utils;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestApiBuilder {
    private final RequestSpecification requestSpecification;

    public RestApiBuilder(String baseUrl) {
        requestSpecification = RestAssured.given().baseUri(baseUrl)
                .config(RestAssuredConfig
                        .config()
                        .httpClient(HttpClientConfig
                                .httpClientConfig()
                                .setParam("http.connection.timeout", 5000)
                        )
                )
                .contentType(ContentType.JSON)
                .relaxedHTTPSValidation();
    }

    public RequestSpecification build() {
        return requestSpecification;
    }

    public RestApiBuilder addAuth(String token) {
        requestSpecification.header("Autorization", "Bearer " + token);

        return this;
    }

    public RestApiBuilder addHeader(String headerName, String headerValue) {
        requestSpecification.header(headerName, headerValue);

        return this;
    }
}
