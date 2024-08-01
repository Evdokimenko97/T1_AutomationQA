package pages.WelcomePage.HoverPage;

import io.qameta.allure.Step;
import pages.WelcomePage.WelcomePage;

public class HoverPage extends WelcomePage {
    private static final String avatar = "(//img[@alt='User Avatar'])[%s]";
    private static final String avatarFigcaption = avatar + "/parent::*/div[@class='figcaption']";

    public HoverPage() {
        super();
        clickHoversPage();
    }


    @Step("Навести мышь на автар '{nameNum}'")
    public void hoverAvatar(int nameNum) {
        String avatarLocator = String.format(avatar, nameNum);
        elementActions.hoverElement(avatarLocator, false);
    }

    @Step("Получить текст под аватаром '{nameNum}'")
    public String getTextAvatar(int nameNum) {
        String avatarFigcaptionLocator = String.format(avatarFigcaption, nameNum);
        return elementActions.getTextOrValueFromField(avatarFigcaptionLocator, false, false);
    }
}
