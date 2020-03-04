package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utils.SeleniumUtilities;

public class Login {

	SeleniumUtilities utils = new SeleniumUtilities();
	
	public Login() {
	PageFactory.initElements(new AjaxElementLocatorFactory(Driver.getPgDriver(), 20), this);
	Driver.getPgDriver().switchTo().defaultContent();
	Driver.getPgDriver().switchTo().frame(this.Iframeelement);
	}
	
	@FindBy(name="main")
	WebElement Iframeelement;
	
	@FindBy(name="name")
	WebElement txtBxUsername;
	
	@FindBy(name="password")
	WebElement txtBxPassword;
	
	@FindBy(xpath="//input[@name='login']")
	WebElement btnLogin;
	
	@FindBy(xpath="//input[@id='txtSearch']")
	WebElement txtBxGoogle;
	
	@FindBy(id="j_username")
	WebElement txtBxUserName;
	
	
	public boolean enterUsernameAndPassword(String[] args) throws InterruptedException
	{
		if(utils.enterTextinAnelemnt(txtBxUsername, args[0], "Login", "UserName"));
			if(utils.enterTextinAnelemnt(txtBxPassword, args[1], "Login", "Password"));
			Thread.sleep(2000);
			Driver.pgDriver.findElement(By.xpath("//input[@name='login']")).click();
			return true;
		//return utils.enterTextinAnelemnt(txtBxUserName, args[0], "Login", "UserName");
		
		
	}
	
	
	
	
	
	
	
	
}
