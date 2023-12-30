@VID-223
Feature: VID-223

  @223_1_Part1
  Scenario: To verify fields are not auto selected in Findings section for Pediatric TTE Preliminary Report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "THE" and last name as "LISENDO 880 PEDS SR GOLD"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report" option
    Then the Report Editor window should load
    When I maximize report editor
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field      | type | value |
      | Heart rate | text | 80    |
    And I click on "Yellow Measurements" under left panel
    And I click on "Tricuspid Valve" subtab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field        | type | value |
      | TV Dec Slope | text | 5     |
    Then below fields should be auto populated for Findings section
      | Left panel section         | Tab name     | Field label                       |
      | AV Valves-Tricuspid Valve  | Hemodynamics | TV Dec Slope                      |
      | Ventricle- Left Ventricle  | LVOT         | LVCODop LVCIDop                   |
      | Outflow Tracts             | LVOT         | LVCODop LVCIDop                   |
      | Ventricle- Right Ventricle | RVOT         | RVOTSI RVOTCO RVOTCI RVOTDiam(2D) |
      | Outflow Tracts             | RVOT         | RVOTSI RVOTCO RVOTCI RVOTDiam(2D) |
      | SL Valves- Aortic Valve    | Hemodynamics | AVCO AVCI                         |
    When I click on "Yellow Measurements" under left panel
    And I click on "Yellow Aorta" subtab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field     | type | value |
      | AoV Ann d | text | 12    |
      | AoV Ann s | text | 12    |
    And I click on "Yellow Pulmonic Valve" subtab
    And I enter below details in the "Pediatric TTE Preliminary Report" fields
      | field    | type | value |
      | PV Ann d | text | 12    |
      | PV Ann s | text | 12    |
    Then below fields should be auto populated for Findings section
      | Left panel section        | Tab name     | Field label                 |
      | SL Valves- Pulmonic Valve | General      | AoVAnndPVAnnd AoVAnnsPVAnns |
      | AV Valves- Mitral Valve   | Hemodynamics | MVCO MVCI                   |

  @223_1_Part2
  Scenario: To verify fields are not auto selected in Findings section for Pediatric TTE Final Report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "THE" and last name as "LISENDO 880 PEDS SR GOLD"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Final Report" option
    Then the Report Editor window should load
    When I maximize report editor
    And I enter below details in the "Pediatric TTE Final Report" fields
      | field      | type | value |
      | Heart rate | text | 80    |
    And I click on "Yellow Measurements" under left panel
    And I click on "Tricuspid Valve" subtab
    And I enter below details in the "Pediatric TTE Final Report" fields
      | field        | type | value |
      | TV Dec Slope | text | 5     |
    Then below fields should be auto populated for Findings section
      | Left panel section         | Tab name     | Field label                       |
      | AV Valves-Tricuspid Valve  | Hemodynamics | TV Dec Slope                      |
      | Ventricle- Left Ventricle  | LVOT         | LVCODop LVCIDop                   |
      | Ventricle- Right Ventricle | RVOT         | RVOTSI RVOTCO RVOTCI RVOTDiam(2D) |
      | SL Valves- Aortic Valve    | Hemodynamics | AVCO AVCI                         |
    When I click on "Yellow Measurements" under left panel
    And I click on "Yellow Aorta" subtab
    And I enter below details in the "Pediatric TTE Final Report" fields
      | field     | type | value |
      | AoV Ann d | text | 12    |
      | AoV Ann s | text | 12    |
    And I click on "Yellow Pulmonic Valve" subtab
    And I enter below details in the "Pediatric TTE Final Report" fields
      | field    | type | value |
      | PV Ann d | text | 12    |
      | PV Ann s | text | 12    |
    Then below fields should be auto populated for Findings section
      | Left panel section        | Tab name     | Field label                 |
      | SL Valves- Pulmonic Valve | General      | AoVAnndPVAnnd AoVAnnsPVAnns |
      | AV Valves- Mitral Valve   | Hemodynamics | MVCO MVCI                   |

  @ExitReportAndViewer
    @223_2
  Scenario Outline: To verify fields are not auto selected in Findings section when HR is updated
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ADRIANNA" and last name as "FULLMER"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<report type>" option
    Then the Report Editor window should load
    When I maximize report editor
    And I enter below details in the "<report type>" fields
      | field             | type | value |
      | Yellow Heart rate | text | 80    |
    And I click on "Yellow Measurements" under left panel
    And I click on "Tricuspid Valve" subtab
    And I enter below details in the "<report type>" fields
      | field        | type | value |
      | TV Dec Slope | text | 5     |
    Then below fields should be auto populated for Findings section
      | Left panel section        | Tab name     | Field label  |
      | AV Valves-Tricuspid Valve | Hemodynamics | TV Dec Slope |
    When I click on "Yellow Measurements" under left panel
    And I click on "Aorta" subtab
    And I enter below details in the "<report type>" fields
      | field     | type | value |
      | AoV Ann d | text | 12    |
      | AoV Ann s | text | 12    |
    And I click on "Pulmonic Valve" subtab
    And I enter below details in the "<report type>" fields
      | field    | type | value |
      | PV Ann d | text | 12    |
      | PV Ann s | text | 12    |
    Then below fields should be auto populated for Findings section
      | Left panel section        | Tab name | Field label                 |
      | SL Valves- Pulmonic Valve | General  | AoVAnndPVAnnd AoVAnnsPVAnns |
    Examples:
      | report type                      |
      | Pediatric TTE Preliminary Report |
      | Pediatric TTE Final Report       |