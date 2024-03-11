import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class UiTest {
    @Test
    public void test() {
        System.out.println("UI тест запущен!");
    }

    @Test
    @Tag("smoke")
    public void testUISmoke() {
        System.out.println("Smoke UI тест запущен!");
    }
}
