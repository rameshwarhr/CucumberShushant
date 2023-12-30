@insprint
Feature: 46406

  @46406-1
  Scenario: Verify ECG study with MDC coding schema displays both Report preview and Datasets
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    Then reporting UI left panel should be displayed
    When I select "show datasets" display view
    Then reporting UI left panel should be displayed

  @46406-2
  Scenario: Verify left panel contains the listed tabs for ECG study with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    And left panel should contain the following tabs
      | Patient Profile     |
      | Measurements        |
      | ECG Interpretations |
      | Recommendations     |
      | Indications         |
      | Procedure           |
      | Diagnosis           |
      | Reports             |
      | Previous studies    |

  @46406-3
  Scenario: Verify Patient profile data and Measurement section for ECG study with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "Patient Profile"
    Then expanded section should contain the below fields
      | Sex        |
      | DOB        |
      | Exam Date  |
      | Study Time |
    When I select "Measurements"
    Then expanded section should contain the below fields
      | Vent Rate (BPM) |
      | PR int (ms)     |
      | QRS Dur (ms)    |
      | QT (ms)         |
      | QTC (ms)        |
      | P Axes          |
      | R Axes          |
      | T Axes          |
      | R-R Int (ms)    |

  @46406-4
  @901_1
  Scenario: Verify user is able to enter patient details in various tabs for study with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Patient Profile"
    And edit "Sex" as "F"
    And edit "DOB" as "01/18/1926"
    And edit "Exam Date" as "01/18/1960"
    And edit "Study Time" as "11:09:59am"
    Then report should get populated with "Patient Profile" data
    When I select "Measurements"
    And enter "Vent Rate (BPM)" as "10"
    And enter "PR int (ms)" as "20"
    And enter "QRS Dur (ms)" as "30"
    And enter "QT (ms)" as "40"
    And enter "QTC (ms)" as "50"
    And enter "P Axes" as "70"
    And enter "R Axes" as "60"
    And enter "T Axes" as "50"
    And enter "R-R Int (ms)" as "60"
    Then report should get populated with "Measurements" data

  @RevertSignedSimpleViewerReport
  @46406-5
  Scenario: Verify whether user is able to sign ECG study report with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I sign report
    Then "Final Report" status should be "COMPLETED"

  @46406-6
  Scenario: Verify whether ecg waveform is displayed in rich viewer for study with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open an ecg study for patient with first name as "JONATHAN" and last name as "TEST"
    Then viewer should load the study for patient
    And ecg waveform should be displayed in rich viewer

  @46406-7
  Scenario: Verify whether ecg waveform is displayed in rich viewer for study with non MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open an ecg study for patient with first name as "MATHEW" and last name as "TEST"
    Then viewer should load the study for patient
    And ecg waveform should be displayed in rich viewer

  @46406-8
  Scenario: Verify user is able to add indications to the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Indications"
    And select "Myocardial Perfusion Scan" in "indications" category
    And select code "A00.0" from table
    Then "Indications" should get added to the report

  @46406-9
  Scenario: Verify user is able to add procedures to the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Procedure"
    And select "Myocardial Perfusion Scan" in "procedure" category
    And select code "0016070" from table
    Then "Procedure" should get added to the report

  @46406-10
  Scenario: Verify whether diagnosis are added to the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Diagnosis"
    And select "Myocardial Perfusion Scan" in "diagnosis" category
    And select code "0010" from table
    Then "Diagnosis" should get added to the report

  @46406-11
  Scenario: Verify user is able to enter notes in Recommendations area
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "Recommendations"
    And enter "Physician notes" in the recommendations area
    Then "Recommendations" should get added to the report

  @46406-12
  Scenario: Verify error message for incorrect field format in Patient Profile tab
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Patient Profile"
    And edit "DOB" as "-01/-18/1926"
    Then error should be displayed
    When I edit "Exam Date" field as "-01/-18/1960"
    Then error should be displayed
    When I edit "Study Time" field as "11:09:59TM"
    Then error should be displayed

  @46406-13
  Scenario: Verify error message for incorrect field format in Measurements tab
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Measurements"
    And enter "Vent Rate (BPM)" as "AB"
    Then error should be displayed
    And enter "PR int (ms)" as "AB"
    Then error should be displayed
    And enter "QRS Dur (ms)" as "AB"
    Then error should be displayed
    And enter "QT (ms)" as "%*"
    Then error should be displayed
    And enter "QTC (ms)" as "%*"
    Then error should be displayed
    And enter "P Axes" as "0.18"
    Then error should be displayed
    And enter "R Axes" as "09-TF"
    Then error should be displayed
    And enter "T Axes" as "1/1"
    Then error should be displayed
    And enter "R-R Int (ms)" as "2*5"
    Then error should be displayed

  @RevertSignedReportPrintAutomatically
  @46406-14
  Scenario: Verify sign report functionality for study with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open an ecg study for patient with first name as "JONATHAN" and last name as "TEST"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    And select "Print report automatically after signing" checkbox
    And save the preferences
    And I navigate to "Reports" menu
    And select the "EKG Final Report" option
    Then the Report Editor window should load
    When I sign the report
    Then print report window should be displayed

  @RevertSignedReportPrintAutomatically
  @46406-15
  Scenario: Verify sign report functionality for study with non MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open an ecg study for patient with first name as "MATHEW" and last name as "TEST"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    And select "Print report automatically after signing" checkbox
    And save the preferences
    And I navigate to "Reports" menu
    And select the "EKG Final Report" option
    Then the Report Editor window should load
    When I sign the report
    Then print report window should be displayed