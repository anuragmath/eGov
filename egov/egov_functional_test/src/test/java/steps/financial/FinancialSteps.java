package steps.financial;

import cucumber.api.java8.En;
import entities.financial.FinancialBankDetails;
import entities.financial.FinancialExpenseBillDetails;
import entities.financial.FinancialJournalVoucherDetails;
import entities.propertyTax.ApprovalDetails;
import org.junit.Assert;
import pages.financial.FinancialPage;
import steps.BaseSteps;
import utils.ExcelReader;

import java.text.ParseException;

/**
 * Created by vinaykumar on 20/12/16.
 */
public class FinancialSteps extends BaseSteps implements En {

    public FinancialSteps() {

        And("^officer will enter the journal voucher details as (\\w+)$", (String voucher) -> {
            FinancialJournalVoucherDetails financialJournalVoucherDetails = new ExcelReader(financialTestDataFileName).getJournalVoucherDetails(voucher);
            pageStore.get(FinancialPage.class).enterJournalVoucherDetails(financialJournalVoucherDetails);
        });

        And("^officer will enter the approval details as (\\w+)$", (String approveOfficer) -> {
            ApprovalDetails approvalDetails = new ExcelReader(ptisTestDataFileName).getApprovalDetails(approveOfficer);
            try {
                String userName = pageStore.get(FinancialPage.class).enterFinanceApprovalDetails(approvalDetails);
                scenarioContext.setUser(userName);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        And("^officer will get successful voucher created and closes it$", () -> {
            String voucherNumber = pageStore.get(FinancialPage.class).getVoucherNumber();
            scenarioContext.setVoucherNumber(voucherNumber.split("\\.")[0].split("\\ ")[1]);
            scenarioContext.setActualMessage(voucherNumber.split("\\.")[0]);
        });

        Then("^the officer will click on the above voucher number$", () -> {
            pageStore.get(FinancialPage.class).openVoucherFromInboxOrDrafts(scenarioContext.getVoucherNumber());
        });

        And("^officer will closes the acknowledgement page$", () -> {
            String actualMessage = pageStore.get(FinancialPage.class).closePage();
            scenarioContext.setActualMessage(actualMessage);

            if(scenarioContext.getIsRemittance() == 1) {
                scenarioContext.setVoucherNumber(actualMessage.split("\\n")[0].split("\\ ")[7] + "-CASH");
                scenarioContext.setIsRemittance(0);
            }
        });

        And("^officer click on approval of the voucher$", () -> {
            pageStore.get(FinancialPage.class).approvalPage();
        });

        Then("^officer will modify the results depending upon the fund and date as (\\w+)$", (String date) -> {
            pageStore.get(FinancialPage.class).billSearch(date);
        });

        And("^officer will act upon the above voucher with payment mode as (\\w+)$", (String paymentMode) -> {
            pageStore.get(FinancialPage.class).actOnAboveVoucher(paymentMode , scenarioContext.getVoucherNumber());
        });

        And("^officer will verify the voucher number$", () -> {
            String voucher = pageStore.get(FinancialPage.class).verifyVoucher();
            Assert.assertEquals(voucher , scenarioContext.getVoucherNumber());
        });

        And("^officer will enter the bank details$", () -> {
            String bankDetails = "SBI";
            FinancialBankDetails financialBankDetails = new ExcelReader(financialTestDataFileName).getFinancialBankDetails(bankDetails);
            pageStore.get(FinancialPage.class).billPayment(financialBankDetails);
        });

        And("^officer will enter the remittance bank details$", () -> {
            String bankDetails = "SBI1";
            FinancialBankDetails financialBankDetails = new ExcelReader(financialTestDataFileName).getFinancialBankDetails(bankDetails);
            pageStore.get(FinancialPage.class).billRemittancePayment(financialBankDetails);
            scenarioContext.setIsRemittance(1);

        });

        And("^officer will the expense bill details as (\\w+)$", (String expenseBill) -> {
            FinancialExpenseBillDetails financialBill = new ExcelReader(financialTestDataFileName).getFinancialExpenseBillDetails(expenseBill);
            pageStore.get(FinancialPage.class).createNewExpenseBill(financialBill);
        });

        And("^officer will enter the expense approval details as (\\w+)$", (String approveOfficer) -> {
            ApprovalDetails approvalDetails = new ExcelReader(ptisTestDataFileName).getApprovalDetails(approveOfficer);
            String userName = pageStore.get(FinancialPage.class).enterExpenseApprovalDetails(approvalDetails);
            scenarioContext.setUser(userName.split("\\ ")[0]);
        });

        And("^officer will closes the expense acknowledgement page$", () -> {
            String expenseBillNumber = pageStore.get(FinancialPage.class).closesTheExpensePage();
            scenarioContext.setVoucherNumber(expenseBillNumber.split("\\ ")[2]);
            scenarioContext.setActualMessage(expenseBillNumber.split("\\ ")[3]);
        });

        And("^officer will closes the successfull payment page$", () -> {
            String billNUmber = pageStore.get(FinancialPage.class).closesSuccessfulPaymentPage();
            scenarioContext.setVoucherNumber(billNUmber);
        });

        And("^officer will get successful BAN NUMBER created and closes it$", () -> {
            String voucherNumber = pageStore.get(FinancialPage.class).getVoucherNumber();
            scenarioContext.setVoucherNumber(voucherNumber.split("\\ ")[1]);
            scenarioContext.setActualMessage(voucherNumber.split("\\:")[1]);
        });

        And("^user will enter the account code to modify as (\\w+)$", (String glCode) -> {
            pageStore.get(FinancialPage.class).enterAccountCodeToModify(glCode);
        });

        And("^user will map the account code to particular$", () -> {
            pageStore.get(FinancialPage.class).toModifyTheGLCodeAccount();
        });

        And("^officer will enter the remittance details as (\\w+)$", (String voucher) -> {
            FinancialJournalVoucherDetails financialJournalVoucherDetails = new ExcelReader(financialTestDataFileName).getJournalVoucherDetails(voucher);
            pageStore.get(FinancialPage.class).enterRemittanceVoucherDetails(financialJournalVoucherDetails);
        });

        And("^officer will search for remittance bills$", () -> {
            pageStore.get(FinancialPage.class).searchRemittanceBill();
            pageStore.get(FinancialPage.class).selectRemittanceBIll(scenarioContext.getVoucherNumber());
        });

        And("^officer will filter the bill according to the type$", () -> {
            pageStore.get(FinancialPage.class).filterCreateVoucherBill(scenarioContext.getVoucherNumber());
        });

        And("^officer will set the new expense voucher number and closes it$", () -> {
            String expenseVoucherMessage = pageStore.get(FinancialPage.class).closesExpenseVoucherPage();
            scenarioContext.setVoucherNumber(expenseVoucherMessage.split("\\ ")[4].split("\\.")[0]);
            scenarioContext.setActualMessage(expenseVoucherMessage);
        });

        And("^officer will filter the payment cheque assignment bill$", () -> {
            pageStore.get(FinancialPage.class).chequeAssignmentBillSearch(scenarioContext.getVoucherNumber());
        });

        And("^officer will select the bill and enter the details (\\w+)$", (String assignmentMode) -> {
            pageStore.get(FinancialPage.class).toFillChequeAssignmentDetails(assignmentMode);
        });

        And("^officer will close the successfull assignment page$", () -> {
            String msg = pageStore.get(FinancialPage.class).closeAssignmentSuccessPage();
            scenarioContext.setActualMessage(msg);
        });

        And("^officer will enter the direct bank payment details with (\\w+)$", (String mode) -> {
            pageStore.get(FinancialPage.class).enterDirectBankPaymentDetails(mode);
        });

        And("^officer will see the successful voucher creation page and closes it$", () -> {
            String msg = pageStore.get(FinancialPage.class).directBankSuccessPage();
            if(msg.contains("Successful")) {
                scenarioContext.setVoucherNumber(msg.split("\\ ")[8]);
            }
            scenarioContext.setActualMessage(msg);
        });

        And("^officer will enter the bank to bank transfer details$", () -> {
            pageStore.get(FinancialPage.class).enterBankToBankDetails();
        });

        And("^officer will close the successful creation page$", () -> {
            String message = pageStore.get(FinancialPage.class).closesSuccessfulTransferCreationPage();
            scenarioContext.setActualMessage(message);
        });
    }
}
