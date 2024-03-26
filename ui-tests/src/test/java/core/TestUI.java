package core;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import exception.BaseException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.*;

public class TestUI extends BaseTest {
    // PO
    private static final String checkboxesPage = "//li/a[text()='Checkboxes']";
    private static final String dropdownPage = "//li/a[text()='Dropdown']";
    private static final String disappearingElementsPage = "//li/a[text()='Disappearing Elements']";
    private static final String inputsPage = "//li/a[text()='Inputs']";
    private static final String hoversPage = "//li/a[text()='Hovers']";
    private static final String notificationMessagesPage = "//li/a[text()='Notification Messages']";
    private static final String addRemoveElementsPage = "//li/a[text()='Add/Remove Elements']";
    private static final String statusCodesPage = "//li/a[text()='Status Codes']";

    // Checkboxes
    private static final String checkboxFirst = "//form[@id='checkboxes']/input[1]";
    private static final String checkboxSecond = "//form[@id='checkboxes']/input[2]";

    // Dropdowns
    private static final String dropdown = "//select[@id='dropdown']";

    // Elements
    private static final String disappearingElements = "//div[@id='content']//ul/li";
    private static final String avatars = "//img[@alt='User Avatar']";

    // Element
    private static final String notificationMsg = "//div[@class='flash notice']";
    private static final String status200 = "//a[text()='200']";
    private static final String status301 = "//a[text()='301']";
    private static final String status404 = "//a[text()='404']";
    private static final String status500 = "//a[text()='500']";
    private static final String messageContent = "//div[@id='content']//p";



    // Input fields
    private static final String inputNumber = "//input[@type='number']";

    // Buttons
    private static final String clickButton = "//a[text()='Click here']";
    private static final String addElement = "//button[text()='Add Element']";
    private static final String buttonElements = "//div[@id='elements']/button";


    @Test
    void test1() {
        // Открытие страницы 'Checkboxes'
        $x(checkboxesPage).click();

        // Выбор первого и отключение второго чек-бокса
        $x(checkboxFirst).click();
        $x(checkboxSecond).click();

        // Проверки
        System.out.println($x(checkboxFirst).getAttribute("checked"));
        System.out.println($x(checkboxSecond).getAttribute("checked"));
    }

    @Test
    void test2() {
        // Открытие страницы 'Dropdown'
        $x(dropdownPage).click();

        // Выбор комбо-бокса и вывод текста
        $x(dropdown).selectOption("Option 1");
        System.out.println($x(dropdown).getText());

        // Выбор комбо-бокса и вывод текста
        $x(dropdown).selectOption("Option 2");
        System.out.println($x(dropdown).getText());
    }

    @Test
    void test3() throws BaseException {
        // Открытие страницы 'Dropdown'
        $x(disappearingElementsPage).click();

        // Проверка 5 элементов
        ElementsCollection elements = null;
        for (int i = 0; i < 10; i++) {
            elements = $$x(disappearingElements);
            if (elements.size() != 5) {
                refresh();
            } else {
                break;
            }
        }

        if (elements.size() != 5) {
            throw new BaseException("Ошибка!");
        }
    }

    @Test
    void test4() {
        // Открытие страницы 'Inputs'
        $x(inputsPage).click();

        // Заполнение и проверка поля
        $x(inputNumber).setValue("5555");
        System.out.println($x(inputNumber).getValue());
    }

    @Test
    void test5() {
        // Открытие страницы 'Hovers'
        $x(hoversPage).click();

        ElementsCollection avatarsCollection = $$x(avatars);

        // Наводим курсор и выводим текст
        for (int i = 1; i <= avatarsCollection.size(); i++) {
            String avatar = "(" + avatars + ")[" + i + "]";

            $x(avatar).hover();
            System.out.println($x(avatar + "/parent::*/div[@class='figcaption']").getText());
        }
    }

    @Test
    void test6() {
        // Открытие страницы 'Notification Message'
        $x(notificationMessagesPage).click();

        // Нажатие на кн. 'Click here'
        $x(clickButton).click();

        while (true) {
            if (!$x(notificationMsg).getText().contains("Action successful")) {
                // Закрытие уведомления
                $x(notificationMsg + "/a[@class='close']").click();

                // Повторное нажатие
                $x(clickButton).click();
                $x(notificationMsg).should(Condition.visible, Duration.ofSeconds(5));
            } else {
                break;
            }
        }
    }

    @Test
    void test7() {
        ElementsCollection elements;

        // Открытие страницы 'Add/Remove Elements'
        $x(addRemoveElementsPage).click();

        // Нажатие на кн. 'Add Element'
        for (int i = 0; i < 5; i++) {
            $x(addElement).click();
            elements = $$x(buttonElements);

            // Вывод текста последнего элемента
            System.out.println(elements.last().getText());
        }

        // Нажатие на разные кнопки 'Delete' 3 раза
        for (int i = 0 ; i < 3; i++) {
            // Рандом от 1 до 5
            Random random = new Random();
            int randomNumber = random.nextInt(5) + 1;

            // Нажатие на кн. 'Delete'
            $x(buttonElements + "[" + randomNumber + "]").should(Condition.visible, Duration.ofSeconds(1)).click();
        }

        // Вывод количество элементов и текста
        System.out.println($$x(buttonElements).size());
        System.out.println($$x(buttonElements).texts());
    }

    @Test
    void test8() {
        // Открытие страницы 'Status Codes'
        $x(statusCodesPage).click();

        // Вывод в консоль сообщения после нажатия на статус
        System.out.println(messageStatus(status200));
        System.out.println(messageStatus(status301));
        System.out.println(messageStatus(status404));
        System.out.println(messageStatus(status500));
    }

    private String messageStatus(String xPath) {
        // Нажимаем на кнопку со статусом
        $x(xPath).click();
        String msg = $x(messageContent).getText();

        Selenide.back();
        return msg;
    }
}
