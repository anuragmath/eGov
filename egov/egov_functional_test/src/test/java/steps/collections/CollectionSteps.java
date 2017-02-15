package steps.collections;

import cucumber.api.java8.En;
import entities.collections.ChallanHeaderDetails;
import entities.collections.PaymentMethod;
import entities.propertyTax.ApprovalDetails;
import pages.collections.CollectionsPage;
import pages.collections.PropertyTaxPage;
import steps.BaseSteps;
import utils.ExcelReader;

public class CollectionSteps extends BaseSteps implements En {
    public CollectionSteps() {


        And("^he enters challan details$", () -> {
            String challanheaderid = "challanHeader";
            String approverId = "seniorAssistant";

            ChallanHeaderDetails challanHeaderDetails = new ExcelReader(collectionsTestDataFileName).getChallanHeader(challanheaderid);
            pageStore.get(CollectionsPage.class).enterChallanHeader(challanHeaderDetails);

            ApprovalDetails approverDetails = new ExcelReader(collectionsTestDataFileName).getApprovalDetailsOfChallan(approverId);
            pageStore.get(CollectionsPage.class).enterApprovalDetails(approverDetails);
        });

        And("^he validate the challan$", () -> {
           pageStore.get(CollectionsPage.class).validateChallan();

            String msg = pageStore.get(CollectionsPage.class).successMessage();
            scenarioContext.setActualMessage(msg);

            pageStore.get(CollectionsPage.class).close();

        });
        And("^he search for challan number$", () -> {
            pageStore.get(CollectionsPage.class).enterChallanNumber(scenarioContext.getChallanNumber());
        });

        And("^he chooses to collect water charge for (\\w+)$", (String consumerNumber) -> {
            pageStore.get(CollectionsPage.class).collectChargeFor(consumerNumber);
        });
        And("^he chooses to pay water charge$", () -> {
            pageStore.get(CollectionsPage.class).collectCharge();
        });
        And("^he create challan and closes acknowledgement$", () -> {
            String challanNumber = pageStore.get(CollectionsPage.class).generateChallan();
            scenarioContext.setChallanNumber(challanNumber);

            String msg = pageStore.get(CollectionsPage.class).successMessage();
            scenarioContext.setActualMessage(msg);

          //  pageStore.get(CollectionsPage.class).close();
        });
        And("^he pay using (\\w+)$", (String paymentMethod) -> {
            PaymentMethod paymentmethod = new ExcelReader(collectionsTestDataFileName).getPaymentMethodDetails(paymentMethod);
            pageStore.get(PropertyTaxPage.class).collectTax(paymentmethod,paymentMethod,"challan");
        });
        Given("^User will Visit Property Tax onlinepayent link$", () -> {
            pageStore.get(CollectionsPage.class).propertyTaxOnlinePaymentLink();

        });
        And("^User will enter Assessment Number and click on search button$", () -> {
           // pageStore.get(CollectionsPage.class).enerterAssessmentNumber("1016094329");
            pageStore.get(CollectionsPage.class).enerterAssessmentNumber(scenarioContext.getAssessmentNumber());
        });
        And("^user will fill amount and select the AXIS Bank Payment Gateway and click on PayOnline$", () -> {
            pageStore.get(CollectionsPage.class).enterAmountAndPayOnline();
        });
        And("^user will select the card, enter all the details and click on pay now button$", () -> {
           pageStore.get(CollectionsPage.class).enterCarddetailsAndPay();
        });


    }

}
