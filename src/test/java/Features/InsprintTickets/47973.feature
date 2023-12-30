@insprint
Feature: 47973

  @47973-1
  Scenario: Verify whether Volume - method of disks measurement unit is displayed on image
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 13
    And I click on "Volume - method of disks" option
    And I draw "Volume - method of disks" annotation on viewport
    Then incorrect measurement unit should not be displayed
    When I go to image number 2
    And I click on "Pause" button to stop the cineloop playing
    And I click on "Volume - method of disks" option
    And I draw "Volume - method of disks" annotation on viewport
    Then incorrect measurement unit should not be displayed