# SeleniumProject
Complete Selenium Automation Framework

#Steps to Import the SeleniumProject

Method 1
1. Navigate to src/test/resources
2. Right click and run the PC_MAC.xml as testNG suite.

Method 2
1. Else, open the project in eclipse
2. Right click on pom.xml on src folder.
3. Select mvn > install

Method 3
1. git clone the project
2. Install Maven in your PC
3. Go to the project src Folder
4. Run command "mvn clean install"

Output Folder
1. After test execution, Navigate to TestOutput folder.
2. Open the emailable-report.html
3. Detailed report of execution is available.

Folder Structure
1. src/main  
--> has the base test methods for browser initialization
--> has the page object classes
    
2. src/test --> has the test cases

3. src/test/resources
--> has the config.properties, InputTestData.xlsx and other drivers.

