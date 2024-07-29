package utils;

import com.codeborne.selenide.SelenideElement;
import exception.BaseException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ElementActions {

    /**
     * Заполнение значения поля по локатору
     *
     * @param locator    - локатор поля
     * @param sNameField - название поля
     * @param sValue     - вводимое значение
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     */
    private void inputFieldValue(String locator, String sNameField, String sValue, boolean cssOrXPath) throws BaseException {
        try {
            (cssOrXPath ? $(locator) : $x(locator)).setValue(sValue);
        } catch (Throwable ex) {
            throw new BaseException("Ошибка при заполнении поля '" + sNameField + "'! " + ex.getMessage());
        }
    }

    /**
     * Выбор значения из выпадающего списка
     *
     * @param locator    - локатор поля
     * @param sNameField - название поля
     * @param sValue     - заполняемое значение
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     */
    private void choiceValue(String locator, String sNameField, String sValue, boolean cssOrXPath) throws BaseException {
        try {
            (cssOrXPath ? $(locator) : $x(locator)).selectOption(sValue);
        } catch (Throwable ex) {
            throw new BaseException("Ошибка при выборе '" + sNameField + "' из списка! " + ex.getMessage());
        }
    }

    /**
     * Проверка текста в поле
     *
     * @param locator    - локатор поля
     * @param sNameField - название поля
     * @param sValue     - значение поля
     * @param containsOrEquals   - параметр проверки части строки (true - по части строки, false - точное значение)
     * @param cssOrXPath - true - Css локатор, false - XPath локатор
     */
    private void validateFieldText(String locator, String sNameField, String sValue, boolean containsOrEquals, boolean cssOrXPath) throws BaseException {
        try {
            SelenideElement eField = cssOrXPath ? $(locator) : $x(locator);
            String sValueText = eField.getText();
            boolean condition = containsOrEquals ? sValueText.contains(sValue) : sValueText.equals(sValue);

            if (!condition) {
                throw new BaseException("Поле '" + sNameField + "' имеет значение '" + sValueText + "', а должно - '" + sValue + "'!");
            }
        } catch (Throwable ex) {
            throw new BaseException("Ошибка при проверке текста поля '" + sNameField + "'! " + ex.getMessage());
        }
    }

}
