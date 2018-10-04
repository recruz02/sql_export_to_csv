package EnterpriseArchitecture.SQLExportToCSV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.context.annotation.ImportResource;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

@SpringBootApplication
@ImportResource("classpath:datasources.xml")	//defaults to src/main/resources directory
public class Application implements CommandLineRunner {

	//local class objects
    @Autowired JdbcTemplate CLEAR_PROD;	
	
	@Value("${InputFileLocation}") 
	private String InputFileLocation;
	
	@Value("${OutputFileLocation}") 
	private String OutputFileLocation;

	@Value("${ColumnsToExport}") 
	private String ColumnsToExport;
	
	@Value("${IncludeHeaders}") 
	private Boolean IncludeHeaders;	
	
	@Value("${DateTimeFormat}") 
	private String DateTimeFormat;	
	
	private String SQLQuery;
	private String ResultData;
    private static final Logger log = LoggerFactory.getLogger(Application.class);	
	
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);		
    }
	
    @Override
	public void run(String... strings) throws Exception {			
		//1) Get SQL Query
		String myQuery = new FileDataAccessObject(InputFileLocation).GetDataFromFileLocation();
		
		//2) Execute Query
		String resultData = this.executeQuery(myQuery, ColumnsToExport);
		
		//3) Add Header Row Information
		if (IncludeHeaders)
			resultData = this.addHeaderRow(resultData, ColumnsToExport);

		//4) Add DateTimeStamp
		if (DateTimeFormat.length()>0)
			OutputFileLocation = this.addDateTimeStamp(OutputFileLocation, DateTimeFormat);
		
		//5) Write Data to File
		new FileDataAccessObject(OutputFileLocation, resultData).WriteDataToFileLocation();
	}

	private String executeQuery(String pQuery, String pColumns)
	{
		String sOutput = "";
		
		try
		{				
			String[] columns = pColumns.split(",");
			SqlRowSet srs = CLEAR_PROD.queryForRowSet(pQuery);		
			while (srs.next()) 
			{
				for (int i=0; i<columns.length; i++)
					sOutput += srs.getString(columns[i]) + ",";
				sOutput = sOutput.replaceAll(",$", "");		//remove trailing comma
				sOutput += System.lineSeparator();			//add new line
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());	
		}
		
		return sOutput;
	}
	
	private String addHeaderRow(String pData, String pHeaderRow)
	{
		return pHeaderRow + System.lineSeparator() + pData;
	}
	
	private String addDateTimeStamp(String pFileLocation, String pDateTimeFormat)
	{
		String returnValue = "";
		
		try
		{
			String fileExtension = "." + this.getFileExtension(pFileLocation);
			SimpleDateFormat dateFormatter = new SimpleDateFormat(pDateTimeFormat);
			returnValue = pFileLocation.replace(fileExtension, dateFormatter.format(new Date()) + fileExtension);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return returnValue;
	}
	
	private String getFileExtension(String pFilename)
	{
		try 
		{
			return pFilename.substring(pFilename.lastIndexOf(".") + 1);
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
			return "";
		}
	}	
}
