package steps.works;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import entities.works.ApproverDetails;
import pages.DashboardPage;
import pages.works.MilestoneTrackPage;
import pages.works.SpillOverEstimatePage;
import steps.BaseSteps;
import utils.ExcelReader;

/**
 * Created by karthik on 26/12/16.
 */
public class MilestoneTrackSteps extends BaseSteps implements En {
    public MilestoneTrackSteps() {
        And("^he search and select the required file$", () -> {
            pageStore.get(MilestoneTrackPage.class).search();

            pageStore.get(MilestoneTrackPage.class).select();

            pageStore.get(MilestoneTrackPage.class).createMilestone();
        });
        And("^he stores the loa number and enters details$", () -> {
            String number = pageStore.get(MilestoneTrackPage.class).getLoaNumber();
            scenarioContext.setLoaNumber(number);

            pageStore.get(MilestoneTrackPage.class).enterMilestoneDetails();
        });
        And("^he save the file and close$", () -> {
            pageStore.get(MilestoneTrackPage.class).save();

            String actualMsg = pageStore.get(MilestoneTrackPage.class).successMessage();
            scenarioContext.setActualMessage(actualMsg);

            pageStore.get(MilestoneTrackPage.class).close();
        });

        And("^he search application using loa number$", () -> {
            pageStore.get(MilestoneTrackPage.class).searchUsingLoa(scenarioContext.getLoaNumber());
        });
        And("^he select the application$", () -> {
            pageStore.get(MilestoneTrackPage.class).selectApplication();
        });
        And("^he enters the milestone details$", () -> {
            pageStore.get(MilestoneTrackPage.class).enterTrackMilestoneDetails();
        });

        And("^he select the required file$", () -> {
           pageStore.get(MilestoneTrackPage.class).select();

           pageStore.get(MilestoneTrackPage.class).createContractorBill();
        });

        And("^he chooses to act upon on contractorBillNumber$", () -> {
//            pageStore.get(DashboardPage.class).openApplicationNew(scenarioContext.getContractorBillNumber());
            pageStore.get(DashboardPage.class).openApplicationNew(scenarioContext.getApplicationNumber());
        });
        And("^he approves the bill$", () -> {
           pageStore.get(MilestoneTrackPage.class).approve();

           String actualMsg = pageStore.get(MilestoneTrackPage.class).successMessage1();
           scenarioContext.setActualMessage(actualMsg);

            pageStore.get(MilestoneTrackPage.class).close();
        });
        And("^he enters contractor details for part bill (\\w+)$", (String approverDetailsDataId) -> {

            pageStore.get(MilestoneTrackPage.class).enterContractorBillDetails("part");

            ApproverDetails approverDetails = new ExcelReader(lineEstimateTestDataFileName).getApprovalDetailsForEstimate(approverDetailsDataId);
            pageStore.get(SpillOverEstimatePage.class).enterApproverDetails(approverDetails);

            String billNumber = pageStore.get(MilestoneTrackPage.class).forwardToDEEContractorBill();
//            scenarioContext.setContractorBillNumber(billNumber);
            scenarioContext.setApplicationNumber(billNumber);

            String actualMessage =  pageStore.get(MilestoneTrackPage.class).successMessage1();
            scenarioContext.setActualMessage(actualMessage);

            pageStore.get(MilestoneTrackPage.class).close();

        });
        And("^he enters contractor details for full bill (\\w+)$", (String approverDetailsDataId) -> {
            pageStore.get(MilestoneTrackPage.class).enterContractorBillDetails("full");

            ApproverDetails approverDetails = new ExcelReader(lineEstimateTestDataFileName).getApprovalDetailsForEstimate(approverDetailsDataId);
            pageStore.get(SpillOverEstimatePage.class).enterApproverDetails(approverDetails);

            String billNumber = pageStore.get(MilestoneTrackPage.class).forwardToDEEContractorBill();
//            scenarioContext.setContractorBillNumber(billNumber);
            scenarioContext.setApplicationNumber(billNumber);

            String actualMessage =  pageStore.get(MilestoneTrackPage.class).successMessage1();
            scenarioContext.setActualMessage(actualMessage);

            pageStore.get(MilestoneTrackPage.class).close();

        });
    }
}