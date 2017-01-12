package steps.AdvertisementTax;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import entities.works.ApproverDetails;
import pages.AdvertisementTax.AdvertisementsPage;
import pages.DashboardPage;
import pages.works.SpillOverEstimatePage;
import steps.BaseSteps;
import utils.ExcelReader;


/**
 * Created by karthik on 11/1/17.
 */
public class AdvertisementsSteps extends BaseSteps implements En {

    public AdvertisementsSteps(){

        And("^he chooses to create advertisement$", () -> {
            pageStore.get(DashboardPage.class).chooseToCreateAdvertisement();
        });

        And("^he enters details for advertisement creation$", () -> {
           pageStore.get(AdvertisementsPage.class).enterAdvertisementDetails();

           pageStore.get(AdvertisementsPage.class).enterPermissionDetails();

           pageStore.get(AdvertisementsPage.class).enterLocalityDetails();

           pageStore.get(AdvertisementsPage.class).enterStructureDetails();

           String approverDetailsDataId = "commissioner";

           ApproverDetails approverDetails = new ExcelReader(lineEstimateTestDataFileName).getApprovalDetailsForEstimate(approverDetailsDataId);

           pageStore.get(SpillOverEstimatePage.class).enterApproverDetails(approverDetails);

        });
        And("^he forwards and closes the acknowledgement$", () -> {
            String number = pageStore.get(AdvertisementsPage.class).forward();
            scenarioContext.setApplicationNumber(number);

            String actualMsg = pageStore.get(AdvertisementsPage.class).successMessage();
            scenarioContext.setActualMessage(actualMsg);

            pageStore.get(AdvertisementsPage.class).close();

        });
        And("^he clicks on advertisement and opens the application$", () -> {
           pageStore.get(AdvertisementsPage.class).selectAdvertisementTag(scenarioContext.getApplicationNumber());
        });
        And("^he approves the advertisement application$", () -> {
            pageStore.get(AdvertisementsPage.class).approverComment();
            pageStore.get(AdvertisementsPage.class).approve();

            String advertisementNumber = pageStore.get(AdvertisementsPage.class).getAdvertisementNumber();
            scenarioContext.setApplicationNumber(advertisementNumber);

            String actualMsg = pageStore.get(AdvertisementsPage.class).successMessage();
            scenarioContext.setActualMessage(actualMsg);

            //pageStore.get(AdvertisementsPage.class).close();
        });
        And("^he chooses to search advertisement$", () -> {
           pageStore.get(DashboardPage.class).chooseToSearchAdvertisement();
        });
        And("^he search and select the required advertisement$", () -> {
            pageStore.get(AdvertisementsPage.class).searchAndSelect(scenarioContext.getApplicationNumber());
        });
        And("^he view and close the acknowledgement$", () -> {
            String url = "http://kurnool-uat.egovernments.org/adtax/hoarding/adtax-search";
            pageStore.get(AdvertisementsPage.class).closeMultipleWindows(url);
        });

    }

}