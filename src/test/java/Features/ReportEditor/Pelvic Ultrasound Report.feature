Feature: Pelvic Ultrasound Report

  @TC_PUS
  @TC_PUS-Uterus
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Uterus observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Uterus" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Endometrium
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Endometrium observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Endometrium" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Cervix
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Cervix observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Cervix" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-RightOvary
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Right Ovary observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Right Ovary" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-LeftOvary
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Left Ovary observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Left Ovary" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-TubeF-Right
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Right Fallopian Tube observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Right F-Tube" tab in "Fallopian Tube" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-TubeF-Left
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Left Fallopian Tube observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Left F-Tube" tab in "Fallopian Tube" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-CulDeSac
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Cul de Sac observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Cul de Sac" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Bladder
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Bladder observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Bladder" tab in "Kidney Bladder" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-RightKidney
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Right Kidney observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Right Kidney" tab in "Kidney Bladder" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-LeftKidney
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Left Kidney observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Left Kidney" tab in "Kidney Bladder" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Prostate
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Prostate observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Prostate" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Recommendations
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Recommendations observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Recommendations" tab in "Recommendations" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Indications
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Indications are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Indications" section
    And I enter below details in the below fields under "Free Text" tab in "Indications" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Diagnosis
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Diagnosis are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Diagnosis" section
    And I enter below details in the below fields under "Free Text" tab in "Diagnosis" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-Procedure
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Procedure are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Procedure" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-ExaminationInfo
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Examination information are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Examination information" tab in "Examination information" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-PatientInfo
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Patient information are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Patient information" tab in "Patient information" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_PUS
  @TC_PUS-CopyTo
  @DeleteLastSignedReport
  Scenario: Verify entered details in Pelvic Ultrasound report Copy to information are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "Pelvic Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Copy to" tab in "Copy to" section
    And I sign the report
    And close report viewer
    When I right click on "Pelvic Ultrasound" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "Pelvic Ultrasound" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports