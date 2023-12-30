@46758

Feature: 46758

  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  @46758-1
  Scenario: To verify that final report is linked with signed prelim report data using Admin user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MARIO" and last name as "REYES"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report recommendations" in "Free Text Area" on report editor
    And I click on "Impressions" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report impressions" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Report" option
    Then the Report Editor window should load
    And pyp scan final report should get linked with signed prelim report

  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  @46758-2
  Scenario: To verify that final report is linked with signed prelim report data using Technician/Physician user
    Given vidistar application is launched
    And I have logged in the application as "Technician" user
    When I open a study for patient with first name as "MARIO" and last name as "REYES"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report recommendations" in "Free Text Area" on report editor
    And I click on "Impressions" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report impressions" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I log out of the application
    And log back in the application as "Physician" user
    And I open the same study again
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Report" option
    Then the Report Editor window should load
    And pyp scan final report should get linked with signed prelim report

  @RevertSignedWorksheetStatus @DeleteBothSeries
  @46758-3
  Scenario: To verify that final report is linked with amended prelim signed report data using Admin user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MARIO" and last name as "REYES"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report recommendations" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And I right click on "Cardiac Amyloidosis PYP Scan Preliminary Report" from reports list
    And click on "AMEND" option for report
    Then the Report Editor window should load
    When I click on "Impressions" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report impressions" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Report" option
    Then the Report Editor window should load
    And pyp scan final report should get linked with latest signed prelim report report

  @RevertSignedWorksheetStatus @DeleteBothSeries
  @46758-4
  Scenario: To verify that final report is linked with amended prelim signed report data using Technician/Physician user
    Given vidistar application is launched
    And I have logged in the application as "Technician" user
    When I open a study for patient with first name as "MARIO" and last name as "REYES"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Recommendations" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report recommendations" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And I sort the reports in the descending order
    And I select "Cardiac Amyloidosis PYP Scan Preliminary report" for amending
    Then the Report Editor window should load
    When I click on "Impressions" under left panel
    And I click on "FreeText" subtab
    And type "pyp scan report impressions" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And close the dicom viewer
    And I log out of the application
    And log back in the application as "Physician" user
    And I open the same study again
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "Cardiac Amyloidosis PYP Scan Report" option
    Then the Report Editor window should load
    And pyp scan final report should get linked with latest signed prelim report report