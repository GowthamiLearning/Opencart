package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import TestBase.BaseClass;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass
{
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven")
	public void verify_LoginDDT(String email, String password, String exp)
	{
		try
		{
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(password);
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage myacc = new MyAccountPage(driver);
		boolean targetPage = myacc.isMyAccountPageExists();
		
		
		/*
		 Data is Valid  ----  Login Success  ---  Test Pass  --- logout
		 						Login unsuccess  --  Test Fail
		 						
		 						
		 Data Is Invalid   ----   Login Success   ----  Test Fail   --- logout
		 							Login unsuccess  --   Test Pass
		 */
		
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage == true)
			{
				myacc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage == true)
			{
				myacc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
	 }
		catch(Exception e)
		{
			Assert.fail();
		}
		
  }

}
