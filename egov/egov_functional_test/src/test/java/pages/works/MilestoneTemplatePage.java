package pages.works;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;

import java.util.Calendar;
import java.util.List;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by karthik on 21/12/16.
 */
public class MilestoneTemplatePage extends BasePage {

    private WebDriver driver;

    @FindBy(css = "input[name ='code'][type = 'text']")
    private WebElement templateCodeBox;

    @FindBy(xpath = ".//*[@id='name']")
    private WebElement templateNameTextBox;

    @FindBy(id = "templateDescription")
    private WebElement templateDescriptionTextBox;

    @FindBy(xpath = ".//*[@id='typeOfWork']")
    private WebElement typeOfWorkBox;

    @FindBy(xpath = ".//*[@id='subType']")
    private WebElement subTypeOfWorkBox;

    @FindBy(id = "temptActvRow")
    private WebElement addTemplateActivityButton;

    @FindBy(className = "yui-dt-data")
    private WebElement stageTable;

    @FindBy(css = "input[value='Save'][type='submit']")
    private WebElement saveButton;

    @FindBy(css = "input[id='closeButton'][value='Close']")
    private WebElement closeButton;

    @FindBy(xpath = ".//*[@id='workType']")
    private WebElement typeOfWorkForViewBox;

    @FindBy(css = "input[value='Search'][type='submit']")
    private WebElement searchButton;

    @FindBy(id = "currentRow")
    private WebElement searchTable;

    @FindBy(xpath = "(//*[@id='currentRow']/tbody/tr/td/a)[last()]")
    private WebElement requiredRowForView;

    @FindBy(css = "input[value='Modify'][type='button']")
    private WebElement modifyButton;

    @FindBy(css = "input[value='Modify'][type='submit']")
    private WebElement modifyButtonAfterModication;

    @FindBy(css = "div[id='msgsDiv'][class='new-page-header']")
    private WebElement creationMsg;

    @FindBy(xpath = "(.//*[@id='milestoneTemplate-searchDetails']/div[4]/div[1]/div[2]/span[2]/a)[last()]")
    private WebElement lastPageLink;

    public MilestoneTemplatePage(WebDriver driver) {
        this.driver = driver;
    }

    public String successMessage(){
        waitForElementToBeVisible(creationMsg, driver);
        String msg = creationMsg.getText();
        System.out.println("---------->"+msg);
        return msg;
    }

    public void enterMilestoneTemplateDetails() {

     waitForElementToBeVisible(templateCodeBox,driver);
     templateCodeBox.sendKeys("TC"+get6DigitRandomInt());

     waitForElementToBeClickable(templateNameTextBox,driver);
     templateNameTextBox.sendKeys("testing");

     waitForElementToBeClickable(templateDescriptionTextBox,driver);
     templateDescriptionTextBox.sendKeys("Test-automation for the project");

     waitForElementToBeClickable(typeOfWorkBox,driver);
     new Select(typeOfWorkBox).selectByVisibleText("Roads, Drains, Bridges and Flyovers");

     waitForElementToBeClickable(subTypeOfWorkBox,driver);
     waitForElementToBePresent(By.cssSelector("select[id='subType'] option[value='5']"),driver);
     new Select(subTypeOfWorkBox).selectByVisibleText("Roads");

     waitForElementToBeVisible(addTemplateActivityButton,driver);
     addTemplateActivityButton.click();

     waitForElementToBeVisible(stageTable,driver);
     List<WebElement>totalRows = stageTable.findElement(By.tagName("tr")).findElements(By.tagName("td"));
        WebElement stageOrderTextBox = totalRows.get(0).findElement(By.className("slnowk"));
        stageOrderTextBox.sendKeys("1");
        WebElement stageDescriptionTextBox = totalRows.get(1).findElement(By.className("selectmultilinewk"));
        stageDescriptionTextBox.sendKeys("Testing for Roads");
        WebElement stagePercentageTextBox = totalRows.get(2).findElement(By.className("selectamountwk"));
        stagePercentageTextBox.sendKeys("100");
    }

