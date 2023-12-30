@VID-894
Feature: VID-894

  @894_1
  Scenario: Verify tool tip labels are displayed properly for disabled buttons in simple viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "BUSBY^SHERION" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    And reporting UI left panel should be displayed
    When I hover on "First Page" button
    Then tool tip should display label as "First Page"
    When I hover on "Previous Page" button
    Then tool tip should display label as "Previous Page"
    When I hover on "Next Page" button
    Then tool tip should display label as "Next Page"
    When I hover on "Last Page" button
    Then tool tip should display label as "Last Page"

  @894_2
  @894_3
  @894_4
  @894_5
  Scenario: Verify last page and first page buttons on simple viewer are working as expected
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "BUSBY^SHERION" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "Indications"
    And select code "A00.0" from table
    And select code "A00.1" from table
    And I select "Procedure"
    And select code "0016070" from table
    And select code "0016071" from table
    And I select "Diagnosis"
    And select code "0010" from table
    And I hover on "Next Page" enabled button
    Then tool tip should display label as "Next Page"
    When I hover on "Last Page" enabled button
    Then tool tip should display label as "Last Page"
    When I click on "Last Page" button in simple viewer
    Then user should be directed to the "Last page" of the report
    When I hover on "Previous Page" enabled button
    Then tool tip should display label as "Previous Page"
    When I hover on "First Page" enabled button
    Then tool tip should display label as "First Page"
    When I click on "First Page" button in simple viewer
    Then user should be directed to the "First page" of the report

