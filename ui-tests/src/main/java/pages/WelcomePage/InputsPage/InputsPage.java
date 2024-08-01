package pages.WelcomePage.InputsPage;

import io.qameta.allure.Step;
import pages.WelcomePage.WelcomePage;

public class InputsPage extends WelcomePage {
    private static final String inputNumber = "//input[@type='number']";

    public InputsPage() {
        super();
        clickInputsPage();
    }

    @Step("Ввод числа '{number}' в поле ввода")
    public void inputNumber(String number) {
        elementActions.inputFieldValue(inputNumber, number, false);
    }

    @Step("Получение значения из поля ввода")
    public String getInputValue() {
        return elementActions.getTextOrValueFromField(inputNumber, false, true);
    }
}
