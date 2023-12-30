@VID-712
Feature: VID-712

  @712_1
  @712_2
  @712_3
  @712_4
  @712_5
  @RevertReportTitleAndDeleteLastSignedReport
  Scenario: Verify template admin user is able to edit the report title and title is case sensitive
    Given vidistar application is launched
    And I have logged in the application as "templateadmin" user
    When I open a study for patient with first name as "ECHO" and last name as "TEST"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Template Administration" option
    Then Template admin screen should load
    When I select "com.vidistar.angio.final" configuration instance on the template admin screen
    And select the "REPORT_TITLE" configuration instance parameter list
    And I change the "REPORT_TITLE" to "99SV@@11721-027-3@angiography test report" in template administration
    And I save the report
    And I open the "Reports" menu
    And go to the "Angio" option
    And select the "Angiographic and Interventional Procedure Suite" option
    Then the Report Editor window should load
    And the report "should" display "angiography test report" as report title
    When I sign the report
    And I close report viewer
    And select the "REPORT_TITLE" configuration instance parameter list
    And I change the "REPORT_TITLE" to "99SV@@11721-027-3@ANGIOGRAPHY TEST1 REPORT" in template administration
    And I save the report
    And I right click on "angiography test report" from reports list
    And click on "Create new final report" option for report
    Then the Report Editor window should load
    And the report "should" display "ANGIOGRAPHY TEST1 REPORT" as report title
    And the comparison baseline should display "angiography test report" as report title

  @712_6
  @RevertReportTitle
  Scenario: Verify report title of prelim report is not affected when we change its final report title
    Given vidistar application is launched
    And I have logged in the application as "templateadmin" user
    When I open a study for patient with first name as "ECHO" and last name as "TEST"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Template Administration" option
    Then Template admin screen should load
    When I select "com.vidistar.angio.final" configuration instance on the template admin screen
    And select the "REPORT_TITLE" configuration instance parameter list
    And I change the "REPORT_TITLE" to "99SV@@11721-027-3@Angiography Test Report" in template administration
    And I save the report
    And I open the "Reports" menu
    And go to the "Angio" option
    And select the "Angiographic and Interventional Procedure Suite - Preliminary Report" option
    Then the Report Editor window should load
    And the report "should not" display "Angiography Test Report" as report title