Feature: 45361

  @45361_1
  @45361_2
  @45361_3
  @45361_5
  Scenario: Verify order list is loading when we navigate from Study List->Study Tree->Order List
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I navigate to "Order List" tab
    Then order list should be loaded
    When I log out of the application
    And log back in the application as "Admin" user
    And I navigate to "System Settings" tab
    And I navigate to "Media" tab
    And I navigate to "Patient Service Logs" tab
    And I navigate to "Order List" tab
    Then order list should be loaded
    When I log out of the application
    And log back in the application as "Admin" user
    And I navigate to "Order List" tab
    Then order list should be loaded
    When I enter "JARBOE" in "Patient Name" filter under order list tab
    Then order list should display orders only which are having "Patient Name" as "JARBOE"
    When I clear the "Patient Name" filter under order list tab
    And I enter "HF152144687" in "Patient Id" filter under order list tab
    Then order list should display orders only which are having "Patient Id" as "HF152144687"
    When I clear the "Patient Id" filter under order list tab
    And I enter "36386969" in "Accession Number" filter under order list tab
    Then order list should display orders only which are having "Accession Number" as "36386969"
    When I clear the "Accession Number" filter under order list tab
    And I select "Yes" in "Has Study" filter under order list tab
    Then order list should display orders only which are having "Has Study" as "Yes"
    When I clear the "Has Study" filter under order list tab
    When I enter "5/12/2020" in "Start Date" filter under order list tab
    And I enter "5/12/2020" in "End Date" filter under order list tab
    Then order list should display orders only which are having "Date" as "5/12/2020"