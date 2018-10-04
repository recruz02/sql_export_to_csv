package EnterpriseArchitecture.SQLExportToCSV;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FileDataAccessObjectTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FileDataAccessObjectTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FileDataAccessObjectTest.class );
    }
	
    /**
     * Rigourous Test :-)
     */
    public void testApp1()
    {
		System.out.println("THIS IS TEST #1");
		System.out.println("Attempting to read pom.xml...");
		FileDataAccessObject fdao = new FileDataAccessObject("pom.xml");
		String myString = fdao.GetDataFromFileLocation();
		System.out.println(myString.length());
		assertTrue(myString.length()>0);
    }
	
    /**
     * Rigourous Test :-)
     */
    public void testApp2()
    {
		System.out.println("THIS IS TEST #2");
		System.out.println("Attempting to WRITE test data ...");		
		FileDataAccessObject fdao = new FileDataAccessObject("target/test/unitTestTemp.txt", "THIS IS TEST DATA THAT WILL BE IN THE TEST FILE");
		fdao.WriteDataToFileLocation();
		
		System.out.println("Attempting to READ test data ...");
		fdao = new FileDataAccessObject("target/test/unitTestTemp.txt");
		String myString = fdao.GetDataFromFileLocation();
		System.out.println(myString.length());
		assertTrue(myString.length()>0);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp3()
    {
		/*
		System.out.println("THIS IS TEST #3");
		System.out.println("Attempting to read non-existent file...");
		FileDataAccessObject fdao = new FileDataAccessObject("");			
		String myString = fdao.GetDataFromFileLocation();
		assertTrue(myString.length()==0);
		*/
    }
}


/*
public class FileWriterTest {

    
	private FileWriter fileWriter = new FileWriter();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsErrorWhenTargetFileExists() throws IOException {
        // arrange
        File output = temporaryFolder.newFile("output.txt");

        thrown.expect(IOException.class);
        thrown.expectMessage("file already exists");

        // act
        fileWriter.writeTo(output.getPath(), "test");
    }

    @Test
    public void writesContentToFile() throws IOException {
        // arrange
        File output = temporaryFolder.newFolder("reports")
                .toPath()
                .resolve("output.txt")
                .toFile();

        // act
        fileWriter.writeTo(output.getPath(), "test");

        // assert
        assertThat(output)
                .hasContent("test")
                .hasExtension("txt")
                .hasParent(resolvePath("reports"));
    }

    private String resolvePath(String folder) {
        return temporaryFolder
                .getRoot().toPath()
                .resolve(folder)
                .toString();
    }
}
*/