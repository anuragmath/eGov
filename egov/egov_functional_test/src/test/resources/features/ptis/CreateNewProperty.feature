Feature: Create New Property

  As a register user of the system
  I want to be able to create a new property
  So that the property records are up to date.

  # CREATE NEW PROPERTY SCREEN #

  @Sanity @PropertyTax
  Scenario Outline: Registered user creating a new property in the system
    Given juniorAssistant logs in

    When he chooses to create new property
    And he enters property header details as <propertyHeaderDetails>
    And he enters owner details for the first owner as <ownerDetails>
    And he enters property address details as <propertyAddressDetails>
    And he enters assessment details as <assessmentDetails>

    And he enters amenities as <amenitiesDetails>
    And he enters construction type details as <constructionTypeDetails>
    And he enters floor details as <floorDetails>
    And he enters document type details as <documentDetails>
    And he forwards for approval to billCollector
    Then create property details get saved successfully
    And he will copy the application number
    And user will be notified by "Successfully"
    And current user logs out

    When billCollector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueInspector
    And current user closes acknowledgement
    And current user logs out

    When revenueInspector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueOfficer
    And current user closes acknowledgement
    And current user logs out

    When revenueOfficer logs in
    And chooses to act upon the above application
    And he forwards for approval to commissioner
    And current user closes acknowledgement
    And current user logs out

    When commissioner logs in
    And chooses to act upon the above application
    And he approved the property with remarks "property approved"
    Then create property details get saved successfully
    And he will copy the assessment number
    Then user will be notified by "Successfully"
    And chooses to act upon the above assessment
    And he does a digital signature
    When commissioner closes acknowledgement
    And current user logs out

    And juniorAssistant logs in
    And chooses to act upon the above assessment
    And he generates a notice
    And current user logs out

    Examples:
      | propertyHeaderDetails | ownerDetails | propertyAddressDetails | assessmentDetails     | amenitiesDetails | constructionTypeDetails | floorDetails | documentDetails |
      | residentialPrivate    | bimal        | addressOne             | assessmentNewProperty | all              | defaultConstructionType | firstFloor   | documentSelect  |

   # DATA ENTRY SCREEN #

  @Sanity @PropertyTax
  Scenario: Registered user create property through data entry screen

    Given commissioner logs in
    When he chooses to create data entry
    And he creates a new assessment for a private residential property
    Then dataEntry Details saved successfully
    And he choose to add edit DCB
    And he choose to close the dataentry acknowledgement screen
    And current user logs out

   # ADDITION ALTERATION SCREEN #

  @Sanity @PropertyTax
  Scenario Outline: Registered user Update existing property

    Given commissioner logs in
    When he chooses to create data entry
    And he creates a new assessment for a private residential property
    Then dataEntry Details saved successfully
    And he choose to add edit DCB
    And he choose to close the dataentry acknowledgement screen
    And current user logs out

    Given juniorAssistant logs in
    When he chooses to addition alteration
    And he searches for assessment with number "1016042569"
    And he updates assessment details as <editAssessmentDetails>
    And he enters amenities as <amenitiesDetails>
 #    And he enters Construction Details as <ConstructionTypes1>
    And he enters Floor Details as <editFloorDetails>
    And he forwards for approval to billCollector
    Then edit property details get saved successfully
    And current user logs out

    When billCollector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueInspector
    And current user closes acknowledgement
    And current user logs out

    When revenueInspector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueOfficer
    And current user closes acknowledgement
    And current user logs out

    When revenueOfficer logs in
    And chooses to act upon the above application
    And he forwards for approval to commissioner
    And current user closes acknowledgement
    And current user logs out

    When commissioner logs in
    And chooses to act upon the above application
    And he approved the property with remarks addition "property approved"
    And current user closes acknowledgement

    And chooses to act upon the above assessment
    And he does a digital signature

    When commissioner closes acknowledgement
    And current user logs out

    And juniorAssistant logs in
    And chooses to act upon the above assessment
    And he generates a notice
    And current user logs out

    Examples:
        |  editAssessmentDetails         |     amenitiesDetails |  editFloorDetails            |
        |  assessmentAdditionProperty    |        all           |  firstFloorAdditionaltaration|

  # TRANSFER OF OWNERSHIP SCREEN #

  @Sanity @PropertyTax
  Scenario Outline: Register Choose to do title Transfer

    Given commissioner logs in
    When he chooses to create data entry
    And he creates a new assessment for a private residential property
    Then dataEntry Details saved successfully
    And he choose to add edit DCB
    And he choose to close the dataentry acknowledgement screen
    And current user logs out

    Given juniorAssistant logs in
    When he chooses to collect tax of property
    And he searches for assessment with number "1016000077"
    And he chooses to pay tax
    And he pay tax using Cash

    And he choose to do transfer ownership
    And he searches for assessment with number "1016042569"
    And he chooses Registration already done button
    And he enters registration details for the property <registrationDetails>
    And he enters enclosure details

    And he forwards for approval to billCollector
    Then edit property details get saved successfully
    And current user logs out

    When billCollector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueInspector
    And current user closes acknowledgement
    And current user logs out

    When revenueInspector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueOfficer
    And current user closes acknowledgement
    And current user logs out

    Given juniorAssistant logs in
    When he chooses mutation fee
    And he searches for the assessment with mutation assessment number
    And he pay tax using Cash
    And current user logs out

    When revenueOfficer logs in
    And chooses to act upon the above application
    And he forwards for approval to commissioner
    And current user closes acknowledgement
    And current user logs out

    When commissioner logs in
    And chooses to act upon the above application
    And he approved the property with remarks "property approved" for transfer of ownership
    And current user closes acknowledgement

    And chooses to act upon the above assessment
    And he does a digital signature

    When commissioner closes acknowledgement
    And current user logs out

    And juniorAssistant logs in
    And chooses to act upon the above assessment
    And he generate title transfer notice
    And current user logs out

    Examples:
      |registrationDetails|
      |register           |

  # CREATE REVISION PETITION #

  @Sanity @PropertyTax
  Scenario Outline: Register user choose to do revision petition of property

    Given juniorAssistant logs in
    When he chooses to create new property
    And he enters property header details as <propertyHeaderDetails>
    And he enters owner details for the first owner as <ownerDetails>
    And he enters property address details as <propertyAddressDetails>
    And he enters assessment details as <assessmentDetails>

    And he enters amenities as <amenitiesDetails>
    And he enters construction type details as <constructionTypeDetails>
    And he enters floor details as <floorDetails>
    And he enters document type details as <documentDetails>
    And he forwards for approval to billCollector
    Then create property details get saved successfully
    And he will copy the application number
    And user will be notified by "Successfully"
    And current user logs out

    When billCollector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueInspector
    And current user closes acknowledgement
    And current user logs out

    When revenueInspector logs in
    And chooses to act upon the above application
    And he forwards for approval to revenueOfficer
    And current user closes acknowledgement
    And current user logs out

    When revenueOfficer logs in
    And chooses to act upon the above application
    And he forwards for approval to commissioner
    And current user closes acknowledgement
    And current user logs out

    When commissioner logs in
    And chooses to act upon the above application
    And he approved the property with remarks "property approved"
    Then create property details get saved successfully
    And he will copy the assessment number
    Then user will be notified by "Successfully"

    And chooses to act upon the above assessment
    And he does a digital signature
    When commissioner closes acknowledgement
    And current user logs out

    And juniorAssistant logs in
    And chooses to act upon the above assessment
    And he generates a notice

    When he choose to create revision petition
    And he search for assessment from commissioner screen
    And he choose revision petition header
    And he enters revision petition details<revisionPetitionDetails>
    And he forwards for approval to commissioner
    And current user closes acknowledgement
    And current user logs out
    When commissioner logs in
    And chooses to act upon the above application
    And he choose revision petition header
    And he enters hearing details<hearingDetails>
    And current user closes acknowledgement
    And current user logs out

    And juniorAssistant logs in
    And chooses to act upon the above assessment
    And he choose revision petition header
    And he enters approver remark
    And he forwards for approval to revenueInspector
    And current user closes acknowledgement
    And current user logs out

    When revenueInspector logs in
    And chooses to act upon the above application
    And he enters reason for modification
    And he choose revision petition header
    And he enters inspection details

    And he forwards for approval to revenueOfficer
    And current user closes acknowledgement
    And current user logs out

    When revenueOfficer logs in
    And chooses to act upon the above application
    And he choose revision petition header
    And he enters approver remark
    And he forwards for approval to commissioner
    And current user closes acknowledgement
    And current user logs out

    When commissioner logs in
    And chooses to act upon the above application
    And he choose to approve for revision petition
    And current user closes acknowledgement

    And chooses to act upon the above assessment
    And he prints endoresement notice

    And chooses to act upon the above assessment
    And he does a digital signature

    When commissioner closes acknowledgement
    And current user logs out

    And juniorAssistant logs in
    And chooses to act upon the above assessment
    And he generates a print special notice
    And current user logs out

    Examples:
      | propertyHeaderDetails | ownerDetails | propertyAddressDetails | assessmentDetails     | amenitiesDetails | constructionTypeDetails | floorDetails | revisionPetitionDetails |  hearingDetails | documentDetails |
      | residentialPrivate    | bimal        | addressOne             | assessmentNewProperty | all              | defaultConstructionType | firstFloor   |  revisionpetitionBlock  |  hearingBlock   | documentSelect  |


    # GENERAL REVISION PETITION #

   @Sanity @PropertyTax
   Scenario Outline: Register user choose to do general revision petition of property

     Given commissioner logs in
     When he chooses to create data entry
     And he creates a new assessment for a private residential property
     Then dataEntry Details saved successfully
     And he choose to add edit DCB
     And he choose to close the dataentry acknowledgement screen
     And current user logs out

     Given juniorAssistant logs in
     When he chooses to collect tax of property
     And he searches for assessment with number "1016000077"
     And he chooses to pay tax
     And he pay tax using Cash

     And he choose to do general revision petition
     And he searches for assessment with number "1016042569"

     And he choose revision petition header
     And he enters revision petition details<revisionPetitionDetails>
     And he forwards for approval to commissioner
     And current user closes acknowledgement
     And current user logs out
     When commissioner logs in
     And chooses to act upon the above application
     And he choose revision petition header
     And he enters hearing details<hearingDetails>
     And current user closes acknowledgement
     And current user logs out

     And juniorAssistant logs in
     And chooses to act upon the above assessment
     And he choose revision petition header
     And he enters approver remark
     And he forwards for approval to revenueInspector
     And current user closes acknowledgement
     And current user logs out

     When revenueInspector logs in
     And chooses to act upon the above application
     And he enters reason for modification
     And he choose revision petition header
     And he enters inspection details

     And he forwards for approval to revenueOfficer
     And current user closes acknowledgement
     And current user logs out

     When revenueOfficer logs in
     And chooses to act upon the above application
     And he choose revision petition header
     And he enters approver remark
     And he forwards for approval to commissioner
     And current user closes acknowledgement
     And current user logs out

     When commissioner logs in
     And chooses to act upon the above application
     And he choose to approve for revision petition
     And current user closes acknowledgement

     And chooses to act upon the above assessment
     And he prints endoresement notice

     And chooses to act upon the above assessment
     And he does a digital signature

     When commissioner closes acknowledgement
     And current user logs out

     And juniorAssistant logs in
     And chooses to act upon the above assessment
     And he generates a print special notice
     And current user logs out

     Examples:
       |revisionPetitionDetails |  hearingDetails |
       | revisionpetitionBlock  |  hearingBlock   |




























