import org.junit.jupiter.api.Tag;

public class Test {
    @org.junit.jupiter.api.Test
    public void test() {
        System.out.println("Main тест запущен!");
    }

    @org.junit.jupiter.api.Test
    @Tag("smoke")
    public void testUISmoke() {
        System.out.println("Smoke Main тест запущен!");
    }
}
