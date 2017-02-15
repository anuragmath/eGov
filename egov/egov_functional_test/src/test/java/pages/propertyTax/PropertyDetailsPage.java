package pages.propertyTax;

import entities.propertyTax.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

public class PropertyDetailsPage extends BasePage {

    private WebDriver webDriver;

    @FindBy(id = "propTypeCategoryId")
    private WebElement propertyTypeSelection;

    @FindBy(id = "upicNo")
    private WebElement propertyAssessmentNo;

    @FindBy(id = "propTypeId")
    private WebElement categoryOfOwnershipSelection;

    @FindBy(id = "aadharNo")
    private WebElement aadharNoTextBox;

    @FindBy(id = "mobileNumber")
    private WebElement newMobileNumberTextBox;

    @FindBy(id = "mobileNumber")
    private WebElement mobileNumberTextBox;

    @FindBy(id = "ownerName")
    private WebElement ownerNameTextBox;

    @FindBy(id = "gender")
    private WebElement genderSelection;

    @FindBy(id = "emailId")
    private WebElement emailIdTextBox;

    @FindBy(id = "basicProperty.propertyOwnerInfoProxy[0].owner.guardianRelation")
    private WebElement guardianRelationSelection;

    @FindBy(id = "guardian")
    private WebElement guardianTextBox;

    @FindBy(id = "locality")
    private WebElement localitySelection;

    @FindBy(id = "zoneId")
    private WebElement zoneNumberSelection;

    @FindBy(id = "wardId")
    private WebElement wardNumberSelection;

    @FindBy(id = "blockId")
    private WebElement blockNumberSelection;

    @FindBy(id = "electionWardId")
    private WebElement electionWardSeletion;

    @FindBy(id = "createProperty-create_pinCode")
    private WebElement pincodeTextBox;

    @FindBy(id = "createProperty-create_houseNumber")
    private WebElement doorNumberTextBox;

    @FindBy(id = "mutationId")
    private WebElement reasonForCreationSelection;

    @FindBy(id = "areaOfPlot")
    private WebElement extentOfSiteTextBox;

    @FindBy(id = "propertyDetail.occupancyCertificationNo")
    private WebElement occupancyCertificateNumberTextBox;

    @FindBy(id = "assessmentDocumentNames")
    private WebElement documentTypeDropBox;

    @FindBy(id = "docNo")
    private WebElement deedNoTextBox;

    @FindBy(id = "docDate")
    private WebElement DeedDateDateBox;

//    @FindBy(id = "regdDocNo")
//    private WebElement registrationDocNumber;

//    @FindBy(id = "basicProperty.regdDocDate")
//    private WebElement registrationDocDate;

    @FindBy(id = "propertyDetail.lift")
    private WebElement liftCheckbox;

    @FindBy(id = "propertyDetail.toilets")
    private WebElement toiletsCheckbox;

    @FindBy(id = "propertyDetail.waterTap")
    private WebElement waterTapCheckbox;

    @FindBy(id = "propertyDetail.electricity")
    private WebElement electricityCheckbox;

    @FindBy(id = "propertyDetail.attachedBathRoom")
    private WebElement attachedBathroomCheckbox;

    @FindBy(id = "propertyDetail.waterHarvesting")
    private WebElement waterHarvestingCheckbox;

    @FindBy(id = "propertyDetail.cable")
    private WebElement cableConnectionCheckbox;

    @FindBy(id = "floorTypeId")
    private WebElement floorTypeSelection;

    @FindBy(id = "roofTypeId")
    private WebElement roofTypeSelection;

    @FindBy(id = "wallTypeId")
    private WebElement wallTypeSelection;

    @FindBy(id = "woodTypeId")
    private WebElement woodTypeSelection;

    @FindBy(id = "floorNo")
    private WebElement floorNumberSelection;

    @FindBy(id = "floorConstType")
    private WebElement classificationOfBuildingSelection;

    @FindBy(id = "floorUsage")
    private WebElement natureOfUsageSelection;

    @FindBy(id = "firmName")
    private WebElement firmNameTextBox;

    @FindBy(id = "floorOccupation")
    private WebElement occupancySelection;

    @FindBy(id = "occupantName")
    private WebElement occupantNameTextBox;

    @FindBy(id = "propertyDetail.floorDetailsProxy[0].constructionDate")
    private WebElement constructionDateTextBox;

    @FindBy(id = "propertyDetail.floorDetailsProxy[0].occupancyDate")
    private WebElement effectiveFromDateTextBox;

    @FindBy(id = "unstructuredLand")
    private WebElement unstructuredLandSelection;

