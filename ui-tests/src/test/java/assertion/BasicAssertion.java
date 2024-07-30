package assertion;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import com.codeborne.selenide.SelenideElement;

public class BasicAssertion extends AbstractAssert<BasicAssertion, SelenideElement> {

    private BasicAssertion(SelenideElement actual) {
        super(actual, BasicAssertion.class);
    }

    public static BasicAssertion assertThat(SelenideElement actual) {
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
                .as("Значение элемента не равно %s", expectedValue)
                .isEqualToIgnoringCase(expectedValue);

        return this;
    }

    public BasicAssertion valueIsNotEquals(String expectedValue) {
        Assertions.assertThat(actual.getAttribute("value"))
                .as("Значение элемента не должно быть %s", expectedValue)
                .isNotEqualToIgnoringCase(expectedValue);

        return this;
    }

    public BasicAssertion checkedIsEquals(boolean isChecked) {
        Assertions.assertThat(actual.isSelected())
                .as("Чекбокс не в ожидаемом состоянии %s", isChecked)
                .isEqualTo(isChecked);

        return this;
    }
}
