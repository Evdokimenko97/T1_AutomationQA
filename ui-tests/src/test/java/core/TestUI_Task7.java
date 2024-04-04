package core;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static assertion.BasicAssertion.assertThat;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestUI_Task7 extends BaseTest {
    // PO
    private static final String dragAndDropPage = "//li/a[text()='Drag and Drop']";

    @Test
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
}
