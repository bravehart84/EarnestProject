package page.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OrderPageFactory {

	@FindBy(name = "commit")
	WebElement checkoutButton;

	WebDriver driver;

	@FindBy(xpath = ".//*[@class=\"line_item elephant\"]/td[3]")
	WebElement elephantInstock;

	@FindBy(xpath = ".//*[@class=\"line_item elephant\"]/td[contains(text(),'35')]")
	WebElement elephantPrice;

	@FindBy(id = "line_item_quantity_elephant")
	WebElement elephantQuantity;

	@FindBy(xpath = ".//*[@class=\"line_item giraffe\"]/td[3]")
	WebElement giraffeInstock;

	@FindBy(xpath = ".//*[@class=\"line_item giraffe\"]/td[2]")
	WebElement giraffePrice;

	@FindBy(id = "line_item_quantity_giraffe")
	WebElement giraffeQuantity;

	@FindBy(xpath = ".//*[@class=\"line_item lion\"]/td[3]")
	WebElement lionInstock;

	@FindBy(xpath = ".//*[@class=\"line_item lion\"]/td[2]")
	WebElement lionPrice;

	@FindBy(id = "line_item_quantity_lion")
	WebElement lionQuantity;

	@FindBy(name = "state")
	WebElement stateSelect;

	@FindBy(xpath = ".//*[@class=\"line_item zebra\"]/td[3]")
	WebElement zebraInstock;

	@FindBy(xpath = ".//*[@class=\"line_item zebra\"]/td[2]")
	WebElement zebraPrice;

	@FindBy(id = "line_item_quantity_zebra")
	WebElement zebraQuantity;

	public OrderPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void checkout() {

		this.checkoutButton.click();
	}

	public void clearAllFields() {

		this.zebraQuantity.clear();
		this.giraffeQuantity.clear();
		this.elephantQuantity.clear();
		this.lionQuantity.clear();

	}

	public void enterElephantQuantity(String number) {
		this.elephantQuantity.clear();
		this.elephantQuantity.sendKeys(number);
	}

	public void enterGiraffeQuantity(String number) {
		this.giraffeQuantity.clear();
		this.giraffeQuantity.sendKeys(number);
	}

	public void enterLionQuantity(String number) {
		this.lionQuantity.clear();
		this.lionQuantity.sendKeys(number);
	}

	public void enterZebraQuantity(String number) {
		this.zebraQuantity.clear();
		this.zebraQuantity.sendKeys(number);
	}

	public WebElement findElementContaining(String text) {

		WebElement message = this.driver.findElement(By.xpath(".//*[contains(text()," + text + ")]"));

		return message;
	}

	public int getElephantInstock() {
		String Instock = this.elephantInstock.getText();

		return Integer.parseInt(Instock);

	}

	public double getElephantPrice() {
		String price = this.elephantPrice.getText();

		return Double.parseDouble(price);

	}

	public int getGiraffeInstock() {
		String Instock = this.giraffeInstock.getText();

		return Integer.parseInt(Instock);

	}

	public double getGiraffePrice() {
		String price = this.giraffePrice.getText();

		return Double.parseDouble(price);

	}

	public int getlionInstock() {
		String Instock = this.lionInstock.getText();

		return Integer.parseInt(Instock);

	}

	public double getlionPrice() {
		String price = this.lionPrice.getText();

		return Double.parseDouble(price);

	}

	public int getzebraInstock() {
		String Instock = this.zebraInstock.getText();

		return Integer.parseInt(Instock);

	}

	public double getzebraPrice() {
		String price = this.zebraPrice.getText();

		return Double.parseDouble(price);

	}

	public void selectState(String state) {

		Select states = new Select(this.stateSelect);
		states.selectByVisibleText(state);

	}

	public void selectStateByIndex(int index) {

		Select states = new Select(this.stateSelect);
		states.selectByIndex(index);

	}

}
