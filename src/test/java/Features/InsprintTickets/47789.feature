@47789

Feature: 47789

  @DontSaveReport
    @47789-1
  Scenario Outline: Verify details entered for a selected fetus are not duplicated in other fetuses for Echo & Stress fetal reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I increase the number of fetus by 2
    And I "expand" the "Yellow Fetus(A)" section under the left panel
    And click on "Measurements" subsection
    And select "Vena Cava" tab
    And I enter below details in the "<Report Template>" fields
      | field     | type | value |
      | IVC A Vel | text | 10    |
      | IVC A Dur | text | 10    |
    And I "collapse" the "Yellow Fetus(A)" section under the left panel
    And I "expand" the "Yellow Fetus(B)" section under the left panel
    And click on "Measurements" subsection
    And select "Vena Cava" tab
    Then details entered in Fetus A should not be present in "Fetus(B)"
    And I "collapse" the "Yellow Fetus(B)" section under the left panel
    And I "expand" the "Yellow Fetus(C)" section under the left panel
    And click on "Measurements" subsection
    And select "Vena Cava" tab
    Then details entered in Fetus A should not be present in "Fetus(C)"
    Examples:
      | Report Category | Report Name                             | Report Template            |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) |

  @DontSaveReport
    @47789-2
  Scenario Outline: Verify whether details entered for a selected fetus are not duplicated in other fetuses for OB-GYN fetal reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I increase the number of fetus by 2
    And I "expand" the "Yellow Fetus (A)" section under the left panel
    And click on "HR and Rhythm" subsection
    And select "HR and Rhythm" tab
    And I enter below details in the "<Report Template>" fields
      | field | type | value       |
      | FHR   | text | 250         |
      | Other | text | TEST REPORT |
    And I "collapse" the "Yellow Fetus (A)" section under the left panel
    And I "expand" the "Yellow Fetus (B)" section under the left panel
    And click on "HR and Rhythm" subsection
    And select "HR and Rhythm" tab
    Then details entered in Fetus A should not be present in "Fetus (B)"
    And I "collapse" the "Yellow Fetus (B)" section under the left panel
    And I "expand" the "Yellow Fetus (C)" section under the left panel
    And click on "HR and Rhythm" subsection
    And select "HR and Rhythm" tab
    Then details entered in Fetus A should not be present in "Fetus (C)"
    Examples:
      | Report Category | Report Name                      | Report Template                  |
      | OB-GYN          | OB Fetal Echo Preliminary Report | OB Fetal Echo Preliminary Report |
      | OB-GYN          | OB Fetal Echo Final Report       | OB Fetal Echo Preliminary Report |
      | OB-GYN          | OB Ultrasound Final Report       | OB Ultrasound Final Report       |
      | OB-GYN          | OB Ultrasound Preliminary Report | OB Ultrasound Final Report       |

  @DontSaveReport
    @47789-3
  Scenario Outline: Verify whether procedure report details are not duplicated in other fetuses for Echo & Stress fetal reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ESAOTE OMEGA" and last name as "34 WEEK FETAL"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I increase the number of fetus by 1
    And I "expand" the "Highlighted Fetus(A)" section under the left panel
    And click on "Yellow HR and Rhythm" subsection
    And select the highlighted "HR and Rhythm" tab
    Then procedure report data "should" be present in "Fetus(A)" for the selected tab
    When I click on "Yellow Measurements" subsection
    And select the highlighted "Pulmonic Valve" tab
    Then procedure report data "should" be present in "Fetus(A)" for the selected tab
    When select the highlighted "Pulmonary Artery" tab
    Then procedure report data "should" be present in "Fetus(A)" for the selected tab
    When I "collapse" the "Highlighted Fetus(A)" section under the left panel
    And I "expand" the "Fetus(B)" section under the left panel
    And click on "HR and Rhythm" subsection
    And select "HR and Rhythm" tab
    Then procedure report data "should not" be present in "Fetus(B)" for the selected tab
    When I click on "Measurements" subsection
    And select "Pulmonic Valve" tab
    Then procedure report data "should not" be present in "Fetus(A)" for the selected tab
    When select "Pulmonary Artery" tab
    Then procedure report data "should not" be present in "Fetus(A)" for the selected tab
    Examples:
      | Report Category | Report Name                             |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       |