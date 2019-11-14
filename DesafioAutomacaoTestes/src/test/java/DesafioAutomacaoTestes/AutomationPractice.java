package DesafioAutomacaoTestes;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.*;

public class AutomationPractice extends SeleniumTestCase {
	private static final String BLOUSE = "Blouse";
	private static final String QUANTITY = "1";
	private static final String TOTAL_PRICE = "$29.00";
	private static final String EMAIL = "sherlock2019@email.com";
	private static final String FIRSTNAME = "Sherlock";
	private static final String LASTNAME = "Holmes";
	private static final String ADDRESS = "221B Baker Street";
	private static final String CITY = "London";
	private static final String STATE = "Texas";
	private static final String POSTCODE = "50010";
	private static final String COUNTRY = "United States";
	private static final String PHONE_MOBILE = "558499999999";
	
	
    @Test 
    public void testAutomationPractice() {
    	
        waitForElement(By.id("search_query_top")).sendKeys(BLOUSE);
        waitForElement(By.xpath("//button[@name='submit_search']")).click();
    
        scroll(By.xpath("//img[@alt='Blouse']"));
        waitForElement(By.className("product_img_link")).click();
        waitForElementVisible(By.xpath("//span[contains(.,'Add to cart')]")).click();
        waitForElementVisible(By.xpath("//h2[contains(.,'Product successfully added to your shopping cart')]"));
        
        scroll(By.xpath("//span[contains(.,'Proceed to checkout')]"));
        waitForElement(By.xpath("//span[contains(.,'Proceed to checkout')]")).click();
        
        //Cart validation
        assertEquals(BLOUSE, waitForElement(By.xpath("//td[contains(@class, 'cart_description')]/p/a"))
        		.getAttribute("textContent"));
        assertEquals(QUANTITY, waitForElement(By.className("cart_quantity_input"))
        		.getAttribute("value"));
        
        waitForElement(By.className("standard-checkout")).click();
        
        waitForElement(By.id("email_create")).sendKeys(EMAIL);
        waitForElement(By.id("SubmitCreate")).click();

        //Filling personal information form
        waitForElement(By.id("customer_firstname")).sendKeys(FIRSTNAME);
        waitForElement(By.id("customer_lastname")).sendKeys(LASTNAME);
        waitForElement(By.id("passwd")).sendKeys("12345");
        new Select(waitForElement(By.id("days"))).selectByValue("1");
        new Select(waitForElement(By.id("months"))).selectByValue("4");
        new Select(waitForElement(By.id("years"))).selectByValue("2001");
        waitForElement(By.id("address1")).sendKeys(ADDRESS);
        waitForElement(By.id("city")).sendKeys(CITY);
        new Select(waitForElement(By.id("id_state"))).selectByVisibleText(STATE);
        waitForElement(By.id("postcode")).sendKeys(POSTCODE);
        new Select(waitForElement(By.id("id_country"))).selectByVisibleText(COUNTRY);
        waitForElement(By.id("phone_mobile")).sendKeys(PHONE_MOBILE);
        waitForElement(By.id("alias")).clear();
        waitForElement(By.id("alias")).sendKeys("home");
        waitForElement(By.id("submitAccount")).click();
        
        //Validating address
        assertEquals(FIRSTNAME + " " + LASTNAME, 
        		waitForElement(By.xpath("//ul[@id='address_delivery']/li[contains(@class, 'address_firstname')]"))
        		.getAttribute("textContent"));
        assertEquals(ADDRESS + " ", 
        		waitForElement(By.xpath("//ul[@id='address_delivery']/li[contains(@class, 'address_address1')]"))
        		.getAttribute("textContent"));
        assertEquals(CITY + ", " + STATE + " " + POSTCODE, 
        		waitForElement(By.xpath("//ul[@id='address_delivery']/li[contains(@class, 'address_city')]"))
        		.getAttribute("textContent"));
        assertEquals(COUNTRY, 
        		waitForElement(By.xpath("//ul[@id='address_delivery']/li[contains(@class, 'address_country_name')]"))
        		.getAttribute("textContent"));
        assertEquals(PHONE_MOBILE, 
        		waitForElement(By.xpath("//ul[@id='address_delivery']/li[contains(@class, 'address_phone_mobile')]"))
        		.getAttribute("textContent"));
        
        waitForElement(By.xpath("//button[contains(@name, 'processAddress')]")).click();
        
        waitForElement(By.id("cgv")).click();
        waitForElement(By.xpath("//button[contains(@name, 'processCarrier')]")).click();
        
        //Price validation
        assertEquals(TOTAL_PRICE, waitForElement(By.id("total_price")).getAttribute("textContent"));
        
        waitForElement(By.className("bankwire")).click();
        
        waitForElement(By.xpath("//span[contains(.,'I confirm my order')]"));
        
        //Order Confirmation
        assertEquals("Order Confirmation", waitForElement(By.className("page-heading"))
        		.getAttribute("textContent"));
        assertEquals("Your order on My Store is complete.", 
        		waitForElement(By.className("cheque-indent")).getAttribute("textContent"));
        
    }
}
