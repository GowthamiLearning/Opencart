package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path = ".\\testData\\LoginTestData.xlsx";//xl sheet path
		
		ExcelUtility xlutil = new ExcelUtility(path);//creating object for excel utility to invoke the excel utility methods
		
		int totalRows = xlutil.getRowCount("Sheet1");//gives total num of rows
		int totalCols = xlutil.getCellCount("Sheet1", 1);// gives total num of columns
		
		String logindata [][] = new String [totalRows][totalCols];
		
		//the for loop will read the data from the xl sheet and copy into 2D Array
		for(int r=1; r<totalRows; r++)//rows start from 1, as we ignore the header
		{
			for(int c=0; c<totalCols; c++)//columns start from 0
			{
				logindata[r-1][c] = xlutil.getCellData("Sheet1", r, c);//As array index starts from 0, in excel line starts from 1.so 'r-1'
			}
		}
		
		return logindata;
		
		
	}

}
