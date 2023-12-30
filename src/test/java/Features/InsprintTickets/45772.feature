@45772
Feature: 45772

  @45772_1
  @RevertSignedReport
  Scenario: verify final report status is completed when the worksheet status is scheduled and we sign the report in simple viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "BUSBY^SHERION" patient name in "Patient Name" filter
    And I change the worksheet status to "SCHEDULED"
    And I change the final report status to "SCHEDULED"
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I sign report
    Then "Final Report" status should be "COMPLETED"

  @45772_2
  @45772_3
  @45772_8
  @RevertSignedReport
  Scenario: Verify final report status is completed when the worksheet status is completed and we sign the report in simple viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "BUSBY^SHERION" patient name in "Patient Name" filter
    And I change the worksheet status to "COMPLETED"
    And I change the final report status to "SCHEDULED"
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I sign report
    Then "Final Report" status should be "COMPLETED"
    When right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I sign report
    Then "Final Report" status should be "COMPLETED"

  @45772_4
  Scenario: Verify worksheet report status is completed when the prelim report is signed from report viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "BUSBY^SHERION" patient name in "Patient Name" filter
    And I change the worksheet status to "SCHEDULED"
    And I change the final report status to "SCHEDULED"
    And right click on the study from study list
    And I select "Open with Rich Viewer" option from study list right click menu
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "EKG Preliminary Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then I should return to the study list page
    And "Worksheet" status should be "COMPLETED"

  @45772_5
  @45772_9
  @RevertAndDeleteFinalReport
  Scenario: Verify final report status is completed when the final report is signed from report viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "BUSBY^SHERION" patient name in "Patient Name" filter
    And I change the worksheet status to "SCHEDULED"
    And I change the final report status to "SCHEDULED"
    And right click on the study from study list
    And I select "Open with Rich Viewer" option from study list right click menu
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "EKG Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then I should return to the study list page
    And "Final Report" status should be "COMPLETED"
    When right click on the study from study list
    And I select "Open with Rich Viewer" option from study list right click menu
    Then viewer should load the study for patient
    When I switch to web application window
    Then "Final Report" status should be "COMPLETED"

  @45772_6
  @45772_7
  @RevertAndDeleteFinalReport
  Scenario: Verify final report status is completed when we sign the final report in rich viewer and again open the report in simple viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "BUSBY^SHERION" patient name in "Patient Name" filter
    And I change the worksheet status to "SCHEDULED"
    And I change the final report status to "SCHEDULED"
    And right click on the study from study list
    And I select "Open with Rich Viewer" option from study list right click menu
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "EKG Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then I should return to the study list page
    And "Final Report" status should be "COMPLETED"
    When right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I navigate to "Study List" tab
    Then "Final Report" status should be "COMPLETED"
    When I navigate to Simple Viewer tab
    And I close the viewer tab
    Then "Final Report" status should be "COMPLETED"

  @45772_10
  @RevertClinicParameterValue
  @RevertReport
  Scenario: Verify report list is visible when final report status is completed and clinic parameter is set to true
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I enter "BUSBY^SHERION" patient name in "Patient Name" filter
    And I change the final report status to "COMPLETED"
    And right click on the study from study list
    And I select "Open with Rich Viewer" option from study list right click menu
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    Then "EKG Final Report" option "should not" be displayed