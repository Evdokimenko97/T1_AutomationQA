package pages.WelcomePage.DropdownPage;

import io.qameta.allure.Step;
import pages.WelcomePage.WelcomePage;

public class DropdownPage extends WelcomePage {
    private static final String dropdown = "//select[@id='dropdown']";
    private static final String optionTemplate = "//option[text()='%s']";

    public DropdownPage() {
        super();
        clickDropdownPage();
    }


    @Step("Выбрать опцию '{option}' в комбо-боксе")
    public void selectOption(String option) {
        elementActions.choiceValue(dropdown, option, false);
    }

    @Step("Проверить, что выбрана опция '{option}'")
    public boolean isOptionSelected(String option) {
        String optionLocator = String.format(optionTemplate, option);
        return elementActions.checkSelectedElement(optionLocator, true, false);
    }
}
