package EnterpriseArchitecture.SQLExportToCSV;

import org.springframework.util.StreamUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDataAccessObject
{
	//Properties
	public String FileLocation;
	public String Data;

	//Empty Constructor 
	public FileDataAccessObject()
	{
	}
	
	//Constructor 1
	public FileDataAccessObject(String pFileLocation)
	{
		this.FileLocation = pFileLocation;
	}
	
	//Constructor 2
	public FileDataAccessObject(String pFileLocation, String pData)
	{
		this.FileLocation = pFileLocation;
		this.Data = pData;
	}

	public String GetDataFromFileLocation()
	{
		try 
		{
			FileInputStream in = new FileInputStream(FileLocation);		
			this.Data = new String(StreamUtils.copyToByteArray(in));
			in.close();
        } 
		catch (IOException e)
		{
            e.printStackTrace();
        }
		
		return this.Data;
	}

	public void WriteDataToFileLocation()
	{
		File file = new File(FileLocation);
		try (FileOutputStream fop = new FileOutputStream(file)) 
		{
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = Data.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done Writing File");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
