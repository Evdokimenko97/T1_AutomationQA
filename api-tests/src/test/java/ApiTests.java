public class ApiTests {
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
