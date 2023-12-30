Feature: OB Consultation Report

@TC_AT-ReportVerification
Scenario: Verify entered details in patient demographics are reflected correctly in PDF and text reports
Given vidistar application is launched
And I have logged in the application as "Admin" user
When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
Then viewer should load the study for patient
When I navigate to "Reports" menu
And go to the "OB-GYN" option
And select the "OB Consultation Final Report" option
Then the Report Editor window should load
When I enter below details in the below fields under "Patient information" tab in "Patient information" section
|field						|value		|
|Weight						|120		|
|Height						|60			|
|BMI						|Select		|
|Heart rate					|66			|
|Respiratory rate			|5			|
|Temperature				|98			|
|Systolic blood pressure	|120		|
|Diastolic blood pressure	|80			|
And I sign the report
And close report viewer
When I right click on "Consultation Final Report" from reports list
And export report as "PDF"
And save the PDF report
And I right click on "Consultation Final Report" from reports list
And export report as "TXT"
And save the TXT report
Then all entered details should be present in PDF and TXT reports