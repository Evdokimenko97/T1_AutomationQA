package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.ConfigReader;

import java.time.Duration;

abstract class BaseTest {

    /**
     * Добавляем конфигурации для браузера
     */
    private void configurationBrowser() {

        Configuration.browser = ConfigReader.getProperty("browser");
        Configuration.pageLoadStrategy = "normal";

        // С версии Chrome 111 невозможно открыть ссылки без авторизации запроса
        if (ConfigReader.getProperty("browser").equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            Configuration.browserCapabilities = new DesiredCapabilities();
            Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    /**
     * Открытие браузер
     */
    private void openBrowserWithURL() {
        Selenide.open(ConfigReader.getProperty("baseURL"));
        WebDriverRunner.getWebDriver().manage().window().maximize();
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeEach
    public void setUp() {
        configurationBrowser();
        openBrowserWithURL();
    }
}
