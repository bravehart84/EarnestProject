package page.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import page.classes.CheckoutPageFactory;
import page.classes.OrderPageFactory;
import utilities.ExtentManager;
import utilities.Screenshots;
import utilities.WaitUtil;

public class TestOrderPage {

	CheckoutPageFactory checkoutPage;

	private String dir = System.getProperty("user.dir");

	private WebDriver driver;

	ExtentReports extent;
	File file = new File(this.dir + "\\statesList.properties");

	FileInputStream fileInput = null;

	OrderPageFactory orderPage;
	Properties prop = new Properties();

	private SoftAssert softAssert = new SoftAssert();
	private SoftAssert softAssert2 = new SoftAssert();
	private SoftAssert softAssert3 = new SoftAssert();

	double subTotal = 0;
	double taxes = 0;
	ExtentTest test;
	double total = 0;

	@AfterMethod
	public void result(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = Screenshots.takeScreenshot(this.driver, testResult.getName());
			this.test.log(Status.FAIL, "Snapshot below: " + this.test.addScreenCaptureFromPath(path));
		}

	}

	@BeforeClass
	public void setUp() {

		this.driver = new FirefoxDriver();
		// this.driver = new InternetExplorerDriver();

		// this.driver.manage().window().maximize();
		// this.driver.manage().window().setSize(new Dimension(1500, 1080));

		this.extent = ExtentManager.GetExtent("QA Automation Report", "Regression Cycle");
		this.test = this.extent.createTest("Jungle Socks Order page Tests", "Testing Jungle Socks app");
		this.driver.get("https://jungle-socks.herokuapp.com/");
		this.test.log(Status.INFO, "Navigated to jungle socks order page");

		this.orderPage = new OrderPageFactory(this.driver);
		this.checkoutPage = new CheckoutPageFactory(this.driver);

		this.driver.manage().timeouts().pageLoadTimeout(8000, TimeUnit.SECONDS);

		try {
			this.fileInput = new FileInputStream(this.file);

		}

		catch (FileNotFoundException e) {
			System.out.println("Could not locate properties file");
			this.test.log(Status.FAIL, "Could not locate properties file");
			e.printStackTrace();

		}

		try {
			this.prop.load(this.fileInput);
			this.test.log(Status.INFO, "Loaded Properties file ");
		}

		catch (IOException e) {
			System.out.println("Cannot load properties file input");
			this.test.log(Status.FAIL, "Cannot load properties file input");
			e.printStackTrace();
		}

	}

	@AfterClass
	public void tearDown() {

		this.extent.flush();
		// this.driver.close();
		// this.driver.quit();

	}

	@Test(priority = 6)
	public void testDecimal() {

		this.test = this.extent.createTest("Test Decimal Value", "Testing for Decimal entry");

		try {

			WaitUtil.getWhenVisible(this.driver, By.id("line_item_quantity_zebra"), 20);

			int quant = this.orderPage.getzebraInstock();

			double decimalQuant = quant / 1000000.0;

			String decimal = String.valueOf(decimalQuant);

			this.orderPage.clearAllFields();

			this.orderPage.enterZebraQuantity(decimal);
			this.test.log(Status.INFO, "Entered " + decimal + " as zebra quantity");

			String errorMsg = "Please enter a valid quantity";
			WebElement error = this.orderPage.findElementContaining(errorMsg);
			this.test.log(Status.INFO, "Checking if correct error message was displayed ");
			Assert.assertTrue(error.isDisplayed());

			this.test.log(Status.PASS, "Displayed correct error Message");

		}

		catch (Exception e) {
			this.test.log(Status.FAIL, "Did not display appropriate error Message for Decimal Quantity Entry");
		}

	}

	@Test(priority = 4)
	public void testInvalidData() {

		this.test = this.extent.createTest("Test Invalid Data", "Testing for Invalid data type");

		try {

			String badEntry = "2yopw";

			this.driver.navigate().to("https://jungle-socks.herokuapp.com/");
			this.test.log(Status.INFO, "Navigated to Order page ");

			WaitUtil.getWhenVisible(this.driver, By.id("line_item_quantity_zebra"), 20);

			this.orderPage.enterZebraQuantity(badEntry);
			this.test.log(Status.INFO, "Entered " + badEntry + " as zebra quantity");
			String errorMsg = "Please enter a valid quantity";
			WebElement error = this.orderPage.findElementContaining(errorMsg);
			this.test.log(Status.INFO, "Checking if error message is correctly displayed ");
			Assert.assertTrue(error.isDisplayed());

			this.test.log(Status.PASS, "Displayed correct error Message");

		}

		catch (Exception e) {
			this.test.log(Status.FAIL, "Did not display appropriate error Message for Invalid data");
		}

	}

	@Test(priority = 5)
	public void testNegatives() {

		this.test = this.extent.createTest("Test Negative Value", "Testing for Negative quantity entry");

		try {
			WaitUtil.getWhenVisible(this.driver, By.id("line_item_quantity_zebra"), 20);

			int quant = this.orderPage.getzebraInstock();
			int negativeQuant = Math.negateExact(quant);
			String negative = String.valueOf(negativeQuant);

			this.orderPage.clearAllFields();

			this.orderPage.enterZebraQuantity(negative);
			this.test.log(Status.INFO, "Entered " + negative + " as zebra quantity");

			String errorMsg = "Please enter a valid quantity";
			WebElement error = this.orderPage.findElementContaining(errorMsg);
			this.test.log(Status.INFO, "Checking if error message is correctly displayed ");
			Assert.assertTrue(error.isDisplayed());
			this.test.log(Status.PASS, "Displayed correct error Message");
		}

		catch (Exception e) {
			this.test.log(Status.FAIL, "Did not display appropriate error Message for Negative Quantity Entry");
		}

	}

	@Test(priority = 2)
	public void testNullEntry() {

		this.test = this.extent.createTest("Test Null Entries", "Testing for Null Quantity");

		try {
			Enumeration<Object> keys = this.prop.keys();
			String state = (String) keys.nextElement();

			this.orderPage.clearAllFields();

			this.orderPage.selectState(state);
			this.test.log(Status.INFO, "Selected " + state + " with null quantity");

			this.orderPage.checkout();
			this.test.log(Status.INFO, "Clicked checkout button ");
			String errorMsg = "Please enter quantity of 1 or more";
			WebElement error = this.orderPage.findElementContaining(errorMsg);
			this.test.log(Status.INFO, "Checking if error message is correctly displayed ");
			Assert.assertTrue(error.isDisplayed());
			this.test.log(Status.PASS, "Displayed correct error Message");
		}

		catch (Exception e) {
			this.test.log(Status.FAIL, "Did not display appropriate error Message");
		}

	}

	@Test(priority = 3)
	public void testNullState() {

		this.test = this.extent.createTest("Test Null State Selection", "Testing for Null State Selection");

		try {

			this.driver.navigate().to("https://jungle-socks.herokuapp.com/");
			this.test.log(Status.INFO, "Navigated to order page ");

			WaitUtil.getWhenVisible(this.driver, By.id("line_item_quantity_zebra"), 20);

			int quant = this.orderPage.getzebraInstock() - 1;

			String quantString = String.valueOf(quant);

			this.orderPage.enterZebraQuantity(quantString);

			this.test.log(Status.INFO, "Entered " + quantString + " as zebra quantity");

			this.orderPage.selectStateByIndex(0);
			this.test.log(Status.INFO, "Selected a null state ");

			this.orderPage.checkout();
			this.test.log(Status.INFO, "Clicked checkout button ");
			String errorMsg = "Please enter a valid state";
			WebElement error = this.orderPage.findElementContaining(errorMsg);
			this.test.log(Status.INFO, "Checking if correct error message is displayed ");
			Assert.assertTrue(error.isDisplayed());

			this.test.log(Status.PASS, "Displayed correct error Message");

		} catch (Exception e) {
			this.test.log(Status.FAIL, "Did not display appropriate error Message for valid state");
		}

	}

	@Test(priority = 0)
	public void testPricingAndTaxes() {
		String quantity = "3";
		int quantityInt = Integer.parseInt(quantity);
		WaitUtil.getWhenVisible(this.driver, By.xpath(".//*[@class=\"line_item elephant\"]/td[2]"), 15);
		double elephantPrice = this.orderPage.getElephantPrice();

		double giraffePrice = this.orderPage.getGiraffePrice();

		double lionPrice = this.orderPage.getlionPrice();
		double zebraPrice = this.orderPage.getzebraPrice();

		Enumeration<Object> states = this.prop.keys();

		while (states.hasMoreElements()) {

			String key = (String) states.nextElement();

			String taxRates = this.prop.getProperty(key);
			double rates = Double.parseDouble(taxRates);
			this.subTotal = quantityInt * (zebraPrice + lionPrice + elephantPrice + giraffePrice);

			WaitUtil.getWhenVisible(this.driver, By.id("line_item_quantity_elephant"), 20);

			this.orderPage.enterZebraQuantity(quantity);

			this.orderPage.enterLionQuantity(quantity);
			this.orderPage.enterElephantQuantity(quantity);
			this.orderPage.enterGiraffeQuantity(quantity);
			this.test.log(Status.INFO, "Entered quantity as " + quantity);

			this.orderPage.selectState(key);
			this.orderPage.checkout();
			this.test.log(Status.INFO, "Clicked checkout button for " + key);

			this.taxes = (rates / 100) * this.subTotal;
			this.total = this.subTotal + this.taxes;

			WaitUtil.getWhenVisible(this.driver, By.id("subtotal"), 15);
			try {
				this.softAssert.assertEquals(this.checkoutPage.getSubTotal(), this.subTotal);

				this.test.log(Status.INFO, "Verifying SubTotal for " + key);
				this.softAssert.assertAll();

				this.test.log(Status.PASS, "SubTotal is correct for " + key);

				System.out.println(key + ":- " + taxRates);
			}

			catch (AssertionError e) {

				System.out.println("SubTotal is incorrect for " + key);

				this.test.log(Status.FAIL, "SubTotal is incorrect for " + key);
			}

			try {
				this.softAssert2.assertEquals(this.checkoutPage.getTaxes(), this.taxes);
				this.test.log(Status.INFO, "Verifying Taxes for " + key);
				this.softAssert2.assertAll();

				this.test.log(Status.PASS, "State Tax is correct for " + key);

			}

			catch (AssertionError e) {
				System.out.println("Taxes and Total are incorrect for " + key);

				this.test.log(Status.FAIL, "Taxes and Total are incorrect for " + key);

			}

			try {
				this.softAssert3.assertEquals(this.checkoutPage.getTotal(), this.total);
				this.test.log(Status.INFO, "Verifying Total for " + key);
				this.softAssert3.assertAll();

				this.test.log(Status.PASS, "Total amount is correct for " + key);

			}

			catch (AssertionError e) {
				System.out.println("Total amount is incorrect for " + key);

				this.test.log(Status.FAIL, "Total amount is incorrect for " + key);

			}

			this.driver.navigate().to("https://jungle-socks.herokuapp.com/");

		}

		this.test.log(Status.INFO, "Completed checks for all states.");

	}

	@Test(priority = 1)
	public void testQuantity() {

		this.test = this.extent.createTest("Test Excess Quantity", "Testing for Quanitity above stock");

		try {

			int zebra = this.orderPage.getzebraInstock() + 1;
			String errorMsg = "Quantity Exceeds amount available in stock";

			WaitUtil.getWhenVisible(this.driver, By.id("line_item_quantity_zebra"), 20);
			String zebraString = String.valueOf(zebra);

			this.orderPage.enterZebraQuantity(zebraString);
			this.test.log(Status.INFO, "Entered " + zebraString + " as excess zebra quantity");

			WebElement error = this.orderPage.findElementContaining(errorMsg);
			this.test.log(Status.INFO, "Checking if correct error message is displayed ");
			Assert.assertTrue(error.isDisplayed());
			this.test.log(Status.PASS, "Displayed correct error Message");
		}

		catch (Exception e) {
			this.test.log(Status.FAIL, "Did not display appropriate error message");
		}

	}

}
