package pages;

import org.openqa.selenium.By;

import utils.BaseLogger;
import utils.SeleniumUtilities;

public class LaunchNetSense {

	String STP2 = "https://hic011168/mas/";
	//String STP2= "https://rally1.rallydev.com/slm/login.op";
	String pegaURL;
	String envPath;
	BaseLogger blogger = new BaseLogger();

	public String getPegaURL() {
		return pegaURL;
	}

	public void setPegaURL(String pegaURL) {
		this.pegaURL = pegaURL;
	}

	public void launchPega(String env, String browser) throws InterruptedException {
		if (browser.equalsIgnoreCase("ie")) {
			if (env.equalsIgnoreCase("STP2"))
				this.setPegaURL(STP2);
			try {
				Driver.getPgDriver().get(this.getPegaURL());
				Driver.getPgDriver().manage().window().maximize();
				String str = Driver.getPgDriver().getCurrentUrl();
				System.out.println("The current URL is " + str);
				Driver.pgDriver.findElement(By.linkText("More information")).click();
				Driver.pgDriver.findElement(By.linkText("Go on to the webpage (not recommended)")).click();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				blogger.logserviceDownForBrowserNotInvoked();
			}
		} else if (env.equalsIgnoreCase("STP2"))
			this.setPegaURL(STP2);
		try {
			Driver.getPgDriver().get(this.getPegaURL());
			Driver.getPgDriver().manage().window().maximize();
			String str = Driver.getPgDriver().getCurrentUrl();
			System.out.println("The current URL is " + str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			blogger.logserviceDownForBrowserNotInvoked();
		}
	}

	public String getEnvpath(String env) {
		return envPath;
	}

}
