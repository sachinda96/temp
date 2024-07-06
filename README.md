\#\# SOUL Web API

## Installation

### Server Requirements

Java

1. Download java jdk 8 and install it.

2. setup enviorment JAVA_HOME and JAVA_JRE

	JAVA_HOME : "C:\Program Files\Java\jdk1.8.0_221"
	JAVA_JRE  : "C:\Program Files\Java\jre1.8.0_221"
	
3. Java configuration finish.	

Netbeans

1. if netbeans not install please install first. (current version 8.2)

Glassfish

1. if glassfish server is not install please install first. (current version 4.1.1)

Wamp server

1. if wamp server is not install please install first. (any latest version)

2. open phpmyadmin for mysql and import soul database.

Maven

1. download maven package form site : https://maven.apache.org/download.cgi
	package name : apache-maven-3.6.3-bin.zip
	version : 3.6.3 (latest as of now - 04-12-2020)

2. unzip it and paste that package at desire location most recomended is C:\
	
3. open netbeans go to tool menu > Options > select "java" tab > select "Maven" tab then select path of maven package "C:\apache-maven-3.6.3" 

4. netbeans detect mavne now click on ok (maven configuration finish).



## Instruction

First time: To setup SOUL web api (One time only process)

Use cmd or Netbeans to clone soulweb from SOUL Git.

`git clone http://172.16.0.155:3000/superadmin/soulweb.git`


1. In netbeans left hand side menu select "server" tab. > select "Database" and right click > New connection > select mysql > setup db connection with mysql.

2. Select project then right click on project > click on new > then other.. and search "persistence Unit" and click next. Now Click on Data Source dropdown and click on "New Data Source" then type JNDI name "soul" and select mysql connection from dropdown (created in step 1) then ok and finish.

3. Two file created in project "persistence.xml" and "glassfish-resources.xml" in project if not created please again follow the 3,4 step.

4. After successful done all above steups. Right click on project and select build with dependencies (project will download all required jar file from maven repository).

5. After successful build again Right click on project and hit run (project will deployed on server and strat it).


To run SOUL web api: (Local Development Server)

1. Right click on project and hit run. (this will start the server and deploy the SOUL web api on it).