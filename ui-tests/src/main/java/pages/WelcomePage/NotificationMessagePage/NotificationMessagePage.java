package pages.WelcomePage.NotificationMessagePage;

import io.qameta.allure.Step;
import pages.WelcomePage.WelcomePage;

public class NotificationMessagePage extends WelcomePage {
    private static final String clickHereButton = "//a[text()='Click here']";
    private static final String notificationMsg = "//div[@class='flash notice']";
    public NotificationMessagePage() {
        super();
        clickNotificationMessagesPage();
    }

    @Step("Нажать на кнопку 'Click Here'")
    public void clickHereButton() {
        elementActions.clickElement(clickHereButton, false);
    }

    @Step("Проверить форму сообщения")
    public String getNotificationMessage() {
        return elementActions.getTextOrValueFromField(notificationMsg, false, false);
    }
}
