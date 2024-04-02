package assertion;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;

public class BasicAssertion extends AbstractAssert<BasicAssertion, WebElement> {

    private BasicAssertion(WebElement actual) {
        super(actual, AbstractAssert.class);
    }

    public static BasicAssertion assertThat(WebElement actual) {
        return new BasicAssertion(actual);
    }

    public BasicAssertion textIsEquals(String expectedText) {
        Assertions.assertThat(actual.getText())
                .as("Текст элемента не равен %s", expectedText)
                .isEqualToIgnoringCase(expectedText);

        return this;
    }

    public BasicAssertion valueIsEquals(String expectedValue) {
        Assertions.assertThat(actual.getAttribute("value"))
                .as("Значение элемента не равен %s", expectedValue)
                .isEqualToIgnoringCase(expectedValue);

        return this;
    }

    public BasicAssertion valueIsNotEquals(String expectedValue) {
        Assertions.assertThat(actual.getAttribute("value"))
                .as("Значение элемента не равен %s", expectedValue)
                .isEqualToIgnoringCase(expectedValue);

        return this;
    }


    public BasicAssertion checkedIsEquals(String expectedChecked) {
        Assertions.assertThat(actual.getAttribute("checked"))
                .as("Значение 'checked' элемента не равен %s", expectedChecked)
                .isEqualToIgnoringCase(expectedChecked);

        return this;
    }
}
