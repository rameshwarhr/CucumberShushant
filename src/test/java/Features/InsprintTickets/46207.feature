@46207
Feature: 46207

  @46207_1
  @46207_2
  @46207_3
  @45521_3
  @DontSaveReport
  Scenario: Verify that procedure measurement data has a machine icon against it
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ERIC" and last name as "DOVE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Measurements" in report editor left panel
    And select "Ao Valve" tab
    Then "AV Mean Vel" field should have a machine icon
    And "AV Mean Grad" field should have a machine icon
    When I click on mean calculation icon of "AV Mean Vel" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the machine icon value should be checked
    And I click on "OK" button to close the mean calculation dialog
    When I click on mean calculation icon of "AV Mean Grad" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the machine icon value should be checked
    And I click on "OK" button to close the mean calculation dialog
    When I enter below details in the "Echo Preliminary Report" fields
      | field       | type | value |
      | AV Mean Vel | text | 80    |
    And I move the mouse cursor out of the measurement field
    Then "AV Mean Vel(Updated Value)" field should have a vidistar icon

  @46207_4
  @DontSaveReport
  Scenario: Verify that procedure measurement data icon can be changed
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ERIC" and last name as "DOVE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Measurements" in report editor left panel
    And select "Ao Valve" tab
    And I click on mean calculation icon of "AV Mean Vel" measurement field
    Then Mean Calculation dialog should open
    When I click on the vidistar icon checkbox
    And I click on "OK" button to close the mean calculation dialog
    Then "AV Mean Vel(With Vidistar Icon)" field should have a vidistar icon

  @46207_5
  @RevertSingleMonitorView
  Scenario: Verify that after recalculating the procedure data value of meanPG and vMean, the icon gets changed from machine to vidistar
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ERIC" and last name as "DOVE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    And close the dicom viewer
    And I open a study for patient with first name as "ERIC" and last name as "DOVE"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 4
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then report editor should open within the same window
    When I click on "Measurements" in report editor left panel
    And select "Ao Valve" tab
    Then "AV Mean Vel" field should have a machine icon
    And "AV Mean Grad" field should have a machine icon
    When click on the bullseye of "AV VTI" measurement label
    And I measure the value for "AV VTI" using the bullseye on the image
    Then "AV Mean Vel(Recalculated Value)" field should change to vidistar icon
    And "AV Mean Grad(Recalculated Value)" field should change to vidistar icon
    When I click on mean calculation icon of "AV Mean Vel(Recalculated Value)" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog
    When I click on mean calculation icon of "AV Mean Grad(Recalculated Value)" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog

  @46207_6
  @RevertSingleMonitorView
  Scenario: Verify that calculating the value of meanPG and vMean, vidistar icon is visible
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MARK" and last name as "YAKSHOE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    And close the dicom viewer
    And I open a study for patient with first name as "MARK" and last name as "YAKSHOE"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 16
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Measurements" in report editor left panel
    And select "Shunts" tab
    And click on the bullseye of "PDA Sys VTI" measurement label
    And I measure the value for "PDA Sys VTI" using the bullseye on the image
    Then "PDA Sys Vmean" field should change to vidistar icon
    And "PDA Sys MeanPG" field should change to vidistar icon
    When I click on mean calculation icon of "PDA Sys Vmean" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog
    When I click on mean calculation icon of "PDA Sys MeanPG" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog