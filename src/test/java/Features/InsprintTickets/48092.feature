@48092
Feature: 48092

  @48092_1
  @DeleteLatestInstance
  Scenario: Verify that the admin user is not able to see the patient details in log file after annotating the image
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And get the count of total images
    And I click on "Text" option
    And I click on image in the window
    And I annotate "1234" in image
    And I right click on viewport 0
    And click on "Save" option
    Then annotated image should appear at the end of the study
    When close the dicom viewer
    And I log out of the application
    Then the patient name "THOMAS" and patient DOB "10/02/1961" should not be present in the log file

  @48092_2
  Scenario: Verify that the admin user is not able to see the patient details in log file after creating a new final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I right click on "Echo Report" from reports list
    And click on "Create new final report" option for report
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    And I log out of the application
    Then the patient name "THOMAS" and patient DOB "10/02/1961" should not be present in the log file

  @48902_3
  @RevertAndDeleteLatestSignedFinalReport
  Scenario: Verify that the physician user is not able to see the patient details in log file after amending the report
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I open a study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I right click on "Echo Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "Indications History" under left panel
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
    And close the dicom viewer
    And I log out of the application
    Then the patient name "MAKAYLA" and patient DOB "05/05/2020" should not be present in the log file

  @48092_4
    @RevertAndDeleteLatestSignedFinalReport
  Scenario Outline: Verify that the physician user is not able to see the patient details in log file after signing the report
    Given vidistar application is launched
    And I have logged in the application as "<users>" user
    When I open a study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<report subtype>" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    And I log out of the application
    Then the patient name "MAKAYLA" and patient DOB "05/05/2020" should not be present in the log file
    Examples:
      | users      | report subtype                          |
      | physician  | Echo Final Report                       |
      | technician | Exercise Stress Echo Preliminary Report |