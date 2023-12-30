@VID-8
Feature: VID-8

  @8_1
  @8_4
  @8_7
  @8_8
  @DontSaveReport
  Scenario: Verify text is annotated in default size and different font formatting rules can be applied to the annotated text
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then default font size should be 15
    When I select "Include diagram in the report" in report editor
    And I click on "ACTUAL SIZE" icon
    And I select "Annotation" option
    And I annotate "Test" in the report
    And I click on "Next Page" icon
    Then annotated text "should" be displayed in its default font size in "Report"
    And annotated text "should" be displayed in its default font size in "Dynamic Preview"
    When I select "Select" option
    And I select "Annotated text(Default Size)" from the anatomy diagram
    And I enter below details in the "Abdominal Aorta Preliminary Report" fields
      | field     | type     | value |
      | Font Size | dropdown | 35    |
    Then annotated text "should not" be displayed in its default font size in "Report"
    And annotated text "should not" be displayed in its default font size in "Dynamic Preview"
    When I select "Bold" option
    Then annotated text should be displayed in "Bold" font style in "Report"
    And annotated text should be displayed in "Bold" font style in "Dynamic Preview"
    When I deselect "Bold" option
    And I select "Italics" option
    Then annotated text should be displayed in "Italics" font style in "Report"
    And annotated text should be displayed in "Italics" font style in "Dynamic Preview"
    When I deselect "Italics" option
    And I select "Underline" option
    Then annotated text should be displayed in "Underline" font style in "Report"
    And annotated text should be displayed in "Underline" font style in "Dynamic Preview"
    When I select "Annotation" option
    And I annotate text "Test1" in "Region1" in the report
    Then annotated text should be displayed in "Underline(Test1)" font style in "Report"
    And annotated text should be displayed in "Underline(Test1)" font style in "Dynamic Preview"

  @8_5
  @8_6
  @DeselectAllAndDontSaveReport
  Scenario: Verify diagram toolbar and selected annotated texts are in sync
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    And I select "Include diagram in the report" in report editor
    And I click on "FITPAGE" icon
    And I enter below details in the "Abdominal Aorta Preliminary Report" fields
      | field     | type     | value |
      | Font Size | dropdown | 35    |
    And I select "Bold" option
    And I select "Underline" option
    And I select "Annotation" option
    And I annotate "Test" in the report
    And I deselect "Bold" option
    And I deselect "Underline" option
    And I select "Italics" option
    And I select "Annotation" option
    And I annotate text "Test1" in "Region1" in the report
    When I select "Select" option
    And I select "Annotated text(Bold and Underline)" from the anatomy diagram
    Then "Bold" font style icon should be highlighted in blue box
    And "Underline" font style icon should be highlighted in blue box
    When I select "Select" option
    And I select "Annotated text(Italics)" from the anatomy diagram
    Then "Italics" font style icon should be highlighted in blue box
    When I click on "Reports" menu on report viewer
    And click on "Vascular" menu item on report viewer
    And select the "Abdominal Aorta Final Report" sub option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then default font size should be 15

  @8_2
  @8_9
  @8_10
  @8_11
  @DeleteLatestSeriesAndRevertWorksheetStatusReport
  Scenario: Verify font change on annotated text is present when report is amended
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Venous Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    And I select "Include diagram in the report" in report editor
    And I enter below details in the "Abdominal Venous Preliminary Report" fields
      | field     | type     | value |
      | Font Size | dropdown | 35    |
    And I select "Bold" option
    And I select "Annotation" option
    And I annotate "Test" in the report
    And I sign the report
    And close report viewer
    And I right click on "Abdominal Venous Preliminary Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    And I click on "ACTUAL SIZE" icon
    And I click on "Next Page" icon
    Then annotated text should be displayed in "Bold" font style in "Report"
    And annotated text should be displayed in "Bold" font style in "Dynamic Preview"
    When I select "Annotation" option
    And I annotate text "Test" in "Region1" in the report
    Then annotated text should be displayed in "Default Font" in "Report"
    And annotated text should be displayed in "Default Font" in "Dynamic Preview"
    When I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Venous Final Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    And I click on "ACTUAL SIZE" icon
    And I click on "Next Page" icon
    Then annotated text should be displayed in "Bold" font style in "Report"
    And annotated text should be displayed in "Bold" font style in "Dynamic Preview"

  @8_12
  @DontSaveReport
  Scenario: Verify text is annotated and different font formatting rules can be applied to the annotated text when user selects different diagram from same template
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    And change the dropdown from "Abdominal veins" to "Abdominal artery duplex" anatomy diagram
    And I select "Include diagram in the report" in report editor
    And I click on "ACTUAL SIZE" icon
    And I click on "Next Page" icon
    And I enter below details in the "Abdominal Aorta Preliminary Report" fields
      | field         | type     | value |
      | Font Size(13) | dropdown | 35    |
    And I select "Bold" option
    And I select "Annotation" option
    And I annotate text "Test" in "Region2" in the report
    Then annotated text should be displayed in "Bold(Test)" font style in "Report"
    And annotated text should be displayed in "Bold(Test)" font style in "Dynamic Preview"