Feature: VID-806

  @806_1
  @806_7
  @RevertFinalReportStatusAndRevertClinicParameter
  Scenario: Verify all right clicked menu options are visible on viewport when clinic parameter is set to false
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "false" and click on Save
    And I navigate to "Study List" tab
    And I enter "Thomas^James" in "Patient Name" filter under study list tab
    And I change the worksheet status to "COMPLETED"
    And I change the final report status to "COMPLETED"
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I right click on viewport 0
    Then the following options should be "displayed" in the right click menu list
      | Toggle image selection (for report)  |
      | Select all still images (for report) |
      | Unselect all images (for report)     |
      | Toggle image selection (for viewing) |
      | Unselect all images (for viewing)    |

  @806_2
  @806_3
  @806_13
  Scenario: Verify all right clicked menu options are visible on viewport when clinic parameter is set to true and final report status is scheduled
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I enter "Thomas^James" in "Patient Name" filter under study list tab
    And I change the final report status to "SCHEDULED"
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I right click on viewport 0
    Then the following options should be "displayed" in the right click menu list
      | Toggle image selection (for report)  |
      | Select all still images (for report) |
      | Unselect all images (for report)     |
      | Toggle image selection (for viewing) |
      | Unselect all images (for viewing)    |

  @806_6
  @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
  Scenario: Verify limited right click menu options are visible on viewport when clinic parameter is set to true and final report is signed
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    When I right click on viewport 0
    Then the following options should be "not displayed" in the right click menu list
      | Toggle image selection (for report)  |
      | Select all still images (for report) |
      | Unselect all images (for report)     |
      | Toggle image selection (for viewing) |
      | Unselect all images (for viewing)    |

  @806_8
  @DeleteLastSignedPrelim
  Scenario: Verify all right click menu options are visible when prelim report is signed and clinic parameter is true
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Exercise Stress Echo Preliminary Report" option
    Then the Report Editor window should load
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    When I right click on viewport 0
    Then the following options should be "displayed" in the right click menu list
      | Toggle image selection (for report)  |
      | Select all still images (for report) |
      | Unselect all images (for report)     |
      | Toggle image selection (for viewing) |
      | Unselect all images (for viewing)    |

  @806_9
  @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
  Scenario: Verify limited right click menu options are visible when user signs an amended final report and clinic parameter is true
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I right click on "Pediatric Echo Report(1-18)" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    When I right click on viewport 0
    Then the following options should be "not displayed" in the right click menu list
      | Toggle image selection (for report)  |
      | Select all still images (for report) |
      | Unselect all images (for report)     |
      | Toggle image selection (for viewing) |
      | Unselect all images (for viewing)    |

  @806_14
  @RevertFinalReport
  Scenario: Verify limited right clicked menu options are visible for a paused cineloop when final report status is completed and clinic parameter is true
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "markReadOnlyOnSign" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb3.StudyMgr2.markReadOnlyOnSign" in the filtered clinic parameter result
    And I set the parameter value to "true" and click on Save
    And I navigate to "Study List" tab
    And I enter "Collins^Jaxx" in "Patient Name" filter under study list tab
    And I change the final report status to "COMPLETED"
    And I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    When I go to image number 2
    And I click on "Pause" button to stop the cineloop playing
    And I right click on viewport 0
    Then the following options should be "not displayed" in the right click menu list
      | Toggle image selection (for report)  |
      | Select all still images (for report) |
      | Unselect all images (for report)     |
      | Toggle image selection (for viewing) |
      | Unselect all images (for viewing)    |

  @806_24
  @806_25
  @806_26
  @806_27
  @RemoveIcon
  @deleteAllMaskedImages
  Scenario: Verify export and print report functionality with limited right click menu options
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I right click on viewport 3
    And I select "Toggle image selection (for export/print)" option from rich viewer right click menu
    Then export and print icon "should" be present on the image
    When I open the "Reports" menu
    Then "Print Selected Images" option "should" be present in the reports menu
    And select the "Print Selected Images" option
    And I click on "Preview" button on report modification popup
    Then the Report Editor window should load
    And image should be toggled to the preview
    When close report viewer
    And I right click on viewport 3
    And I select "Export" option from rich viewer right click menu
    And select the "Export to JPEG (still picture)" sub menu option
    Then Anonymization window should be displayed
    When I mask area of patient details
    And I click on browse button
    And select the export location
    Then masked images should be downloaded
