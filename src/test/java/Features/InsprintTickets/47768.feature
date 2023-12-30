@47768
Feature: 47768

  @47768_1
  @RevertSingleMonitorView
  Scenario: Verify that after clicking on the re-calculated value of vMean, maxPG and peak fields, it does not change to its original value
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
    And click on the bullseye of "AV VTI" measurement label
    And I measure the value for "AV VTI" using the bullseye on the image
    And I click on "AV PV(Recalculated value)" measurement field
    Then AV PV, AV Mean Vel, AV Peak Grad, AV Mean Grad field value should not change to its original value
    When I click on "AV Mean Vel(Recalculated Value)" measurement field
    Then AV PV, AV Mean Vel, AV Peak Grad, AV Mean Grad field value should not change to its original value
    When I click on "AV Peak Grad(Recalculated Value)" measurement field
    Then AV PV, AV Mean Vel, AV Peak Grad, AV Mean Grad field value should not change to its original value
    When I click on "AV Mean Grad(Recalculated Value)" measurement field
    Then AV PV, AV Mean Vel, AV Peak Grad, AV Mean Grad field value should not change to its original value

  @47768_2
  @RevertSingleMonitorView
  Scenario: Verify that calculating the value of meanPG, vMean and peak vidistar icon is visible
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
    And click on the bullseye of "AV VTI" measurement label
    And I measure the value for "AV VTI" using the bullseye on the image
    Then AV PV, AV Mean Vel, AV Peak Grad, AV Mean Grad field value should change to vidistar icon
    When I click on mean calculation icon of "AV PV(Recalculated value)" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog
    When I click on mean calculation icon of "AV Mean Vel(Recalculated Value)" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog
    When I click on mean calculation icon of "AV Peak Grad(Recalculated Value)" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog
    When I click on mean calculation icon of "AV Mean Grad(Recalculated Value)" measurement field
    Then Mean Calculation dialog should open
    And the checkbox of the vidistar icon value should be checked
    And I click on "OK" button to close the mean calculation dialog