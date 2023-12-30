@44631
Feature: 44631

  @DeleteReportTemplateInstance
  @44631-1
  @44631-2
  @44631-4
  @44631-5
  @44631-6
  @44631-7
  Scenario: To verify report template is visible as per the service location configuration
    Given vidistar application is launched
    And I have logged in the application as "templateadmin" user
    When I navigate to "System Settings" tab
    And I click on the "Template administration" option in the settings panel
    And I enter "Cath Final Report" under instances search box
    And I right click on the template instance search result
    And select "Clone" from the right click menu
    And enter "Cath Final Report Test" in the GUI Path textbox
    And save the Configuration instance
    And I enter "Cath Final Report Test" under instances search box
    Then "Cath Final Report Test" should be visible under template instances
    When I click on the template instance filtered result
    And I enter "SRT" in Designator search box under Codes in selected Context Group
    And enter "Unknown" in Meaning search box under Codes in selected Context Group
    And I select and drag the result to the Allowed study types section
    And I unfold the Service Location configuration
    And enter "Truist Park" in Name search box under Available service locations
    And I select and drag the result to the Included service locations section
    And I double click on "Truist Park" in the included service locations section
    Then "Truist Park" should move under Available service locations
    When enter "Truist Park" in Name search box under Available service locations
    And I select and drag the result to the Included service locations section
    And I enter "Amsterdam" in Name search box under Available service locations
    And I select and drag the result to the Excluded service locations section
    And I double click on "Amsterdam" in the excluded service locations section
    Then "Amsterdam" should move under Available service locations
    When enter "Amsterdam" in Name search box under Available service locations
    And I select and drag the result to the Excluded service locations section
    And I unfold the User role assignment configuration
    And enter "physician" in Name search box under Available roles
    And I select and drag the result to the Visible for Roles section
    And I log out of the application
    And log back in the application as "physician" user
    And I open a study for patient with first name as "WILLIAM" and last name as "HALL"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    Then "Cath Final Report Test" option "should not" be present in the reports menu
    When I close the dicom viewer
    And I open a study for patient with first name as "KAYDEN" and last name as "PILON"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    Then "Cath Final Report Test" option "should" be present in the reports menu
    When I select the "Cath Final Report Test" option
    Then the Report Editor window should load

  @DeleteDefaultTemplateInstance
  @44631-3
  Scenario: To verify template shown for all service location when include and exclude lists are empty
    Given vidistar application is launched
    And I have logged in the application as "templateadmin" user
    When I navigate to "System Settings" tab
    And I click on the "Template administration" option in the settings panel
    And I enter "Cath Final Report" under instances search box
    And I right click on the template instance search result
    And select "Clone" from the right click menu
    And enter "Cath Final Report Test" in the GUI Path textbox
    And save the Configuration instance
    And I enter "Cath Final Report Test" under instances search box
    And I click on the template instance filtered result
    And I enter "SRT" in Designator search box under Codes in selected Context Group
    And enter "Unknown" in Meaning search box under Codes in selected Context Group
    And I select and drag the result to the Allowed study types section
    And I unfold the Service Location configuration
    Then included and excluded service location is empty
    When I unfold the User role assignment configuration
    And enter "physician" in Name search box under Available roles
    And I select and drag the result to the Visible for Roles section
    And I log out of the application
    And log back in the application as "physician" user
    And I open a study for patient with first name as "GAIL" and last name as "POORE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    Then "Cath Final Report Test" option "should" be present in the reports menu
    When I select the "Cath Final Report Test" option
    Then the Report Editor window should load
    When I close report viewer
    And click on Don't Save
    And close the dicom viewer
    And I open a study for patient with first name as "LARRY" and last name as "BARKER"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    Then "Cath Final Report Test" option "should" be present in the reports menu
    When I select the "Cath Final Report Test" option
    Then the Report Editor window should load