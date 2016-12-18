Munsell-Color-Recognition-App

Read Summary: This app is created to aid geologists or archeologists in finding the true Munsell values of artifacts in their preferred locations through the use of a camera. 

Read Install: To use this app or modify it, please clone or download the project,
and import into android studio. 

Read Authors: Jennifer Benedict Drew University '18, Camille Berger Drew University '18, 
Kent Harris Drew University '18, Brittany Grabowski '18.

Used Services: Google Play Services 

Description of this version: 
When installed on emulator or android device, this app will promp the home screen, which is controlled
by MainActivity.java. This screen will alert you to turn location access on, and will have three buttons:
"Take Picture" "Select from Gallery" and "Calibrate Camera". NOTE: "Calibrate Camera" button does not have any functionality
in this version, even though there is a Calibrate class. NOTE: "Select from Gallery" crashes when an image that was previously taken by the camera is selected, only downloaded or screenshots of images will work for this feature in this version. Upon choosing an image from the preferred method, ImageActivity will be prompted, with the Munsell chip number, a background to reflect this chip, and three buttons along the bottom row. The buttons are "Home" "Submit" and "Save" from left to right in thar order. Home button will take the user back home, Submit button will take user to SubmitForm.java activity. In this activity users have the option to submit the munsell chip, location of picture, and any notes, to a data list, in DataForm.java activity, which can be emailed as a .txt file. NOTE: Upon emailing, the existing data.txt file will be deleted. The third button in ImageActivity is the save button which will take a screenshot of the image and munsell chip and save to a new album titled "photos" in the device's gallery. 

NOTE: although there are two activities Calibrate.java and ImageSelection.java, in this version they are not referenced, but do contain code. ImageSelection.java allows a user to select a certain part of the image to be evaluated, which can then be used to calibrate the camera using actual Munsell Chips. In this version ImageSelection.java does not work as intended and has a few glitches. Calibrate.java and ImageSelection.java are not referenced in this version of the app.
