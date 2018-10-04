d:
cd D:\java\EnterpriseArchitecture.SQLExportToCSV-1.0.0.0


SET INPUTFILE=\\<<SERVERNAME>>\sql\BusinessProtectorAppServerDataExtract.script.sql
SET OUTPUTFILE=\\<<SERVERNAME>>\Destination\PCITG.ZYONT.N0010278.ClearApps.csv


java.exe -jar target/EnterpriseArchitecture.SQLExportToCSV-1.0.0.0.jar --InputFileLocation=%INPUTFILE% --OutputFileLocation=%OUTPUTFILE% --ColumnsToExport=ApplicationGUID,ApplicationName,ApplicationShortName --IncludeHeaders=true --DateTimeFormat=_yyyy-MM-dd'_'HH-mm-ss
