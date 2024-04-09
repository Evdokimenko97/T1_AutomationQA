package endpoints;

import io.restassured.response.Response;
import pojo.Register;
import utils.RestApiBuilder;

public class AuthApi {
    public static Response registerNewUser(String login, String password) {
        return new RestApiBuilder("http://9b142cdd34e.vps.myjino.ru:49268").build()
                .body(new Register(login, password))
                .post(Urls.REGISTER);
    }

    public static Response loginUser(String login, String password) {
        return new RestApiBuilder("http://9b142cdd34e.vps.myjino.ru:49268").build()
                .body(new Register(login, password))
                .post(Urls.LOGIN);
    }
}
