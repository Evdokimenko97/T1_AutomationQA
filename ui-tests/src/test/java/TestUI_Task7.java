import com.codeborne.selenide.Selenide;
import core.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestUI_Task7 extends BaseTest {
    // PO
    private static final String dragAndDropPage = "//li/a[text()='Drag and Drop']";
    private static final String contextMenu = "//li/a[text()='Context Menu']";
    private static final String infiniteScroll = "//li/a[text()='Infinite Scroll']";
    private static final String keyPresses = "//li/a[text()='Key Presses']";

    @Test
    @DisplayName("Тест перемещение блоков без применения dragAndDrop")
    void notDragAndDropTest() {
        // Открытие страницы 'Drag And Drop'
        $x(dragAndDropPage).click();

        WebElement elementA = $x("//div[@id='column-a']");
        WebElement elementB = $x("//div[@id='column-b']");

        Point pointA = $x("//header[text()='A']").getRect().getPoint();
        Point pointB = $x("//header[text()='B']").getRect().getPoint();

        // Выполняем перетаскивание элемента A на элемент B
        Selenide.actions()
                .clickAndHold(elementA)
                .moveToElement(elementB)
                .release()
                .build()
                .perform();

        Point newPointA = $x("//header[text()='A']").getRect().getPoint();
        Point newPointB =  $x("//header[text()='B']").getRect().getPoint();

        assertTrue(pointA.equals(newPointB) && pointB.equals(newPointA), "Квадратики не переместились!");
    }

    @Test
    @DisplayName("Тест с проверкой контекстного сообщения")
    void contextMenuTest() {
        // Открытие страницы 'Context menu'
        $x(contextMenu).click();

        // Нажимаем на элемент правой кнопкой мыши
        $("#hot-spot").contextClick();

        // Проверить текст алерта
        String alertText = switchTo().alert().getText();
        assertEquals("You selected a context menu", alertText, "Контекстное сообщения не совпадает!");
    }

    @Test
    @DisplayName("Тест с проверкой скролинга")
    void infiniteScrollTest() {
        // Открытие страницы 'Infinite Scroll'
        $x(infiniteScroll).click();

        // Прокручиваем до тех пор, пока не найдём 'Eius'
        while (!$(By.xpath("//*[contains(text(),'Eius')]")).isDisplayed()) {
            executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }

    @Test
    @DisplayName("Тест с проверкой горячих клавиш")
    void keyPressesTest() {
        // Открытие страницы 'Key Presses'
        $x(keyPresses).click();

        // Нажать на 10 латинских символов
        String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (String character : chars) {
            $("#target").sendKeys(character);

            // Проверка отображения соответствующего всплывающего текста
            $("#result").should(text("You entered: " + character));
            $("#target").clear();
        }

        // Нажатие клавиши ENTER
        actions().sendKeys(Keys.ENTER).perform();
        $("#result").should(text("You entered: ENTER"));

        // Нажатие клавиши CONTROL
        actions().sendKeys(Keys.CONTROL).perform();
        $("#result").should(text("You entered: CONTROL"));

        // Нажатие клавиши ALT
        actions().sendKeys(Keys.ALT).perform();
        $("#result").should(text("You entered: ALT"));

        // Нажатие клавиши TAB
        actions().sendKeys(Keys.TAB).perform();
        $("#result").should(text("You entered: TAB"));
    }
}
