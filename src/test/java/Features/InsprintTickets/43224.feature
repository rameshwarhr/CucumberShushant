@insprint
Feature: 43224

  @43224_1
  Scenario: Verify spacebar functionality to play/pause cine in different layouts
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    And I click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I click the "spacebar" keyboard button again
    Then cine on viewport 0 should "resume" playing
    When I change the layout to "2:1"
    And I click on viewport 1
    And click the "spacebar" keyboard button
    Then cine on viewport 1 should "stop" playing
    When I click the "spacebar" keyboard button again
    Then cine on viewport 1 should "resume" playing
    When I change the layout to "4:1"
    And I click on viewport 2
    And click the "spacebar" keyboard button
    Then cine on viewport 2 should "stop" playing
    When I click the "spacebar" keyboard button again
    Then cine on viewport 2 should "resume" playing
    When I change the layout to "6:1"
    And I click on viewport 3
    And click the "spacebar" keyboard button
    Then cine on viewport 3 should "stop" playing
    When I click the "spacebar" keyboard button again
    Then cine on viewport 3 should "resume" playing

  @43224_2
  Scenario: Verify image traversing functionality using keyboard controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And click the "right" arrow keyboard button
    Then viewer should display the "next" image
    When I click the "left" arrow keyboard button
    Then viewer should display the "previous" image
    When I change the layout to "4:1"
    And I click on viewport 0
    And click the "right" arrow keyboard button
    Then "next" set of images should be displayed
    When I click the "left" arrow keyboard button
    Then "previous" set of images should be displayed

  @43224_3
  Scenario: Verify frame traversing functionality using keyboard controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    And click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I perform the "CTRL+Right" arrow keyboard control
    Then "next" frame should be displayed
    When I perform the "CTRL+Left" arrow keyboard control
    Then "previous" frame should be displayed
    When I change the layout to "4:1"
    And click on viewport 0
    And click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I double click on viewport 0
    And perform the "CTRL+Right" arrow keyboard control
    Then "next" frame should be displayed
    When I perform the "CTRL+Left" arrow keyboard control
    Then "previous" frame should be displayed

  @43224_4
  Scenario: Verify frame traversing functionality using combination of viewport icon and keyboard controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    And I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing
    When I perform the "CTRL+Right" arrow keyboard control
    And "next" frame should be displayed
    When I perform the "CTRL+Left" arrow keyboard control
    Then "previous" frame should be displayed
    When I change the layout to "4:1"
    And click on viewport 0
    And I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing
    When I double click on viewport 0
    And perform the "CTRL+Right" arrow keyboard control
    Then "next" frame should be displayed
    When I perform the "CTRL+Left" arrow keyboard control
    Then "previous" frame should be displayed

  @43224_5
  Scenario: Verify frame traversing functionality using combination of viewport frame controls and  keyboard controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And click on viewport 0
    And go to image number 2
    And click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I click on the "Next frame" button on slider
    And "next" frame should be displayed
    When I click on the "Previous frame" button on slider
    Then "previous" frame should be displayed
    When I change the layout to "4:1"
    And click on viewport 0
    And click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I double click on viewport 0
    And I click on the "Next frame" button on slider
    Then "next" frame should be displayed
    When I click on the "Previous frame" button on slider
    Then "previous" frame should be displayed

  @43224_6
  Scenario: Verify frame traversing functionality using viewport controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And click on viewport 0
    And go to image number 2
    And I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing
    When I click on the "Next frame" button on slider
    Then "next" frame should be displayed
    When I click on the "Previous frame" button on slider
    Then "previous" frame should be displayed
    When I change the layout to "4:1"
    And click on viewport 0
    And I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing
    When I double click on viewport 0
    And I click on the "Next frame" button on slider
    Then "next" frame should be displayed
    When I click on the "Previous frame" button on slider
    Then "previous" frame should be displayed

  @43224_7
  Scenario: Verify frame traversing functionality using combination of mouse scroll and viewport icons
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 2
    And I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing
    When I scroll the mouse wheel "downwards" for 1 time(s) in "1:1" layout
    And "next" frame should be displayed
    When I scroll the mouse wheel "upwards" for 1 time(s) in "1:1" layout
    Then "previous" frame should be displayed
    When I change the layout to "4:1"
    And click on viewport 0
    And I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing
    When I double click on viewport 0
    And scroll the mouse wheel "downwards" for 1 time(s) in "1:1" layout
    Then "next" frame should be displayed
    When I scroll the mouse wheel "upwards" for 1 time(s) in "1:1" layout
    Then "previous" frame should be displayed

  @43224_8
  Scenario: Verify frame traversing functionality using combination of mouse scroll and keyboard controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And click on viewport 0
    And go to image number 2
    And click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I scroll the mouse wheel "downwards" for 1 time(s) in "1:1" layout
    And "next" frame should be displayed
    When I scroll the mouse wheel "upwards" for 1 time(s) in "1:1" layout
    Then "previous" frame should be displayed
    When I change the layout to "4:1"
    And click on viewport 0
    And click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I double click on viewport 0
    And I scroll the mouse wheel "downwards" for 1 time(s) in "1:1" layout
    Then "next" frame should be displayed
    When I scroll the mouse wheel "upwards" for 1 time(s) in "1:1" layout
    Then "previous" frame should be displayed

  @43224_9
  Scenario: Verify zoom functionality using keyboard controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And click on viewport 0
    And go to image number 2
    And click the "spacebar" keyboard button
    Then cine on viewport 0 should "stop" playing
    When I perform the "CTRL+Up" arrow keyboard control
    Then the image should be zoomed in
    When I perform the "CTRL+Down" arrow keyboard control
    Then the image should be zoomed out
    When I change the layout to "4:1"
    And click on viewport 1
    And click the "spacebar" keyboard button
    Then cine on viewport 1 should "stop" playing
    When I perform the "CTRL+Up" arrow keyboard control
    Then the image should be zoomed in
    When I perform the "CTRL+Down" arrow keyboard control
    Then the image should be zoomed out

  @43224_10
  Scenario: Verify zoom functionality using combination of viewport icon and  keyboard controls
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And click on viewport 0
    And go to image number 2
    And I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing
    When I perform the "CTRL+Up" arrow keyboard control
    Then the image should be zoomed in
    When I perform the "CTRL+Down" arrow keyboard control
    Then the image should be zoomed out
    When I change the layout to "4:1"
    And click on viewport 1
    And I click on "Stop" button in viewport 1 to stop the cineloop
    Then cine on viewport 1 should "stop" playing
    When I perform the "CTRL+Up" arrow keyboard control
    Then the image should be zoomed in
    When I perform the "CTRL+Down" arrow keyboard control
    Then the image should be zoomed out

  @43224_11
  Scenario: Verify that user is not able to change frame without pausing the cine
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ODIS" and last name as "HYATT"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And click on viewport 0
    And go to image number 2
    And I perform the "CTRL+Right" arrow keyboard control without pausing cine
    Then frame should not get changed
    When I perform the "CTRL+Left" arrow keyboard control without pausing cine
    Then frame should not get changed
    When I click on the "Next frame" button on slider without pausing cine
    Then frame should not get changed
    When I click on the "Previous frame" button on slider without pausing cine
    Then frame should not get changed

  @RevertUnselectedAutostartPlayback
  @43224_12
  Scenario: Verify cine play/pause functionality with keyboard and viewport controls after disabling Auto-start
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ODIS" and last name as "HYATT"
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
    Then cineloop "should not" start playing automatically
    When I click the "spacebar" keyboard button
    Then cine on viewport 0 should "resume" playing
    When I click the "spacebar" keyboard button again
    Then cine on viewport 0 should "stop" playing
    When I close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    Then cineloop "should not" start playing automatically
    When I click on "play" button in viewport 0 to start the cineloop
    Then cine on viewport 0 should "resume" playing
    When I click on "Stop" button in viewport 0 to stop the cineloop
    Then cine on viewport 0 should "stop" playing