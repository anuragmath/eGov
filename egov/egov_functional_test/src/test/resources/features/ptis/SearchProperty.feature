Feature: search property

  As a register user of the system
  I want to be able to search for a property
  So that the property records are up to date.

@Sanity
Scenario Outline: Registered user searching a property with particular search details

  Given Admin logs in

  When he chooses to search property
  And he search property with <searchDetails>
  And he check total number of records found
  And current user closes acknowledgement
  And current user logs out

  Examples:

  |searchDetails              |
  |searchWithAssessmentNumber |
  |searchWithMobileNumber     |
  |searchWithDoorNumber       |
  |searchWithZoneAndWardNumber|
  |searchWithOwnerName        |
  |searchByDemand             |


