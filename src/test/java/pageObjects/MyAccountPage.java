package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage
{
	//Constructor
	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}
	
	//Locators
	@FindBy(xpath = "//h2[text()='My Account']")
	WebElement msgHeader;
	
	@FindBy(xpath = "//div[@class='list-group'] //a[text()='Logout']")
	WebElement lnkLogout;
	
	//Action Methods
	public boolean isMyAccountPageExists()
	{
		try
		{
			return(msgHeader.isDisplayed());
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	public void clickLogout()
	{
		lnkLogout.click();
	}

}
