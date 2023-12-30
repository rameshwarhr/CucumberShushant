Feature: General Ultrasound Report

  @TC_GUS
  @TC_GUS-PatientInfo
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Patient profile data are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Patient profile data" tab in "Patient profile data" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-StudyDescription
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Study Description are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "Study Description" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-Diagnosis
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Diagnosis are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Diagnosis" section
    And I enter below details in the below fields under "Free Text" tab in "Diagnosis" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-Procedure
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Procedure are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Procedure" section
    And I enter below details in the below fields under "Free Text" tab in "Procedure" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-LesionLocation
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Lesion location are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Lesion location" tab in "Lesion location" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-LesionCharacteristic
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Lesion characteristic are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Lesion characteristic" tab in "Lesion characteristic" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-PhysicalExam
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Physical Exam are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Physical Exam" tab in "Physical Exam" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-Intervention
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Intervention are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Intervention" tab in "Intervention" section
    And I enter below details in the below fields under "Biopsy Approach" tab in "Intervention" section
    And I enter below details in the below fields under "Device Utilized" tab in "Intervention" section
    And I enter below details in the below fields under "Post Biopsy Image" tab in "Intervention" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-Follow-upPlan
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Follow-up Plan are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Follow-up Plan" tab in "Follow-up Plan" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-USFindings
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report US Findings are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Right" tab in "US Findings" section
    And I enter below details in the below fields under "Left" tab in "US Findings" section
    And I enter below details in the below fields under "Favorites" tab in "US Findings" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-Conclusions
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Conclusions are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Conclusions" tab in "Conclusions" section
    And I enter below details in the below fields under "Favorites" tab in "Conclusions" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-PhoneLog
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Phone log are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Phone log" tab in "Phone log" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_GUS
  @TC_GUS-CopyTo
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report Copy to are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Copy to" tab in "Copy to" section
    And I sign the report
    And close report viewer
    When I right click on "Ultrasound report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Ultrasound report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports