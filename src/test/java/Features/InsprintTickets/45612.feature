@insprint
Feature: 45612

  @45612-1
  Scenario: Verify whether system names and numbers are available under System Settings tab
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "System Names and Numbers" option in the settings panel
    Then system name and number should be available

  @45612-2
  Scenario: Verify whether all the existing system names and numbers are available while editing and adding a log
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "System Names and Numbers" option in the settings panel
    And get the system names and numbers
    And I navigate to "Patient Service Logs" tab
    And I right click on log number 1
    And click on "Edit" menu option
    Then all the system details for respective clinic should be available under Patient Service Logs
    When I right click on log number 1
    And click on "New" menu option
    And select "Hub" as "Florida"
    And select "Service Location" as "Key Largo"
    And select "Type" as "US"
    And click on "OK" button
    Then all the system details for respective clinic should be available under Patient Service Logs
    When I click on "Add New Patient Service Log" button icon
    And select "Hub" as "Florida"
    And select "Service Location" as "Key Largo"
    And select "Type" as "US"
    And click on "OK" button
    Then all the system details for respective clinic should be available under Patient Service Logs

  @RevertSystemName
  @45612-3
  Scenario: Verify whether renamed system names and numbers is available while editing and adding a log
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "System Names and Numbers" option in the settings panel
    And get the system names and numbers
    And I navigate to "Patient Service Logs" tab
    And I right click on log number 1
    And click on "Edit" menu option
    Then all the system details for respective clinic should be available under Patient Service Logs
    When I navigate to "System Settings" tab
    And rename "AutomationTestSystem" system to "AutomationTest"
    And I navigate to "Patient Service Logs" tab
    And I right click on log number 1
    And click on "Edit" menu option
    Then the "renamed" system should be available in the dropdown list under Patient Service Logs
    When I click on "Add New Patient Service Log" button icon
    And select "Hub" as "Florida"
    And select "Service Location" as "Key Largo"
    And select "Type" as "US"
    And click on "OK" button
    Then the "renamed" system should be available in the dropdown list under Patient Service Logs
    When I right click on log number 1
    And click on "New" menu option
    And select "Hub" as "Florida"
    And select "Service Location" as "Key Largo"
    And select "Type" as "US"
    And click on "OK" button
    Then the "renamed" system should be available in the dropdown list under Patient Service Logs

  @DeleteNewlyAddedSystem
  @45612-4
  Scenario: Verify whether newly added system is available while editing and adding a log
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "System Names and Numbers" option in the settings panel
    And get the system names and numbers
    And I navigate to "Patient Service Logs" tab
    And I right click on log number 1
    And click on "Edit" menu option
    Then all the system details for respective clinic should be available under Patient Service Logs
    When I navigate to "System Settings" tab
    And "click" the "Add new System Name and Number" button
    And create a new system as "NewTestSystem"
    And I navigate to "Patient Service Logs" tab
    And I right click on log number 1
    And click on "Edit" menu option
    Then the "newly created" system should be available in the dropdown list under Patient Service Logs
    When I click on "Add New Patient Service Log" button icon
    And select "Hub" as "Florida"
    And select "Service Location" as "Key Largo"
    And select "Type" as "US"
    And click on "OK" button
    Then the "newly created" system should be available in the dropdown list under Patient Service Logs
    When I right click on log number 1
    And click on "New" menu option
    And select "Hub" as "Florida"
    And select "Service Location" as "Key Largo"
    And select "Type" as "US"
    And click on "OK" button
    Then the "newly created" system should be available in the dropdown list under Patient Service Logs