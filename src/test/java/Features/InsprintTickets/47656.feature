@47656
Feature: 47656

  @47656_1
  @47656_2
  @DontSaveReport
  Scenario: Verify that greyed out fields have f(x) sign and mean calculation icon is not present on hovering
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MARC" and last name as "BLEVINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Measurements" in report editor left panel
    And select "Yellow Left Ventricle" tab
    Then "M-Mode" measurement fields should not display mean sign for greyed out fields
    And "LVIDd Index (2D)" measurement field should be greyed out and should have a fx sign
    When I hover on "LVIDd Index (2D)" measurement
    Then mean calculation icon should not be visible

  @47656_3
  @DontSaveReport
  Scenario: Verify that vidistar icon is displayed after calculating the value manually for the greyed out fields
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MARC" and last name as "BLEVINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Measurements" in report editor left panel
    And select "Yellow Aorta" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field     | type | value |
      | AoV Ann d | text | 40    |
    And select "Yellow Pulmonic Valve" tab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field    | type | value |
      | PV Ann d | text | 20    |
    Then "AoV Ann d&PV Ann d" greyed out measurement field should display vidistar icon

  @47656_4
  @RevertSingleMonitorView
  Scenario: Verify that vidistar icon is displayed after calculating the value by bullseye for the greyed out fields
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MARC" and last name as "BLEVINS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    And close the dicom viewer
    And I open a study for patient with first name as "MARC" and last name as "BLEVINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 6
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Measurements" in report editor left panel
    And select "Yellow Aorta" tab
    And click on the bullseye of "AoV Ann d" measurement label
    And I measure the value for "AoV Ann d" using the bullseye on the image
    And select "Yellow Pulmonic Valve" tab
    And click on the bullseye of "PV Ann d" measurement label
    And I measure the value for "PV Ann d" using the bullseye on the image
    Then "AoV Ann d&PV Ann d(Bullseye Value)" greyed out measurement field should display vidistar icon