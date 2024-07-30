import assertion.BasicAssertion;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import core.BaseTest;
import pages.Checkboxes.CheckboxesPage;
import pages.DropdownPage.DropdownPage;
import pages.WelcomePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUI extends BaseTest {

//    // Elements
//    private static final String disappearingElements = "//div[@id='content']//ul/li";
//    private static final String avatars = "//img[@alt='User Avatar']";
//
//    // Element
//    private static final String notificationMsg = "//div[@class='flash notice']";
//    private static final String messageContent = "//div[@id='content']//p";
//
//
//    // Input fields
//    private static final String inputNumber = "//input[@type='number']";
//
//    // Buttons
//    private static final String clickButton = "//a[text()='Click here']";
//    private static final String addElement = "//button[text()='Add Element']";
//    private static final String buttonElements = "//div[@id='elements']/button";

    @ParameterizedTest(name = "Тест с выбором чек-бокса #{0}")
    @ValueSource(ints = {1, 2})
    void testCheckboxSelections(int param) {
        CheckboxesPage checkboxesPage = new CheckboxesPage();
        assertEquals("Checkboxes", checkboxesPage.getPageTitle(), "Заголовок страницы не соответствует ожидаемому");

        if (param == 1) {
            checkboxesPage.selectFirstCheckbox(true);
            assertTrue(checkboxesPage.isFirstCheckboxSelected(true), "Первый чек-бокс должен быть выбран");
        } else if (param == 2) {
            checkboxesPage.selectSecondCheckbox(true);
            assertTrue(checkboxesPage.isSecondCheckboxSelected(true), "Второй чек-бокс должен быть выбран");
        }
    }

    @Test
    @DisplayName("Тест выбора комбо-бокса")
    void testDropdown() {
        DropdownPage dropdownPage = new DropdownPage();
        assertEquals("Dropdown List", dropdownPage.getPageTitle(), "Заголовок страницы не соответствует ожидаемому");

        dropdownPage.selectOption("Option 1", 5);
        assertTrue(dropdownPage.isOptionSelected("Option 1", 5), "Выпадающее значение 'Option 1' не выбрана");

        dropdownPage.selectOption("Option 2", 5);
        assertTrue(dropdownPage.isOptionSelected("Option 2", 5), "Выпадающее значение 'Option 2' не выбрана");
    }

//
//    @RepeatedTest(value = 5, failureThreshold = 1, name = "Тест с отображением 5 элементов #{currentRepetition} из {totalRepetitions}")
//    void test3() {
//        // Открытие страницы 'Dropdown'
//        $x(disappearingElementsPage).click();
//
//        // Проверка 5 элементов
//        ElementsCollection elements = $$x(disappearingElements);
//        elements.should(CollectionCondition.size(5));
//    }
//
//    @TestFactory
//    @DisplayName("Тест с заполнением поля ввода")
//    List<DynamicTest> test4() {
//        List<DynamicTest> result = new ArrayList<>();
//
//        // Открытие страницы 'Inputs'
//        $x(inputsPage).click();
//
//        // Позитивные тесты
//        List<String> testData = Arrays.asList("0", "123", "456", "789", "987", "654", "321", "1000", "999", "9999");
//        for (int i = 0; i < testData.size(); i++) {
//            String number = testData.get(i);
//            result.add(
//                    DynamicTest.dynamicTest("Позитивный тест #" + i + ": ввод числа " + number,
//                            () -> {
//                                inputField(inputNumber, number);
//                                $x(inputNumber).shouldHave(exactValue(number));
//                            })
//            );
//        }
//
//        // Негативные тесты
//        List<String> invalidInputs = Arrays.asList("abc", "!@#", " 123", "456 ");
//        for (int i = 0; i < invalidInputs.size(); i++) {
//            String invalidInput = invalidInputs.get(i);
//            result.add(
//                    DynamicTest.dynamicTest("Негативный тест #" + i + ": попытка ввода " + invalidInput,
//                            () -> {
//                                inputField(inputNumber, invalidInput);
//                                $x(inputNumber).shouldNotHave(exactValue(invalidInput));
//                            })
//            );
//        }
//
//        return result;
//    }
//
//    // Ввод поля
//    private void inputField(String xpath, String value) {
//        $x(xpath).clear();
//        $x(xpath).sendKeys(value);
//    }
//
//    @ParameterizedTest(name = "Тест с наведением мыши на элемент")
//    @ValueSource(ints = {1, 2, 3})
//    void test5(int avatarNum) {
//        // Открытие страницы 'Hovers'
//        $x(hoversPage).click();
//
//        // Наведение мыши на элемент
//        String avatar = "(" + avatars + ")[" + avatarNum + "]";
//        $x(avatar).hover();
//
//        // Проверка
//        String expectedText = "user" + avatarNum;
//        $x(avatar + "/parent::*/div[@class='figcaption']").shouldHave(text(expectedText));
//    }
//
//    @RepeatedTest(value = 10, name = "Тест с проверкой сообщения #{currentRepetition} из {totalRepetitions}")
//    void test6() {
//        // Открытие страницы 'Notification Message'
//        $x(notificationMessagesPage).click();
//
//        // Нажатие на кн. 'Click here' и проверка
//        $x(clickButton).click();
//        $x(notificationMsg).shouldHave(text("Action successful"));
//    }
//
//
//    @TestFactory
//    @DisplayName("Тест с добавлением и удалением элементов")
//    List<DynamicTest> test7() {
//        List<DynamicTest> result = new ArrayList<>();
//
//        // Открытие страницы 'Add/Remove Elements'
//        $x(addRemoveElementsPage).click();
//
//
//        String[][] testData = {
//                {"2", "1"},
//                {"5", "2"},
//                {"1", "3"}
//        };
//
//        for (int i = 0; i < testData.length; i++) {
//            int addCount = Integer.parseInt(testData[i][0]);
//            int removeCount = Integer.parseInt(testData[i][1]);
//
//            result.add(
//                    DynamicTest.dynamicTest("Тест #" + i + ": добавляем и удаляем элемнты в пропорциях " + Arrays.toString(testData[i]),
//                            () -> {
//                                // Обновление браузера
//                                Selenide.refresh();
//
//                                // Добавление кнопки
//                                for (int j = 0; j < addCount; j++) {
//                                    $x(addElement).click();
//                                }
//
//                                // Удаляем первую кнопку из добавленных
//                                for (int z = 0; z < removeCount; z++) {
//                                    $x(buttonElements).click();
//                                }
//
//                                // Проверка количества элементов на странице
//                                $$x(buttonElements).should(CollectionCondition.size(addCount - removeCount));
//                            })
//            );
//        }
//
//        return result;
//    }
//
//    @ParameterizedTest(name = "Тест с проверкой статуса #{0}")
//    @ValueSource(strings = {"200", "301", "404", "500"})
//    void test8(String status) {
//        // Открытие страницы 'Status Codes'
//        $x(statusCodesPage).click();
//
//        // Проверка статус кода
//        verifyStatusCode("//a[text()='" + status + "']", "This page returned a " + status + " status code.");
//    }
//
//    private void verifyStatusCode(String statusCode, String expectedMessage) {
//        $x(statusCode).click();
//        $x(messageContent).shouldHave(text(expectedMessage));
//        back();
//    }
//
//    @Step("Нажать на элемент '{elementName}'")
//    private void clickElement(SelenideElement element, String elementName) {
//        element.should(visible, Duration.ofSeconds(2)).click();
//    }
//
//    @Step("Выбрать комбо-бокс '{comboBox}'")
//    private void selectOption(SelenideElement element, String comboBox) {
//        element.should(visible, Duration.ofSeconds(1)).selectOption(comboBox);
//    }
//
//    @Step("Проверить, что выбрана опция '{optionText}'")
//    private void verifySelectedOption(String optionText) {
//        $x("//option[text()='" + optionText + "']").should(attribute("selected", "true"));
//    }
//
//    @Step("Проверить, что выбран или нет чек-бокс '{checkbox}'")
//    private void verifyCheckbox(SelenideElement checkbox, String checkboxName, boolean isSelected) {
//        if (isSelected)
//            checkbox.should(checked);
//        else
//            checkbox.should(not(checked));
//    }
}
