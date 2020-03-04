package pages;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import extentmanager.ExtentManager;
import stepdefinition.stepdefinition;
import utils.ErrorLogger;
//import utils.SeleniumCustomServlets;
import utils.Utilities;



public class Driver {

	public static WebDriver pgDriver;
	public static String drType;
	WebDriverWait wait;
	static ErrorLogger err = new ErrorLogger();
	
	//static ExtentManager extentmanager = new ExtentManager();
	
	//public static SeleniumCustomServlets customServlet = null;
	static Utilities comnutils;
	
	//static WebDriver ieDriver;

	public static WebDriver getPgDriver() {
		return pgDriver;
	}

	static int count=0;
	//public static Object customServlet;
	public static void setPgDriver(String brsrType, String runtype,String ip ) throws Exception {	
		if(runtype.equalsIgnoreCase("Local")){
			if(brsrType.equalsIgnoreCase("chrome")){
				//File file = new File("C:\\Drivers\\chromedriver.exe");
				File file = new File("Webdrivers//chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				System.setProperty("webdriver.chrome.driver","Webdrivers//chromedriver.exe" );
				//WebDriver chromeDriver = new ChromeDriver();
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				WebDriver chromeDriver = new ChromeDriver(options);
				pgDriver = chromeDriver;
				drType = "chrome";
				//Thread.sleep(5000);
			} else if(brsrType.equalsIgnoreCase("firefox")){
				WebDriver fireFoxDriver = new FirefoxDriver();
				pgDriver = fireFoxDriver;
				drType = "firefox";
			} else if (brsrType.equalsIgnoreCase("ie")){
				File file = new File("Webdrivers//IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				System.out.println("Path: "+file.getAbsolutePath());
				System.setProperty("webdriver.ie.driver", "Webdrivers//IEDriverServer.exe");
							
				try{
					
					/*DesiredCapabilities capabilities = new DesiredCapabilities();
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);*/
					WebDriver ieDriver = new InternetExplorerDriver();
					//Thread.sleep(3000);
					/*ieDriver.findElement(By.linkText("More information")).click();
					ieDriver.findElement(By.linkText("Go on to the webpage (not recommended)")).click();*/
					pgDriver = ieDriver;
					drType = "ie";
				}
				catch(Exception e)
				{
					count++;
					err.logError("Exception occurred: "+e);
					try
					{
					//Runtime.getRuntime().exec("taskkill /im IEDriverServer.exe /f");
					//Runtime.getRuntime().exec("taskkill /im iexplore.exe /f");
					}catch(Exception e1){
						
						System.out.println("Exception: "+e1);
					}
					drType = "ie";
					if(count<=3) 
						setPgDriver(brsrType,  runtype, ip);	
			}
		}
		}

		else if (runtype.equals("Grid"))
		{

			if(brsrType.equalsIgnoreCase("chrome")){
				try {
					DesiredCapabilities capabilities = DesiredCapabilities.chrome();
					pgDriver = new RemoteWebDriver(new URL("http://" + ip + "/wd/hub"), capabilities);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					stepdefinition.isServicedown=true;
					//extentmanager.ExtentManager.setTeststatus("Warning");
					//err.logremoteNotconnectedError(e);
					
					e.printStackTrace();
				}
			}

			if(brsrType.equalsIgnoreCase("ie")){
				try {
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability("ie.ensureCleanSession", true);
					pgDriver = new RemoteWebDriver(new URL("http://" + ip + "/wd/hub"), capabilities);
					Thread.sleep(1000);
				} catch (NoSuchSessionException e) {
					// TODO Auto-generated catch block
				
				count++;
				if(count<=3){
					
					setPgDriver(brsrType, runtype,ip );
					
				/*DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability("ie.ensureCleanSession", true);
				pgDriver = new RemoteWebDriver(new URL("http://" + ip + "/wd/hub"), capabilities);
				*/
					stepdefinition.logger.info("Trying" +count+"time");
				}
				
				else{
					stepdefinition.isServicedown=true;
				//extentmanager.ExtentManager.setTeststatus("Warning-Browser Issue");
				//err.logremoteNotconnectedError(e);
				}
				//pgDriver.close();
				//pgDriver.quit();
				//throw new CustomException(err.getErrormessage(),stepdefinition.isServicedown,stepdefinition.r,logger,mongotest);
					e.printStackTrace();
				}
			}



			//DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			//capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			//capabilities.setCapability(ChromeDriver. value);

			//capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			//pgDriver = new RemoteWebDriver(new URL("http://30.138.83.108:4444/wd/hub"), capabilities);
			//http://30.138.156.136:4444/wd/hub

		}

		else if (runtype.equals("Mobile")){
			File file = new File("webdrivers//chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver","webdrivers//chromedriver.exe" );

			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Nexus 5");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			WebDriver driver = new ChromeDriver(chromeOptions);						
			pgDriver = driver;
			drType = "chrome";

		}
		else if (runtype.equals("API")){
			System.out.println("API");
			// Declaring and initialising the HtmlUnitWebDriver
			HtmlUnitDriver unitDriver = new HtmlUnitDriver();
			pgDriver=unitDriver;

		}	
		


	}
	public static void setPgDriver(String brsrType) throws Exception {		
		if(brsrType.equalsIgnoreCase("chrome")){
			//File file = new File("C:\\Drivers\\chromedriver.exe");
			File file = new File("Webdrivers//chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver","Webdrivers//chromedriver.exe" );

			//WebDriver chromeDriver = new ChromeDriver();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			WebDriver chromeDriver = new ChromeDriver(options);
			pgDriver = chromeDriver;
			drType = "chrome";
			//Thread.sleep(5000);
		} else if(brsrType.equalsIgnoreCase("firefox")){
			WebDriver fireFoxDriver = new FirefoxDriver();
			pgDriver = fireFoxDriver;
			drType = "firefox";
		} else if (brsrType.equalsIgnoreCase("ie")){
			//File file = new File("C:\\SolutionCentralEngine\\IEDriverServer.exe");
			File file = new File("Webdrivers//IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			try{
			WebDriver ieDriver = new InternetExplorerDriver();
			pgDriver = ieDriver;
			drType = "ie";
			}
			catch(Exception e)
			{
				WebDriver ieDriver = new InternetExplorerDriver();
				pgDriver = ieDriver;
				drType = "ie";
			}
		}

		else if (brsrType.equals("Remote"))
		{

			//DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			//capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			//capabilities.setCapability(ChromeDriver. value);

			//capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			pgDriver = new RemoteWebDriver(new URL("http://30.142.9.59:4444/wd/hub"), capabilities);
			//http://30.138.156.136:4444/wd/hub

		}


	}

	
	public static void killCloseDriver(){
		pgDriver.close();

		//pgDriver.quit();
		if (drType.equalsIgnoreCase("chrome")){

			WindowsUtils.killByName("chromedriver.exe");
		}else if(drType.equalsIgnoreCase("ie")){	
			try{
			WindowsUtils.killByName("IEDriverServer.exe");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		else {
			//System.exit(0);
		}

	}

	public static void setUrl(String url) throws Exception {	
		Driver.getPgDriver().get(url);
		Driver.getPgDriver().manage().window().maximize();
		String str = Driver.getPgDriver().getCurrentUrl();
		System.out.println("The current URL is " + str);
	}



}