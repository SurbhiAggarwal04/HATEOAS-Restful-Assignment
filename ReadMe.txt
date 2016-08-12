STEPS TO RUN THE ASSIGNMENT

1. Extract the zip folder "HATEOAS-Appeals-saggarw9-Eclipse" and you will see below the three components:
1. HATEOAS-Appeals-saggarw9-Eclipse-Server
2. HATEOAS-Appeals-saggarw9-Eclipse-Client
3. ReadMe.txt file
4. Surbhi_Diagrams.docx

2. Import both the projects into Eclipse.
   Check the following things after importing the project.
   1. The JRE JDK version is 1.8 and the apache tomcat version is Tomcat 8.
   2. Go to Properties of both the projects:
          1. Go to Deployment Assembly, and check if Maven dependencies is there and if it's not there, Go to Add->Java Build path Entries->Maven Dependencies.
          2. Go to Java Build Path, check if JRE 1.8 is there, if not, Go to Libraries-> Remove any other jre present->Add Library->JRE System Library.
          3. Go to Java Compiler-> Select Compiler Compliance Level : 1.8
          4. Go to Project facets, it should have Java 1.8 and Runtimes should have Tomcat version 8 checked.

2. First Run "HATEOAS-Appeals-saggarw9-Eclipse-Server" on Server. A page is displayed which confirms "Server Started".

3. Now Run "HATEOAS-Appeals-saggarw9-Eclipse-Client" on Server.
 
4. Functionality:

1. Happy Case 
Create appeals : Create the appeals
Get Appeals: Get the created appeals
Update appeals : Update the appeal items
Submit appeals : Submit appeals to professor and Change the status of appeals from "PREPARING" to "READY"
Response: Get the response from the Professor

2. Abandon Case
Create appeals : Create the appeals
Get Appeals: Get the created appeals
Abandon appeals : Delete appeals before submitting it to the professor

3. Forgotten Case
Create appeals : Create the appeals
Get Appeals: Get the created appeals
Submit appeals : Submit appeals to professor and Change the status of appeals from "PREPARING" to "READY"
Followup on appeals : Followup appeals and Change the status of appeals from "READY" to "FOLLOWUP"

4. Bad Start Case
Create appeals : Create the appeal items with bad uri which results in 404.

5. Bad ID Case
Create appeals : Create the appeals
Get Appeals: Get the created appeals
Submit appeals : Submit appeals to professor and Change the status of appeals from "PREPARING" to "READY"
Followup on appeals : Followup appeals with bad uri and thus results in 404.  
  
   