package steps.collections;

import cucumber.api.java8.En;
import pages.collections.CollectionAcknowledgementPage;
import pages.propertyTax.PropertyAcknowledgementPage;
import steps.BaseSteps;



/**
 * Created by karthik on 7/12/16.
 */
public class CollectionAcknowledgementSteps extends BaseSteps implements En {
    public CollectionAcknowledgementSteps() {
        And("^he stores the challan number and closes acknowledgement$", () -> {
            String challanNumber = (pageStore.get(CollectionAcknowledgementPage.class).getChallanNumber()) ;
            scenarioContext.setChallanNumber(challanNumber);

            String msg = pageStore.get(CollectionAcknowledgementPage.class).successMessage();
            scenarioContext.setActualMessage(msg);

            pageStore.get(CollectionAcknowledgementPage.class).close();
        });


        And("^he closes the acknowledgement$", () -> {
            pageStore.get(PropertyAcknowledgementPage.class).close();
        });
        And("^he approves the receipt$", () -> {
            try {
                pageStore.get(CollectionAcknowledgementPage.class).approveCollection();
            } catch (Exception e) {
                e.printStackTrace();
            }

            pageStore.get(PropertyAcknowledgementPage.class).close();
        });
    }
}
