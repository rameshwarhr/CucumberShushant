@VID-507
Feature: VID-507

  @507_1
    @DontSaveReport
  Scenario Outline: Verify vidistar icon is displayed after entering the values in biometry measurements
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "<report type>" option
    And select the "<report subtype>" option
    Then the Report Editor window should load
    When I maximize report editor
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field           | type     | value           |
      | GA(AUA)         | text     | 35w3d           |
      | First sono EDD  | dropdown | Todays EDD(AUA) |
    And I click on "Yellow Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 85    |
    Then "BPD(Rank Value with Default Formula)" rank field should have a vidistar icon
    Examples:
      | report type   | report subtype                          |
      | Echo & Stress | Fetal TTE Preliminary Report (Standard) |

  @507_2
  @507_3
  @RevertBPDCalculatingMethod
  Scenario: Verify vidistar icon is displayed after changing the OB settings in Window Preferences
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "OB Settings" option under preferences
    And I change the default percentile calculating method of BPD to "Papageorghiou Calculating Method"
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I maximize report editor
    And I increase the number of fetus by 1
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field           | type     | value           |
      | GA(AUA)         | text     | 35w3d           |
      | First sono EDD  | dropdown | Todays EDD(AUA) |
    And I click on "Yellow Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 85    |
    Then "BPD(Rank Value with Different Formula)" rank field should have a vidistar icon
    When I click on "Yellow Fetus (B)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 85    |
    Then "BPD(Rank Value with Different Formula)" rank field should have a vidistar icon

  @507_4
  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  Scenario:Verify vidistar icon is displayed while amending the report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I maximize report editor
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field           | type     | value           |
      | GA(AUA)         | text     | 35w3d           |
      | First sono EDD  | dropdown | Todays EDD(AUA) |
    And I click on "Yellow Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 85    |
    And I sign the report
    And I close report viewer
    And I right click on "Fetal TTE Preliminary Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I maximize report editor
    And I click on "Yellow Fetus (A)" in report editor left panel
    Then "BPD(Rank Value with Default Formula)" rank field should have a vidistar icon

  @507_5
  @DontSaveReport
  Scenario: Verify vidistar icon is displayed for patient with machine generated field values
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "ESAOTE OMEGA" and last name as "34 WEEK FETAL"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I maximize report editor
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field  | type     | value      |
      | By LMP | dropdown | EDD by BPD |
    And I click on "Yellow Fetus (A)" in report editor left panel
    Then "BPD Rank(Machine Generated)" rank field should have a vidistar icon

  @507_6
  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  Scenario: Verify vidistar icon is displayed is present in final report after signing its prelim report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I maximize report editor
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field           | type     | value           |
      | GA(AUA)         | text     | 35w3d           |
      | First sono EDD  | dropdown | Todays EDD(AUA) |
    And I click on "Yellow Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 85    |
    Then "BPD(Rank Value with Default Formula)" rank field should have a vidistar icon
    When I sign the report
    And I close report viewer
    And  I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Final Report (Standard)" option
    Then the Report Editor window should load
    When I maximize report editor
    And I click on "Yellow Fetus (A)" in report editor left panel
    Then "BPD(Rank Value with Default Formula)" rank field should have a vidistar icon