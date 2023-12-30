@41083
Feature: 41083

  @RevertAndDeleteFinalReport
  @41083_1
  @41083_2
  @41083_3
  @41083_12
  Scenario: To verify View Last Final Report menu option and icon for scheduled/reverted/completed final report status on study list & study tree
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "IRIS" and last name as "GLADES"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" in report editor left panel
    And type "This is a CT Automation Test Fax" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I hover on the first study
    Then floating "View Last Final Report" icon is "enabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "enabled" from study list right click menu
    When I navigate to "Study Tree" tab
    When I expand first patient
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "enabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "enabled" from study tree right click menu
    When I navigate to "Study List" tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "reverted"
    And I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu
    When I navigate to "Study List" tab
    And I right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "scheduled"
    And I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu

  @41083_4
  Scenario: To verify View Last Final Report menu option and icon is greyed out after signing a prelim report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "IRIS" and last name as "GLADES"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    When I expand first patient
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu

  @RevertAndDeleteFinalReport
  @41083_5
  @41083_6
  @41083_7
  @41083_8
  Scenario: To verify View Last Final Report menu option and icon is enabled after signing a final report and greyed out on unfinalizing the study
    Given vidistar application is launched
    And I have logged in the application as "global Admin" user
    When I open a study for patient with first name as "IRIS" and last name as "GLADES"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" in report editor left panel
    And type "This is a CT Automation Test Fax" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I hover on the first study
    Then floating "View Last Final Report" icon is "enabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "enabled" from study list right click menu
    When I navigate to "Study Tree" tab
    When I expand first patient
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "enabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "enabled" from study tree right click menu
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    When I log out of the application
    And log back in the application as "Admin" user
    When I enter "GLADES^IRIS" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Unfinalize study" option from study list right click menu
    Then unfinalize study alert should be displayed
    When I enter "test" and click on "OK"
    Then the final report status should change to "reverted"
    When I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    When I expand first patient
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu
    When I navigate to "Study List" tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "completed"
    And I hover on the first study
    Then floating "View Last Final Report" icon is "enabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "enabled" from study list right click menu
    When I navigate to "Study Tree" tab
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "enabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "enabled" from study tree right click menu
    When I navigate to "Study List" tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "scheduled"
    And I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu
    When I navigate to "Study List" tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "reverted"
    And I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu

  @41083_9
  Scenario: To verify View Last Final Report menu option and icon is greyed out when there is no final report
    Given vidistar application is launched
    When I have logged in the application as "Admin" user
    And I enter "PRICE^ANGELA" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "completed"
    And I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    And I expand first patient
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu

  @RevertAndDeleteFinalReport
  @41083_13
  Scenario: To verify that other icons on the floating toolbar works as expected and should not be greyed out irrespective of the final report status
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I open a study for patient with first name as "IRIS" and last name as "GLADES"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" in report editor left panel
    And type "This is a CT Automation Test Fax" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I hover on the first study
    Then below icons on study list should be "enabled" irrespective of the final report status
      | Assign Study to Physicians           |
      | Assign Study to Referring Physicians |
      | Assign Study to Physician Groups     |
      | Export Last Final Report with HL7    |
      | Study charges                        |
      | Edit Study Attributes                |
    When I navigate to "Study Tree" tab
    And I expand first patient
    And hover on the first study in the study tree tab
    Then below icons on study tree should be "enabled" irrespective of the final report status
      | Assign Study to Physicians           |
      | Assign Study to Referring Physicians |
      | Assign Study to Physician Groups     |
      | Export Last Final Report with HL7    |
      | Study charges                        |
      | Edit Study Attributes                |
    When I navigate to "Study List" tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "reverted"
    And I hover on the first study
    Then below icons on study list should be "enabled" irrespective of the final report status
      | Assign Study to Physicians           |
      | Assign Study to Referring Physicians |
      | Assign Study to Physician Groups     |
      | Export Last Final Report with HL7    |
      | Study charges                        |
      | Edit Study Attributes                |
      | OCR Study                            |
    When I navigate to "Study Tree" tab
    And I expand first patient
    And hover on the first study in the study tree tab
    Then below icons on study tree should be "enabled" irrespective of the final report status
      | Assign Study to Physicians           |
      | Assign Study to Referring Physicians |
      | Assign Study to Physician Groups     |
      | Export Last Final Report with HL7    |
      | Study charges                        |
      | Edit Study Attributes                |
      | OCR Study                            |
    When I navigate to "Study List" tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "scheduled"
    And I hover on the first study
    Then below icons on study list should be "enabled" irrespective of the final report status
      | Assign Study to Physicians           |
      | Assign Study to Referring Physicians |
      | Assign Study to Physician Groups     |
      | Export Last Final Report with HL7    |
      | Study charges                        |
      | Edit Study Attributes                |
      | OCR Study                            |
    When I navigate to "Study Tree" tab
    And hover on the first study in the study tree tab
    Then below icons on study tree should be "enabled" irrespective of the final report status
      | Assign Study to Physicians           |
      | Assign Study to Referring Physicians |
      | Assign Study to Physician Groups     |
      | Export Last Final Report with HL7    |
      | Study charges                        |
      | Edit Study Attributes                |
      | OCR Study                            |
    When I navigate to "Study List" tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "completed"


  @DeleteStudy
  @41083_10
  @41083_11
  Scenario: To verify View Last Final Report menu option and icon is greyed out when we upload a study and sign the prelim report and enabled when we sign final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And click on import study from CD DVD ROM icon
    Then the Upload Wizard window should load
    When I select the DicomDir file
    And follow the prompts and click on finish
    And I navigate to "Study List" tab
    When I open a study for patient with first name as "HOLLY" and last name as "HITACHI"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I hover on the first study
    Then floating "View Last Final Report" icon is "disabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "disabled" from study list right click menu
    When I navigate to "Study Tree" tab
    When I expand first patient
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "disabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "disabled" from study tree right click menu
    And I navigate to "Study List" tab
    When I open a study for patient with first name as "HOLLY" and last name as "HITACHI"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" in report editor left panel
    And type "This is a CT Automation Test Fax" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I hover on the first study
    Then floating "View Last Final Report" icon is "enabled" on study list
    When I right click on the study from study list
    Then "View Last Final Report" option is "enabled" from study list right click menu
    When I navigate to "Study Tree" tab
    And hover on the first study in the study tree tab
    Then floating "View Last Final Report" is "enabled" for study attributes
    When I right click on the first study from study tree
    Then "View Last Final Report" option is "enabled" from study tree right click menu

  @RevertAndDeleteFinalReport
  @RevertClinicParameterValue
  @markReadOnlyOnSign
  Scenario: To verify delete series icon is enabled/disabled after changing the markReadOnlyOnSign clinic parameter
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I open a study for patient with first name as "IRIS" and last name as "GLADES"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" in report editor left panel
    And type "This is a CT Automation Test Fax" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I log out of the application
    And log back in the application as "Admin" user
    And I enter "GLADES^IRIS" in "Patient Name" filter under study list tab
    And I navigate to "Study Tree" tab
    And I expand first patient
    And I expand study attributes
    Then delete series button should be "Disabled"
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "false" and click on Save
    And I log out of the application
    And log back in the application as "Admin" user
    And I enter "GLADES^IRIS" in "Patient Name" filter under study list tab
    And I navigate to "Study Tree" tab
    And I expand first patient
    And I expand study attributes
    Then delete series button should be "Enabled"