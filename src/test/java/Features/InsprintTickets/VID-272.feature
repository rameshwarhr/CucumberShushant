@VID-272
Feature: VID-272

  @272_1
    @272_2
    @272_3
    @272
  Scenario Outline: Verify ECG study with MDC coding schema displays both Report preview and Datasets
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "<patientName>" patient name in "Patient Name" filter
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    Then "Report Preview" view should be displayed
    When I select "show datasets" display view
    Then reporting UI left panel should be displayed
    And "<dataset>" image should be displayed
    Examples:
      | patientName     | dataset                 |
      | TEST^JONATHAN   | TEST^JONATHAN Dataset   |
      | TEST^EKG^LANCE4 | TEST^EKG^LANCE4 Dataset |

  @RevertSignedReport
  @272_4
  @272
  Scenario: Verify user is able to sign ECG study report with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN^^^" patient name in "Patient Name" filter
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    And "Report Preview" view should be displayed
    When I select "show datasets" display view
    Then "TEST^JONATHAN Dataset" image should be displayed
    When I sign report
    Then study list tab should display "Final Report" status as "COMPLETED"

  @272_5
  Scenario: Verify whether user is able to use caliper tool for ECG study with MDC coding schema
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" patient name in "Patient Name" filter
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    And "Report Preview" view should be displayed
    When I select "show datasets" display view
    Then "Datasets" view should be displayed
    When I click on "Caliper tool" in show datasets
    Then calipers should be displayed on the datasets
    When I move one of the caliper on the datasets
    Then the caliper should be moved to the respective place on datasets

  @272_6
  @272_7
  @272
  Scenario: Verify user is able to zoom in and out using toolbar icons in simple viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^JONATHAN" patient name in "Patient Name" filter
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    And "Report Preview" view should be displayed
    When I select "show datasets" display view
    Then "Datasets" view should be displayed
    When I click on "Zoom in" in show datasets
    Then dataset view should be "Zoomed In"
    And zoom percentage should be increased to "120%"
    When I click on "Zoom out" in show datasets
    Then dataset view should be "Zoomed Out"
    And zoom percentage should be decreased to "100%"