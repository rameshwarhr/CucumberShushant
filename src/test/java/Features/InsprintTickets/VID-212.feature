@VID-212
Feature: VID-212

  @ExitReportAndViewer
    @212_1
    @212_2
    @212_3
    @212_6
    @212_7
    @212_8
  Scenario Outline: To verify ghost fields do not appear when we increase and decrease the fetus count on patient information page
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "RICKY" and last name as "JACKSON"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "<report type>" option
    Then the Report Editor window should load
    When I enter below details in the "<report type>" fields
      | field             | type | value |
      | Number of fetuses | text | 2     |
    Then fields should not be distorted in below subtabs
      | subtab              | subtab fields              |
      | Patient information | Patient Information Fields |
    When I select below checkbox on "Patient information" subtab
      | Conception date |
    And I enter below details in the "<report type>" fields
      | field                    | type | value |
      | Systolic blood pressure  | text | 120   |
      | Diastolic blood pressure | text | 80    |
    Then "Patient Info observations" should be reflected on the dynamic preview
    When I enter below details in the "<report type>" fields
      | field                         | type | value |
      | Highlighted Number of fetuses | text | 5     |
    Then fields should not be distorted in below subtabs
      | subtab              | subtab fields              |
      | Patient information | Patient Information Fields |
    When I enter below details in the "<report type>" fields
      | field                         | type | value |
      | Highlighted Number of fetuses | text | 4     |
    Then fields should not be distorted in below subtabs
      | subtab              | subtab fields              |
      | Patient information | Patient Information Fields |
    When I select below checkbox on "Patient Information" subtab
      | EDD Outside    |
      | EDD First sono |
      | EDD AUA        |
    And I enter below details in the "<report type>" fields
      | field                         | type | value |
      | Highlighted Number of fetuses | text | 2     |
    Then fields should not be distorted in below subtabs
      | subtab              | subtab fields       |
      | Patient information | Patient Info Fields |
    When I enter below details in the "<report type>" fields
      | field             | type | value |
      | Pre pregnancy BMI | text | 18    |
      | Temperature       | text | 18    |
    Then "Patient information observations" should be reflected on the dynamic preview
    When I click on "Highlighted Fetus(A)" under left panel
    And I click on "HR and Rhythm" under left panel
    Then fields should not be distorted in below subtabs
      | subtab        | subtab fields        |
      | HR and Rhythm | HR and Rhythm Fields |
    Examples:
      | report type                      |
      | OB Fetal Echo Preliminary Report |
      | OB Ultrasound Final Report       |

  @212_4
  Scenario: To verify ghost fields do not appear on subtabs
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "RICKY" and last name as "JACKSON"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Fetal Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "OB Fetal Echo Preliminary Report" fields
      | field             | type | value |
      | Number of fetuses | text | 5     |
    And respective fetuses should be added under left panel
    And I click on "Fetus(A)" under left panel
    Then fields should not be distorted in below subtabs
      | subtab            | subtab fields            |
      | Cardiac Anatomy   | Cardiac Anatomy Fields   |
      | Echo Measurements | Echo Measurements Fields |
      | Antenatal Testing | Antenatal Testing Fields |

  @212_5
  Scenario: To verify ghost fields do not appear on subtabs
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "RICKY" and last name as "JACKSON"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Ultrasound Final Report" option
    Then the Report Editor window should load
    When I enter below details in the "OB Ultrasound Final Report" fields
      | field                         | type | value |
      | Highlighted Number of fetuses | text | 5     |
    And respective fetuses should be added under left panel
    And I click on "Fetus(A)" under left panel
    Then fields should not be distorted in below subtabs
      | subtab              | subtab fields              |
      | Biophysical Profile | Biophysical Profile Fields |
      | Antenatal Testing   | Antenatal Testing Fields   |
      | Lower Extremities   | Lower Extremities Fields   |
      | Upper Extremities   | Upper Extremities Fields   |