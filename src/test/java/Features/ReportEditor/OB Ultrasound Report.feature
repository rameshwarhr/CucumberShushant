Feature: OB Ultrasound Report

  @TC_US
  @TC_US-Impressions
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report impressions are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Impressions" tab in "Impressions" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Gestation
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report singleton multiple gestation observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Singleton Multiple Gestation" tab in "Impressions" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Maternal
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report maternal observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Maternal" tab in "Impressions" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-FetalGeneral
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report fetal general observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Biophysical Profile" tab in "Fetus(A)" section
    And I open "Impressions" section on the left panel
    And I enter below details in the below fields under "Fetal General" tab in "Impressions Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-PresentationPlacenta
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report presentation and placenta observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Presentation" tab in "Fetus(A)" section
    And I enter below details in the below fields under "Placenta" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "FETUS" should be present in PDF and TXT reports

  @TC_US
  @TC_US-AntenatalTesting
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Antenatal testing observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Antenatal Testing" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "FETUS" should be present in PDF and TXT reports

  @TC_US
  @TC_US-AFICord
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report AFI and Cord observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I set below values in "Patient information" tab under "Patient information" section
      | field         | value        |
      | EDD Type      | Outside EDD  |
      | EDD (Outside) | Current date |
    And I enter below details in the below fields under "AFI" tab in "Fetus(A)" section
    And I enter below details in the below fields under "Cord" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "FETUS" should be present in PDF and TXT reports

  @TC_US
  @TC_US-HeartRateAndRhythm
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Heart rate and Rhythm observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I open "Fetus(A)" section on the left panel
    And I enter below details in the below fields under "Heart Rate and Rhythm" tab in "Fetus(A) HR and Rhythm" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Head
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Head observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Head" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-Neck
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Neck observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Neck" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-Spine
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Spine observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Spine" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-Face
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Face observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Face" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-Abdomen
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Abdomen observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Abdomen" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-Thorax
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Thorax observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Thorax" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-UpperExtremities
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Upper Extremities observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Upper Extremities" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-LowerExtremities
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Lower Extremities observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Lower Extremities" tab in "Fetus(A)" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all observations marked by " X " for below header values should be correct with entered details in PDF and TXT reports for "Fetal Anatomy Details"
      | Normal     |
      | Suboptimal |
      | Abnormal   |
      | Seen       |
      | Not Seen   |

  @TC_US
  @TC_US-Amniocentesis
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Amniocentesis observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Amniocentesis" tab in "Clinical Procedures" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "Amniocentesis" should be present in PDF and TXT reports

  @TC_US
  @TC_US-General
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report General observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "General" tab in "Clinical Procedures" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "General" should be present in PDF and TXT reports

  @TC_US
  @TC_US-CVS
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report CVS (A) observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "CVS (A)" tab in "Clinical Procedures" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "General" should be present in PDF and TXT reports

  @TC_US
  @TC_US-FetalReduction
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Fetal Reduction observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Fetal Reduction" tab in "Clinical Procedures" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "General" should be present in PDF and TXT reports

  @TC_US
  @TC_US-FetalBloodSampling
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Fetal Blood Sampling observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Fetal Blood Sampling (A)" tab in "Clinical Procedures" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "General" should be present in PDF and TXT reports

  @TC_US
  @TC_US-FetalTransfusion
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Fetal Transfusion (A) observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Fetal Transfusion (A)" tab in "Clinical Procedures" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "General" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Other
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Other observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Other" tab in "Clinical Procedures" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "General" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Uterus
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Uterus observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Uterus" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Endometrium
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Endometrium observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Endometrium" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Cervix
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Cervix observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Cervix" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Ovary-Right
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Right Ovary observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Ovary-Right" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Ovary-Left
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Left Ovary observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Ovary-Left" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-F-Tube-Right
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Right Fallopian tube observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "F Tube-Right" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-F-Tube-Left
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Left Fallopian tube observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "F Tube-Left" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Cul-de-Sac
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Cul de Sac observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Cul de Sac" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Kidney-Bladder
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Kidney/Bladder observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Kidney Bladder" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Ectopic
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Ectopic observations are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Ectopic" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "GYN" should be present in PDF and TXT reports

  @TC_US
  @TC_US-Favorites
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Favorites are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "GYN" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Comments
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Comments" tab in "Comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-ExamInfo
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Examination information are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Examination information" tab in "Examination information" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Indications
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Indications are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Indications" section
    And I enter below details in the below fields under "Other" tab in "Indications" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-ProcedureCodes
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Procedure codes are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Codes" tab in "Procedure codes" section
    And I enter below details in the below fields under "Free text" tab in "Procedure codes" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Medications
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Medications are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Medications" tab in "Medications" section
    And I enter below details in the below fields under "Other" tab in "Medications" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Allergies
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Allergies are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Allergies" tab in "Allergies" section
    And I enter below details in the below fields under "Other" tab in "Allergies" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Labs
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Labs are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Labs" tab in "Labs" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-TechComments
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Tech comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Tech comments" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-NewPatient
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report New patient comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "New patient" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-FollowupPatient
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Follow-up patient comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Follow-up patient" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-BPP
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report BPP comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "BPP" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-CervicalExam
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Cervical exam comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Cervical exam" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-FetalEchoCardiac
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Fetal Echo Cardiac comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Fetal Echo Cardiac" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-OtherAnomalies
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Other Anomalies comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Other Anomalies and Misc" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-TwinGestation
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Twin Gestation comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Twin Gestation" tab in "Tech comments" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-StudyDescription
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Study Description comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Favorites" tab in "Study Description" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-ClinicalSummary
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Clinical Summary comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Clinical Summary" tab in "Clinical Summary" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-Consultation
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Consultation comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Consultation" tab in "Consultation" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports

  @TC_US
  @TC_US-MultipleGestation
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Multiple Gestation comments are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Multiple Gestation" tab in "Multiple Gestation" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details for "Multiple Gestation" should be present in PDF and TXT reports

  @TC_US
  @TC_US-PatientInformation
  @DeleteLastSignedReport
  Scenario: Verify entered details in OB Ultrasound report Patient Information are reflected correctly in PDF and text reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the below fields under "Patient information" tab in "Patient information" section
    And I sign the report
    And close report viewer
    When I right click on "OB Ultrasound final report" from reports list
    And export report as "PDF"
    And save the PDF report
    And I right click on "OB Ultrasound final report" from reports list
    And export report as "TXT"
    And save the TXT report
    Then all entered details should be present in PDF and TXT reports