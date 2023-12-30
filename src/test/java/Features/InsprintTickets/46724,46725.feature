@46724
Feature: 46724,46725

  @TC_46725,46724-1
  @TC_46725,46724-2
  @TC_46725,46724-3
  @TC_46725,46724-6
  @RevertSignedWorksheetStatus
  Scenario: Verify that final report does not contains an extra zero in height field and has its unit in cm after signing its prelim report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "TEE Preliminary Report" fields
      | field | type     | value |
      | in    | dropdown | cm    |
    And I enter below details in the "TEE Preliminary Report" fields
      | field          | type | value |
      | Height(Yellow) | text | 80    |
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Final Report" option
    Then the Report Editor window should load
    And the height field in the report should not contain an extra zero
    When I click on "Actual size" icon
    Then the height field in dynamic preview should not contain an extra zero and should be displayed in cm
    When I close report viewer
    And click on Don't Save
    And close the dicom viewer
    And I revert the status of the signed prelim report
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "TEE Preliminary Report" fields
      | field | type     | value |
      | in    | dropdown | cm    |
    And I enter below details in the "TEE Preliminary Report" fields
      | field          | type | value |
      | Height(Yellow) | text | 80    |
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Final Report" option
    Then the Report Editor window should load
    And the height field in the report should not contain an extra zero
    When I click on "Actual size" icon
    Then the height field in dynamic preview should not contain an extra zero and should be displayed in cm

  @TC_46725,46724-4
  @TC_46725,46724-5
  @TC_46725,46724-10
  @RevertSignedWorksheetStatus
  Scenario: Verify that weight and BSA units are not affected in final report after signing its prelim report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Preliminary Report" option
    Then the Report Editor window should load
    And I enter below details in the "TEE Preliminary Report" fields
      | field  | type | value |
      | Height | text | 31.5  |
    And I click on "Actual size" icon
    Then "Height(In Inches)" value should be reflected on the dynamic preview
    When I enter below details in the "TEE Preliminary Report" fields
      | field  | type | value |
      | Weight | text | 65    |
    Then BSA value should be reflected on report editor
    When I enter below details in the "TEE Preliminary Report" fields
      | field | type     | value |
      | in    | dropdown | cm    |
    Then "Height(In cm)" value should be reflected on the dynamic preview
    And BSA value should not be updated on report editor
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Final Report" option
    Then the Report Editor window should load
    When I click on "Actual size" icon
    Then the weight and BSA field in the dynamic preview should have the same unit and value as prelim report
    When I enter below details in the "TEE Preliminary Report" fields
      | field | type     | value |
      | cm    | dropdown | in    |
    Then "Height(In Inches)" value should be reflected on the dynamic preview
    And BSA value should not be updated on report editor

  @TC_46725,46724-7
  @RevertSignedWorksheetStatus
  Scenario: Verify that final report does not contains an extra zero in height field when the prelim report is signed with data other than height field
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "TEE Preliminary Report" fields
      | field | type     | value |
      | in    | dropdown | cm    |
    And I enter below details in the "TEE Preliminary Report" fields
      | field          | type | value |
      | Height(Yellow) | text | 80    |
    And I click on "Indications History" under left panel
    And I select the below codes
      | Typhoid fever                        |
      | Typhoid fever with heart involvement |
      | Typhoid arthritis                    |
    And I click on "Study Quality" in report editor left panel
    And I right click on "Average" label
    And I click on "Left Ventricle" in report editor left panel
    And I right click on "Normal systolic function" label
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Final Report" option
    Then the Report Editor window should load
    When I click on "Actual size" icon
    Then the height field in dynamic preview should not contain an extra zero

  @TC_46725,46724-8
  Scenario: Verify that "Use preferred units in report editor" is removed from window preferences
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And "Use preferred units in report editor" option should not be present in DICOM viewer

  @TC_46725,46724-9
    @TC_46725,46724-12
    @RevertSignedWorksheetStatus
  Scenario Outline: Verify that final report does not contain an extra zero for different height values in prelim report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "TEE Preliminary Report" fields
      | field | type     | value |
      | in    | dropdown | cm    |
    And I enter below details in the "TEE Preliminary Report" fields
      | field  | type | value    |
      | Height | text | <Height> |
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "TEE Final Report" option
    Then the Report Editor window should load
    When I click on "Actual size" icon
    Then the height field in dynamic preview should not contain an extra zero when the value is entered in three digit or in decimal
    Examples:
      | Height |
      | 850    |
      | 8.6    |