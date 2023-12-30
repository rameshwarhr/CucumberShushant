@45521
Feature: 45521

  @45521_2
  @DeleteSeriesForLastStudy
  Scenario: Verify measurement source should persist on the filed along with measurement field tools when moused hover
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open 3 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Measurements" under left panel
    And select "Right ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field      | type | value |
      | RVIDd (2D) | text | 25    |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    And I open 2 study for patient with first name as "MAKAYLA" and last name as "PETTIBONE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report" option
    Then the Report Editor window should load
    And I click on "Measurements" under left panel
    And select "Right Ventricle" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field      | type | value |
      | RVIDd (2D) | text | 25    |
    Then vidistar icon should be displayed for "RVIDd (2D)" field in the report editor
    When I hover on "RVIDd (2D)(Field with value)" measurement
    Then measurement tools should be displayed for the field
    When I move the mouse cursor out of the measurement field
    Then vidistar icon should be displayed for "RVIDd (2D)" field in the report editor

  @45521_4
  @45521_8
  @45221_10
  @DontSaveReport
  Scenario: Verify measurement field enables vidistar symbol when value is originated from manual entry, auto-calculation and offline measurement
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ERIC" and last name as "DOVE"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 45
    And open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Measurements" in report editor left panel
    And I click on "Coronary Sinus" under left panel
    When I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field               | type | value |
      | Coronary Sinus Vmax | text | 56    |
    Then vidistar icon should be displayed for "Coronary Sinus Vmax" field in the report editor
    And "Coronary Sinus MaxPG" auto-calculated field should have a vidistar icon
    When hover on "Coronary Sinus MaxPG" measurement
    Then phrase and formula should be displayed for "Coronary Sinus MaxPG" greyed out measurement field
    When I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field                | type | value |
      | Coronary Sinus MaxPG | text | 56    |
    Then the value for "Coronary Sinus MaxPG" greyed out field should not be updated
    When select "Yellow LVOT" tab
    And click on the bullseye of "LVOT Diam(M-Mode)" measurement label
    And I switch to the viewer window
    And measure the value for "LVOT Diam(M-Mode)" using the bullseye on the image
    And I switch to the report window
    Then "LVOT Diam(M-Mode)" field should have a vidistar icon

  @45221_3
  @45221_6
  @45221_9
  Scenario: Verify identifiers are updated dynamically when a value is populated or selected from measurement table
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ERIC" and last name as "DOVE"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 32
    And open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Measurements" in report editor left panel
    And select "Yellow Right Ventricle" tab
    Then "RVIDd(M-Mode)(Machine Value)" field should have a machine icon
    And click on the bullseye of "RVIDd(M-Mode)(Machine Value)" measurement label
    And I switch to the viewer window
    And measure the value for "RVIDd(M-Mode)" using the bullseye on the image
    And I switch to the report window
    Then "RVIDd(M-Mode)(Bullseye Calculated)" field should change to vidistar icon
    When I click on mean calculation icon of "RVIDd(M-Mode)(Bullseye Calculated)" measurement field
    Then Mean Calculation dialog should open
    When I click on the machine icon checkbox
    And I click on "OK" button to close the mean calculation dialog
    Then "RVIDd(M-Mode)" field should have a machine icon

