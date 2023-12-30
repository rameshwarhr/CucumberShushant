@40978
Feature: 40978

  @TC_40978-1
  @TC_40978-2
  @TC_40978-3
  @TC_40978-7
  @DontSaveReport
  Scenario: Verify lesion gets selected and deleted by only backspace button by using the pointer icon
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then preliminary report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "FITPAGE" icon
    Then preliminary report diagram "should" be present in dynamic report
    When I select "Lesion" option
    And I mark lesion on aorta in the report
    Then marked lesion should be visible in the prelim report dynamic preview
    When I press "Backspace" key
    Then no element should get deleted from the "Report"
    And no element should get deleted from the "Dynamic preview"
    When I select "Select" option
    And I select "Lesion" from the anatomy diagram
    Then the marked element should get selected
    When I press "Enter" key
    Then the selected element "should not" get deleted from "Report"
    And the selected element "should not" get deleted from "Dynamic preview"
    When I press "Escape" key
    Then the selected element "should not" get deleted from "Report"
    And the selected element "should not" get deleted from "Dynamic preview"
    When I press "Backspace" key
    Then the selected element "should" get deleted from "Report"
    And the selected element "should" get deleted from "Dynamic preview"

  @TC_40978-4
  @TC_40978-5
  @TC_40978-11
  @DontSaveReport
  Scenario: Verify lesion can be dragged only within the image
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then preliminary report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "FITPAGE" icon
    Then preliminary report diagram "should" be present in dynamic report
    When I select "Lesion" option
    And I mark lesion on aorta in the report
    Then marked lesion should be visible in the prelim report dynamic preview
    When I click on "Actual size" icon
    And I select "Select" option
    And I select "Lesion" from the anatomy diagram
    Then the marked element should get selected
    When I drag the selected element to another location "Within" the image
    Then the selected element "should" get dragged to the targeted location
    When I drag the selected element to another location "Outside" the image
    Then the selected element "should not" get dragged to the targeted location

  @TC_40978-6
  @TC_40978-8
  @DontSaveReport
  Scenario: Verify all the lesions gets deleted after clicking on 'X' icon
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then preliminary report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "FITPAGE" icon
    Then preliminary report diagram "should" be present in dynamic report
    When I select "Lesion" option
    And I mark lesion on aorta in the report
    Then marked lesion should be visible in the prelim report dynamic preview
    When I select "Select" option
    And I select "Clear all" option
    And I click on Ok button for the confirmation
    Then all the elements should get deleted from the "Report"
    And all the elements should get deleted from the "Dynamic preview"

  @TC_40978-10
  @DontSaveReport
  Scenario: Verify any modification of the lesion reflects on dynamic preview
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then preliminary report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "FITPAGE" icon
    Then preliminary report diagram "should" be present in dynamic report
    When I select "Lesion" option
    And I mark lesion on aorta in the report
    And I select "Annotation" option
    And I annotate "test" in the report
    And I select "Stent" option
    And I mark "stent" in the report
    And I select "Aneurysm" option
    And I mark "aneurysm" in the report
    And I select "Bypass" option
    And I mark "bypass" in the report
    Then all the marked elements should be present in dynamic preview

  @TC_40978-12
  @DontSaveReport
  Scenario: Verify lesion is present when the report is amended
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Anatomy" in report editor
    Then preliminary report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "FITPAGE" icon
    Then preliminary report diagram "should" be present in dynamic report
    When I select "Lesion" option
    And I mark lesion on aorta in the report
    Then marked lesion should be visible in the prelim report dynamic preview
    When I save the report
    And close report viewer
    And I right click on "Abdominal Aorta Saved Report" from reports list
    And click on "Amend" option for report
    Then the Report Editor window should load
    When I click on "FITPAGE" icon
    And all the elements should also be present in dynamic report