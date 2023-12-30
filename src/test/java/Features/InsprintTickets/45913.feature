@45913
Feature: 45913

  @DeleteBurnedPatients
    @45913-1
  Scenario Outline: Verify user is able to download ISO for study after changing report status
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "RAMOS^CARLOS" in "Patient Name" filter under study list tab
    And I change the worksheet status to "<ReportStatus>"
    And I change the final report status to "<ReportStatus>"
    And I log out of the application
    And log back in the application as "<User>" user
    And I enter "RAMOS^CARLOS" in "Patient Name" filter under study list tab
    And I navigate to "Study Tree" tab
    And select the patient for CD burning
    And click on "Burn selected Entities to CD/DVD ROM" button
    And click on Yes
    And I navigate to "Media" tab
    And I search patient "RAMOS^CARLOS" under media tab
    Then the patient should have green check CD icon
    Examples:
      | User       | ReportStatus |
      | Technician | COMPLETED    |
      | Technician | SCHEDULED    |
      | Technician | REVERTED     |
      | Physician  | COMPLETED    |
      | Physician  | SCHEDULED    |
      | Physician  | REVERTED     |

  @RevertClinicParameterValue
    @DeleteBurnedPatients
    @45913-2
  Scenario Outline: Verify user is able to download ISO for study after signing report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "<ReadOnlyFlag>" and click on Save
    And I navigate to "Study List" tab
    When I enter "RAMOS^CARLOS" in "Patient Name" filter under study list tab
    And I change the final report status to "<ReportStatus>"
    And I log out of the application
    And log back in the application as "Physician" user
    And I open a study for patient with first name as "CARLOS" and last name as "RAMOS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I enter "RAMOS^CARLOS" in "Patient Name" filter under study list tab
    And I navigate to "Study Tree" tab
    And select the patient for CD burning
    And click on "Burn selected Entities to CD/DVD ROM" button
    And click on Yes
    And I navigate to "Media" tab
    And I search patient "RAMOS^CARLOS" under media tab
    Then the patient should have green check CD icon
    Examples:
      | ReadOnlyFlag | ReportStatus |
      | true         | SCHEDULED    |
      | true         | REVERTED     |
      | false        | SCHEDULED    |
      | false        | REVERTED     |