@insprint
Feature: 47513

  @47513-1
  Scenario: Verify whether free hand surface measurement value is displayed
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 29
    When I click on "Free hand surface" option
    And I draw "Free hand surface" annotation on viewport
    Then "Free hand surface" measurement should be displayed