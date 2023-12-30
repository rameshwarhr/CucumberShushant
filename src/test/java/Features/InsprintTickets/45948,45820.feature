Feature: 45948,45820

  @TC_45948-1
  Scenario: Verify that the user is not able to see the patient details in log file after opening and closing the viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When close the dicom viewer
    And I log out of the application
    Then the patient name "THOMAS" and patient DOB "10/02/1961" should not be present in the log file

  @TC_45984-2
  @TC_45984-3
  Scenario: Verify that the user is not able to see the patient details in log file after signing the report and unfinalizing it
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    And I log out of the application
    Then the patient name "THOMAS" and patient DOB "10/02/1961" should not be present in the log file
    When log back in the application as "Admin" user
    And I enter "THOMAS^JAMES" in "Patient Name" filter under study list tab
    And I revert the status of the signed final report
    And I log out of the application
    Then the patient name "THOMAS" and patient DOB "10/02/1961" should not be present in the log file

  @TC_45984-4
  Scenario: Verify that the user is not able to see the patient details in log file after opening and closing the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I close report viewer
    And click on Don't Save
    And close the dicom viewer
    And I log out of the application
    Then the patient name "THOMAS" and patient DOB "10/02/1961" should not be present in the log file