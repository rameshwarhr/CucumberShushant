@24074

Feature: 24074

  @DeselectAllAndDontSaveReport
  @24074-1
  Scenario: Verify umbilical artery S/D fields for Fetal and Ultrasound reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    Then below fields should be present in "OB Ultrasound Final Report" report
      | region                             | type     | field               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
      | Fetal Growth Curve with history    | checkbox | Umbilical Artery SD |
    When I click on "Reports" menu on report viewer
    And click on "OB-GYN" option on report viewer
    And select the "OB Ultrasound Preliminary Report" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    Then below fields should be present in "OB Ultrasound Final Report" report
      | region                             | type     | field               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
      | Fetal Growth Curve with history    | checkbox | Umbilical Artery SD |
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" menu item on report viewer
    And select the "Fetal TTE Final Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    Then below fields should be present in "FetalTTEReport(Standard)" report
      | region                             | type     | field               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
      | Fetal Growth Curve with history    | checkbox | Umbilical Artery SD |
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" menu item on report viewer
    And select the "Fetal TTE Preliminary Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    Then below fields should be present in "FetalTTEReport(Standard)" report
      | region                             | type     | field               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
      | Fetal Growth Curve with history    | checkbox | Umbilical Artery SD |

  @DeselectAllAndDontSaveReport
  @24074-2
  Scenario: Verify S/D ratio field for Fetal and Ultrasound reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I click on "Fetus(A)" under left panel
    And select "Antenatal Testing" tab
    And press the tab key
    Then below fields should be present in "OB Ultrasound Final Report" report
      | region                  | type    | field     |
      | Doppler location_Region | Textbox | S D Ratio |
    When I click on "Reports" menu on report viewer
    And click on "OB-GYN" option on report viewer
    And select the "OB Ultrasound Preliminary Report" sub option
    Then the Report Editor window should load
    When I click on "Fetus(A)" under left panel
    And select "Antenatal Testing" tab
    And press the tab key
    Then below fields should be present in "OB Ultrasound Final Report" report
      | region                  | type    | field     |
      | Doppler location_Region | Textbox | S D Ratio |
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" menu item on report viewer
    And select the "Fetal TTE Final Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Fetus(A)" under left panel
    And select "Antenatal Testing" tab
    And press the tab key
    Then below fields should be present in "FetalTTEReport(Standard)" report
      | region                  | type    | field     |
      | Doppler location_Region | Textbox | S D Ratio |
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" menu item on report viewer
    And select the "Fetal TTE Preliminary Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Fetus(A)" under left panel
    And select "Antenatal Testing" tab
    And press the tab key
    Then below fields should be present in "FetalTTEReport(Standard)" report
      | region                  | type    | field     |
      | Doppler location_Region | Textbox | S D Ratio |

  @DeselectAllAndDontSaveReport
  @24074-3
  Scenario: Verify umbilical artery S/D ratio charts along with % for Fetal and Ultrasound reports for a patient with single study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Reports" menu on report viewer
    And click on "OB-GYN" option on report viewer
    And select the "OB Ultrasound Final Report" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" option on report viewer
    And select the "Fetal TTE Preliminary Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" option on report viewer
    And select the "Fetal TTE Final Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview

  @DeselectAllAndDontSaveReport
    @24074-4
  Scenario Outline: Verify umbilical artery S/D ratio charts along with % for Fetal and Ultrasound reports for a patient with multiple studies
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open <Study Number> study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Reports" menu on report viewer
    And click on "OB-GYN" menu item on report viewer
    And select the "OB Ultrasound Final Report" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" menu item on report viewer
    And select the "Fetal TTE Preliminary Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Reports" menu on report viewer
    And click on "Echo & Stress" menu item on report viewer
    And select the "Fetal TTE Final Report (Standard)" sub option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                           | type     | value               |
      | Fetal Growth Curve with history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    When I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    Then umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    Examples:
      | Study Number |
      | 3            |
      | 2            |

  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  @24074-5
  Scenario: To verify that final report contains latest signed prelim charts for 0B ultrasound report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve with history    | checkbox | Umbilical Artery SD |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    And I sign the report
    And I close report viewer
    And I click on "Reports" menu on report viewer
    And click on "OB-GYN" menu item on report viewer
    And select the "OB Ultrasound Final Report" sub option
    Then the Report Editor window should load
    And umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Next" page icon
    Then umbilical artery "SD ratio chart" should be displayed in report preview

  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  @24074-6
  Scenario: To verify that final report contains latest signed prelim charts for fetal report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I enter below details in the "Fetal TTE Report(Standard)" fields
      | field                              | type     | value               |
      | Fetal Growth Curve with history    | checkbox | Umbilical Artery SD |
      | Fetal Growth Curve without history | checkbox | Umbilical Artery SD |
    And I sign the report
    And I close report viewer
    And I click on "Reports" menu on report viewer
    And click on "Echo & Stress" menu item on report viewer
    And select the "Fetal TTE Final Report (Standard)" sub option
    Then the Report Editor window should load
    And umbilical artery "SD ratio chart" should be displayed in report preview
    And umbilical artery "SD ratio percentile" should be displayed in report preview
    When I click on "Next" page icon
    Then umbilical artery "SD ratio chart" should be displayed in report preview

  @RevertFinalReportAndDeleteSeriesForAllSignedReports
    @24074-7
  Scenario Outline: To verify fetal with history plots for umbilical artery S/D chart for a patient with history
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 24    |
      | UA EDV  | text | 4     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/08/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 1 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 40    |
      | UA EDV  | text | 5     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 07/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) |

  @RevertFinalReportAndDeleteSeriesForAllSignedReports
    @24074-8
  Scenario Outline: To verify fetal without history plots for umbilical artery S/D chart for a patient with history
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 24    |
      | UA EDV  | text | 4     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/08/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 1 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 40    |
      | UA EDV  | text | 5     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 07/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) |

  @DontSaveReport
    @24074-9
  Scenario Outline: To verify fetal without history plots for umbilical artery S/D chart for a patient with no history
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 03/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    And I click on "FitPage" icon
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) |

  @DontSaveReport
    @24074-10
  Scenario Outline: To verify fetal with history plots for umbilical artery S/D chart for a patient with no history
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 03/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    And I click on "FitPage" icon
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) |

  @RevertFinalReportAndDeleteSeriesForAllSignedReports
    @24074-11
  Scenario Outline: To verify fetal with history plots for umbilical artery S/D chart for a patient with history are displayed while amending
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 24    |
      | UA EDV  | text | 4     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/08/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 1 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 40    |
      | UA EDV  | text | 5     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 07/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I right click on "<Report to be amended>" from reports list
    And click on "AMEND" option for report
    Then the Report Editor window should load
    When I click on "FitPage" icon
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            | Report to be amended         |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) | Fetal TTE Preliminary Report |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) | Fetal TTE Final Report       |

  @RevertFinalReportAndDeleteSeriesForAllSignedReports
    @24074-12
  Scenario Outline: To verify fetal without history plots for umbilical artery S/D chart for a patient with history are displayed while amending
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 24    |
      | UA EDV  | text | 4     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 10/08/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 1 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 40    |
      | UA EDV  | text | 5     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 07/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I right click on "<Report to be amended>" from reports list
    And click on "AMEND" option for report
    Then the Report Editor window should load
    When I click on "FitPage" icon
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            | Report to be amended         |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) | Fetal TTE Preliminary Report |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) | Fetal TTE Final Report       |

  @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
    @24074-13
  Scenario Outline: To verify fetal without history plots for umbilical artery S/D chart for a patient with no history are displayed while amending
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 03/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I right click on "<Report to be amended>" from reports list
    And click on "AMEND" option for report
    Then the Report Editor window should load
    When I click on "FitPage" icon
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            | Report to be amended         |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) | Fetal TTE Preliminary Report |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) | Fetal TTE Final Report       |

  @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
    @24074-14
  Scenario Outline: To verify fetal with history plots for umbilical artery S/D chart for a patient with no history are displayed while amending
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "FitPage" icon
    And I "expand" the "Fetus(A)" section under the left panel
    And click on "Extracardiac Structures" subsection
    And select "Cord" tab
    And I enter below details in the "<Report Template>" fields
      | field   | type | value |
      | UA Vmax | text | 10    |
      | UA EDV  | text | 2     |
    And I "collapse" the "Fetus(A)" section under the left panel
    And I set below values in "Patient information" tab under "Patient information" section
      | field    | value  |
      | EDD Type | By LMP |
    And I enter below details in the "<Report Template>" fields
      | field     | type | value      |
      | EDD (LMP) | text | 03/29/2020 |
    And I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field               | type     |
      | Umbilical Artery SD | checkbox |
    Then plots for selected charts should be present in report preview
    When I sign the report
    And I close report viewer
    And I right click on "<Report to be amended>" from reports list
    And click on "AMEND" option for report
    Then the Report Editor window should load
    When I click on "FitPage" icon
    Then plots for selected charts should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            | Report to be amended         |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) | Fetal TTE Preliminary Report |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) | Fetal TTE Final Report       |