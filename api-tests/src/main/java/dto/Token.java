package dto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.Register;


public class Token {
    public static String getToken(String endPoint, String username, String password) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new Register(username, password))
                .when()
                .post(endPoint);

        String jsonString = response.getBody().asString();
        return JsonPath.from(jsonString).get("access_token");
    }
}
