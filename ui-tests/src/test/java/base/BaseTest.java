package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public abstract class BaseTest {

    /**
     * Добавляем конфигурации для браузера
     */
    private void configurationBrowser() throws MalformedURLException {

        Configuration.browser = ConfigReader.getProperty("browser");
        Configuration.pageLoadStrategy = "normal";

        if (ConfigReader.getProperty("setUp").equals("local")) {
            // С версии Chrome 111 невозможно открыть ссылки без авторизации запроса
            if (ConfigReader.getProperty("browser").equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                Configuration.browserCapabilities = new DesiredCapabilities();
                Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

            } else if (ConfigReader.getProperty("setUp").equals("selenoid")) {
                // Параметры Selenoid (Selenoid UI)
                ChromeOptions options = new ChromeOptions();
                options.setCapability("browserVersion", "latest");
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("sessionTimeout", "15m");

                    put("labels", new HashMap<String, Object>() {{
                        put("manual", "true");
                    }});

                    put("enableVNC", true);
                }});

                // Docker
                WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
                setWebDriver(driver);


                SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
            }
        }
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
    protected void setUp() throws MalformedURLException {
        configurationBrowser();
        openBrowserWithURL();
    }

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }
}