    @FindBy(id = "builtUpArealength")
    private WebElement lengthTextBox;

    @FindBy(id = "builtUpAreabreadth")
    private WebElement breadthTextBox;

    @FindBy(id = "builtUpArea")
    private WebElement plinthAreaTextBox;

    @FindBy(id = "propertyDetail.floorDetailsProxy[0].buildingPermissionNo")
    private WebElement buildingPermissionNumberTextBox;

    @FindBy(id = "propertyDetail.floorDetailsProxy[0].buildingPermissionDate")
    private WebElement buildingPermissionDateTextBox;

    @FindBy(id = "propertyDetail.floorDetailsProxy[0].buildingPlanPlinthArea.area")
    private WebElement plinthAreaInBuildingPlanTextBox;

    @FindBy(id = "approverDepartment")
    private WebElement approverDepartmentSelection;

    @FindBy(id = "approverDesignation")
    private WebElement approverDesignationSelection;

    @FindBy(id = "approverPositionId")
    private WebElement approverSelection;

    @FindBy(id = "Forward")
    private WebElement forwardButton;

    @FindBy(id = "Approve")
    private WebElement approveButton;

    @FindBy(id = "Sign")
    private WebElement signButton;

    @FindBy(id = "Generate Notice")
    private WebElement generateNotice;

    @FindBy(id = "Create")
    private WebElement create;

    @FindBy(id = "propertyIdentifier")
    private WebElement assessmentNumberTextBox1;

    @FindBy(id = "consumerCodeData")
    private WebElement hscNumberTextBox;

    @FindBy(id = "executionDate")
    private WebElement connectionDateTextBox;

    @FindBy(xpath = ".//*[@id='assessmentNum']")
    private WebElement assessmentNumTextBox;

    @FindBy(id = "searchByassmentno")
    private WebElement searchButtonByAssmentNo;

    @FindBy(id = "doorNo")
    private WebElement doorNoTextBox;

    @FindBy(css = "input[id='zoneform_houseNumBndry'][type='text']")
    private WebElement houseNumTextBoxForSearch;

    @FindBy(css = "input[id='zoneform_ownerNameBndry'][type='text']")
    private WebElement ownerNameTextBoxForSearch;

    @FindBy(id = "mobileNumber")
    private WebElement mobileNoTextBox;

    @FindBy(id = "searchDoorno")
    private WebElement searchButtonByDoorNo;

    @FindBy(id = "searchMobileno")
    private WebElement searchButtonByMobileNo;

    @FindBy(id = "zoneId")
    private WebElement zoneId;

    @FindBy(id = "wardId")
    private WebElement wardId;

    @FindBy(id = "searchByBndry")
    private WebElement searchButtonByZoneAndWard;

    @FindBy(id = "Create")
    private WebElement submitButton;

    @FindBy(id ="upicNo")
    private WebElement assessmentNumberTextBox;

    @FindBy(id = "Create")
    private WebElement createButton;

    @FindBy(id = "assessmentNum")
    private WebElement assessmentTextbox;

    @FindBy(id = "assessmentform_search")
    private WebElement searchButton;

    @FindBy(name = "assessmentNum")
    private WebElement searchAssessmentTextBox;

    @FindBy(id = "certificationNumber")
    private WebElement editOccupancyTextBox;

    @FindBy(id = "occupantname")
    private WebElement editoccupantNameTextBox;

    @FindBy(id = "propertyDetail.floorDetailsProxy[%#floorsstatus.index].constructionDate")
    private WebElement editconstructionDateTextBox;

    @FindBy(id = "propertyDetail.floorDetailsProxy[%#floorsstatus.index].occupancyDate")
    private WebElement editeffectiveFromDateTextBox;

    @FindBy(id = "approvalComent")
    private WebElement approvalWaterComment;

    @FindBy(id = "Forward")
    private WebElement additionalForwardButton;

    @FindBy(css = "input[value='Pay'][type='submit']")
    private WebElement payButton;

    @FindBy(css = "input[type='text'][name = 'totalamounttobepaid']")
    private WebElement propertyAmountPaid;

    @FindBy (css = "input[type='text'][name='instrHeaderCash.instrumentAmount']")
    private WebElement propertyAmountToBePaid;

    @FindBy(xpath = ".//*[@id='createProperty-forward']/div/table/tbody/tr[1]/td")
    private WebElement commAssessmentNo;

    @FindBy(xpath = ".//*[@id='modifyProperty-approve']/div/table/tbody/tr[1]/td")
    private WebElement additionAssessmentNo;

    @FindBy(xpath = ".//*[@id='approve']/div/table/tbody/tr[1]/td/span[2]")
    private WebElement commAssessmentNo1;

