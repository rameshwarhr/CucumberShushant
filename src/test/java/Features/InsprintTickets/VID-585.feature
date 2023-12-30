@VID-585
Feature: VID-585

  @585_1
    @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
  Scenario Outline: Verify echo final report is opened when its prelim report is signed with cardiac index field value
    Given vidistar application is launched
    And I have logged in the application as "<userRole>" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I click on "Left Ventricle" under left panel
    And I click on "Hemodynamics" subtab
    And I enter below details in the "Echo Preliminary Report" fields
      | field         | type | value |
      | Cardiac Index | text | 3.5   |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I log out of the application
    And log back in the application as "Physician" user
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    And report editor tab should open
    And "Cardiac Index" should be reflected on the dynamic preview
    Examples:
      | userRole   |
      | Admin      |
      | Technician |