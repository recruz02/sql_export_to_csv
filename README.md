# MS SQL Export to CSV

This is a stand-alone Java SpringBoot application that can read a single query SQL file, execute the query against the desired MS SQL database and export the result set to a CSV file. This application can be scheduled execute at any desired interval.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

The server will need the following software and technical capabilities to allow the jar file to execute:

* Java Runtime Environment (JRE) 1.7
* Access to folders your application will push and pull files from
* Access to a SQL Database
* A scheduler to run the application (e.g., Task Scheduler on Windows)

## Running Unit Tests

Unit tests can be completed via the Maven Build Tool using the SureFire Plugin. Executing the unit tests with maven is as simple as:
```
C:\github\sql_export-to-csv>mvn test
```

Sample Output:
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running EnterpriseArchitecture.SQLExportToCSV.ApplicationTest
THIS IS ApplicationTest #1
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.006 sec - in EnterpriseArchitecture.SQLExportToCSV.ApplicationTest
Running EnterpriseArchitecture.SQLExportToCSV.FileDataAccessObjectTest
THIS IS TEST #1
Attempting to read pom.xml...
1874
THIS IS TEST #2
Attempting to WRITE test data ...
Done Writing File
Attempting to READ test data ...
47
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.004 sec - in EnterpriseArchitecture.SQLExportToCSV.FileDataAccessObjectTest

Results :

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```

### Deployment

1) Create a folder location for a java executable
2) Create a folder location for a MS SQL script
3) Create a folder location for a CSV export file
4) Create a folder location for a batch script
5) Find the compiled java application runtime (jar) file and copy it to the java folder on your server.
6) Generate your desired MS SQL query and save it to your MS SQL script folder location. Your query should only have one result set and each of your returned columns should be aliased.

Sample sql file:
```
SELECT 
	QUOTENAME(CONVERT(VARCHAR(MAX), [ITEMID]), CHAR(34)) AS ApplicationGUID,
	QUOTENAME(app.[Name],CHAR(34)) AS ApplicationName,
	QUOTENAME(app.[Short Name],CHAR(34)) AS ApplicationShortName
FROM
	[IT: Application] app
ORDER BY
	ApplicationName ASC
```
7) Generate your desired batch file that can be scheduled to execute. This batch file will contain your command line execution and parameters for the java executable. The java executable requires five (5) parameters:
* **InputFileLocation** - Location and name of the MS SQL script to read from
* **OutputFileLocation** - Location and name of the file to export the data to
* **ColumnsToExport** - Comma delimited list of the columns that should be output. These columns should match the aliased columns in the MS SQL script
* **IncludeHeaders** - true/false - true will include the ColumnsToExport as the first line in the export
* **DateTimeFormat** - This field utilizes the Java7 SimpleDateFormat object. This parameter accepts the same patterns that are available for this class object:  https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html

Sample bat file:
```
d:
cd D:\BusinessProtector\java
java.exe -jar EnterpriseArchitecture.SQLExportToCSV-1.0.0.0.jar --InputFileLocation=D:\BusinessProtector\sql\myQuery.sql --OutputFileLocation=D:\BusinessProtector\output.csv --ColumnsToExport=ApplicationGUID,ApplicationName,ApplicationShortName --IncludeHeaders=true --DateTimeFormat=_yyyy-MM-dd'_'HH-mm-ss
```
8) Configure your scheduler to execute the bat file

### Execution

Java:
```
java.exe -jar EnterpriseArchitecture.SQLExportToCSV-1.0.0.0.jar --InputFileLocation=D:\BusinessProtector\sql\myQuery.sql --OutputFileLocation=D:\BusinessProtector\output.csv --ColumnsToExport=ApplicationGUID,ApplicationName,ApplicationShortName --IncludeHeaders=true --DateTimeFormat=_yyyy-MM-dd'_'HH-mm-ss
```
Maven:
```
mvn exec:java -Dexec.args="--InputFileLocation=\\<SERVERNAME>>\BusinessProtector\BusinessProtectorAppServerDataExtract.script.sql --OutputFileLocation=\\<<SERVERNAME>>\BusinessProtector\TEST_PCITG.ZYONT.N0010278.ClearApps.csv --ColumnsToExport=ApplicationGUID,ApplicationName,ApplicationShortName --IncludeHeaders=true --DateTimeFormat=_yyyy-MM-dd'_'HH-mm-ss"
```

## Built With

* [JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) - Java Development Kit 7
* [Spring Framework](https://projects.spring.io/spring-framework/) - Java Application Framework
* [SpringBoot](https://spring.io/guides/gs/spring-boot/) - Java Application Template
* [Maven](https://maven.apache.org/) - Dependency Management and Build Tool
* [JUnit](https://junit.org/) - Unit Testing Tool

## Contributing

n/a

## Versioning

Version 0.5.0.0

## Authors

* **Rob Cruz** - *Initial work*

