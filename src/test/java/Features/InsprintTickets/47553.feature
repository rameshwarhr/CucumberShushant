@47533

Feature: 47533

  @RevertFinalReportAndDeleteSeriesForLastStudy
    @47533-1
  Scenario Outline: Verify whether measurement trends for the respective field are displayed in Right ventricle tab for Pediatric TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 20    |
      | RVEDD (PSAX)   | text | 20    |
      | RVAWd (2D)     | text | 20    |
      | RVAWs (2D)     | text | 20    |
      | RV Basal (D1)  | text | 20    |
      | RV Mid (D2)    | text | 20    |
      | RV Long (D3)   | text | 20    |
      | RV Sys Area    | text | 20    |
      | RV Dias Area   | text | 20    |
      | RV Length Dias | text | 20    |
      | RV Length Sys  | text | 20    |
      | RV Width Dias  | text | 20    |
      | RV Width Sys   | text | 20    |
      | RV Wall Dias   | text | 20    |
      | RV Wall Syst   | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 25    |
      | RVEDD (PSAX)   | text | 25    |
      | RVAWd (2D)     | text | 25    |
      | RVAWs (2D)     | text | 25    |
      | RV Basal (D1)  | text | 25    |
      | RV Mid (D2)    | text | 25    |
      | RV Long (D3)   | text | 25    |
      | RV Sys Area    | text | 25    |
      | RV Dias Area   | text | 25    |
      | RV Length Dias | text | 25    |
      | RV Length Sys  | text | 25    |
      | RV Width Dias  | text | 25    |
      | RV Width Sys   | text | 25    |
      | RV Wall Dias   | text | 25    |
      | RV Wall Syst   | text | 25    |
    Then measurement trends should be displayed for above fields in report preview on clicking the respective trend icon
    Examples:
      | Report Name                                 |
      | Pediatric TTE Preliminary Report (Standard) |
      | Pediatric TTE Final Report (Standard)       |

  @RevertFinalReportAndDeleteSeriesForLastStudy
    @47533-2
  Scenario Outline: Verify whether measurement trends for the respective field are displayed in Right ventricle tab for Fetal TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 20    |
      | RVEDD (SAX)    | text | 20    |
      | RVAWd (2D)     | text | 20    |
      | RVAWs (2D)     | text | 20    |
      | RV Basal (D1)  | text | 20    |
      | RV Mid (D2)    | text | 20    |
      | RV Long (D3)   | text | 20    |
      | RV Sys Area    | text | 20    |
      | RV Dias Area   | text | 20    |
      | RV Length Dias | text | 20    |
      | RV Length Sys  | text | 20    |
      | RV Width Dias  | text | 20    |
      | RV Width Sys   | text | 20    |
      | RV Wall Dias   | text | 20    |
      | RV Wall Syst   | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 25    |
      | RVEDD (SAX)    | text | 25    |
      | RVAWd (2D)     | text | 25    |
      | RVAWs (2D)     | text | 25    |
      | RV Basal (D1)  | text | 25    |
      | RV Mid (D2)    | text | 25    |
      | RV Long (D3)   | text | 25    |
      | RV Sys Area    | text | 25    |
      | RV Dias Area   | text | 25    |
      | RV Length Dias | text | 25    |
      | RV Length Sys  | text | 25    |
      | RV Width Dias  | text | 25    |
      | RV Width Sys   | text | 25    |
      | RV Wall Dias   | text | 25    |
      | RV Wall Syst   | text | 25    |
    Then measurement trends should be displayed for above fields in report preview on clicking the respective trend icon
    Examples:
      | Report Name                             |
      | Fetal TTE Preliminary Report (Standard) |
      | Fetal TTE Final Report (Standard)       |

  @RevertFinalReportAndDeleteSeriesForLastStudy
  @47533-3
  Scenario: Verify whether measurement trends are linked between preliminary and final reports for Pediatric TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 20    |
      | RVEDD (PSAX)   | text | 20    |
      | RVAWd (2D)     | text | 20    |
      | RVAWs (2D)     | text | 20    |
      | RV Basal (D1)  | text | 20    |
      | RV Mid (D2)    | text | 20    |
      | RV Long (D3)   | text | 20    |
      | RV Sys Area    | text | 20    |
      | RV Dias Area   | text | 20    |
      | RV Length Dias | text | 20    |
      | RV Length Sys  | text | 20    |
      | RV Width Dias  | text | 20    |
      | RV Width Sys   | text | 20    |
      | RV Wall Dias   | text | 20    |
      | RV Wall Syst   | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Final Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 25    |
      | RVEDD (PSAX)   | text | 25    |
      | RVAWd (2D)     | text | 25    |
      | RVAWs (2D)     | text | 25    |
      | RV Basal (D1)  | text | 25    |
      | RV Mid (D2)    | text | 25    |
      | RV Long (D3)   | text | 25    |
      | RV Sys Area    | text | 25    |
      | RV Dias Area   | text | 25    |
      | RV Length Dias | text | 25    |
      | RV Length Sys  | text | 25    |
      | RV Width Dias  | text | 25    |
      | RV Width Sys   | text | 25    |
      | RV Wall Dias   | text | 25    |
      | RV Wall Syst   | text | 25    |
    Then measurement trends should be displayed for above fields in report preview on clicking the respective trend icon

  @RevertFinalReportAndDeleteSeriesForLastStudy
  @47533-4
  Scenario: Verify whether measurement trends are linked between preliminary and final reports for Fetal TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 20    |
      | RVEDD (SAX)    | text | 20    |
      | RVAWd (2D)     | text | 20    |
      | RVAWs (2D)     | text | 20    |
      | RV Basal (D1)  | text | 20    |
      | RV Mid (D2)    | text | 20    |
      | RV Long (D3)   | text | 20    |
      | RV Sys Area    | text | 20    |
      | RV Dias Area   | text | 20    |
      | RV Length Dias | text | 20    |
      | RV Length Sys  | text | 20    |
      | RV Width Dias  | text | 20    |
      | RV Width Sys   | text | 20    |
      | RV Wall Dias   | text | 20    |
      | RV Wall Syst   | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Final Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field          | type | value |
      | RVIDs (2D)     | text | 25    |
      | RVEDD (SAX)    | text | 25    |
      | RVAWd (2D)     | text | 25    |
      | RVAWs (2D)     | text | 25    |
      | RV Basal (D1)  | text | 25    |
      | RV Mid (D2)    | text | 25    |
      | RV Long (D3)   | text | 25    |
      | RV Sys Area    | text | 25    |
      | RV Dias Area   | text | 25    |
      | RV Length Dias | text | 25    |
      | RV Length Sys  | text | 25    |
      | RV Width Dias  | text | 25    |
      | RV Width Sys   | text | 25    |
      | RV Wall Dias   | text | 25    |
      | RV Wall Syst   | text | 25    |
    Then measurement trends should be displayed for above fields in report preview on clicking the respective trend icon

  @RevertFinalReportAndDeleteSeriesForLastStudy
    @47533-5
  Scenario Outline: Verify whether measurement trends for a signed report are displayed while amending Pediatric TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "<Report Template>" fields
      | field      | type | value |
      | RVIDs (2D) | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "<Report Template>" fields
      | field      | type | value |
      | RVIDs (2D) | text | 25    |
    And select the trend for the "RVIDs (2D)" field
    And I sign the report
    And I close report viewer
    And I right click on "<Report to be amended>" from reports list
    And click on "AMEND" option for report
    Then the Report Editor window should load
    When I click on "FitPage" icon
    Then measurement trend should be displayed
    Examples:
      | Report Name                                 | Report to be amended              | Report Template                  |
      | Pediatric TTE Preliminary Report (Standard) | Pediatric Echo Preliminary Report | Pediatric TTE Preliminary Report |
      | Pediatric TTE Final Report (Standard)       | Pediatric Echo Final Report       | Pediatric TTE Preliminary Report |

  @RevertFinalReportAndDeleteSeriesForLastStudy
    @47533-6
  Scenario Outline: Verify whether measurement trends for a signed report are displayed while amending Fetal TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "<Report Template>" fields
      | field      | type | value |
      | RVIDs (2D) | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "<Report Template>" fields
      | field      | type | value |
      | RVIDs (2D) | text | 25    |
    And select the trend for the "RVIDs (2D)" field
    And I sign the report
    And I close report viewer
    And I right click on "<Report to be amended>" from reports list
    And click on "AMEND" option for report
    Then the Report Editor window should load
    When I click on "FitPage" icon
    Then measurement trend should be displayed
    Examples:
      | Report Name                             | Report to be amended         | Report Template            |
      | Fetal TTE Preliminary Report (Standard) | Fetal TTE Preliminary Report | Fetal TTE Report(Standard) |
      | Fetal TTE Final Report (Standard)       | Fetal TTE Final Report       | Fetal TTE Report(Standard) |

  @RevertFinalReportAndDeleteSeriesForLastStudy
    @47533-7
  Scenario Outline: Verify whether measurement trend persists when navigating through various tabs for Pediatric TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field      | type | value |
      | RVIDs (2D) | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field      | type | value |
      | RVIDs (2D) | text | 25    |
    And select the trend for the "RVIDs (2D)" field
    Then measurement trend should be displayed
    When select "Left ventricle" tab
    And switch back to the "Right ventricle" tab
    Then measurement trend should be displayed
    Examples:
      | Report Name                                 |
      | Pediatric TTE Preliminary Report (Standard) |
      | Pediatric TTE Final Report (Standard)       |

  @RevertFinalReportAndDeleteSeriesForLastStudy
    @47533-8
  Scenario Outline: Verify whether measurement trend persists when navigating through various tabs for Fetal TTE reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field      | type | value |
      | RVIDs (2D) | text | 20    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Right Ventricle" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field      | type | value |
      | RVIDs (2D) | text | 25    |
    And select the trend for the "RVIDs (2D)" field
    Then measurement trend should be displayed
    When select "Left ventricle" tab
    And switch back to the "Right ventricle" tab
    Then measurement trend should be displayed
    Examples:
      | Report Name                             |
      | Fetal TTE Preliminary Report (Standard) |
      | Fetal TTE Final Report (Standard)       |