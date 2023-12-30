@RichViewer
@Regression
Feature: Rich Viewer

  @Stable
  @DeleteExistingRichViewerFile
  @AT-197
  Scenario: Verify user is able to install viewer
    Given vidistar url is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    And click on rich viewer download button
    And save the ViewerDownloader exe file
    When I install the rich viewer
    Then rich viewer should get installed
    When I log out of the application
    And vidistar application is relaunched through desktop icon
    And I have logged in the application as "Global Admin" user
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient

  @Stable
  @AT-28
  Scenario: Verify user is able to invoke rich viewer after selecting a study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient

  @Stable
  @AT-79
  Scenario: Verify user is able to view only the selected thumbnail on viewer
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I select "PatientInfo" thumbnail
    And I change the layout to "1:1"
    Then I should be able to view only the "PatientInfoViewer" thumbnail

  @Stable
  @AT-50
  @DeleteLastSignedReport
  Scenario: Verify user is able to Import PDF Report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    And navigate to "Reports" menu
    And select the "Import PDF Report" option
    And import the PDF report file
    Then PDF report should be present in the report list

  @Stable
  @CloseViewerAndDeleteCreatedStudy
  @AT-237
  Scenario: Verify user is able to create new study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Study Tree" tab
    When I expand studies for first patient
    And I click on new study option
    Then Add new study modal should be displayed
    When I enter study details
      | field             | value               |
      | Study Description | TESTSTUDY           |
      | Service Location  | Accreditation Logos |
    And click on save button
    Then new study should be created
    When I open created study in rich viewer
    Then viewer should be blank as there are no images

  @Stable
  @DeleteLastInstance
  @AT-62
  Scenario: verify annotated image is saved to the end of the study
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And get the count of total images
    And I click on "Text" option
    And I click on image in the window
    And I annotate "1234" in image
    And I right click on viewport 0
    And click on "Save" option
    Then annotated image should appear at the end of the study
    When close the dicom viewer
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    Then annotated image should still be at the end of the study

  @Stable
  @selectLeftPanelOptionOnWindowPreferences
  @AT-1
  Scenario: Verify layout
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    And left panel "should" be visible on viewer
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When unselect "Show left panel on startup" checkbox
    And save the preferences
    And close the dicom viewer
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    And left panel "should not" be visible on viewer

  @Stable
  @AT-201
  Scenario: Start and Stop clips
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    Then cineloop should "Start" playing
    When I set the speed for cine loop to "1:10"
    And I click on "Stop" button to stop the cineloop playing
    Then cineloop should "stop" playing
    When I click on "Play" button to resume cineloop playing
    Then cineloop should resume playing from the point it was stopped
    When I click on "Stop" button to stop the cineloop playing
    And move the cine slider "forward"
    Then image should remain "paused"
    And "next" frame should be displayed
    When I click on "Play" button to resume cineloop playing
    Then cineloop should "resume" playing
    When I click on "Stop" button to stop the cineloop playing
    And move the cine slider "backward"
    Then image should remain "paused"
    And "previous" frame should be displayed

  @Stable
  @AT-6
  Scenario: Image operations
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I click on "Zoom" option
    And I click and drag mouse towards "top" on viewport
    Then the image should be "zoomed out"
    When I click and drag mouse towards "bottom" on viewport
    Then the image should be "zoomed in"
    When I click on "Pan" option
    And I click and drag mouse towards "top" on viewport
    Then the image should be "panned up"
    When I click and drag mouse towards "bottom" on viewport
    Then the image should be "panned down"
    When I click and drag mouse towards "left" on viewport
    Then the image should be "panned left"
    When I click and drag mouse towards "right" on viewport
    Then the image should be "panned right"
    When I click on "Brightness/Contrast" option
    And I click and drag mouse towards "right" on viewport
    Then the image contrast should "increase"
    When I click and drag mouse towards "left" on viewport
    Then the image contrast should "decrease"
    When I click and drag mouse towards "top" on viewport
    Then the image brightness should "decrease"
    When I click and drag mouse towards "bottom" on viewport
    Then the image brightness should "increase"

  @Stable
  @AT-239
  Scenario: Verify layout does not change after selecting VTI
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "BENJAMIN" and last name as "MACDONALD"
    Then viewer should load the study for patient
    And images are displayed in "4:1" layout
    When I double click on viewport 0
    Then images are displayed in "1:1" layout
    When I change the layout to "4:1"
    Then images are displayed in "4:1" layout
    When I change the layout to "1:1"
    And go to image number 44
    And I click on "VTI" option
    And I double click on VTI section of the image
    Then images are displayed in "1:1" layout

  @Stable
  @AT-230
  Scenario: Verify report template GUI is open
    Given vidistar application is launched
    And I have logged in the application as "templateadmin" user
    When I open a study for patient with first name as "RUBEN" and last name as "ALVAREZ"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Template Administration" option
    Then Template admin screen should load
    When I select "com.vidistar.cath.final" configuration instance on the template admin screen
    And select the "VALIDATION_RULES" configuration instance parameter list
    And click three dots on the editor parameter
    Then prelim report template GUI opens

  @Stable
  @AT-233
  Scenario: Verify user is able to perform stress echo sync
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I click on "Sync play" option
    And click on "Stop" button in viewport 1 to stop the cineloop
    Then cine on viewport 1 should "stop" playing
    And cine on viewport 2 should "stop" playing
    And cine on viewport 3 should "stop" playing

  @RevertNuclearTabsOrder
  @AT-42
  @Nuclear
  Scenario: Verify Viewer Layout
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "DANNY" and last name as "JOHNSON"
    Then viewer should load the study for patient
    And below tabs are displayed in nuclear study
      | Array             |
      | Raw               |
      | Polar plots       |
      | Slice             |
      | 3D                |
      | Full Display      |
      | Quick View        |
      | Secondary Capture |
    When I click on "Array" tab
    Then "Array" images should be present
    When I click on "Raw" tab
    Then "Raw" images should be present
    When I click on "Polar plots" tab
    Then "Polar plots" images should be present
    When I click on "Slice" tab
    Then "Slice" images should be present
    When I click on "3D" tab
    Then "3D" images should be present
    When I click on "Full Display" tab
    Then "Full Display" images should be present
    When I click on "Quick View" tab
    Then "Quick View" images should be present
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Active Tabs" option under "Nuclear Medicine" option
    And select "SPLASH" under active tabs
    And click "Down" button 2 times
    And save the preferences
    And close the dicom viewer
    When I open a study for patient with first name as "DANNY" and last name as "JOHNSON"
    Then viewer should load the study for patient
    And below tabs are displayed in nuclear study after changes
      | Raw               |
      | Polar plots       |
      | Array             |
      | Slice             |
      | 3D                |
      | Full Display      |
      | Quick View        |
      | Secondary Capture |

  @Stable
  @ClearCacheForRichViewer
  @AT-4
  Scenario: Verify study loading on clearing cache
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load and images "should not" be prefetched
    When I close the dicom viewer after prefetching is completed
    And I open the same study again
    Then viewer should load and images "should" be prefetched

  @DeleteCreatedPatient
  @AT-3
  Scenario: Verify user is able to fax reports from the viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Study Tree" tab
    And I add a new patient
    Then Add new study modal should be displayed
    When I enter study details
      | field             | value         |
      | Study Description | TESTSTUDY     |
      | Service Location  | VidiStar, LLC |
    And click on save button
    And I expand studies for first patient
    Then new study should be created
    When I open created study in rich viewer
    Then viewer should be blank as there are no images
    When I open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "First Trimester Final Report" option
    Then the Report Editor window should load
    When I sign the report
    And I close report viewer
    When I right click on the report from the Reports tab on viewer
    And click on "Fax & Email" option for report
    Then send fax and email window should be displayed
    When I add a referring physician to selected contacts
    And click on Send button
    And close the dicom viewer
    And I navigate to "Study List" tab
    Then black phone icon should be displayed for the patient study

  @Stable
  @UnselectPrinterIconOption
  @AT-7
  @582_12
  Scenario: Verify that the user can select images for printing
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "HERSHELL" and last name as "SLIMMER"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I right click on viewport 0
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then the report icon "should" appear
    When I right click on viewport 0
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    Then the report icon "should not" appear
    When I right click on viewport 0
    And I select "Select all still images (for report)" option from rich viewer right click menu
    Then the report icon should appear on all images

  @Stable
  @AT-202
  Scenario: Verify when viewer is launched the cine loops should begin playing automatically
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Auto-start playback" checkbox
    And save the preferences
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    Then cineloop "should not" start playing automatically
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Auto-start playback" checkbox
    And save the preferences
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    Then cineloop "should" start playing automatically

  @Stable
  @AT-5
  Scenario: Verify user can perform various image measurement operations
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 29
    And I click on "Distance" option
    And I click and drag mouse towards "top" on viewport
    Then "Distance" measurement should be displayed
    When I click on "Distance between parallels" option
    And I click and drag mouse towards "top" on viewport
    Then "Parallel" measurement should be displayed
    When I click on "Region of interest" option
    And I draw "Region of interest" annotation on viewport
    Then "Region of interest" measurement should be displayed
    When I click on "Free hand surface" option
    And I draw "Free hand surface" annotation on viewport
    Then "Free hand surface" completion should be displayed
    When I go to image number 2
    And I click on "Pause" button to stop the cineloop playing
    And I click on "Distance" option
    And I click and drag mouse towards "top" on viewport
    Then distance measurement in "Pixel" should be displayed

  @Stable
  @AT-ZoomPan
  Scenario Outline: Verify user is able to perform combination of Zoom & Pan on a particular image
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I click on "Zoom" option
    And I click and drag mouse towards "<zoomDirection>" on viewport
    When I click on "Pan" option
    And I click and drag mouse towards "<panDirection>" on viewport
    Then the "<zoom>" image should be "<pan>"
    Examples:
      | zoomDirection | panDirection | zoom      | pan         |
      | top           | top          | ZoomedOut | PannedUp    |
      | top           | bottom       | ZoomedOut | PannedDown  |
      | top           | left         | ZoomedOut | PannedLeft  |
      | top           | right        | ZoomedOut | PannedRight |
      | bottom        | top          | ZoomedIn  | PannedUp    |
      | bottom        | bottom       | ZoomedIn  | PannedDown  |
      | bottom        | left         | ZoomedIn  | PannedLeft  |
      | bottom        | right        | ZoomedIn  | PannedRight |

  @Stable
  @AT-PanZoom
  Scenario Outline: Verify user is able to perform combination of Pan & Zoom on a particular image
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I click on "Pan" option
    And I click and drag mouse towards "<panDirection>" on viewport
    When I click on "Zoom" option
    And I click and drag mouse towards "<zoomDirection>" on viewport
    Then the "<pan>" image should be "<zoom>"
    Examples:
      | zoomDirection | panDirection | zoom      | pan         |
      | top           | top          | ZoomedOut | PannedUp    |
      | top           | bottom       | ZoomedOut | PannedDown  |
      | top           | left         | ZoomedOut | PannedLeft  |
      | top           | right        | ZoomedOut | PannedRight |
      | bottom        | top          | ZoomedIn  | PannedUp    |
      | bottom        | bottom       | ZoomedIn  | PannedDown  |
      | bottom        | left         | ZoomedIn  | PannedLeft  |
      | bottom        | right        | ZoomedIn  | PannedRight |

  @Stable
  @AT-18
  Scenario: Verify user can configure DICOM calibration units
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    And I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When select "cm" as units of measurements
    And save the preferences
    And I click on "Distance" option
    And I click and drag mouse towards "top" on viewport
    Then measurement should be displayed in "cm"
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When select "mm" as units of measurements
    And save the preferences
    And I click on "Distance" option
    And I click and drag mouse towards "top" on viewport
    Then measurement should be displayed in "mm"

  @Stable
  @RevertFontSettings
  @AT-9
  Scenario: Verify user is able to change font and size for text annotation
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I click on "Text" option
    And I draw "Text" annotation on viewport
    Then "Text" measurement should be displayed
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I click on change font button
    And set the font as "Times New Roman"
    And set the font size as "28"
    And I click OK
    And save the preferences
    And I draw "Text" annotation on viewport
    Then text annotation should be displayed in font "Times New Roman" with size "28"

  @Stable
  @RevertViewerLayout
  @AT-203
  Scenario: Verify user is able to configure viewer layout
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I change default row count to "5"
    And I change default column count to "5"
    And save the preferences
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    And viewer row count should be "5"
    And viewer column count should be "5"

  @Stable
  @AT-204
  Scenario: Verify user is able to configure viewer left panel visibility
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Show left panel on startup" checkbox
    And save the preferences
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    And left panel "should not" be visible on viewer
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Show left panel on startup" checkbox
    And save the preferences
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    And left panel "should" be visible on viewer

  @Stable
  @AT-12
  Scenario: Verify DICOM playback functionality
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I click on "Stop" button in viewport 1 to stop the cineloop
    Then cine on viewport 1 should "stop" playing
    And cine on viewport 2 should "remain" playing
    And cine on viewport 3 should "remain" playing
    When I drag the cineslider to end position on viewport 1
    And I click on "Arrow" option
    And draw an arrow in the paused cineloop
    Then "Arrow" measurement should be displayed

  @Stable
  @AT-205
  Scenario: Verify user is able to configure viewer left panel size
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I increase the left panel size to 50 percent
    And save the preferences
    And I exit dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    And left panel size should be set to 50 percent of the viewer
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I decrease the left panel size to 25 percent
    And save the preferences
    And I exit dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    And left panel size should be set to 25 percent of the viewer

  @Stable
  @RemovePrintIcon
  @AT-63
  Scenario: - Verify selected images are attached to the report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "MARK" and last name as "YAKSHOE"
    Then viewer should load the study for patient
    When I right click on viewport 1
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    And I open the "Reports" menu
    And go to the "General US" option
    And select the "General - Preliminary report" option
    Then the Report Editor window should load
    When I click on "FITWIDTH" icon
    Then only selected image should be present in the report
    When I click on "File" menu on report viewer
    And click on "Exit" menu item on report viewer
    And I open the same study again
    Then viewer should load the study for patient
    When I right click on viewport 2
    And I select "Toggle image selection (for report)" option from rich viewer right click menu
    And I open the "Reports" menu
    And go to the "General US" option
    And select the "General - Preliminary report" option
    Then the Report Editor window should load
    And only selected images should be present in the report

  @Stable
  @AT-200
  Scenario: - Verify scrolling of images
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "MARK" and last name as "YAKSHOE"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I click on "Next images" option for 1 time(s)
    Then next sequence of image should be displayed
    When I click on "Previous images" option for 1 time(s)
    Then previous sequence of image should be displayed
    When I scroll the scroll bar "downwards" for 1 time(s)
    Then next sequence of image should be displayed
    When I scroll the scroll bar "upwards" for 1 time(s)
    Then previous sequence of image should be displayed
    When I scroll the mouse wheel "downwards" for 1 time(s)
    Then next sequence of image should be displayed
    When I scroll the mouse wheel "upwards" for 1 time(s)
    Then previous sequence of image should be displayed

  @RevertReportEditorChanges
  @AT-229
  Scenario: Verify Template Admin Post Processing
    Given vidistar application is launched
    And I have logged in the application as "templateadmin" user
    When I open a study for patient with first name as "SAMUEL" and last name as "WYSE"
    Then viewer should load the study for patient
    When I navigate to "Reports" menu
    And select the "Template Administration" option
    Then Template admin screen should load
    When I select "com.vidistar.cath.final" configuration instance on the template admin screen
    And select the "TEMPLATE_POSTPROCESSING" configuration instance parameter list
    And click three dots on the editor parameter
    Then report template editor should load
    When I delete "Patient location" label
    And I drag "Diastolic blood pressure" to new location
    And I navigate to "File" menu
    And select the "Save" option
    And I select "Template Administration" tab
    And I navigate to "File" menu
    And select the "Save" option
    When I navigate to "Reports" menu
    And go to the "Cath Final Report" option
    And select the "Cath Final Report" option
    Then "Patient location" "text" field "should not" be present on the "OBConsultationFinalReport" editor window
    And updated location of Diastolic blood pressure should be reflected

  @Stable
  @AT-199
  Scenario: Verify user is able to change image layout
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    Then 1 viewport should be displayed
    When I change the layout to "2:1"
    Then 2 viewports should be displayed
    When I change the layout to "4:1"
    Then 4 viewports should be displayed
    When I change the layout to "6:1"
    Then 6 viewports should be displayed

  @Stable
  @DeleteImportedStudy
  @AT-180
  Scenario: Verify user is able to import study from CD
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I navigate to "Study Tree" tab
    And click on import study from CD DVD ROM icon
    Then the Upload Wizard window should load
    When I select the DicomDir file
    And follow the prompts and click on finish
    And I navigate to "Study List" tab
    And I enter "HITACHI^HOLLY" in "Patient Name" filter under study list tab
    Then study list "should" display studies only which are having "Patient Name" as "HITACHI^HOLLY"

  @AT-2
  @Nuclear
  Scenario: Verify animation playing for nuclear study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "DANNY" and last name as "JOHNSON"
    Then viewer should load the study for patient
    When I click "Gate" button in nuclear toolbar
    Then animation should "Start" playing
    When I unclick "Gate" button in nuclear toolbar
    Then animation should "stop" playing

  @AT-43
  @Nuclear
  Scenario: Verify that tool produces excepted results when utilized
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "DANNY" and last name as "JOHNSON"
    Then viewer should load the study for patient
    When I click "Gate" button in nuclear toolbar
    Then animation should "Start" playing
    When I unclick "Gate" button in nuclear toolbar
    Then animation should "stop" playing
    When I click "Contours" button in nuclear toolbar
    Then contour lines "should" appear on the images
    When I unclick "Contours" button in nuclear toolbar
    Then contour lines "should not" appear on the images
    When I click "Process" menu in nuclear toolbar
    Then the study should re-process the images
    And "Array" images should be present
    When I click on "Polar plots" tab
    Then images should contain numbers
    When I unclick "Numbers" button in nuclear toolbar
    Then images should not contain numbers

  @AT-41
  @Nuclear
  Scenario: Verify array loaded correctly on opening nuclear study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "DANNY" and last name as "JOHNSON"
    Then viewer should load the study for patient
    When I click on "Array" tab
    Then "Array" should be loaded correctly on dicom viewer page

  @AT-61_Part1
  @Nuclear
  Scenario: Verify nuclear study viewer manipulation for Array,Raw,Slice,3D,Full Display,Quick View Tab
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "FATHY" and last name as "RASHWAN"
    Then viewer should load the study for patient
    When I click on "Array" tab
    And click 10 times on "next" arrow of scroll bar under SAX(APEX-BASE) section in viewport
    Then array slices should change accordingly in viewport
    When I click on "Raw" tab
    And click 10 times on "next" arrow of scroll bar under STRESS GATED section in viewport
    Then clip image should change in viewport
    When I click on "Slice" tab
    And move "white line" marker under STRESS GATED section in viewport
    Then slice image should change accordingly in viewport
    When I click on "3D" tab
    And drag the image box under STRESS GATED in viewport
    Then 3d image should change accordingly in viewport
    When I click on "Full Display" tab
    And move "line" marker under STRESS GATED section in viewport
    Then image should change accordingly in viewport
    When I drag the image box in viewport
    Then image box should change accordingly in viewport
    When I click on "Quick View" tab
    And move "small white line" marker under STRESS GATED section in viewport
    Then slice image should change accordingly in viewport

  @AT-61_Part2
  @Nuclear
  Scenario: Verify nuclear study viewer manipulation for Secondary Capture Tab
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "FATHY" and last name as "RASHWAN"
    Then viewer should load the study for patient
    When I click on "Secondary Capture" tab
    And I go to image number 2 in secondary capture
    And I click on "stop" button in viewport 0 to stop the cineloop
    When I right click on viewport 0
    And I select "Mouse mode" option from rich viewer right click menu
    And select the "Pan" sub menu option
    And I click and drag mouse towards "top" on viewport in secondary capture
    Then the image should "pan up"
    And I click on "stop" button in viewport 0 to stop the cineloop
    When I right click on viewport 0
    And I select "Mouse mode" option from rich viewer right click menu
    And select the "Pan" sub menu option
    When I click and drag mouse towards "bottom" on viewport in secondary capture
    Then the image should "pan down"
    And I click on "stop" button in viewport 0 to stop the cineloop
    When I right click on viewport 0
    And I select "Mouse mode" option from rich viewer right click menu
    And select the "Zoom" sub menu option
    And I click and drag mouse towards "top" on viewport in secondary capture
    Then the image should "zoom out"
    And I click on "stop" button in viewport 0 to stop the cineloop
    When I right click on viewport 0
    And I select "Mouse mode" option from rich viewer right click menu
    And select the "Zoom" sub menu option
    When I click and drag mouse towards "bottom" on viewport in secondary capture
    Then the image should "zoom in"
    And I click on "stop" button in viewport 0 to stop the cineloop
    When I right click on viewport 0
    And I select "Mouse mode" option from rich viewer right click menu
    And select the "Loupe" sub menu option
    When I click and drag mouse towards "top" on viewport in secondary capture
    Then the image should "loupe in SA section"
    And I click on "stop" button in viewport 0 to stop the cineloop
    When I right click on viewport 0
    And I select "Mouse mode" option from rich viewer right click menu
    And select the "Loupe" sub menu option
    When I click and drag mouse towards "bottom" on viewport in secondary capture
    Then the image should "loupe in HLA section"

  @ResetSessionTimeout
  @AT-212
  Scenario: Viewer should close after inactivity
    Given vidistar application is launched
    And I have logged in the application as "JBOSSADMIN" user
    When I open vidistar admin control access
    Then JMX Agent View should open
    When I set the session timeout to 60 seconds
    And I switch back to Vidistar
    And I refresh the page
    And I open a study for patient with first name as "MARY" and last name as "PLAYER"
    Then viewer should load and get closed in 60 seconds
    When I refresh the page
    Then login screen should appear

  @deleteAllMaskedImages
  @AT-252
  Scenario: Anonymization mask
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ZAKIYA" and last name as "JONES"
    Then viewer should load the study for patient
    When I right click and select "Toggle image selection (for export/print)" option for first 3 viewports
    Then images should display "export icon" on the top right for first 2 viewports
    When I right click on viewport 2
    And I select "Export" option from rich viewer right click menu
    And select the "Export to JPEG (still picture)" sub menu option
    Then Anonymization window should be displayed
    When I mask area of patient details
    Then selected area becomes blacked out
    And next images should also be masked
    When I click on browse button
    And select the export location
    Then masked images should be downloaded
    When I right click on viewport 2
    And I select "Export" option from rich viewer right click menu
    And select the "Export to GIF (still picture)" sub menu option
    Then Anonymization window should be displayed
    When I mask area of patient details
    Then selected area becomes blacked out
    And next images should also be masked
    When I click on browse button
    And select the export location
    Then masked images should be downloaded
    When I right click on viewport 2
    And I select "Export" option from rich viewer right click menu
    And select the "Export to BMP (still picture)" sub menu option
    Then Anonymization window should be displayed
    When I mask area of patient details
    Then selected area becomes blacked out
    And next images should also be masked
    When I click on browse button
    And select the export location
    Then masked images should be downloaded
    When I right click on viewport 2
    And go to the "Export" option from the right click menu
    And select the "Export to PNG (still picture)" sub menu option
    Then Anonymization window should be displayed
    When I mask area of patient details
    Then selected area becomes blacked out
    And next images should also be masked
    When I click on browse button
    And select the export location
    Then masked images should be downloaded

  @AT-10
  @Nuclear
  Scenario: Verify workspace layout
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "DANNY" and last name as "JOHNSON"
    Then viewer should load the study for patient
    And "Array" tab should be displayed by default
    When I click on "Raw" tab
    Then "Raw" images should be present
    When I click on "Polar plots" tab
    Then "Polar plots" images should be present
    When I click on "Slice" tab
    Then "Slice" images should be present
    When I click on "3D" tab
    Then "3D" images should be present
    When drag the image box under STRESS GATED in viewport
    Then 3d image should change accordingly in viewport
    When I click on "Full Display" tab
    Then "Full Display" images should be present
    When I click on "Quick View" tab
    Then "Quick View" images should be present
    When I click on "Secondary Capture" tab
    Then "Secondary Capture" images should be present

  @AT-58
  @Nuclear
  Scenario: Verify nuclear study DICOM viewer playback
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "FATHY" and last name as "RASHWAN"
    Then viewer should load the study for patient
    When I click on "Secondary Capture" tab
    And I go to image number 2 in secondary capture
    And I click on "stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing