package pages;

import io.qameta.allure.Step;
import utils.ElementActions;

public class WelcomePage extends BasePage{
    private static final String checkboxesPage = "//li/a[text()='Checkboxes']";
    private static final String dropdownPage = "//li/a[text()='Dropdown']";
    private static final String disappearingElementsPage = "//li/a[text()='Disappearing Elements']";
    private static final String inputsPage = "//li/a[text()='Inputs']";
    private static final String hoversPage = "//li/a[text()='Hovers']";
    private static final String notificationMessagesPage = "//li/a[text()='Notification Messages']";
    private static final String addRemoveElementsPage = "//li/a[text()='Add/Remove Elements']";
    private static final String statusCodesPage = "//li/a[text()='Status Codes']";
    private static final String pageTitle = "//h3";

    protected final ElementActions elementActions;

    public WelcomePage() {
        this.elementActions = new ElementActions();
    }


    @Step("Открытие страницы 'Checkboxes'")
    public void clickCheckboxesPage() {
        elementActions.clickElement(checkboxesPage, false);
    }

    @Step("Открытие страницы 'Dropdown'")
    public void clickDropdownPage() {
        elementActions.clickElement(dropdownPage, false);
    }

    @Step("Открытие страницы 'Disappearing Elements'")
    public void clickDisappearingElementsPage() {
        elementActions.clickElement(disappearingElementsPage, false);
    }

    @Step("Открытие страницы 'Inputs'")
    public void clickInputsPage() {
        elementActions.clickElement(inputsPage, false);
    }

    @Step("Открытие страницы 'Hovers'")
    public void clickHoversPage() {
        elementActions.clickElement(hoversPage, false);
    }

    @Step("Открытие страницы 'Notification Messages'")
    public void clickNotificationMessagesPage() {
        elementActions.clickElement(notificationMessagesPage, false);
    }

    @Step("Открытие страницы 'Add/Remove Elements'")
    public void clickAddRemoveElementsPage() {
        elementActions.clickElement(addRemoveElementsPage, false);
    }

    @Step("Открытие страницы 'Status Codes'")
    public void clickStatusCodesPage() {
        elementActions.clickElement(statusCodesPage, false);
    }

    @Step("Получение заголовка страницы")
    public String getPageTitle() {
        return super.getPageTitle(pageTitle);
    }
}
