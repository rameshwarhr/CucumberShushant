@VID-685
Feature: VID-685

@685_1
@685_2
@685_3
@685_4
@RevertSingleMonitorView
Scenario: Verify bullseye is highlighted in red box on hovering and turns red for the label calculated using bullseye
Given vidistar application is launched
And I have logged in the application as "Technician" user
When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
Then viewer should load the study for patient
When I navigate to "Window" menu
And select the "Preferences" option
Then Window Preferences dialog should open
When select "Use just one window (may be useful when having just one screen)" checkbox
And save the preferences
And click on 'Restart later' button on the Restart required modal
And close the dicom viewer
And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
Then viewer should load the study for patient
When I change the layout to "1:1"
And I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Preliminary Report" option
Then report editor should open within the same window
When I click on "Measurements" under left panel
And switch to "LVOT" subtab
And hover on the bullseye of "LVOT Diam(2D)" measurement label
Then "Bullseye in red box" should be displayed for the measurement label
When I click on the bullseye of "LVOT Diam(2D)" measurement label
Then "LVOT Diam(2D)" measurement label should be auto-checked
When I measure the value for "LVOT Diam(2D)" using the bullseye on the image
And hover on "LVOT Diam(2D)(Bullseye Calculated)" measurement
Then "Red Bullseye" should be displayed for the measurement label
When I enter below details in the "Pediatric TTE Preliminary Report" fields
|field						|type		|value		|
|LVOT Area(2D)				|text		|45			|
And I move the mouse cursor out of the measurement field
And hover on "LVOT Area(2D)" measurement
Then "Black Bullseye" should be displayed for the measurement label
When I switch to "Aorta" subtab
And I enter below details in the "Pediatric TTE Preliminary Report" fields
|field						|type		|value		|
|AoV Annulus(M-Mode)		|text		|45			|
And I move the mouse cursor out of the measurement field
And hover on "Aov Annulus(M-Mode)(Manually Calculated)" measurement
Then "Black Bullseye" should be displayed for the measurement label

@685_5
@685_6
@RevertSingleMonitorView
Scenario: Verify bullseye turns black when we clear the text field calculated by bullseye
Given vidistar application is launched
And I have logged in the application as "Technician" user
When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
Then viewer should load the study for patient
When I navigate to "Window" menu
And select the "Preferences" option
Then Window Preferences dialog should open
When select "Use just one window (may be useful when having just one screen)" checkbox
And save the preferences
And click on 'Restart later' button on the Restart required modal
And close the dicom viewer
And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
Then viewer should load the study for patient
When I change the layout to "1:1"
And I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Preliminary Report" option
Then report editor should open within the same window
When I click on "Measurements" under left panel
And switch to "LVOT" subtab
And click on the bullseye of "LVOT Diam(2D)" measurement label
And measure the value for "LVOT Diam(2D)" using the bullseye on the image
And unselect "LVOT Diam(2D)" on report editor
And hover on "LVOT Diam(2D)(Checkbox unselected)" measurement
Then "Red Bullseye" should be displayed for the measurement label
When I clear the text value field of "LVOT Diam(2D)(Checkbox unselected)" measurement label
And unselect "LVOT Diam(2D)" on report editor
And hover on "LVOT Diam(2D)" measurement
Then "Black Bullseye" should be displayed for the measurement label

@685_7
@RevertSingleMonitorView
Scenario: Verify bullseye turns red for machine generated measurement field
Given vidistar application is launched
And I have logged in the application as "Admin" user
When I open 4 study for patient with first name as "DOLAYNIE" and last name as "HERNANDEZ-RAMO"
Then viewer should load the study for patient
When I change the layout to "1:1"
And I go to image number 33
And I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Preliminary Report" option
Then report editor should open within the same window
When I click on "Yellow Measurements" under left panel
And switch to "Yellow Left Ventricle" subtab
And click on the bullseye of "LVPWd(M-Mode)(Machine Value)" measurement label
And measure the value for "LVPWd(M-Mode)" using the bullseye on the image
And hover on "LVPWd(M-Mode)(Bullseye Calculated)" measurement
Then "Red Bullseye" should be displayed for the measurement label

@685_8
@DeleteLatestSeriesAndRevertWorksheetStatusReport
Scenario: Verify bullseye is black for amended report
Given vidistar application is launched
And I have logged in the application as "Technician" user
When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
Then viewer should load the study for patient
When I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Preliminary Report" option
Then report editor should open within the same window
When I click on "Measurements" under left panel
And switch to "LVOT" subtab
And click on the bullseye of "LVOT Diam(2D)" measurement label
And I switch to the viewer/report window
And I change the layout to "1:1"
And measure the value for "LVOT Diam(2D)" using the bullseye on the image
And I switch to the viewer/report window
And I sign the report
And close report viewer
And I right click on "Pediatric Echo Preliminary Report" from reports list
And click on "Amend" option for report
Then the Report Editor window should load
When I click on "Yellow Measurements" under left panel
And switch to "Yellow LVOT" subtab
And hover on "LVOT Diam(2D)(Calculated)" measurement
Then "Red Bullseye" should be displayed for the measurement label