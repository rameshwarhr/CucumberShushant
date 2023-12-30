Feature: VID-528

  @528_1
  @528_2
  @DontSaveReport
  Scenario: Verify user is able to add/remove image to the report from dicom viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I switch to the viewer window
    And I right click on viewport 3
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then report icon "should" be present on the image
    When I switch to the report window
    Then toggled image should be "added" to the report
    When I switch to the viewer window
    And I right click on viewport 3
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then report icon "should not" be present on the image
    When I switch to the report window
    Then toggled image should be "removed" from the report

  @528_6
  @528_13
  @RemoveIcon
  Scenario: Verify image is added to the second page for an amended report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I right click on "Pediatric Echo Report(1-18)" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I switch to the viewer window
    And I right click on viewport 3
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then report icon "should" be present on the image
    When I switch to the report window
    And I click on "Next Page" icon
    Then toggled image should be "added" to the report

  @528_14
  @DontSaveReport
  Scenario: Verify user is able to add image to a report with existing diagram
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then preliminary report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "Next Page" icon
    Then preliminary report diagram "should" be present in dynamic report
    When I switch to the viewer window
    And I right click on viewport 3
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then report icon "should" be present on the image
    When I switch to the report window
    Then toggled image should be "added" to the report

  @528_8
  @RemoveReportIconAndDeleteLastSignedPrelimReport
  Scenario: Verify image is toggled on the final report after signing its prelim report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Exercise Stress Echo Preliminary Report" option
    Then the Report Editor window should load
    When I switch to the viewer window
    And I right click on viewport 3
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then report icon "should" be present on the image
    When I switch to the report window
    Then toggled image should be "added" to the report
    When I sign the report
    And close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Exercise Stress Echo Final Report" option
    Then the Report Editor window should load
    And toggled image should be "added" to the report

  @528_9
  @RemoveReportIconAndDeleteTemplate
  Scenario: Verify image is toggled to template
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Exercise Stress Echo Preliminary Report" option
    Then the Report Editor window should load
    When I switch to the viewer window
    And I right click on viewport 3
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then report icon "should" be present on the image
    When I switch to the report window
    And I click on "SAVE AS TEMPLATE" icon
    Then Save Report Template window is opened
    When I enter name "AutomationTemplate" for normal template
    And I click OK
    And I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And select the "AutomationTemplate" option
    Then the Report Editor window should load
    And toggled image should be "added" to the report

  @528_15
  @RemoveIcon
  Scenario: Verify image is toggled to "Show images selected for report" section
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I right click on viewport 0
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then report icon "should" be present on the image
    When I click on "Show images selected for report" option
    Then images should display "Report Icon" on the top right for 0 viewport
