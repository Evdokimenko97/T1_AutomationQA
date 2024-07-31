package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

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
     * Получение текста или значения из поля с ожиданием
     *
     * @param locator     - локатор элемента
     * @param cssOrXPath  - true - Css локатор, false - XPath локатор
     * @param timeout     - время ожидания
     * @param checkValue  - true - проверка значения, false - проверка текста
     */
    public String getTextOrValueFromField(String locator, boolean cssOrXPath, int timeout, boolean checkValue) {
        SelenideElement element = cssOrXPath ? $(locator) : $x(locator);
        element.shouldBe(Condition.visible, Duration.ofSeconds(timeout));
        return checkValue ? element.getValue() : element.getText();
    }

    /**
     * Получение текста или значения из поля
     * См. описание выше
     */
    public String getTextOrValueFromField(String locator, boolean cssOrXPath, boolean checkValue) {
        return getTextOrValueFromField(locator, cssOrXPath, 0, checkValue);
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
     * @param locator    - локатор элемента
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
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
     * Перемещаем курсор на элемент
     *
     * @param locator    - локатор элемента
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     * @param timeout    - время ожидания
     */
    public void hoverElement(String locator, boolean cssOrXPath, int timeout) {
        SelenideElement element = cssOrXPath ? $(locator) : $x(locator);
        element.shouldBe(Condition.visible, Duration.ofSeconds(timeout)).hover();
    }

    /**
     * Перемещаем курсор на элемент
     * См. описание выше
     */
    public void hoverElement(String locator, boolean cssOrXPath) {
        hoverElement(locator, cssOrXPath, 0);
    }
}