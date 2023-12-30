@VID-105
Feature: VID-105

  @105_1
  @105_2
  @105_3
  @DeleteSeriesFromTwoStudies
  Scenario: Verify that graph decorator is present for the measurement field even after switching to another tab or subtab
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open 5 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I click on "Yellow Measurements" under left panel
    And select "R&L Ventricle(2D)" tab
    And I enter below details in the "Echo Preliminary Report" fields
      | field                  | type | value |
      | IVS Th Fract(2D-Mode)  | text | 35    |
      | LVPW Th Fract(2D-Mode) | text | 82.6  |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I open 4 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Yellow Measurements" under left panel
    And select "R&L Ventricle(2D)" tab
    And I enter below details in the "Echo Preliminary Report" fields
      | field                  | type | value |
      | IVS Th Fract(2D-Mode)  | text | 40    |
      | LVPW Th Fract(2D-Mode) | text | 84    |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I open 3 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Yellow Measurements" under left panel
    And select "Yellow R&L Ventricle(2D)" tab
    And I hover on "IVS Th Fract(2D-Mode) Calculated" measurement
    Then the graph decorator "should" appear
    When I hover on "LVPW Th Fract(2D-Mode) Calculated" measurement
    Then the graph decorator "should" appear
    When I click on the graph decorator of "IVS Th Fract(2D-Mode) Calculated" measurement field
    And I click on "Next Page" icon
    Then the graph of "IVS Th Fract(2D-Mode)" measurement field "should" be displayed on dynamic preview
    When I click on "EF(MOD A4C)" measurement field
    Then the graph decorator of "IVS Th Fract(2D-Mode)" measurement field "should" be visible on the report
    When select "Yellow LVOT" tab
    And select "Yellow R&L Ventricle(2D)" tab
    Then the graph decorator of "IVS Th Fract(2D-Mode)" measurement field "should" be visible on the report

  @105_4
  @DeleteSeriesFromTwoStudies
  Scenario: Verify that graph decorator is not present for the measurement field when the graph decorator is switched off
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open 5 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I click on "Yellow Measurements" under left panel
    And select "R&L Ventricle(2D)" tab
    And I enter below details in the "Echo Preliminary Report" fields
      | field                  | type | value |
      | IVS Th Fract(2D-Mode)  | text | 35    |
      | LVPW Th Fract(2D-Mode) | text | 82.6  |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I open 4 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Yellow Measurements" under left panel
    And select "R&L Ventricle(2D)" tab
    And I enter below details in the "Echo Preliminary Report" fields
      | field                  | type | value |
      | IVS Th Fract(2D-Mode)  | text | 40    |
      | LVPW Th Fract(2D-Mode) | text | 84    |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I open 3 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Yellow Measurements" under left panel
    And select "Yellow R&L Ventricle(2D)" tab
    And I hover on "IVS Th Fract(2D-Mode) Calculated" measurement
    Then the graph decorator "should" appear
    When I hover on "LVPW Th Fract(2D-Mode) Calculated" measurement
    Then the graph decorator "should" appear
    When I click on the graph decorator of "IVS Th Fract(2D-Mode) Calculated" measurement field
    And I click on "Next Page" icon
    Then the graph of "IVS Th Fract(2D-Mode)" measurement field "should" be displayed on dynamic preview
    When I click on the graph decorator of "IVS Th Fract(2D-Mode) with graph decorator" measurement field
    Then the graph of "IVS Th Fract(2D-Mode)" measurement field "should not" be displayed on dynamic preview
    When I click on "EF(MOD A4C)" measurement field
    Then the graph decorator of "IVS Th Fract(2D-Mode)" measurement field "should not" be visible on the report

  @105_5
  @DeleteSeriesFromThreeStudies
  Scenario: Verify that graph decorator is visible on amending the report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open 5 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I click on "Yellow Measurements" under left panel
    And select "R&L Ventricle(2D)" tab
    And I enter below details in the "Echo Preliminary Report" fields
      | field                  | type | value |
      | IVS Th Fract(2D-Mode)  | text | 35    |
      | LVPW Th Fract(2D-Mode) | text | 82.6  |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I open 4 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Yellow Measurements" under left panel
    And select "R&L Ventricle(2D)" tab
    And I enter below details in the "Echo Preliminary Report" fields
      | field                  | type | value |
      | IVS Th Fract(2D-Mode)  | text | 40    |
      | LVPW Th Fract(2D-Mode) | text | 84    |
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I open 3 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Yellow Measurements" under left panel
    And select "Yellow R&L Ventricle(2D)" tab
    And I click on the graph decorator of "IVS Th Fract(2D-Mode) Calculated" measurement field
    And I click on "Next Page" icon
    Then the graph of "IVS Th Fract(2D-Mode)" measurement field "should" be displayed on dynamic preview
    When I sign the report
    And I close report viewer
    And I right click on "Echo Preliminary Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "Yellow Measurements" under left panel
    And select "Yellow R&L Ventricle(2D)" tab
    Then the graph decorator of "IVS Th Fract(2D-Mode)" measurement field "should" be visible on the report
    When I click on "Next Page" icon
    Then the graph of "IVS Th Fract(2D-Mode)" measurement field "should" be displayed on dynamic preview