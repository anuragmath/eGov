package steps.councilManagement;

import cucumber.api.java8.En;
import entities.councilManagement.CreatePreambleDetails;
import org.junit.Assert;
import pages.councilManagement.CouncilManagementPage;
import steps.BaseSteps;
import utils.ExcelReader;

/**
 * Created by tester1 on 1/4/2017.
 */
public class CouncilManagementSteps extends BaseSteps implements En {
    public CouncilManagementSteps() {
        And("^he enters create preamble details as (\\w+)$", (String createPreambleData) -> {
            CreatePreambleDetails createPreambleDetails= new ExcelReader(councilManagementTestDataFileName).getCreatePreambleDetails(createPreambleData);
            pageStore.get(CouncilManagementPage.class).enterCreatePreambleDetails(createPreambleDetails);
        });

        And("^he copies preamble number and closes the acknowledgement$", () -> {
            scenarioContext.setPreambleNumber(pageStore.get(CouncilManagementPage.class).getPreambleNumber());
            String Status= pageStore.get(CouncilManagementPage.class).getStatus();
            scenarioContext.setActualMessage(Status);
        });
        And("^he approves the preamble number$", () -> {
          String Status= pageStore.get(CouncilManagementPage.class).approve();
            scenarioContext.setActualMessage(Status);
        });

        And("^he choose to create agenda for the above preamble$", () -> {
            pageStore.get(CouncilManagementPage.class).enterCreateAgendaDetails(scenarioContext.getPreambleNumber());
        });
        And("^he enters create agenda details as (\\w+)$", (String createAgendaData) -> {
            CreatePreambleDetails createPreambleDetails=new ExcelReader(councilManagementTestDataFileName).getCreateAgendaDetails(createAgendaData);
            pageStore.get(CouncilManagementPage.class).enterCreateAgenda(createPreambleDetails);
        });
        And("^he copies agenda number and closes the acknowledgement$", () -> {
            scenarioContext.setAgendaNumber(pageStore.get(CouncilManagementPage.class).getAgendaNumber());
        });
        And("^he choose to create meeting invitation for the above agenda$", () -> {
            pageStore.get(CouncilManagementPage.class).enterCreateMeetingDetails(scenarioContext.getAgendaNumber());
        });
        And("^he enters meeting details as (\\w+)$", (String createMeetingDetails) -> {
            CreatePreambleDetails createMeetingData=new ExcelReader(councilManagementTestDataFileName).getCreateMeetingDetails(createMeetingDetails);
            pageStore.get(CouncilManagementPage.class).enterCouncilMeetingDetails(createMeetingData);
        });
        And("^he copies meeting number and closes the acknowledgement$", () -> {
            scenarioContext.setMeetingNumber(pageStore.get(CouncilManagementPage.class).getMeetingNumber());
        });
        And("^he enters above meeting number to enter attendance$", () -> {
            pageStore.get(CouncilManagementPage.class).enterMeetingNumber(scenarioContext.getMeetingNumber());
            Assert.assertNotNull(scenarioContext.getMeetingNumber());
        });
        And("^he choose to edit attendance details$", () -> {
            pageStore.get(CouncilManagementPage.class).enterAttendanceDetails();
        });
        And("^he finalize attendance details and comes to home page$", () -> {
            pageStore.get(CouncilManagementPage.class).finalizeAttendance();

        });
        And("^he choose to create council MOM for the meeting number$", () -> {
           pageStore.get(CouncilManagementPage.class).searchMeetingNumber(scenarioContext.getMeetingNumber());
        });
        And("^he enters details to create MOM as (\\w+)$", (String councilMOMData) -> {
            CreatePreambleDetails councilMOMDetails=new ExcelReader(councilManagementTestDataFileName).getCouncilMOMDetails(councilMOMData);
            pageStore.get(CouncilManagementPage.class).enterCouncilMOMDetails(councilMOMDetails);

        });


    }
}
