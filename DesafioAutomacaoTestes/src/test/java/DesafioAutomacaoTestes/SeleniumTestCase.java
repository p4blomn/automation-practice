package DesafioAutomacaoTestes;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;

public abstract class SeleniumTestCase {
	
	private static final String GECKODRIVER_PATH = "src/test/resources/geckodriver.exe";
	
	private static final int TIMEOUT = 20;

	protected WebDriver webDriver;

	protected String urlBase;
	
	public SeleniumTestCase() {
		urlBase = "http://automationpractice.com/";
	}
	
	@Before
	public void openResources() {
		if(webDriver == null) {
			System.setProperty("webdriver.gecko.driver", GECKODRIVER_PATH);
			
			webDriver = new FirefoxDriver();
			
			webDriver.manage().window().maximize();
			
			webDriver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(TIMEOUT, TimeUnit.SECONDS);
			
			this.webDriver.get(this.urlBase);
		}
	}
	
	@After
	public void closeResources() {		
		webDriver.quit();
	}
	
	protected WebElement waitForElement(By by) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(Duration.ofSeconds(TIMEOUT)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	protected WebElement waitForElementVisible(By by) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(Duration.ofSeconds(TIMEOUT)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
				.ignoring(ElementNotVisibleException.class);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	/**
	 * Scrolls the element by such that the element is visible to user 
	 * @param by
	 * @return The element scrolled
	 */
	protected WebElement scroll(By by) {
		WebElement element = webDriver.findElement(by);
		this.executeScript("arguments[0].scrollIntoView(true);", element);
		return element;
	}
	
	protected void executeScript(String script, WebElement element) {
		((JavascriptExecutor) webDriver).executeScript(script, element);
	}

}
