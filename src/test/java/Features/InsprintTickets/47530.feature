Feature: 47530

  @47530-1
  Scenario: Verify that instance count matches with the number of images in viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "DIRKSEN^RALPH" in "Patient Name" filter under study list tab
    And I click on Advanced filter
    And I enter "XA" in "Modality" filter
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And get the series count for the expanded study
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "Ralph" and last name as "Dirksen"
    Then viewer should load the study for patient
    And instance count should match with the number of images

  @RevertServiceLocationProperty
  @47530-2
  Scenario: Verify that instance count matches with the number of images in viewer even after allow dicom report exporting
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "DIRKSEN^RALPH" in "Patient Name" filter under study list tab
    And I click on Advanced filter
    And I enter "XA" in "Modality" filter
    And I navigate to "System Settings" tab
    And I click on the "Service Locations" option in the settings panel
    And I search "VidiStar, LLC" in service location tab
    And I edit the service location
    And I "select" the "Allow Dicom Report Exporting" checkbox
    And I save service location
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And get the series count for the expanded study
    And I navigate to "Study List" tab
    And I open a study for patient with first name as "Ralph" and last name as "Dirksen"
    Then viewer should load the study for patient
    And instance count should match with the number of images