package uat;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Base implements UatBase {

// later we need to implement through excel or through property file
	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static FileReader fr;

	// String URL = "https://release.ilearningengines.com/login";
	// String URL = "https://www.google.com";

	// wait
	WebDriverWait wait = null;

	protected ExtentReports extent;

	@BeforeSuite
	public void startApp() throws FileNotFoundException {
		/*
		 * WebDriverManager.chromedriver().setup(); driver = new ChromeDriver();
		 * driver.manage().window().maximize(); wait = new WebDriverWait(driver,
		 * Duration.ofSeconds(10));
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		 * driver.get(URL);
		 */

		if (driver == null) {
			FileReader fr = new FileReader(
					"D:/Selenium-Projects/UAT-New-Framework/configfiles/config.properties");
			try {
				prop.load(fr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.get(prop.getProperty("url"));
			driver.manage().window().maximize();
		} else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.get(prop.getProperty("url"));
			driver.manage().window().maximize();

		}
		// blank html
		ExtentSparkReporter reporter = new ExtentSparkReporter("./TestReport.html");

		// report
		extent = new ExtentReports();

		// attaching html to report
		extent.attachReporter(reporter);

	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
		
		driver.quit();

	}

//Wait method using css
	public void waitMethod(String path) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(path)));
	}

//Wait using xpath
	public void xpathWaitMethod(String path) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
	}

//Wait using Webelement
	public WebElement webElementWait(WebElement ele) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
		return element;
	}

//Locator using css
	public WebElement locator(String path) {
		//waitMethod(path);
		return driver.findElement(By.cssSelector(path));
	}

//Loctaor using xpath
	public WebElement xpathLocator(String path) {
		xpathWaitMethod(path);
		return driver.findElement(By.xpath(path));
	}

	// Click functionality
	public void click(WebElement ele) {

		webElementWait(ele).click();
	}

	public boolean isDisplayed(WebElement ele) {
		// WebElement element = webElementWait(ele);
		return ele.isDisplayed();
	}

	public void visibilityCheck(WebElement ele) {

		Assert.assertTrue(ele.isDisplayed());
		System.out.println("Element is visible");
	}

	public void visibilityCheck(WebElement ele, String msg) {

		Assert.assertTrue(ele.isDisplayed(), msg + " Element is not displayed");
		System.out.print("Element is displayed " + msg);
	}

	public void sleep(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Screen shot
	public void screenShot(String fileName) {
		File firstSource = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("./snaps/" + fileName);
		try {
			FileHandler.copy(firstSource, dest);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

//Sendkeys using webelement
	public void elementInput(WebElement ele, String input) {
		ele.clear();
		ele.sendKeys(input);
	}

//Sendkeys using direct path
	public void elementInput(String path, String input) {
		WebElement element = locator(path);
		element.clear();
		element.sendKeys(input);
	}

	public void xpathElementInput(String path, String input) {
		WebElement element = xpathLocator(path);
		element.clear();
		element.sendKeys(input);
	}

//Assert check
	public void assertEqualCheck(String actual, String expected) {
		Assert.assertEquals(actual, expected);
	}

	// Get Title
	public String getTitle() {

		return driver.getTitle();
	}

	//Config properties
	public String propertyRead(String path,String key){
		try {
			FileReader fr = new FileReader("D:/Selenium-Projects/UAT-New-Framework/configfiles/"+path);
			try {
				prop.load(fr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String keyValue = prop.getProperty(key);
		return keyValue;

	}
}
