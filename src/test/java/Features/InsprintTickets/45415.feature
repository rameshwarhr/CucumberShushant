@45415
Feature: 45415

  @45415_1
    @45415_3
  Scenario Outline: To verify Study details are correct for create study using right click menu option for different roles
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I navigate to "Order List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under order list tab
    And right click on the patient from order list
    And I select "Create Study" option from order list right click menu
    Then create new study modal should be displayed
    And study details should be correct on order list page
    Examples:
      | role         |
      | Admin        |
      | Global Admin |
      | Operator     |

  @45415_2
  Scenario Outline: To verify Study details are correct for create study using floating toolbar icon for different roles
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I navigate to "Order List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under order list tab
    And hover on the first patient under order list tab
    And I click "Create Study" icon from the floating toolbar
    Then create new study modal should be displayed
    And study details should be correct on order list page
    Examples:
      | role         |
      | Admin        |
      | Global Admin |
      | Operator     |

  @DeleteStudyCreatedFromOrderList
  @45415_4
  @45415_5
  @45415_6
  @45415_7
  @45415_8
  @45415_9
  Scenario: To verify information pop up is displayed for has study and study details are correct on study list and study tree for the created study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Order List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under order list tab
    And right click on the patient from order list
    And I select "Create Study" option from order list right click menu
    Then create new study modal should be displayed
    When I enter study details in create study window
      | field            | value         |
      | Service Location | VidiStar, LLC |
    And click on save button
    Then Has Study checkbox should be "checked"
    When I hover on the first patient under order list tab
    And I click "Create Study" icon from the floating toolbar
    Then information pop up should be displayed
    When I right click on the patient from order list
    And I select "Create Study" option from order list right click menu
    Then information pop up should be displayed
    When I navigate to "Study List" tab from order list
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under study list tab
    And I right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    Then Edit Study Attributes modal should open
    And study details should be correct on study list page
    When I navigate to "Study Tree" tab
    And I expand patient "VIDISTAR^GRANT" to view its studies
    And I right click on the first study from study tree
    And I select "Edit Study Attributes" option from study tree right click menu
    Then Edit Study Attributes modal should open on study tree page
    And study details should be correct on study tree page
    When I navigate to "Study List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under study list tab
    Then study list "should" display studies only which are having "Patient Name" as "VIDISTAR^GRANT"
    When I open a study for patient with first name as "GRANT" and last name as "VIDISTAR"
    Then viewer should be blank as there are no images
    When I close the dicom viewer
    And I navigate to "Study Tree" tab
    Then single study should be visible for created order

  @DeleteStudyCreatedFromOrderList
  @45415_10
  @45415_11
  Scenario: To verify Has Study checkbox is unchecked on deletion of created study from order list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Order List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under order list tab
    And right click on the patient from order list
    And I select "Create Study" option from order list right click menu
    Then create new study modal should be displayed
    When I enter study details in create study window
      | field            | value         |
      | Service Location | VidiStar, LLC |
    And click on save button
    Then Has Study checkbox should be "checked"
    When I navigate to "Study Tree" tab from order list
    And I expand patient "VIDISTAR^GRANT" to view its studies
    And I right click on the first study from study tree
    And I select "Delete Study" option from study tree right click menu
    And I navigate to "Order List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under order list tab
    Then Has Study checkbox should be "unchecked"
    And right click on the patient from order list
    And I select "Create Study" option from order list right click menu
    Then create new study modal should be displayed
    When I enter study details in create study window
      | field            | value         |
      | Service Location | VidiStar, LLC |
    And click on save button
    Then Has Study checkbox should be "checked" again

  @DeleteStudyCreatedFromOrderList
  @45415_12
  Scenario: To verify user can sign the reports for newly created study from order list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Order List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under order list tab
    And right click on the patient from order list
    And I select "Create Study" option from order list right click menu
    Then create new study modal should be displayed
    When I enter study details in create study window
      | field            | value         |
      | Service Location | VidiStar, LLC |
    And click on save button
    And I navigate to "Study List" tab from order list
    When I open a study for patient with first name as "GRANT" and last name as "VIDISTAR"
    Then viewer should be blank as there are no images
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I sign the report
    And I close report viewer
    And I close the dicom viewer
    Then study list "should" display studies only which are having "Worksheet" as "COMPLETED"

  @DeleteStudyCreatedFromOrderList
  @45415_13
  Scenario: To verify Advanced Tab options on Create Study window reflect correctly on the study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Order List" tab
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under order list tab
    And right click on the patient from order list
    And I select "Create Study" option from order list right click menu
    Then create new study modal should be displayed
    When I click on "Advanced" tab on the create study modal
    And I select "SCHEDULED" in "Worksheet" filter under order list tab
    And I select "SCHEDULED" in "Final Report" filter under order list tab
    And I click on "Basic" tab on the create study modal
    And I enter study details in create study window
      | field            | value         |
      | Service Location | VidiStar, LLC |
    And click on save button
    And I navigate to "Study List" tab from order list
    And I enter "VIDISTAR^GRANT" in "Patient Name" filter under study list tab
    Then study list "should" display studies only which are having "Patient Name" as "VIDISTAR^GRANT"
    And study list "should" display studies only which are having "Worksheet" as "SCHEDULED"
    And study list "should" display studies only which are having "Final Report" as "SCHEDULED"