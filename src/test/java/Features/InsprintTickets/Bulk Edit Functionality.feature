Feature: BulkFunctionality

  @BulkFunctionality_1
  @RevertAssignedPhysician
  Scenario: Verify physician assigned to the service location is added to the studies through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 1 time
    And I search for the below 2 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
    And click on search button
    And click on "Assign Studies to Physicians" button
    Then Assign Studies to Physicians modal should open
    When I assign "AutoPhysician1" to the studies
    Then all the studies "should" display "Assigned Users" as "AutoPhysician1"
    When I navigate to "Study Tree" tab
    And I expand first patient
    Then all the studies of first patient "should" display Assigned Users as "AutoPhysician1"
    When I unexpand the studies of 1st patient
    And I expand second patient
    Then all the studies of second patient "should" display Assigned Users as "AutoPhysician1"

  @BulkFunctionality_2
  @RevertAssignedPhysician
  Scenario: Verify physician of different service location is not added to study through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 1 time
    And I search for the below 2 patients
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And click on "Assign Studies to Physicians" button
    Then Assign Studies to Physicians modal should open
    When I assign "AutoPhysician1" to the studies
    Then all the studies "should not" display "Assigned Users" as "AutoPhysician1"
    When I navigate to "Study Tree" tab
    And I expand first patient
    Then all the studies of first patient "should not" display Assigned Users as "AutoPhysician1"
    When I unexpand the studies of 1st patient
    And I expand second patient
    Then all the studies of second patient "should" display Assigned Users as "AutoPhysician1"

  @BulkFunctionality_3
  @RevertAssignedPhysician
  Scenario: Verify physician assigned to the service location is added to the studies through bulk functionality on study tree page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 1 time
    And I search for the below 2 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
    And click on search button
    And I navigate to "Study Tree" tab
    And I expand first patient
    And select all the studies of 1st patient
    And I expand second patient
    And select all the studies of 2nd patient
    And click on "Assign selected Studies to Physicians" button
    Then Assign Studies to Physicians modal should open
    When I assign "AutoPhysician1" to the studies
    And I unexpand the studies of 2nd patient
    Then all the studies of first patient "should" display Assigned Users as "AutoPhysician1"
    When I unexpand the studies of 1st patient
    And I expand second patient
    Then all the studies of second patient "should" display Assigned Users as "AutoPhysician1"

  @BulkFunctionality_4
  @RevertAssignedPhysician
  Scenario: Verify physician of different service location is not added to study through bulk functionality on study tree page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 1 time
    And I search for the below 2 patients
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And I navigate to "Study Tree" tab
    And I expand first patient
    And select all the studies of 1st patient
    And I expand second patient
    And select all the studies of 2nd patient
    And click on "Assign selected Studies to Physicians" button
    Then Assign Studies to Physicians modal should open
    When I assign "AutoPhysician1" to the studies
    And I unexpand the studies of 2nd patient
    Then all the studies of first patient "should not" display Assigned Users as "AutoPhysician1"
    When I unexpand the studies of 1st patient
    And I expand second patient
    Then all the studies of second patient "should" display Assigned Users as "AutoPhysician1"

  @BulkFunctionality_5
  @RevertAssignedPhysicianGroup
  Scenario: Verify physician groups are assigned to all studies through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 2 time
    And I search for the below 3 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And click on "Assign Studies to Physician Groups" button
    Then Assign Studies to Physician Groups modal should open
    When I assign "American League" to the studies
    Then all the studies "should" display "Assigned Groups" as "American League"

  @BulkFunctionality_6
  @RevertAssignedPhysicianGroup
  Scenario: Verify physician groups are assigned to all studies through bulk functionality on study tree page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 1 time
    And I search for the below 2 patients
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And I navigate to "Study Tree" tab
    And I expand first patient
    And select all the studies of 1st patient
    And I expand second patient
    And select all the studies of 2nd patient
    And click on "Assign selected Studies to Physician Groups" button
    Then Assign Studies to Physicians modal should open
    When I assign "American League" to the studies
    And I unexpand the studies of 2nd patient
    Then all the studies of first patient "should" display Assigned Groups as "American League"
    When I unexpand the studies of 1st patient
    And I expand second patient
    Then all the studies of second patient "should" display Assigned Groups as "American League"

  @BulkFunctionality_7
  Scenario: Verify accession number is applied to all studies through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 2 time
    And I search for the below 3 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And click on "Mass edit study attributes" button
    Then Edit Study Attributes modal should open
    When I edit "Accession Number" as "123"
    Then all the studies "should" display "Chart #" as "123"

  @BulkFunctionality_8
  Scenario: Verify study description is updated for all the studies through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 2 time
    And I search for the below 3 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And click on "Mass edit study attributes" button
    Then Edit Study Attributes modal should open
    When I edit "Description" as "TEST AUTOMATION"
    Then all the studies "should" display "Description" as "TEST AUTOMATION"

  @BulkFunctionality_9
  Scenario: Verify study date/time is updated for all the studies through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 2 time
    And I search for the below 3 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And click on "Mass edit study attributes" button
    Then Edit Study Attributes modal should open
    When I edit "Study Date/Time" as "7/27/2020"
    Then all the studies "should" display "Study Date/Time" as "7/27/2020"

  @BulkFunctionality_10
  Scenario: Verify worksheet status is updated for all the studies through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 2 time
    And I search for the below 3 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And click on "Mass edit study attributes" button
    Then Edit Study Attributes modal should open
    When I change the "worksheet" status of "studies" to "scheduled" in bulk
    Then all the studies "should" display "Worksheet" as "SCHEDULED"

  @BulkFunctionality_11
  Scenario: Verify final status is updated for all the studies through bulk functionality on study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 2 time
    And I search for the below 3 patients
      | GULL^DARLENE^^^  |
      | YAMADA^HANAKO^^^ |
      | REYES^MARIO^^^   |
    And click on search button
    And click on "Mass edit study attributes" button
    Then Edit Study Attributes modal should open
    When I change the "final report" status of "studies" to "scheduled" in bulk
    Then all the studies "should" display "Final Report" as "SCHEDULED"