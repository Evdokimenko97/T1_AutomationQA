package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ElementActions {
    /**
     * Заполнение значения поля по локатору с ожиданием
     *
     * @param locator    - локатор поля
     * @param value      - вводимое значение
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     * @param timeout    - время ожидания
     */
    public void inputFieldValue(String locator, String value, boolean cssOrXPath, int timeout) {
        SelenideElement element = cssOrXPath ? $(locator) : $x(locator);
        element.shouldBe(Condition.visible, Duration.ofSeconds(timeout)).setValue(value);
    }

    /**
     * Заполнение значения поля по локатору
     * См. описание выше
     */
    public void inputFieldValue(String locator, String value, boolean cssOrXPath) {
        inputFieldValue(locator, value, cssOrXPath, 0);
    }

    /**
     * Выбор значения из выпадающего списка с ожиданием
     *
     * @param locator    - локатор поля
     * @param value      - заполняемое значение
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     * @param timeout    - время ожидания
     */
    public void choiceValue(String locator, String value, boolean cssOrXPath, int timeout) {
        SelenideElement element = cssOrXPath ? $(locator) : $x(locator);
        element.shouldBe(Condition.visible, Duration.ofSeconds(timeout)).selectOption(value);
    }
    /**
     * Выбор значения из выпадающего списка
     * См. описание выше
     */
    public void choiceValue(String locator, String value, boolean cssOrXPath) {
        choiceValue(locator, value, cssOrXPath, 0);
    }

    /**
     * Проверка текста или значения поля с ожиданием
     *
     * @param locator    - локатор поля
     * @param value      - значение поля
     * @param contains   - параметр проверки части строки (true - по части строки, false - точное значение)
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     * @param checkValue - true - проверка значения, false - проверка текста
     * @param timeout    - время ожидания
     */
    public boolean validateField(String locator, String value, boolean contains, boolean cssOrXPath, boolean checkValue, int timeout) {
        SelenideElement eField = cssOrXPath ? $(locator) : $x(locator);
        String fieldText = checkValue ? eField.shouldBe(Condition.visible, Duration.ofSeconds(timeout)).getValue() : eField.shouldBe(Condition.visible, Duration.ofSeconds(timeout)).getText();
        return contains ? Objects.requireNonNull(fieldText).contains(value) : Objects.equals(fieldText, value);
    }

    /**
     * Проверка текста или значения поля
     * См. описание выше
     */
    public boolean validateField(String locator, String value, boolean contains, boolean cssOrXPath, boolean checkValue) {
        return validateField(locator, value, contains, cssOrXPath, checkValue, 0);
    }

    /**
     * Устанавливаем или снимаем флаг с чек-бокса с ожиданием
     *
     * @param locator    - локатор чек-бокса
     * @param selectFlag - флаг чек-бокса (true - устанавливаем, false - снимаем)
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     * @param timeout    - время ожидания
     */
    public void selectCheckBox(String locator, boolean selectFlag, boolean cssOrXPath, int timeout) {
        SelenideElement checkBox = cssOrXPath ? $(locator) : $x(locator);
        checkBox.shouldBe(Condition.visible, Duration.ofSeconds(timeout));
        if (checkBox.isSelected() != selectFlag) {
            checkBox.click();
        }
    }

    /**
     * Устанавливаем или снимаем флаг с чек-бокса
     * См. описание выше
     */
    public void selectCheckBox(String locator, boolean selectFlag, boolean cssOrXPath) {
        selectCheckBox(locator, selectFlag, cssOrXPath, 0);
    }

    /**
     * Проверка выбран ли элемент (чек-бокс, радио-кнопка) с ожиданием
     *
     * @param locator    - локатор элемента
     * @param isSelected - флаг элемента (true - если выбран, false - если не выбран)
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     * @param timeout    - время ожидания
     */
    public boolean checkSelectedElement(String locator, boolean isSelected, boolean cssOrXPath, int timeout) {
        SelenideElement element = cssOrXPath ? $(locator) : $x(locator);
        element.shouldBe(Condition.visible, Duration.ofSeconds(timeout));
        return element.isSelected() == isSelected;
    }

    /**
     * Проверка выбран ли элемент (чек-бокс, радио-кнопка) с ожиданием
     * См. описание выше
     */
    public boolean checkSelectedElement(String locator, boolean isSelected, boolean cssOrXPath) {
        return checkSelectedElement(locator, isSelected, cssOrXPath, 0);
    }

    /**
     * Нажимаем на элемент с ожиданием
     *
     * @param locator    локатор элемента
     * @param cssOrXPath true - Css локатор, false - XPath локатор
     * @param timeout    - время ожидания
     */
    public void clickElement(String locator, boolean cssOrXPath, int timeout) {
        SelenideElement element = cssOrXPath ? $(locator) : $x(locator);
        element.shouldBe(Condition.visible, Duration.ofSeconds(timeout)).click();
    }

    /**
     * Нажимаем на элемент
     * См. описание выше
     */
    public void clickElement(String locator, boolean cssOrXPath) {
        clickElement(locator, cssOrXPath, 0);
    }

    /**
     * Получение текста из поля с ожиданием
     *
     * @param locator    локатор элемента
     * @param cssOrXPath true - Css локатор, false - XPath локатор
     * @param timeout    - время ожидания
     */
    public String getTextFromField(String locator, boolean cssOrXPath, int timeout) {
        SelenideElement element = cssOrXPath ? $(locator) : $x(locator);
        return element.shouldBe(Condition.visible, Duration.ofSeconds(timeout)).getText();
    }

    /**
     * Получение текста из поля
     * См. описание выше
     */
    public String getTextFromField(String locator, boolean cssOrXPath) {
        return getTextFromField(locator, cssOrXPath, 0);
    }
}