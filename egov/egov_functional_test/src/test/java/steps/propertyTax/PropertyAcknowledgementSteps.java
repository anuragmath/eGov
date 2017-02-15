package steps.propertyTax;

import cucumber.api.java8.En;
import pages.propertyTax.PropertyAcknowledgementPage;
import steps.BaseSteps;

public class PropertyAcknowledgementSteps extends BaseSteps implements En {

    public PropertyAcknowledgementSteps() {
        Then("^create property details get saved successfully$", () -> {
            String assessmentNumber = pageStore.get(PropertyAcknowledgementPage.class).getApplicationNumber();
            scenarioContext.setAssessmentNumber(assessmentNumber);
        });
        And("^current user closes acknowledgement$", () -> {
            pageStore.get(PropertyAcknowledgementPage.class).close();
        });
        When("^commissioner closes acknowledgement$", () -> {
           pageStore.get(PropertyAcknowledgementPage.class).closeFromCommisionersLogin();
        });
        And("^he cancels the print$", () -> {
            pageStore.get(PropertyAcknowledgementPage.class).cancelPrint();
        });
        And("^user will see the successfull page and view the details$", () -> {
            pageStore.get(PropertyAcknowledgementPage.class).toViewSubmissionPage();
        });
        And("^user will close the data entry page$", () -> {
            pageStore.get(PropertyAcknowledgementPage.class).toCloseDataEntryPage();
        });
        And("^user closes acknowledgement form$", () -> {
            pageStore.get(PropertyAcknowledgementPage.class).toCloseAdditionalConnectionPage();
        });
        And("^he will copy the application number$", () -> {
            scenarioContext.setActualMessage(pageStore.get(PropertyAcknowledgementPage.class).getActualMsg());
                    pageStore.get(PropertyAcknowledgementPage.class).close();
        });
        And("^he will copy the assessment number$", () -> {
            scenarioContext.setActualMessage(pageStore.get(PropertyAcknowledgementPage.class).getActualMsgAssessment());
            pageStore.get(PropertyAcknowledgementPage.class).close();
        });
    }
}
