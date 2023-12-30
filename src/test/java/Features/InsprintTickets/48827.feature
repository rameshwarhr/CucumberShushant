@48827
Feature: 48827

  @ExitReportAndViewer
    @48827-1
    @48827-2
    @48827-3
    @48827-4
    @48827-5
    @48827-6
    @48827-7
    @48827-8
    @48827-9
    @48827-11
  Scenario Outline: To verify all free text lines are updated
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I open a study for patient with first name as "ANTONIO" and last name as "LOUISSAINT"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "<reportType>" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "<section>" under left panel
    And select "<tab>" tab
    And I maximize report editor
    And type multiple lines in "Free Text Area" on report editor GUI
    And edit each line in free text area
    And I restore report editor
    Then Report Dynamic Preview should get updated with "Free Text data"
    Examples:
      | role       | reportType    | reports                                             | section      | tab       |
      | technician | Vascular      | Lower Extremity Arterial Doppler Preliminary Report | Right Leg    | Findings  |
      | technician | Echo & Stress | Fetal Echo Preliminary Report                       | Mitral Valve | Free text |

  @ExitReportAndViewer
    @48827-10
  Scenario Outline: To verify all free text lines are updated using keyboard arrow keys
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I open a study for patient with first name as "ANTONIO" and last name as "LOUISSAINT"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "<reportType>" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "<section>" under left panel
    And select "<tab>" tab
    And I maximize report editor
    And type multiple lines in "Free Text Area" on report editor GUI
    And edit each line in free text area using keyboard arrow keys
    And I restore report editor
    Then Report Dynamic Preview should get updated with "Free Text data"
    Examples:
      | role       | reportType    | reports                                             | section      | tab       |
      | technician | Vascular      | Lower Extremity Arterial Doppler Preliminary Report | Right Leg    | Findings  |
      | technician | Echo & Stress | Fetal Echo Preliminary Report                       | Mitral Valve | Free text |

  @ExitReportAndViewer
    @48827-12
  Scenario Outline: To verify all free text lines are updated when copy pasted
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I open a study for patient with first name as "ANTONIO" and last name as "LOUISSAINT"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "<reportType>" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "<section>" under left panel
    And select "<tab>" tab
    And I maximize report editor
    And type multiple lines in "Free Text Area" on report editor GUI
    And copy paste the content in the "Free Text Area"
    And edit each line in free text area
    And I restore report editor
    Then Report Dynamic Preview should get updated with "Copied Free Text data"
    Examples:
      | role       | reportType    | reports                                             | section      | tab       |
      | technician | Vascular      | Lower Extremity Arterial Doppler Preliminary Report | Right Leg    | Findings  |
      | technician | Echo & Stress | Fetal Echo Preliminary Report                       | Mitral Valve | Free text |

  @DeleteTemplate
    @48827-13
  Scenario Outline: To verify all free text lines are edited for pre made template
    Given vidistar application is launched
    And I have logged in the application as "<role>" user
    When I open a study for patient with first name as "ANTONIO" and last name as "LOUISSAINT"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "<reportType>" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "SAVE AS TEMPLATE" icon
    Then Save Report Template window is opened
    When I enter name "<reports>" for normal template
    And I click OK
    And I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "<section>" under left panel
    And select "<tab>" tab
    And I maximize report editor
    And type multiple lines in "Free Text Area" on report editor GUI
    And edit each line in free text area
    And I restore report editor
    Then Report Dynamic Preview should get updated with "Free Text data"
    Examples:
      | role       | reportType    | reports                                             | section      | tab       |
      | technician | Vascular      | Lower Extremity Arterial Doppler Preliminary Report | Right Leg    | Findings  |
      | technician | Echo & Stress | Fetal Echo Preliminary Report                       | Mitral Valve | Free text |