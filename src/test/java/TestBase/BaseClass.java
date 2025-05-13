package TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.net.URI;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass 
{
	
	public static WebDriver driver;
	public Logger logger;
	public Properties prop;
	
	@BeforeClass(groups = {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException
	{
		//loading config.properties file//we can use anyone of the following to load the file.
		prop = new Properties();
		FileInputStream fip = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Config.properties");
//		FileReader file = new FileReader("./src//test//resources//config.properties");
		prop.load(fip);
		
		logger = LogManager.getLogger(this.getClass());//will get the class name and get the logger for that particular class and store in the logger variable.
		
		
		//if the execution environment is remote, then do the following steps		
		
		if(prop.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//we get the os and the browser from the xml. so we should use the same os and browser.
			
			//Operating system
			if(os.equalsIgnoreCase("Windows"))
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("Mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("Linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else
			{
				System.out.println("Matching not found");
				return;
			}
			
			//Browser
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "firefox" : capabilities.setBrowserName("firefox"); break;
			default : System.out.println("No match found"); return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444"), capabilities);
		}
			
		
		//if the execution environment is local, then do the following steps	
		
		if(prop.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome"  : driver = new ChromeDriver();
							 break;
			case "firefox" : driver = new FirefoxDriver();
							 break;
			default        : System.out.println("Invalid browser name");
							 return;
			}
		}			
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
//		driver.get("https://tutorialsninja.com/demo/");
		driver.get(prop.getProperty("appurl"));//reading url from prop file
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@AfterClass(groups = {"Sanity", "Regression", "Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomAlphabetic(int stringLength)
	{
		 RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('A', 'z').filteredBy(CharacterPredicates.LETTERS).build();
		 String randomLetters = generator.generate(stringLength);
		 String a = randomLetters.substring(0,1).toUpperCase();
		 String b = randomLetters.substring(1);
		 return a+b;
	}
	
	public String randomNumeric(int numberLength)
	{
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '9').filteredBy(CharacterPredicates.DIGITS).build();
		 String randomNumbers = generator.generate(numberLength);
		 return randomNumbers;
	}
	
	public String randomAlphaNumeric(int stringLength)
	{
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
		 String randomAlphaNumbers = generator.generate(stringLength);
		 return randomAlphaNumbers;
	}

	public String captureScreen(String tname) //tname is the same as the test method name
	{
		/*
		   SimpleDateFormat df = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss");
	  	   Date dt = new Date();
		   String timeStamp1 = df.format(dt);
		 */
		
		String timeStamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFile = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		
		File targetFilePath = new File(targetFile);

		sourceFile.renameTo(targetFilePath);

		return targetFile;
	}

}
