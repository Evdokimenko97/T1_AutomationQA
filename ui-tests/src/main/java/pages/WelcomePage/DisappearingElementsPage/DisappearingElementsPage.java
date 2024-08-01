package pages.WelcomePage.DisappearingElementsPage;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import pages.WelcomePage.WelcomePage;

import static com.codeborne.selenide.Selenide.$$x;

public class DisappearingElementsPage extends WelcomePage {
    private static final String elementsLocator = "//div[@id='content']//ul/li";

    public DisappearingElementsPage() {
        super();
        clickDisappearingElementsPage();
    }

    @Step("Получение элементов на странице 'Disappearing Elements'")
    public ElementsCollection getElements() {
        return $$x(elementsLocator);
    }
}
