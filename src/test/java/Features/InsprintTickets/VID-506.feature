@VID-506
Feature: VID-506

  @RevertSingleMonitorView
  @506_1
  @506_2
  @506_3
  @506_4
  @506_5
  @342_6
  Scenario: To verify measurement table closes without any error
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "MELANIE" and last name as "KAMINSKY"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    When select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    And close the dicom viewer
    And I open a study for patient with first name as "MELANIE" and last name as "KAMINSKY"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then report editor should open within the same window
    When I click on "Measurements" in report editor left panel
    And I switch to "Ao_LA_RA" subtab
    And I click on mean calculation icon of "Asc Ao Dia (2D-Mode)" measurement field
    Then Mean Calculation dialog should open
    When I click on "OK" button to close the mean calculation dialog
    Then Mean Calculation dialog should close
    When I click on the bullseye of "Yellow Asc Ao Dia (2D-Mode)" measurement label
    And I measure the value for "Asc Ao Dia (2D-Mode)" using the bullseye on the image
    Then "Asc Ao Dia (2D-Mode)" measurement label should get auto populated
    When I click on mean calculation icon of "Highlighted Asc Ao Dia (2D-Mode)" measurement field
    Then Mean Calculation dialog should open
    And value should be present in the measurement table
    When I click on "Cancel" button to close the mean calculation dialog
    Then Mean Calculation dialog should close
    When I click on mean calculation icon of "Highlighted Asc Ao Dia (2D-Mode)" measurement field
    Then Mean Calculation dialog should open
    When I click on "Close" button to close the mean calculation dialog
    Then Mean Calculation dialog should close