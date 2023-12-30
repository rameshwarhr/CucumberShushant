@VID-342
Feature: VID-342

  @DeleteLastSignedPrelim
  @342_2
  @342_4
  Scenario: To verify Echo Preliminary Report is amended by the technician and amended data is present in the report when viewed
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "EMIR" and last name as "HERNANDEZ"
    Then viewer should load the study for patient
    When I right click a completed and signed "Echo Preliminary Report" report from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "Diagnosis" in report editor left panel
    And I switch to "FreeText" subtab
    And type "This is amended content" under the "Free Text Area"
    Then "Amended content" should be reflected on the dynamic preview
    When I sign the report
    And close report viewer
    And I right click on "Echo Preliminary Report" from reports list
    And click on "View" option for report
    Then the Report Editor window should load
    And the report contains the amended data

  @DeleteTemplate
  @342_5
  Scenario: To verify Echo Preliminary Report is getting saved as normal template
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "EMIR" and last name as "HERNANDEZ"
    Then viewer should load the study for patient
    When I right click on "Echo Preliminary Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "Diagnosis" in report editor left panel
    And I switch to "FreeText" subtab
    And type "This is amended content" under the "Free Text Area"
    Then "Amended content" should be reflected on the dynamic preview
    When I click on "SAVE AS TEMPLATE" icon
    Then Save Report Template window is opened
    When I enter name "normal template EMIR HERNANDEZ" for normal template
    And I click OK
    And I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And select the "normal template EMIR HERNANDEZ" option
    Then "Amended content" should be reflected on the dynamic preview

  @RevertToggleImageSelection
  @342_6
  Scenario: To verify images are getting attached to Echo Preliminary Report
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "JULIA" and last name as "ROBERTS"
    Then viewer should load the study for patient
    When I right click on viewport 2
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    And I right click on "Echo Preliminary Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "Next" page icon
    Then only selected image should be present in the report