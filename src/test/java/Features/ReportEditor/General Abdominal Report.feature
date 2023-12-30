Feature: General Abdominal Report

  @TC_ABUS
  @TC_ABUS-PatientInfo
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Patient profile data are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General US" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Patient profile data" tab in "Patient profile data" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-StudyDescription
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Study description is reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General US" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "Study Description" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Diagnosis
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Diagnosis are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Diagnosis" section
    And I enter below details in the below fields under "Free Text" tab in "Diagnosis" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Procedure
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Procedure are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Procedure" tab in "Procedure" section
    And I enter below details in the below fields under "Codes" tab in "Procedure" section
    And I enter below details in the below fields under "Free Text" tab in "Procedure" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-StudyQuality
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Study quality are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Study Quality" tab in "Study Quality" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-ABDFindings
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report ABD Findings are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "ABD Findings" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Liver
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Liver observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Liver" section
    And I enter below details in the below fields under "Focal lesions" tab in "Liver" section
    And I enter below details in the below fields under "Portal and hepatic Veins" tab in "Liver" section
    And I enter below details in the below fields under "Fluid survey" tab in "Liver" section
    And I enter below details in the below fields under "Favorites" tab in "Liver" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Biliary
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Biliary observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Gallbladder" tab in "Biliary" section
    And I enter below details in the below fields under "Favorites Gallbladder" tab in "Biliary" section
    And I enter below details in the below fields under "Bile ducts" tab in "Biliary" section
    And I enter below details in the below fields under "Favorites Bile Ducts" tab in "Biliary" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Pancreas
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Pancreas observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Pancreas" section
    And I enter below details in the below fields under "Favorites" tab in "Pancreas" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Kidneys
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Kidneys observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Right kidney" tab in "Kidneys" section
    And I enter below details in the below fields under "Right Renal Favorites" tab in "Kidneys" section
    And I enter below details in the below fields under "Left kidney" tab in "Kidneys" section
    And I enter below details in the below fields under "Left Renal Favorites" tab in "Kidneys" section
    And I enter below details in the below fields under "Bilateral Kidneys" tab in "Kidneys" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-BladderProstate
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Bladder Prostate observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Bladder" tab in "Bladder Prostate" section
    And I enter below details in the below fields under "Bladder Favorites" tab in "Bladder Prostate" section
    And I enter below details in the below fields under "Prostate" tab in "Bladder Prostate" section
    And I enter below details in the below fields under "Prostate Favorites" tab in "Bladder Prostate" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Spleen
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Spleen observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Spleen" tab in "Spleen" section
    And I enter below details in the below fields under "Favorites" tab in "Spleen" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-LymphNodes
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Lymph Nodes observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Lymph nodes" tab in "Lymph Nodes" section
    And I enter below details in the below fields under "Favorites" tab in "Lymph Nodes" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Aorta
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Aorta observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Aorta" tab in "Aorta" section
    And I enter below details in the below fields under "Favorites Aorta" tab in "Aorta" section
    And I enter below details in the below fields under "Inferior Vena Cava" tab in "Aorta" section
    And I enter below details in the below fields under "Favorites IVC" tab in "Aorta" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Impressions
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Impressions are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Free Text" tab in "Impressions" section
    And I enter below details in the below fields under "Favorites" tab in "Impressions" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-Recommendations
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Recommendations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Recommendations" tab in "Recommendations" section
    And I enter below details in the below fields under "Favorites" tab in "Recommendations" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-PhoneLog
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Phone Log are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Phone log" tab in "Phone log" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_ABUS
  @TC_ABUS-CopyTo
  @DeleteLastSignedReport
  Scenario: Verify entered details in abdominal ultrasound report Copy To are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "General" option
    And select the "General Abdominal Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Copy to" tab in "Copy to" section
    And I sign the report
    And close report viewer
    When I right click on "General Abdominal Study" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "General Abdominal Study" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports