# SAD_Project4

## **Authors**:

|      Name      |     GTID#    |
|:--------------:|:------------:|
| Kevin Johnson  | kjohnson334  |
| Lamar Phillips | wphillips6   |
| Simon Cartoon  | scartoon3    |
| Tyler Schmitz  | tschmitz3    |
| Kiel Murray    | kmurray34    |

### Setting Up Environment

#### Setting Up Tomcat Server

From a terminal window: cd ~ wget http://mirror.sdunix.com/apache/tomcat/tomcat-7/v7.0.65/bin/apache-tomcat-7.0.65.tar.gz  tar zxvf apache-tomcat-7.0.65.tar.gz

From Eclipse: Go to Help -> Install New Software
 
Make your screen look like below by installing the JST Server Adapters and JST Server Adapters Extensions

![Embedded JST Server Install](<Docs/Images/JSTServerInstall.png?raw=true>)

Go to Window -> Show View -> Others

Type in Servers and select Servers and hit OK.  There should now be a Servers tab at the bottom with the other tabs.  Click the link that says 'new server wizard'.  Make your screen look like below and hit Finish.  You may have to hit the Add link on the side and tell Eclipse where the tomcat instance is you just downloaded.  It should be /home/ubuntu/apache-tomcat-7.0.65

![Embedded Server Install](<Docs/Images/NewServerInstall.png?raw=true>)

Right click on the newly created server and click Start.  It should now say [Started, Synchronized]

#### Setting Up Project 4

1. Unzip the archive SAD_Project4.zip into your workspace. This will create a directory called SAD_Project4 and several subdirectories.
2. In Eclipse, select File->Import->General->Existing Projects into Workspace
3. Click *Next*
4. Select SAD_Project4 as root directory
5. Make sure that (1) *SAD_Project4* is selected under *Projects:* and (2) *Copy projects into workspace* is not selected
6. Click *Finish*

#### Setting BuildPath and Environment Variables

Once the SAD_Project4 is loaded into Eclipse and the Tomcat Server is installed you will need to set the Enviornment Variables for the Tomcat server to use.

To accomplish this you will need to right click on the project and select *Run As -> Run Configurations...* Select the drop down arrow next to the Apache Tomcat on the left side and then select the Tomcat v7.0 Server at localhost. You will then proceed to select the ClassPath tab on the right and select *User Entries* and *Add External Jars..* button.
You will need to add the two jars shown in the image below: mysql-connector-java-5.1.16.jar (/usr/share/java/) and gurobi.jar (/home/ubuntu/opt/gurobi605/linux64/lib/)

![Embedded Classpath Settings](<Docs/Images/ServerClassPath.png?raw=true>)

Apply the changes then select the Environment tab.
You will need to add the two variables shown in the image below. GRB_LICENSE_FILE (Note this needs to point to the actual file path of the Gurobi license) & LD_LIBRARY_PATH (/home/ubuntu/opt/gurobi605/linux64/lib)

![Embedded Classpath Settings](<Docs/Images/ServerEnvironment.png?raw=true>)

Apply the changes and close the window.

#### Running the Web Application

Verify that the server is started first then from the main menu select *Run->Run As->Run on Server*

You can now open a FireFox browser and enter the web address: localhost:8080/SAD_Project4/ and go.

