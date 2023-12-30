Feature: Breast Final Report

  @TC_BUS
  @TC_BUS-PatientInfo
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Patient profile data are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Patient profile data" tab in "Patient profile data" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-StudyDescription
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Study Description are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "Study Description" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-Diagnosis
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Diagnosis are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Diagnosis" section
    And I enter below details in the below fields under "Free Text" tab in "Diagnosis" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-Procedure
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Procedure are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Procedure" section
    And I enter below details in the below fields under "Free Text" tab in "Procedure" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-LesionLocation
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Lesion location are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Lesion location" tab in "Lesion location" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-LesionCharacteristic
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Lesion characteristic are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Lesion characteristic" tab in "Lesion characteristic" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-PhysicalExam
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Physical Exam are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Physical Exam" tab in "Physical Exam" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-Intervention
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Intervention are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Intervention" tab in "Intervention" section
    And I enter below details in the below fields under "Biopsy Approach" tab in "Intervention" section
    And I enter below details in the below fields under "Device Utilized" tab in "Intervention" section
    And I enter below details in the below fields under "Post Biopsy Image" tab in "Intervention" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-Follow-upPlan
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Follow-up Plan are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Follow-up Plan" tab in "Follow-up Plan" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-USResults
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report US Results are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Right Breast" tab in "US Results" section
    And I enter below details in the below fields under "Left Breast" tab in "US Results" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-USFindings
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report US Findings are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Right" tab in "US Findings" section
    And I enter below details in the below fields under "Left" tab in "US Findings" section
    And I enter below details in the below fields under "Favorites" tab in "US Findings" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-Conclusions
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Conclusions are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Conclusions" tab in "Conclusions" section
    And I enter below details in the below fields under "Favorites" tab in "Conclusions" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-PhoneLog
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Phone log are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Phone log" tab in "Phone log" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_BUS
  @TC_BUS-CopyTo
  @DeleteLastSignedReport
  Scenario: Verify entered details in breast report Copy to are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Breast - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Copy to" tab in "Copy to" section
    And I sign the report
    And close report viewer
    When I right click on "Breast Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Breast Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports