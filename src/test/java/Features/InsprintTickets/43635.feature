@insprint
Feature: 43635

  @RevertClinicParameterValue
    @RevertAndDeleteFinalReport
    @43635-1
  Scenario Outline: Verify series and instance delete button behavior after signing the final report with Admin role
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "<markReadOnlyClinicalParameter>" and click on Save
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then study list should be displayed
    When I log out of the application
    And log back in the application as "Technician" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    When I log out of the application
    And log back in the application as "Admin" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    Examples:
      | markReadOnlyClinicalParameter | DeleteButtonStatus |
      | true                          | disabled           |
      | false                         | enabled            |

  @RevertClinicParameterValue
    @RevertAndDeleteFinalReport
    @43635-2
  Scenario Outline: Verify series and instance delete button behavior after signing the final report with Physician role
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "<markReadOnlyClinicalParameter>" and click on Save
    And I log out of the application
    And log back in the application as "Physician" user
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then study list should be displayed
    When I log out of the application
    And log back in the application as "Technician" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    When I log out of the application
    And log back in the application as "Admin" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    Examples:
      | markReadOnlyClinicalParameter | DeleteButtonStatus |
      | true                          | disabled           |
      | false                         | enabled            |

  @RevertReport @RevertClinicParameterValue
    @43635-3
  Scenario Outline: Verify whether study series and instance delete buttons are disabled after marking final report as Completed by Admin
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "<markReadOnlyClinicalParameter>" and click on Save
    And I navigate to "Study List" tab
    And I enter "COLLINS^JAXX" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "completed"
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    When I log out of the application
    And log back in the application as "<UserRole>" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    Examples:
      | UserRole   | markReadOnlyClinicalParameter | DeleteButtonStatus |
      | Technician | true                          | disabled           |
      | Technician | false                         | enabled            |

  @RevertClinicParameterValue
    @RevertFinalReport
    @43635-4
  Scenario Outline: Verify whether study series and instance delete buttons are disabled after marking final report as Completed by Technician
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "<markReadOnlyClinicalParameter>" and click on Save
    And I log out of the application
    And I have logged in the application as "<UserRole1>" user
    And I enter "COLLINS^JAXX" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "completed"
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    When I log out of the application
    And log back in the application as "<UserRole2>" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "<DeleteButtonStatus>"
    And delete "Series" button should be "<DeleteButtonStatus>"
    Examples:
      | UserRole1  | UserRole2 | markReadOnlyClinicalParameter | DeleteButtonStatus |
      | Technician | Admin     | true                          | disabled           |
      | Technician | Admin     | false                         | enabled            |

  @43635-5
  Scenario: Verify whether tooltip displays study locked message after marking final report as Completed by Admin
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I log out of the application
    And log back in the application as "Technician" user
    And I enter "WAYNE^JOHN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "completed"
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    And I hover on "Instance" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Instance Cannot be deleted"
    When I hover on "Series" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Series Cannot be Deleted"
    When I log out of the application
    And log back in the application as "Admin" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    And I hover on "Instance" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Instance Cannot be deleted"
    When I hover on "Series" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Series Cannot be Deleted"

  @RevertReport @RevertClinicParameterValue
  @43635-6
  Scenario: Verify whether tooltip displays study locked message after marking final report as Completed by Technician
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I enter "WAYNE^JOHN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "completed"
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    And I hover on "Instance" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Instance Cannot be deleted"
    When I hover on "Series" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Series Cannot be Deleted"
    When I log out of the application
    And log back in the application as "Technician" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    And I hover on "Instance" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Instance Cannot be deleted"
    When I hover on "Series" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Series Cannot be Deleted"

  @RevertClinicParameterValue
  @RevertAndDeleteFinalReport
  @43635-7
  Scenario: Verify whether tooltip displays study locked message after signing the final report with Admin user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then study list should be displayed
    When I log out of the application
    And log back in the application as "Technician" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    And I hover on "Instance" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Instance Cannot be deleted"
    When I hover on "Series" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Series Cannot be Deleted"
    When I log out of the application
    And log back in the application as "Admin" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    And I hover on "Instance" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Instance Cannot be deleted"
    When I hover on "Series" delete button
    Then disabled delete icon tooltip should display "Study is ReadOnly. Series Cannot be Deleted"

  @RevertClinicParameterValue
  @RevertFinalReport
  @43635-8
  Scenario: Verify whether study series and instance delete buttons are not disabled after importing PDF report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    And navigate to "Reports" menu
    And select the "Import PDF Report" option
    And import the PDF report file
    Then PDF report should be present in the report list
    When I close the dicom viewer
    Then study list should be displayed
    When I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    Then delete "Instance" button should be "enabled"
    And delete "Series" button should be "enabled"

  @RevertClinicParameterValue
  @RevertAndDeleteFinalReport
  @43635-9
  Scenario: Verify whether only study with final report COMPLETED gets disabled for a patient with multiple studies using Admin user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then study list should be displayed
    When I log out of the application
    And log back in the application as "Technician" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 2nd study
    And I "expand" 1st series
    Then delete "Series" button should be "disabled"
    When I "collapse" the 1st study
    And I "expand" the 3rd study
    And I "expand" 1st series
    Then delete "Series" button should be "enabled"
    When I log out of the application
    And log back in the application as "Admin" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 2nd study
    And I "expand" 1st series
    Then delete "Series" button should be "disabled"
    When I "collapse" the 1st study
    And I "expand" the 3rd study
    And I "expand" 1st series
    Then delete "Series" button should be "enabled"

  @RevertClinicParameterValue
  @RevertAndDeleteFinalReport
  @43635-10
  Scenario: Verify whether only study with final report COMPLETED gets disabled for a patient with multiple studies using Physician user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And search "markReadOnlyOnSign" in clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I log out of the application
    And log back in the application as "Physician" user
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    Then study list should be displayed
    When I log out of the application
    And log back in the application as "Technician" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 2nd study
    And I "expand" 1st series
    Then delete "Series" button should be "disabled"
    When I "collapse" the 1st study
    And I "expand" the 3rd study
    And I "expand" 1st series
    Then delete "Series" button should be "enabled"
    When I log out of the application
    And log back in the application as "Admin" user
    And search the same patient again
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 2nd study
    And I "expand" 1st series
    Then delete "Series" button should be "disabled"
    When I "collapse" the 1st study
    And I "expand" the 3rd study
    And I "expand" 1st series
    Then delete "Series" button should be "enabled"