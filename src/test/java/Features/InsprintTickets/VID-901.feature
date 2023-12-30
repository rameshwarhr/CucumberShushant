@VID-901
Feature: VID-901

  @901_4
  @901_3
  Scenario: Verify user is not able to enter negative value in measurement fields on simple viewer and sign the report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Measurements"
    And enter "Vent Rate (BPM)" as "-10"
    Then error should be displayed for negative input
    And enter "PR int (ms)" as "-20"
    Then error should be displayed for negative input
    And enter "QRS Dur (ms)" as "-30"
    Then error should be displayed for negative input
    And enter "QT (ms)" as "-40"
    Then error should be displayed for negative input
    And enter "QTC (ms)" as "-50"
    Then error should be displayed for negative input
    And enter "P Axes" as "-60"
    Then error should be displayed for negative input
    And enter "R Axes" as "-70"
    Then error should be displayed for negative input
    And enter "T Axes" as "-80"
    Then error should be displayed for negative input
    And enter "R-R Int (ms)" as "-90"
    Then error should be displayed for negative input
    When enter "R-R Int (ms)" as "-90"
    And I sign report
    Then error message should be displayed after clicking on sign report

  @901_2
  @901_5
  Scenario: Verify an exclamation mark should be displayed when measurement field contains negative value
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Measurements"
    And enter "Vent Rate (BPM)" as "-10"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "PR int (ms)" as "-20"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "QRS Dur (ms)" as "-30"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "QT (ms)" as "-40"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "QTC (ms)" as "-50"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "P Axes" as "-60"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "R Axes" as "-70"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "T Axes" as "-80"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark
    And enter "R-R Int (ms)" as "-90"
    Then an appropriate tooltip message should be displayed when we hover on an exclamation mark

  @901_6
  Scenario: Verify an exclamation mark should be displayed when measurement field does not contains whole number
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    And I select "Measurements"
    And enter "Vent Rate (BPM)" as "2.1"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "PR int (ms)" as "2.2"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "QRS Dur (ms)" as "2.3"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "QT (ms)" as "2.4"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "QTC (ms)" as "2.5"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "P Axes" as "2.6"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "R Axes" as "2.7"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "T Axes" as "2.8"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "R-R Int (ms)" as "2.9"
    Then an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark
    And enter "Vent Rate (BPM)" as "2.0"
    Then exclamation mark should not be displayed