    public void save() {
        waitForElementToBeClickable(saveButton, driver);
        saveButton.click();
    }

    public void close(){
        waitForElementToBeVisible(closeButton, driver);
        closeButton.click();

        switchToPreviouslyOpenedWindow(driver);
    }

    public void enterMilestoneTemplateDetailsForView() {

        waitForElementToBeVisible(typeOfWorkForViewBox,driver);
        new Select(typeOfWorkForViewBox).selectByVisibleText("Roads, Drains, Bridges and Flyovers");

        waitForElementToBeClickable(searchButton,driver);
        searchButton.click();
    }

    public void selectTheRequiredTemplate() {
        waitForElementToBeVisible(searchTable,driver);

        boolean isPresent =  driver.findElements(By.xpath("(.//*[@id='milestoneTemplate-searchDetails']/div[4]/div[1]/div[2]/span[2]/a)[last()]")).size() > 0;

        if(isPresent) {
            waitForElementToBeVisible(lastPageLink, driver);
            lastPageLink.click();
        }

        requiredRowForView.click();
    }

    public void selectTheRequiredTemplateToModify() {

        boolean isPresent =  driver.findElements(By.xpath("(.//*[@id='milestoneTemplate-searchDetails']/div[4]/div[1]/div[2]/span[2]/a)[last()]")).size() > 0;

        if(isPresent) {
            waitForElementToBeVisible(lastPageLink, driver);
            lastPageLink.click();
        }

        waitForElementToBeVisible(searchTable,driver);
        List<WebElement> totalRows = searchTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        System.out.println("Rows:"+totalRows.size());
        WebElement requiredRow = totalRows.get(totalRows.size()-1);
        WebElement element = requiredRow.findElements(By.tagName("td")).get(0).findElement(By.id("radio"));
        jsClick(element,driver);

        waitForElementToBeClickable(modifyButton,driver);
        modifyButton.click();

        switchToNewlyOpenedWindow(driver);
    }

    public void modifyTheMileStoneTemplateSelected() {

        waitForElementToBeVisible(stageTable,driver);
        List<WebElement>totalRows = stageTable.findElement(By.tagName("tr")).findElements(By.tagName("td"));

        WebElement stagePercentageTextBox = totalRows.get(2).findElement(By.className("selectamountwk"));
        stagePercentageTextBox.clear();
        stagePercentageTextBox.sendKeys("80");

        waitForElementToBeVisible(addTemplateActivityButton,driver);
        addTemplateActivityButton.click();
        WebElement requiredRow = stageTable.findElements(By.tagName("tr")).get(1);

        WebElement stageOrderTextBox2 =  requiredRow.findElements(By.tagName("td")).get(0).findElement(By.id("stageOrderNoyui-rec1"));
        stageOrderTextBox2.sendKeys("2");

        WebElement stageDescriptionTextBox2 = requiredRow.findElements(By.tagName("td")).get(1).findElement(By.id("descriptionyui-rec1"));
        stageDescriptionTextBox2.sendKeys("Testing for modification");

        WebElement stagePercentageTextBox2 = requiredRow.findElements(By.tagName("td")).get(2).findElement(By.id("percentageyui-rec1"));
        stagePercentageTextBox2.sendKeys("20");

        modifyButtonAfterModication.click();
    }

    public void closeMultiple(){
        waitForElementToBeClickable(closeButton , driver);
        closeButton.click();

        for (String winHandle : driver.getWindowHandles()) {
            if(driver.switchTo().window(winHandle).getTitle().equals("eGov Works Search Milestone Template")){
                break;
            }
        }

        waitForElementToBeVisible(typeOfWorkForViewBox,driver);
        new Select(typeOfWorkForViewBox).selectByVisibleText("--------Select--------");

        waitForElementToBeClickable(searchButton,driver);
        searchButton.click();

        close();
    }
}