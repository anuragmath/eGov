package steps.propertyTax;

import cucumber.api.java8.En;
import entities.propertyTax.EditAssessmentDetails;
import entities.propertyTax.EditFloorDetails;
import pages.propertyTax.PropertyAcknowledgementPage;
import pages.propertyTax.PropertyDetailsPage;
import steps.BaseSteps;
import utils.ExcelReader;

import static javax.swing.UIManager.get;

public class AdditionSteps extends BaseSteps implements En {
    public AdditionSteps(){
        And("^he searches for assessment with number \"([^\"]*)\"$", (String arg0) -> {

            pageStore.get(PropertyDetailsPage.class).searchAssessmentNumber(scenarioContext.getDataScreenAssessmentNumber());
            pageStore.get(PropertyDetailsPage.class).search();
        });
        And("^he updates assessment details as (\\w+)$", (String assessmentDetailsDataName) -> {
            EditAssessmentDetails assessmentDetails = new ExcelReader(ptisTestDataFileName).getEditAssessmentDetails(assessmentDetailsDataName);
            pageStore.get(PropertyDetailsPage.class).enterEditAssessmentDetails(assessmentDetails);
        });
        And("^he enters Floor Details as (\\w+)$", (String floordetailsDataName) -> {
            // Write code here that turns the phrase above into concrete actions
            EditFloorDetails floorDetails = new ExcelReader(ptisTestDataFileName).getEditFloorDetails(floordetailsDataName);
            pageStore.get(PropertyDetailsPage.class).enterEditFloorDetails(floorDetails);
        });
        And("^he will copy application and assessment number$", () -> {
           scenarioContext.setActualMessage(pageStore.get(PropertyDetailsPage.class).getAssessmentApplicationNo());
            pageStore.get(PropertyAcknowledgementPage.class).close();
        });
    }
}
