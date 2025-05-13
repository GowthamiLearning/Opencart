package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import TestBase.BaseClass;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegTest extends BaseClass
{	
	@Test(groups = {"Regression", "Master"})
	public void verify_Account_Reg()
	{
		logger.info("TC001_AccountRegTest started");
		
		try
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickRegister();
		logger.info("clicked on Register link");
		
		AccountRegistrationPage accregpage = new AccountRegistrationPage(driver);
		logger.info("providing the details");
		accregpage.setFirstName(randomAlphabetic(6));
		accregpage.setLastName(randomAlphabetic(6));
		accregpage.setEmail(randomAlphabetic(10)+"@gmail.com");
		accregpage.setTelephone(randomNumeric(10));
		
		String pwd = randomAlphaNumeric(8);
		accregpage.setPassword(pwd);
		accregpage.setConfirmPassword(pwd);
		
		accregpage.setPrivacyPolicy();
		accregpage.clickContinue();
		
		String msg = accregpage.getConfirmationMsg();
		if(msg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed");
			logger.debug("Debug logs");
			Assert.assertTrue(false);
		}
//		Assert.assertEquals(msg, "Your Account Has Been Created!!!");
		}
		catch(Exception e)
		{			
			Assert.fail();
		}
		
		logger.info("Finished");
	}
	
}





