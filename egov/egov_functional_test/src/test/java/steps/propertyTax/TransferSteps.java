package steps.propertyTax;

import cucumber.api.java8.En;
import entities.propertyTax.RegistrationDetails;
import pages.propertyTax.PropertyAcknowledgementPage;
import pages.propertyTax.PropertyDetailsPage;
import pages.propertyTax.TransferDetailsPage;
import steps.BaseSteps;
import utils.ExcelReader;

public class TransferSteps extends BaseSteps implements En {
    public TransferSteps() {
        And("^he pay tax using Cash$", () -> {
            pageStore.get(PropertyDetailsPage.class).payCash();
            pageStore.get(PropertyAcknowledgementPage.class).close1();
        });
        And("^he chooses Registration already done button$", () -> {
            pageStore.get(TransferDetailsPage.class).chooseRegistrationAlreadyDone();
        });
        And("^he enters registration details for the property (\\w+)$", (String registrationDetailsDataId) -> {
            RegistrationDetails registrationDetails = new ExcelReader(ptisTestDataFileName).getRegistrationDetails(registrationDetailsDataId);
            pageStore.get(TransferDetailsPage.class).enterRegistrationDetails(registrationDetails);
        });
        And("^he enters enclosure details$", () -> {
           pageStore.get(TransferDetailsPage.class).enterEnclosureDetails();
        });
        And("^he searches for the assessment with mutation assessment number$",() -> {
            pageStore.get(TransferDetailsPage.class).searchAssessmentNumber(scenarioContext.getAssessmentNumber());
        });
        And("^he generate title transfer notice$", () -> {
            pageStore.get(TransferDetailsPage.class).generateTitleTransferNotice();
        });
    }
}
