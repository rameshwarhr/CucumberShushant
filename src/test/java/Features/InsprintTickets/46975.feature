Feature: 46975

  @46975_1
  @46975_2
  @DontSaveReport
  Scenario: Verify that rank is getting calculated based on BPD & EDD field values
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 49.8  |
    Then "BPD" rank "should not" get calculated
    When I click on "Patient information" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field  | type     | value           |
      | By LMP | dropdown | Todays EDD(AUA) |
    Then "BPD" rank "should" get calculated

  @46975_3
  @46975_4
  @46975_5
  @46975_7
  @RevertBPDCalculatingMethod
  Scenario: Verify that rank is getting calculated by changing the OB settings in Window Preferences
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
    When I increase the number of fetus by 1
    And I click on "Yellow Fetus (A)" in report editor left panel
    Then the BPD's rank calculating method "should" be changed
    And other fields should have their default rank calculating method as Hadlock
    When I enter below details in the "Fetal TTE Report (Standard)" fields
      | field         | type     | value   |
      | Papageorghiou | dropdown | Hadlock |
    Then the BPD's rank calculating method "should not" be changed
    When I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 49.8  |
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | HC    | text | 191   |
    And I click on "Patient information" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field  | type     | value           |
      | By LMP | dropdown | Todays EDD(AUA) |
    Then "BPD(papageorghiou)" rank "should" get calculated by papageorghiou method
    And "HC" rank "should" get calculated by hadlock method
    When I click on "Yellow Fetus (B)" in report editor left panel
    Then the BPD's rank calculating method "should" be changed
    And other fields should have their default rank calculating method as Hadlock

  @46975_6
  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  Scenario: Verify that rank is present for an amended report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I enter "WAYNE^JOHN" in "Patient Name" filter under study list tab
    And I change the worksheet status to "SCHEDULED"
    And I change the final report status to "SCHEDULED"
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 49.8  |
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | HC    | text | 191   |
    And I click on "Patient information" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field  | type     | value           |
      | By LMP | dropdown | Todays EDD(AUA) |
    Then "BPD & HC" rank "should" get calculated
    When I sign the report
    And I close report viewer
    And I right click on "Fetal TTE Preliminary Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    And "BPD & HC" rank "should" be present on the amended report

  @46975_8
  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  Scenario: Verify rank entered while signing a prelim report is present in final report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 49.8  |
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | HC    | text | 191   |
    And I click on "Patient information" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field  | type     | value           |
      | By LMP | dropdown | Todays EDD(AUA) |
    Then "BPD & HC" rank "should" get calculated
    When I sign the report
    And I close report viewer
    And  I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Final Report (Standard)" option
    Then the Report Editor window should load
    And "BPD & HC" rank "should" be present on dynamic preview of final report

  @46975_9
  @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
  Scenario: Verify existing fields are highlighted in red when we create a new final report from dicom viewer
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I enter "WAYNE^JOHN" in "Patient Name" filter under study list tab
    And I change the worksheet status to "SCHEDULED"
    And I change the final report status to "SCHEDULED"
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal TTE Final Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Fetus (A)" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | BPD   | text | 49.8  |
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field | type | value |
      | HC    | text | 191   |
    And I click on "Patient information" in report editor left panel
    And I enter below details in the "Fetal TTE Report (Standard)" fields
      | field  | type     | value           |
      | By LMP | dropdown | Todays EDD(AUA) |
    Then "BPD & HC" rank "should" get calculated
    When I sign the report
    And I close report viewer
    And I right click on "Fetal TTE Final Report" from reports list
    And click on "Create new final report" option for report
    Then the Report Editor window should load
    When I click on "Yellow Fetus (A)" in report editor left panel
    Then BPD and HC fields should be marked in red