@VID-67
Feature: VID-67

  @67_1
  @67_2
  @67_3
  @67_4
  @67_5
  @67_6
  @67_7
  Scenario: To verify different types of SR open in the DSR viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "TERESA" and last name as "HUSSEIN"
    Then viewer should load the study for patient
    When I double click on the "Fetal Echo Report" from reports list
    Then the Report Editor window should load
    When I close report viewer
    And I click on "SR Others" tab on the viewer
    When I double click on the "Vascular Ultrasound Procedure Report" under SR Others tab on viewer
    Then Dicom SR tab for "Vascular Ultrasound Procedure Report" should open in dicom viewer window
    When I click on previous "tm" study from the study list tab
    And I double click on the "Nuclear processing results" under SR Others tab on viewer
    Then Dicom SR tab for "Nuclear processing results" should open in dicom viewer window
    When I sort Description in ascending in study list tab for study "echo"
    And I click on previous "echo" study from the study list tab
    When I double click on the "Adult Echocardiography Procedure Report" under SR Others tab on viewer
    Then Dicom SR tab for "Adult Echocardiography Procedure Report" should open in dicom viewer window
    When I "expand" node in DSR viewer
    Then tree item should unfold on DSR viewer
    When I "collapse" node in DSR viewer
    Then tree item should collapse on DSR viewer

  @DeleteDSRFile
  @67_8
  @67_9
  @67_15
  @67_16
  Scenario: To verify expand/collapse operation on one SR report does not reflect on other SR when same SR is opened from File menu
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LEXXUS" and last name as "AGUILAR"
    Then viewer should load the study for patient
    When I click on "SR Others" tab on the viewer
    And I double click on the "Adult Echocardiography Procedure Report" under SR Others tab on viewer
    And I expand all nodes for the first tree item
    And I scroll the SR by mouse wheel "downwards" for 2 time(s)
    Then SR should be scrolled
    When I right click on "Adult Echocardiography Procedure Report(selected)" under SR Others tab on viewer
    And export report as "DICOM SR"
    And save the DICOM SR report
    When I open the "File" menu
    And select the "Open File..." option
    And import the DSR report
    Then Dicom SR tab for "DICOM SR Viewer - Report" should open in dicom viewer window
    And all SR nodes should be collapsed

  @67_10
  @67_11
  @67_12
  @67_13
  @67_14
  Scenario: To verify same SR report is not opening in other tabs when we open the SR report twice
    Given vidistar application is launched
    And I have logged in the application as "ReportFixer" user
    When I open a study for patient with first name as "TERESA" and last name as "HUSSEIN"
    Then viewer should load the study for patient
    When I right click on "Fetal Echo Report" from reports list
    Then "View as DSR" option should be "present"
    When I click on "SR Others" tab on the viewer
    When I right click on "Vascular Ultrasound Procedure Report" under SR Others tab on viewer
    And click on "View as DSR" option for report
    And I double click on the "Vascular Ultrasound Procedure Report(selected)" under SR Others tab on viewer
    Then Dicom SR tab for "Vascular Ultrasound Procedure Report" should open only once in dicom viewer window
    When I right click on "Vascular Ultrasound Procedure Report(selected)" under SR Others tab on viewer
    Then "View as DSR" option should be "present"
    And below options should be greyed out for the SR reports
      | View        |
      | Fax & Email |
      | Correct     |

  @67_17
  @67_18
  Scenario: Verify user is able to open the Dicom SR from Reports/SR Others tab
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "TERESA" and last name as "HUSSEIN"
    Then viewer should load the study for patient
    When I right click on "Fetal Echo Report" from reports list
    And click on "View as DSR" option for report
    Then Dicom SR tab for "Fetal Echo Report" should open in dicom viewer window
    And I click on "SR Others" tab on the viewer
    And I right click on "Vascular Ultrasound Procedure Report" under SR Others tab on viewer
    And click on "View as DSR" option for report
    Then Dicom SR tab for "Vascular Ultrasound Procedure Report" should open in dicom viewer window