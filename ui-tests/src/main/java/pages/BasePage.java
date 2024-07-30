package pages;

import utils.ElementActions;

public class BasePage {
    protected ElementActions elementActions;

    public BasePage() {
        this.elementActions = new ElementActions();
    }

    public String getPageTitle(String pageTitleLocator) {
        return elementActions.getTextOrValueFromField(pageTitleLocator, false,false);
    }
}
