Feature: Thyroid Ultrasound Report

  @TC_TUS
  @TC_TUS-PatientInfo
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Patient profile data are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Patient profile data" tab in "Patient profile data" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-StudyDescription
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Study Description are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "Study Description" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-Diagnosis
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Diagnosis are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Diagnosis" section
    And I enter below details in the below fields under "Free Text" tab in "Diagnosis" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-Procedure
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Procedure are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Procedure" section
    And I enter below details in the below fields under "Free Text" tab in "Procedure" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-PhysicalExam
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Physical Exam are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Physical Exam" tab in "Physical Exam" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-Intervention
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Intervention are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Intervention" tab in "Intervention" section
    And I enter below details in the below fields under "Lesion location" tab in "Intervention" section
    And I enter below details in the below fields under "Lesion characteristic" tab in "Intervention" section
    And I enter below details in the below fields under "Biopsy Approach" tab in "Intervention" section
    And I enter below details in the below fields under "Device Utilized" tab in "Intervention" section
    And I enter below details in the below fields under "Post Biopsy Image" tab in "Intervention" section
    And I enter below details in the below fields under "Follow-up Plan" tab in "Intervention" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-Isthmus
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Isthmus are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Isthmus" tab in "Isthmus" section
    And I enter below details in the below fields under "Favorites" tab in "Isthmus" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-RightLobe
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Right Lobe are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Right Lobe" tab in "Right Lobe" section
    And I enter below details in the below fields under "Favorites" tab in "Right Lobe" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-LeftLobe
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Left Lobe are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Left Lobe" tab in "Left Lobe" section
    And I enter below details in the below fields under "Favorites" tab in "Left Lobe" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-RightNeck
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report right neck are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Soft Tissue Mass" tab in "Right neck" section
    And I enter below details in the below fields under "STM Favorites" tab in "Right neck" section
    And I enter below details in the below fields under "Lymph Nodes" tab in "Right neck" section
    And I enter below details in the below fields under "Lymph Nodes Favorites" tab in "Right neck" section
    And I enter below details in the below fields under "Parathyroid" tab in "Right neck" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-LeftNeck
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report left neck are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Soft Tissue Mass" tab in "Left neck" section
    And I enter below details in the below fields under "STM Favorites" tab in "Left neck" section
    And I enter below details in the below fields under "Lymph Nodes" tab in "Left neck" section
    And I enter below details in the below fields under "Lymph Nodes Favorites" tab in "Left neck" section
    And I enter below details in the below fields under "Parathyroid" tab in "Left neck" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-ThyroidFindings
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report thyroid findings are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "Thyroid Findings" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-Impression
  @DeleteLastSignedReport
  Scenario: Verify entered details in ultrasound report impressions are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Impression" section
    And I enter below details in the below fields under "Impressions" tab in "Impression" section
    And I enter below details in the below fields under "Favorites" tab in "Impression" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-PhoneLog
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Phone log are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Phone log" tab in "Phone log" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_TUS
  @TC_TUS-CopyTo
  @DeleteLastSignedReport
  Scenario: Verify entered details in thyroid report Copy to are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Thyroid - Final report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Copy to" tab in "Copy to" section
    And I sign the report
    And close report viewer
    When I right click on "Thyroid Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Thyroid Report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports