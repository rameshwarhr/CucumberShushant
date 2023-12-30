@592
Feature: VID-592

  @ExitReportAndViewer
  @592_1
  @592_2
  Scenario: To verify procedure labels are reflected on the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "CHARLES" and last name as "COLEMAN"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Holter Monitor Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                              |
      | 24 hour Holter monitor | 24 hour Holter monitor Preliminary Report |
      | 48 hour Holter monitor | 48 hour Holter monitor Preliminary Report |
      | 72 hour Holter monitor | 72 hour Holter monitor Preliminary Report |
    When I open the "Reports" menu
    And select the "Holter Monitor Report" option
    Then the Report Editor window should load
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                  |
      | 24 hour Holter monitor | 24 hour Holter monitor Report |
      | 48 hour Holter monitor | 48 hour Holter monitor Report |
      | 72 hour Holter monitor | 72 hour Holter monitor Report |

  @ExitReportAndViewer
  @RevertAndDeleteFinalReport
  @592_3
  Scenario: To verify procedure label is reflected on the preview header in the Preliminary report created from the right click menu
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "CHARLES" and last name as "COLEMAN"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Holter Monitor Report" option
    Then the Report Editor window should load
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                  |
      | 72 hour Holter monitor | 72 hour Holter monitor Report |
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "scheduled"
    And I open the same study again
    Then viewer should load the study for patient
    When I right click on "Holter Monitor Report" from reports list
    And click on "Create new preliminary report" option for report
    Then the Report Editor window should load
    And "72 hour Holter monitor Report" should be reflected on the dynamic preview
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                  |
      | 48 hour Holter monitor | 48 hour Holter monitor Report |

  @ExitReportAndViewer
  @RevertAndDeleteFinalReport
  @592_4
  Scenario: To verify procedure label is reflected on the preview header in the final report created from the right click menu
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "CHARLES" and last name as "COLEMAN"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Holter Monitor Report" option
    Then the Report Editor window should load
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                  |
      | 72 hour Holter monitor | 72 hour Holter monitor Report |
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "scheduled"
    And I open the same study again
    Then viewer should load the study for patient
    When I right click on "Holter Monitor Report" from reports list
    And click on "Create new final report" option for report
    Then the Report Editor window should load
    And "72 hour Holter monitor Report" should be reflected on the dynamic preview
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                  |
      | 48 hour Holter monitor | 48 hour Holter monitor Report |

  @592_5
  @592_6
  @592_7
  @592_8
  @ExitReportAndViewer
  @RevertAndDeletePrelimAndFinalReport
  Scenario: To verify procedure label is reflected on the preview header in the amended report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "CHARLES" and last name as "COLEMAN"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Holter Monitor Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                              |
      | 72 hour Holter monitor | 72 hour Holter monitor Preliminary Report |
    When I sign the report
    And close report viewer
    And I right click on "Holter Monitor Preliminary Report" and view the menu options
    And click on "Amend" option for report
    Then the Report Editor window should load
    And "72 hour Holter monitor Preliminary Report" should be reflected on the dynamic preview
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                              |
      | 48 hour Holter monitor | 48 hour Holter monitor Preliminary Report |
    When I open the "Reports" menu
    And select the "Holter Monitor Report" option
    Then the Report Editor window should load
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                  |
      | 24 hour Holter monitor | 24 hour Holter monitor Report |
    When I sign the report
    And close report viewer
    And click on Don't Save
    And close the dicom viewer
    And I right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "scheduled"
    And I open the same study again
    Then viewer should load the study for patient
    When I right click on "Holter Monitor Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    And "24 hour Holter monitor Report" should be reflected on the dynamic preview
    When I click on "Procedure" under left panel
    Then procedures should be reflected in the report preview
      | procedures             | observations                  |
      | 48 hour Holter monitor | 48 hour Holter monitor Report |