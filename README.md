# EveryOneRich JSP Web Application

----------------------------------

## How to run the project locally:

#### 1) Get the code from git:
- Option 1: Download sourcetree: http://www.sourcetreeapp.com/
- Option 2: Command line
  - Open up a terminal and run ‘git clone https://github.com/sunbryely/Team_USA.git’ from the directory
    where you wish to download the project to.

#### 2) Install Eclipse Kepler (ieng6 machines already have Eclipse kepler open)

#### 3) Open Eclipse and import Project into Eclipse
- In Eclipse go to Window->Show View->Project Explorer
- Right Click on a blank space in the project explorer
- Select “Import...”
- Choose General->Existing projects
- Choose the directory where you placed the code you pulled from git

#### 4) Configure the server
- In Eclipse go to the Window -> Preferences -> Server -> Runtime Environments
- Click the “Add” button and choose Apache -> Apache Tomcat v7.0
- Click next and click the “Download and Install...” button, accept the terms,
  click next and choose your home directory for the installation location.
- Choose jdk1.8.0_05 as JRE
- Click “Finish”

#### 5) Run as... server

> ##### Note:
You may have to make sure that the classpath and build paths are correct in your eclipse settings.
Also make sure that your eclipse has the correct path to your local jdk file in the build path settings.

----------------------------------

## Database Information

#### Database Language: MySQL

#### Database Settings:
- Database Hostname:              **104.131.153.77**
- Database Port:                  **3306**
- Database Username:              **dbConUser**
- Database Password:              **w31rdUn1c@rn5**
- Connection URL:                 **jdbc:mysql://104.131.153.77:3306/teamusa**
- Database Name (Default Schema): **teamusa**

#### Download the MySQL workbench for editing/viewing the databse:
- Link to download: [http://www.mysql.com/products/workbench/](http://www.mysql.com/products/workbench/)
- After you have downloaded MySQL workbench you can connect to our database by clicking on "New Connection"
  and entering all the database information.
- Make sure to click on "Test Connection" before saving to make
  sure you entered all the info correctly.
- It should connect automatically and you should be able to view the Tables/Data.

#### View Database from Eclipse:
- Click on Window->Show View->Data Source Explorer
- Right Click on "Database Connections" and choose "New..."
- In the dialog that pops up choose "MySQL" then click next.
- On the next page, click the button immedieately to the right of the Driver Definitions dropdown
- Under the "Name/Type" tab select MySQL JDBC Driver version 5.1
- Select the "Jar List" tab and click the "Clear All" button then click the "Add Jar/Zip..." button
- In the choose file dialog navigate to where you saved our project then navigate to: 
  WebContent->WEB-INF->lib and choose mysql-connector-java-5.1.29-bin.jar then click "OK".
- Now fill all the form fields using the database info that is entered above.
- Click "Test Connection" and you should get a message saying "Ping Succeeded" (if you don't get this
  message double check and make sure you entered all the database info correctly).
- Click "Finish" then you should automatically connect to the database and  you should be able to 
  view the Tables/Data.

----------------------------------