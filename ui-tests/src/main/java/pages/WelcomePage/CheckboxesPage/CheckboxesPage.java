package pages.WelcomePage.CheckboxesPage;

import io.qameta.allure.Step;
import pages.WelcomePage.WelcomePage;

public class CheckboxesPage extends WelcomePage {
    private static final String pageTitle = "//h3";
    private static final String checkbox1 = "//form[@id='checkboxes']/input[1]";
    private static final String checkbox2 = "//form[@id='checkboxes']/input[2]";

    public CheckboxesPage() {
        super();
        clickCheckboxesPage();
    }

    @Step("Получение заголовка страницы 'Checkboxes'")
    public String getPageTitle() {
        return super.getPageTitle(pageTitle);
    }

    @Step("Выбор первого чек-бокса")
    public void selectFirstCheckbox(boolean select) {
        elementActions.selectCheckBox(checkbox1, select, false);
    }

    @Step("Выбор второго чек-бокса")
    public void selectSecondCheckbox(boolean select) {
        elementActions.selectCheckBox(checkbox2, select, false);
    }

    @Step("Проверка первого чек-бокса")
    public boolean isFirstCheckboxSelected(boolean isSelected) {
        return elementActions.checkSelectedElement(checkbox1, isSelected, false);
    }

    @Step("Проверка второго чек-бокса")
    public boolean isSecondCheckboxSelected(boolean isSelected) {
        return elementActions.checkSelectedElement(checkbox2, isSelected, false);
    }
}
