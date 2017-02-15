package steps.propertyTax;

import cucumber.api.java8.En;

import entities.propertyTax.HearingDetails;
import entities.propertyTax.RevisionPetitionDetails;
import pages.propertyTax.RevisionPetitionPage;
import steps.BaseSteps;
import utils.ExcelReader;

public class RevisionPetitionSteps extends BaseSteps implements En{
    public RevisionPetitionSteps() {

        And("^he search for assessment from commissioner screen$", () -> {
            pageStore.get(RevisionPetitionPage.class).revisionPetitionSearchScreen(scenarioContext.getAssessmentNumber());
        });
        And("^he enters revision petition details(\\w+)$", (String revisionPetitionDataId ) -> {
            RevisionPetitionDetails revisionPetitionDetails = new ExcelReader(ptisTestDataFileName).getRevisionPetitionDetails(revisionPetitionDataId);
            pageStore.get(RevisionPetitionPage.class).revisionPetitionBlock(revisionPetitionDetails);
        });
        And("^he choose revision petition header$", () -> {
            pageStore.get(RevisionPetitionPage.class).chooseRevisionPetitionHeader();
        });
        And("^he enters hearing details(\\w+)$", (String hearingDataId) -> {
            HearingDetails hearingDetails = new ExcelReader(ptisTestDataFileName).getHearingDetails(hearingDataId);
            pageStore.get(RevisionPetitionPage.class).enterHearingDetails(hearingDetails);
        });
        And("^he enters approver remark$", () -> {
            pageStore.get(RevisionPetitionPage.class).enterApproverRemarks();
        });
        And("^he enters reason for modification$", () -> {
            pageStore.get(RevisionPetitionPage.class).selectReasonForModification();
        });
        And("^he enters inspection details$", () -> {
           pageStore.get(RevisionPetitionPage.class).enterInspectionDetails();
        });
        And("^he choose to approve for revision petition$", () -> {
            pageStore.get(RevisionPetitionPage.class).rpApprove();
        });
        And("^he prints endoresement notice$", () -> {
           pageStore.get(RevisionPetitionPage.class).clickPrintEndoresementNotice();
        });
        And("^he generates a print special notice$", () -> {
            pageStore.get(RevisionPetitionPage.class).clickOnPrintSpecialNotice();
        });
    }
}
