package endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import utils.RestApiBuilder;

public class BasicApi{

    protected String token;

    public BasicApi() {
    }

    public BasicApi(String token) {
        this.token = token;
    }

    public RequestSpecification getBuilder() {
        return new RestApiBuilder("http://9b142cdd34e.vps.myjino.ru:49268")
                .addAuth(token)
                .build();
    }

    protected static String toJSON(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected static <T> T toObject(Response response, Class<T> T) {
        try {
            return new ObjectMapper().readValue(response.getBody().prettyPrint(), T);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @BeforeAll
    public static void beforeAll() {
//        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
        RestAssured.filters(RequestLoggingFilter.logRequestTo(System.out), ResponseLoggingFilter.logResponseTo(System.out));
    }
}
