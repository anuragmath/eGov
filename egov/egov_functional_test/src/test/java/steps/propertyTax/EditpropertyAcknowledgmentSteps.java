package steps.propertyTax;

import cucumber.api.java.en.Then;
import pages.propertyTax.PropertyAcknowledgementPage;

import static com.jayway.awaitility.Awaitility.await;
import static steps.BaseSteps.pageStore;

public class EditpropertyAcknowledgmentSteps {

    @Then("^edit property details get saved successfully$")
    public void editPropertyDetailsGetSavedSuccessfully() throws Throwable {
        pageStore.get(PropertyAcknowledgementPage.class).close();
    }
}