    @FindBy(id = "locationId")
    private WebElement locationBoxForSearch;

    @FindBy(id = "ownerName")
    private WebElement ownerNameBoxForSearch;

    @FindBy(id = "searchByowner")
    private WebElement searchButtonForOwner;

    @FindBy(id = "fromDemand")
    private WebElement fromTextBox;

    @FindBy(id = "toDemand")
    private WebElement toTextBox;

    @FindBy(id = "searchByDemand")
    private WebElement searchButtonForDemand;

    String min = String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND));
    String min1 = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));

    public PropertyDetailsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void enterPropertyHeader(PropertyHeaderDetails propertyHeaderDetails) {
        new Select(categoryOfOwnershipSelection).selectByVisibleText(propertyHeaderDetails.getCategoryOfOwnership());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Select(propertyTypeSelection).selectByVisibleText(propertyHeaderDetails.getPropertyType());
    }
    public void enterOwnerDetails(OwnerDetails ownerDetails) {
        waitForElementToBeClickable(newMobileNumberTextBox, webDriver);
        enterText(newMobileNumberTextBox, ownerDetails.getMobileNumber());
        mobileNumberTextBox.sendKeys("94488"+(min+min1));
        enterText(ownerNameTextBox, ownerDetails.getOwnerName());
        new Select(genderSelection).selectByVisibleText(ownerDetails.getGender().toUpperCase());
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript(String.format("document.getElementById('emailId').setAttribute('value', '%s')", ownerDetails.getEmailAddress()));
        new Select(guardianRelationSelection).selectByVisibleText(ownerDetails.getGuardianRelation());
        enterText(guardianTextBox, ownerDetails.getGuardianName());
    }
    public void enterPropertyAddressDetails(PropertyAddressDetails addressDetails) {
        waitForElementToBeClickable(localitySelection, webDriver);
        new Select(localitySelection).selectByVisibleText(addressDetails.getLocality());
        new Select(zoneNumberSelection).selectByVisibleText(addressDetails.getZoneNumber());
        new Select(electionWardSeletion).selectByVisibleText(addressDetails.getElectionWard());
        doorNumberTextBox.sendKeys(addressDetails.getDoorNumber());
        pincodeTextBox.sendKeys(addressDetails.getPincode());
    }
    public void enterAssessmentDetails(AssessmentDetails assessmentDetails) {
        new Select(reasonForCreationSelection).selectByVisibleText(assessmentDetails.getReasonForCreation());
        extentOfSiteTextBox.sendKeys(assessmentDetails.getExtentOfSite());
        occupancyCertificateNumberTextBox.sendKeys(assessmentDetails.getOccupancyCertificateNumber());
//        registrationDocNumber.sendKeys(assessmentDetails.getRegistrationDocNumber());
//        registrationDocDate.sendKeys(assessmentDetails.getRegistrationDocDate());
    }
    public void selectAmenities(Amenities amenities) {
        selectAmenityIfRequired(liftCheckbox, amenities.getLift());
        selectAmenityIfRequired(toiletsCheckbox, amenities.getToilets());
        selectAmenityIfRequired(attachedBathroomCheckbox, amenities.getAttachedBathroom());
        selectAmenityIfRequired(electricityCheckbox, amenities.getElectricity());
        selectAmenityIfRequired(waterTapCheckbox, amenities.getWaterTap());
        selectAmenityIfRequired(waterHarvestingCheckbox, amenities.getWaterHarvesting());
        selectAmenityIfRequired(cableConnectionCheckbox, amenities.getCableConnection());
    }
    private void selectAmenityIfRequired(WebElement element, Boolean hasAmenity) {
        if (hasAmenity && !element.isSelected())
            element.click();
    }
    public void enterConstructionTypeDetails(ConstructionTypeDetails constructionTypeDetails) {
        new Select(floorTypeSelection).selectByVisibleText(constructionTypeDetails.getFloorType());
        new Select(roofTypeSelection).selectByVisibleText(constructionTypeDetails.getRoofType());
        new Select(woodTypeSelection).selectByVisibleText(constructionTypeDetails.getWoodType());
        new Select(wallTypeSelection).selectByVisibleText(constructionTypeDetails.getWallType());
    }
    public void enterFloorDetails(FloorDetails floorDetails) {
        new Select(floorNumberSelection).selectByVisibleText(floorDetails.getFloorNumber());
        new Select(classificationOfBuildingSelection).selectByVisibleText(floorDetails.getClassificationOfBuilding());
        new Select(natureOfUsageSelection).selectByVisibleText(floorDetails.getNatureOfUsage());
        firmNameTextBox.sendKeys(floorDetails.getFirmName());
        new Select(occupancySelection).selectByVisibleText(floorDetails.getOccupancy());
        occupantNameTextBox.sendKeys(floorDetails.getOccupantName());
        constructionDateTextBox.sendKeys(floorDetails.getConstructionDate());
        constructionDateTextBox.sendKeys(Keys.TAB);
        effectiveFromDateTextBox.sendKeys(floorDetails.getEffectiveFromDate());
        effectiveFromDateTextBox.sendKeys(Keys.TAB);
        new Select(unstructuredLandSelection).selectByVisibleText(floorDetails.getUnstructuredLand());
        lengthTextBox.sendKeys(floorDetails.getLength());
        breadthTextBox.sendKeys(floorDetails.getBreadth());
        buildingPermissionNumberTextBox.sendKeys(floorDetails.getBuildingPermissionNumber());
        buildingPermissionDateTextBox.sendKeys(floorDetails.getBuildingPermissionDate());
        plinthAreaInBuildingPlanTextBox.sendKeys(floorDetails.getPlinthAreaInBuildingPlan());


    }

    public void selectDocumentType(DocumentTypeValue documentValue) {
        new Select(documentTypeDropBox).selectByVisibleText(documentValue.getDocumentType());
        enterText(deedNoTextBox, documentValue.getDeedNo());
        enterText(DeedDateDateBox, documentValue.getDeedDate());
    }

    public void enterApprovalDetails(ApprovalDetails approvalDetails) {
        new Select(approverDepartmentSelection).selectByVisibleText(approvalDetails.getApproverDepartment());
        await().atMost(10, SECONDS).until(() -> new Select(approverDesignationSelection).getOptions().size() > 1);
        new Select(approverDesignationSelection).selectByVisibleText(approvalDetails.getApproverDesignation());
        await().atMost(10, SECONDS).until(() -> new Select(approverSelection).getOptions().size() > 1);
        new Select(approverSelection).selectByVisibleText(approvalDetails.getApprover());
    }
    public void forward() {
        forwardButton.click();
    }
    public String approve() {
        approveButton.click();
        waitForElementToBeVisible(commAssessmentNo, webDriver);
        return commAssessmentNo.getText();
    }

    public String approveForCreation() {
        approveButton.click();
        waitForElementToBeVisible(commAssessmentNo1, webDriver);
        return commAssessmentNo1.getText();
    }
    public void digitallySign() {
        signButton.click();
    }
    public void generateNotice() {
        generateNotice.click();
        switchToNewlyOpenedWindow(webDriver);
        webDriver.close();
        switchToPreviouslyOpenedWindow(webDriver);
    }
    public void checkNoOfRecords() {

        Boolean isPresent = webDriver.findElements(By.id("currentRowObject")).size() > 0;

        if(isPresent){
            WebElement tableId = webDriver.findElement(By.id("currentRowObject"));
            waitForElementToBeVisible(tableId,webDriver);
            List<WebElement> totalRows = tableId.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
            System.out.println(" total Number of Records of:"+totalRows.size()+"\n");
        }
        else {
            System.out.println(" no records\n");
        }

    }

    public void enterApplicationInfo(ApplicantInfo applicantInfo){
        waitForElementToBeClickable(assessmentNumberTextBox, webDriver);
        enterText(assessmentNumberTextBox, applicantInfo.getPtAssessmentNumber());
        waitForElementToBeClickable(hscNumberTextBox, webDriver);
        enterText(hscNumberTextBox, applicantInfo.getHscNumber());
        waitForElementToBeClickable(connectionDateTextBox, webDriver);
        enterText(connectionDateTextBox, applicantInfo.getConnectionDate());
    }
    public void chooseToSubmit(){
        waitForElementToBeClickable(submitButton, webDriver);
        submitButton.click();
    }

    public void enterAssessmentNumber(String assessmentNumber) {
        assessmentNumberTextBox.sendKeys(assessmentNumber);
    }
    public void create() {
        createButton.click();
    }
    public void searchAssessmentNumber(String assessmentNum) {
        searchAssessmentTextBox.sendKeys(assessmentNum);
    }
    public void search() {
        searchButton.click();
    }
    public void enterEditAssessmentDetails(EditAssessmentDetails assessmentDetails) {
        extentOfSiteTextBox.clear();
        extentOfSiteTextBox.sendKeys(assessmentDetails.getExtentOfSite());
        editOccupancyTextBox.sendKeys(assessmentDetails.getOccupancyCertificateNumber());
    }
    public void enterEditFloorDetails(EditFloorDetails floorDetails) {
        new Select(floorNumberSelection).selectByVisibleText(floorDetails.getEditfloorNumber());
        new Select(classificationOfBuildingSelection).selectByVisibleText(floorDetails.getEditclassificationOfBuilding());
        new Select(natureOfUsageSelection).selectByVisibleText(floorDetails.getEditnatureOfUsage());
        new Select(occupancySelection).selectByVisibleText(floorDetails.getEditoccupancy());
        editoccupantNameTextBox.sendKeys(floorDetails.getEditoccupantName());
        editconstructionDateTextBox.sendKeys(floorDetails.getEditconstructionDate());
        editconstructionDateTextBox.sendKeys(Keys.TAB);
        editeffectiveFromDateTextBox.sendKeys(floorDetails.getEditeffectiveFromDate());
        editeffectiveFromDateTextBox.sendKeys(Keys.TAB);
        new Select(unstructuredLandSelection).selectByVisibleText(floorDetails.getEditunstructuredLand());
        lengthTextBox.clear();
        lengthTextBox.sendKeys(floorDetails.getEditlength());
        breadthTextBox.clear();
        breadthTextBox.sendKeys(floorDetails.getEditbreadth());
        buildingPermissionNumberTextBox.sendKeys(floorDetails.getEditbuildingPermissionNumber());
        buildingPermissionDateTextBox.sendKeys(floorDetails.getEditbuildingPermissionDate());
        buildingPermissionDateTextBox.sendKeys(Keys.TAB);
        plinthAreaInBuildingPlanTextBox.sendKeys(floorDetails.getEditplinthAreaInBuildingPlan());
    }
    public void payCash() {
        waitForElementToBeClickable(propertyAmountPaid, webDriver);
        waitForElementToBeClickable(propertyAmountToBePaid, webDriver);
        propertyAmountToBePaid.sendKeys(propertyAmountPaid.getAttribute("value").split("\\.")[0]);
        WebElement element = webDriver.findElement(By.id("button2"));
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", element);
        switchToNewlyOpenedWindow(webDriver);
    }

    public void searchProperty(SearchDetails searchDetails, String searchType) {

        System.out.println(searchType+" has ");

        switch (searchType){

            case "searchWithAssessmentNumber":
                waitForElementToBeClickable(assessmentNumTextBox, webDriver);
                enterText(assessmentNumTextBox, searchDetails.getSearchValue1());
                searchButtonByAssmentNo.click();
                break;

            case "searchWithMobileNumber":
                waitForElementToBeClickable(mobileNoTextBox, webDriver);
                enterText(mobileNoTextBox, searchDetails.getSearchValue1());
                searchButtonByMobileNo.click();
                break;

            case "searchWithDoorNumber":
                waitForElementToBeClickable(doorNoTextBox, webDriver);
                enterText(doorNoTextBox, searchDetails.getSearchValue1());
                searchButtonByDoorNo.click();
                break;

            case "searchWithZoneAndWardNumber":
                waitForElementToBeClickable(zoneId, webDriver);
                new Select(zoneId).selectByVisibleText(searchDetails.getSearchValue1());
                new Select(wardId).selectByVisibleText(searchDetails.getSearchValue2());
                enterText(houseNumTextBoxForSearch, searchDetails.getSearchValue3());
                enterText(ownerNameTextBoxForSearch, searchDetails.getSearchValue4());
                searchButtonByZoneAndWard.click();
                break;

            case "searchWithOwnerName":
                waitForElementToBeClickable(locationBoxForSearch,webDriver);
                new Select(locationBoxForSearch).selectByVisibleText(searchDetails.getSearchValue3());
                enterText(ownerNameBoxForSearch,searchDetails.getSearchValue4());
                searchButtonForOwner.click();
                break;

            case "searchByDemand":
                waitForElementToBeClickable(fromTextBox,webDriver);
                enterText(fromTextBox,searchDetails.getSearchValue3());
                enterText(toTextBox,searchDetails.getSearchValue4());
                searchButtonForDemand.click();
                break;
        }
    }

    public String getAssessmentApplicationNo() {
        WebElement ele = webDriver.findElement(By.xpath(".//*[@id='modifyProperty-forward']/div[1]"));
      //  WebElement ele = webDriver.findElement(By.xpath(".//*[@id='save']/div/table/tbody/tr/td[2]"));
        return ele.getText();
    }

    public String approveaddition() {
        approveButton.click();
        waitForElementToBeVisible(additionAssessmentNo, webDriver);
        return additionAssessmentNo.getText();

    }


}
