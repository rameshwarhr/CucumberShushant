@48621
Feature: 48621

  @DontSaveReport
    @48621-1
  Scenario Outline: Verify whether charts without history are populated to the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve without history" region
      | field                      | type     |
      | EFW                        | checkbox |
      | BPD                        | checkbox |
      | HC                         | checkbox |
      | AC                         | checkbox |
      | FL                         | checkbox |
      | HUM                        | checkbox |
      | Tibia                      | checkbox |
      | Fibula                     | checkbox |
      | Radius                     | checkbox |
      | Ulna                       | checkbox |
      | TCD                        | checkbox |
      | Cist Magna                 | checkbox |
      | Umbilical Artery PSV       | checkbox |
      | Umbilical Artery EDV       | checkbox |
      | Umbilical Artery SD        | checkbox |
      | Middle Celebral Artery PSV | checkbox |
    And I click on "Fitpage" icon
    Then selected charts "without history" should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) |
      | OB-GYN          | OB Ultrasound Preliminary Report        | OB Ultrasound Final Report |
      | OB-GYN          | OB Ultrasound Final Report              | OB Ultrasound Final Report |

  @DontSaveReport
    @48621-2
  Scenario Outline: Verify whether charts with history are populated to the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "<Report Category>" option
    And select the "<Report Name>" option
    Then the Report Editor window should load
    When I click on "Charts" under left panel
    And select "Charts" tab
    And I maximize report editor
    And I select the below charts in the "<Report Template>" under the "Fetal Growth Curve with history" region
      | field                      | type     |
      | EFW                        | checkbox |
      | BPD                        | checkbox |
      | HC                         | checkbox |
      | AC                         | checkbox |
      | FL                         | checkbox |
      | HUM                        | checkbox |
      | Tibia                      | checkbox |
      | Fibula                     | checkbox |
      | Radius                     | checkbox |
      | Ulna                       | checkbox |
      | TCD                        | checkbox |
      | Cist Magna                 | checkbox |
      | Umbilical Artery PSV       | checkbox |
      | Umbilical Artery EDV       | checkbox |
      | Umbilical Artery SD        | checkbox |
      | Middle Celebral Artery PSV | checkbox |
    And I click on "Fitpage" icon
    Then selected charts "with history" should be present in report preview
    Examples:
      | Report Category | Report Name                             | Report Template            |
      | Echo & Stress   | Fetal TTE Preliminary Report (Standard) | Fetal TTE Report(Standard) |
      | Echo & Stress   | Fetal TTE Final Report (Standard)       | Fetal TTE Report(Standard) |
      | OB-GYN          | OB Ultrasound Preliminary Report        | OB Ultrasound Final Report |
      | OB-GYN          | OB Ultrasound Final Report              | OB Ultrasound Final Report |