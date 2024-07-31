import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import base.BaseTest;
import pages.WelcomePage.CheckboxesPage.CheckboxesPage;
import pages.WelcomePage.DisappearingElementsPage.DisappearingElementsPage;
import pages.WelcomePage.DropdownPage.DropdownPage;
import pages.WelcomePage.HoverPage.HoverPage;
import pages.WelcomePage.InputsPage.InputsPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUI extends BaseTest {

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

        dropdownPage.selectOption("Option 1");
        assertTrue(dropdownPage.isOptionSelected("Option 1"), "Выпадающее значение 'Option 1' не выбрана");

        dropdownPage.selectOption("Option 2");
        assertTrue(dropdownPage.isOptionSelected("Option 2"), "Выпадающее значение 'Option 2' не выбрана");
    }

    @RepeatedTest(value = 5, failureThreshold = 1, name = "Тест с отображением 5 элементов #{currentRepetition} из {totalRepetitions}")
    void testDisappearingElements() {
        DisappearingElementsPage disappearingElements = new DisappearingElementsPage();
        assertEquals("Disappearing Elements", disappearingElements.getPageTitle(), "Заголовок страницы не соответствует ожидаемому");

        disappearingElements.getElements().should(CollectionCondition.size(5));
    }

    @TestFactory
    @DisplayName("Тест с заполнением поля ввода")
    List<DynamicTest> testInputField() {
        List<DynamicTest> result = new ArrayList<>();

        InputsPage inputsPage = new InputsPage();
        assertEquals("Inputs", inputsPage.getPageTitle(), "Заголовок страницы не соответствует ожидаемому");

        // Позитивные тесты
        List<String> validInputs = Arrays.asList("0", "123", "456", "789", "987", "654", "321", "1000", "999", "9999");
        List<DynamicTest> positiveTests = validInputs.stream()
                .map(number -> DynamicTest.dynamicTest("Позитивный тест: ввод числа " + number,
                        () -> {
                            inputsPage.inputNumber(number);
                            assertEquals(number, inputsPage.getInputValue(), "Значение в поле ввода не соответствует ожидаемому");
                        }))
                .collect(Collectors.toList());

        // Негативные тесты
        List<String> invalidInputs = Arrays.asList("abc", "!@#", " 123", "456 ");
        List<DynamicTest> negativeTests = invalidInputs.stream()
                .map(input -> DynamicTest.dynamicTest("Негативный тест: попытка ввода " + input,
                        () -> {
                            inputsPage.inputNumber(input);
                            assertNotEquals(inputsPage.getInputValue(), input, "Значение в поле ввода не должно соответствовать некорректному вводу");
                        }))
                .collect(Collectors.toList());

        result.addAll(positiveTests);
        result.addAll(negativeTests);

        return result;
    }

    @ParameterizedTest(name = "Тест с наведением мыши на элемент #{0}")
    @ValueSource(ints = {1, 2, 3})
    void testHoverElement(int avatarNum) {
        HoverPage hoverPage = new HoverPage();
        assertEquals("Hovers", hoverPage.getPageTitle(), "Заголовок страницы не соответствует ожидаемому");

        hoverPage.hoverAvatar(avatarNum);
        String expectedText = "user" + avatarNum;
        Assertions.assertTrue(hoverPage.getTextAvatar(avatarNum).contains(expectedText), "Текст аватара некорректный");
    }

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
