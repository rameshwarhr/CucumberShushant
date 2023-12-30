@47827
Feature: 47827

  @47827_1-12
    @47827_16
    @RevertSingleMonitorView
  Scenario Outline: To verify same study opens on clicking restart now when making the window preference 'Use just one window' (check/uncheck) change
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I open a study for patient with first name as "ALINA" and last name as "LOPEZ"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    Then restart required pop up should be displayed
    When I click on 'Restart now' button on the Restart required modal
    Then viewer should load for the same study
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart now' button on the Restart required modal
    Then viewer should load for the same study
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    Then viewer should not reopen
    Examples:
      | role       |
      | Physician  |
      | Operator   |
      | technician |
      | admin      |

  @RevertSingleMonitorView
    @47827_13
  Scenario Outline: To verify same study opens on clicking restart now when making the window preference 'Use just one window' change for nm study type
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I open a study for patient with first name as "EFREN" and last name as "MORENO"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And I select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart now' button on the Restart required modal
    Then viewer should load for the same study
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart now' button on the Restart required modal
    Then viewer should load for the same study
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    Then viewer should not reopen
    Examples:
      | role       |
      | Operator   |
      | technician |

  @RevertSingleMonitorView
  @47827_14
  Scenario: To verify same study opens on clicking restart now when making the window preference 'Use just one window' change for second study
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "NIKESHIA" and last name as "RANSOM"
    Then viewer should load the study for patient
    When I close the dicom viewer
    And I open a study for patient with first name as "JUSTIN" and last name as "VOSHELL"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart now' button on the Restart required modal
    Then viewer should load for the same study

  @RevertSingleMonitorView
  @47827_15
  Scenario: To verify same study opens on clicking restart now when making the window preference 'Use just one window' change for fifth study
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "LORRAINE" and last name as "ADAMS"
    Then viewer should load the study for patient
    When I close the dicom viewer
    And I open a study for patient with first name as "BILBO" and last name as "BAGGINS"
    Then viewer should load the study for patient
    When I close the dicom viewer
    And I open a study for patient with first name as "ALEXANDRE" and last name as "RODIER"
    Then viewer should load the study for patient
    When I close the dicom viewer
    And I open a study for patient with first name as "SUSAN" and last name as "CUCULLU"
    Then viewer should load the study for patient
    When I close the dicom viewer
    And I open a study for patient with first name as "SAMMIE" and last name as "RANDALL"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart now' button on the Restart required modal
    Then viewer should load for the same study