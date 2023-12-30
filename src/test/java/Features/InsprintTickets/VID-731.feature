@VID-731
Feature: VID-731

  @731_3
  @DontSaveReport
  Scenario: Verify purple fields are not populated in free text comparison box when appropriate window preference is unchecked
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Report Editor" option under preferences
    And unselect "Copy right clicked findings to impressions free text box" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I right click on "Patient name" label
    Then "Patient name purple" should turn purple
    When I right click on "Sex" label
    Then "Sex purple" should turn purple
    When I right click on "Patient type" label
    Then "Patient type purple" should turn purple
    When I click on Impressions tab
    And I click Free text subtab
    Then purple fields "should not" be displayed in "Free Text Comparison Box"
    Then purple fields "should" be displayed in "Dynamic Preview"

  @731_4
  @731_5
  @DontSaveReport
  Scenario: Verify long statement labels are displayed within the same hamburger
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Report Editor" option under preferences
    And select "Copy right clicked findings to impressions free text box" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on Impressions tab
    And I click Free text subtab
    And I right click on "LV systolic function improved" label
    Then free text comparison box should display line item in the same hamburger
    When I click on "Right Ventricle" in report editor left panel
    And I right click on "Pacemaker" label
    When I click on "Yellow Impressions" in report editor left panel
    Then line items should be displayed one below the other in "Free Text Comparison Box"
    And line items should be displayed one below the other in "Dynamic Preview"

  @731_6
  @DontSaveReport
  Scenario: Verify phrase is populated in next line after manually entering in the free text comparison box
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Report Editor" option under preferences
    And select "Copy right clicked findings to impressions free text box" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I right click on "Patient name" label
    And I click on "Yellow Impressions" in report editor left panel
    And I click Free text subtab
    And type "This is CT Automation Test" in "Free Text Comparison" box
    And I click on "Right Ventricle" in report editor left panel
    And I right click on "Pacemaker" label
    And I click on "Yellow Impressions" in report editor left panel
    Then line item should be displayed after the manual entry in "Free Text Comparison Box"
    And line item should be displayed after the manual entry in "Dynamic Preview"

  @731_7
  @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
  Scenario: Verify amended report displays the line items one below the other
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Report Editor" option under preferences
    And select "Copy right clicked findings to impressions free text box" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I right click on "Patient name" label
    And I right click on "Patient type" label
    And I right click on "Sex" label
    And I click on "Right Ventricle" in report editor left panel
    And I right click on "Pacemaker" label
    And I sign the report
    And I close report viewer
    And I right click on "Echo Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "Yellow Impressions" in report editor left panel
    Then line items should be displayed in "Free Text Comparison Box" in the amending report
    And  line items should be displayed in "Dynamic Preview" in the amending report

  @731_8
  @DontSaveReport
  Scenario: Verify phrase is populated in previous line after manually deleting in the free text comparison box
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Report Editor" option under preferences
    And select "Copy right clicked findings to impressions free text box" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I right click on "Patient name" label
    And I right click on "Age" label
    And I right click on "Sex" label
    And I click on "Yellow Impressions" in report editor left panel
    And I delete "Age Text" from free text comparison box
    Then line item should be displayed after the manual deletion in "Free Text Comparison Box"
    And line item should be displayed after the manual deletion in "Dynamic Preview"