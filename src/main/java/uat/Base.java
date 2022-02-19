package uat;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Base implements UatBase {


	public static WebDriver driver;
	public static Properties prop = new Properties();


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
	public void waitMethod(String css) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
	}

//Wait using xpath
	public void xpathWaitMethod(String css) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(css)));
	}

//Wait using Web element
	public WebElement webElementWait(WebElement ele) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
		return element;
	}

//Locator using css
	public WebElement locator(String css) {
		//waitMethod(path);
		return driver.findElement(By.cssSelector(css));
	}

//Locator using xpath
	public WebElement xpathLocator(String xpath) {
		xpathWaitMethod(xpath);
		return driver.findElement(By.xpath(xpath));
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

//Send-keys using web-element
	public void elementInput(WebElement ele, String input) {
		ele.clear();
		ele.sendKeys(input);
	}

//Send-keys using direct path
	public void elementInput(String path, String input) {
		WebElement element = locator(path);
		element.clear();
		element.sendKeys(input);
	}

	public void xpathElementInput(String xpath, String input) {
		WebElement element = xpathLocator(xpath);
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

	/**
	 *
	 * @param fileName : name of the config file without extension "properties "
	 * @param key : "key" of the key value pair
	 * @return returns string value
	 */
	//Config properties
	public static String propertyRead(String fileName, String key){
		try {
			FileReader fr = new FileReader("./configfiles/"+fileName+".properties");
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

	/**
	 *
	 * @param fileName - filename of the Excel file without extension
	 * @return it returns 2 dimensional array of string
	 */
	public static String[][] excelFileRead(String fileName) {
		/*
		 * XSSFSheet  for  selecting sheet from excel-sheet index of 0 indicate first sheet
		 * sheet-getLastRoeNum(); for getting lastRowNum
		 *
		 * for loop for iterating row and cell
		 * first loop for row
		 * second loop for cell
		 *
		 */
		XSSFWorkbook wbook = null;
		try {
			wbook = new XSSFWorkbook("./Utils/"+fileName+".xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}

		XSSFSheet sheet = wbook.getSheetAt(0);

		int lastRowNum = sheet.getLastRowNum();
		short lastCellNum = sheet.getRow(0).getLastCellNum();
		String[][] data=new String[lastRowNum][lastCellNum];

		for (int i = 1; i <= lastRowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				String value = cell.getStringCellValue();
				data [i-1][j]= value;
			}
		}
		try {
			wbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;

	}






}
