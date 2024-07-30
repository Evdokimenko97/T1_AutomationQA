package pages.DropdownPage;

import io.qameta.allure.Step;
import pages.WelcomePage;

public class DropdownPage extends WelcomePage {
    private static final String dropdown = "//select[@id='dropdown']";
    private static final String optionTemplate = "//option[text()='%s']";
    private static final String pageTitle = "//h3";

    public DropdownPage() {
        super();
        clickDropdownPage();
    }

    @Step("Выбрать опцию '{option}' в комбо-боксе")
    public void selectOption(String option, int timeout) {
        elementActions.choiceValue(dropdown, option, false, timeout);
    }

    @Step("Проверить, что выбрана опция '{option}'")
    public boolean isOptionSelected(String option, int timeout) {
        String optionLocator = String.format(optionTemplate, option);
        return elementActions.checkSelectedElement(optionLocator, true, false, timeout);
    }

    @Step("Получение заголовка страницы")
    public String getPageTitle() {
        return super.getPageTitle(pageTitle);
    }
}
