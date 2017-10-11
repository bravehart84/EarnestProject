package page.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPageFactory {

	WebDriver driver;

	@FindBy(id = "subtotal")
	WebElement subTotal;

	@FindBy(id = "taxes")
	WebElement taxes;

	@FindBy(id = "total")
	WebElement total;

	public CheckoutPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public double getSubTotal() {
		String sub = this.subTotal.getText().substring(1);
		return Double.parseDouble(sub);

	}

	public double getTaxes() {
		String sub = this.taxes.getText().substring(1);
		return Double.parseDouble(sub);

	}

	public double getTotal() {
		String sub = this.total.getText().substring(1);
		return Double.parseDouble(sub);

	}

}
