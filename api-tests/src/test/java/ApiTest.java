import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testAPI() {
        System.out.println("API тест запущен!");
    }

    @Test
    @Tag("smoke")
    public void testAPISmoke() {
        System.out.println("Smoke API тест запущен!");
    }
}
