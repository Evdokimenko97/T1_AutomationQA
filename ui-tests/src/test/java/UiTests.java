import org.junit.Test;

public class UiTests {
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
