package seleniumgluecode;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class test {
	
    public static WebDriver driver;
    JavascriptExecutor js = (JavascriptExecutor) driver;
    DesiredCapabilities dc = new DesiredCapabilities();
    
    
    @Before public void setUp() {
    	System.setProperty("webdriver.chrome.driver","C:\\Users\\Christian\\Documents\\Projects\\Selenium Test\\Demo Selenium\\Software\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }
    
    @After public void cleanUp() {
        driver.close();
        driver.quit();
    }
    
    @Given("^user is on homepage$")
    public void user_is_on_homepage() throws Throwable {     
        driver.get("http://demo.websiteprojectupdates.com/web11");
    }
    
    @When("^user navigates to Login Page$")
    public void user_navigates_to_Login_Page() throws Throwable {
        driver.get("http://demo.websiteprojectupdates.com/web11/wp-admin/");        
    }
    
    @And("^user enters username \"([^\"]*)\"$")
    public void user_enters_username(String username) throws Throwable {
    	try {
			dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

			try {
				WebElement e =(new WebDriverWait(driver,30)).until(ExpectedConditions.presenceOfElementLocated(By.id("user_login")));
				e.sendKeys(username);
			} catch (UnhandledAlertException f) {
				try {
					Alert alert = driver.switchTo().alert();	
					String alertText = alert.getText();
					System.out.println("Alert data: " + alertText);
					alert.accept();
				} catch (NoAlertPresentException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception error) {
			driver.navigate().to("http://demo.websiteprojectupdates.com/web11/wp-admin/");
			WebElement e = driver.findElement(By.id("user_login"));
			e.sendKeys(username);
			error.printStackTrace();
		}


    }

    @And("^user enters password \\\"([^\\\"]*)\\\"$")
    public void user_enters_password(String password) throws Throwable {
    	WebElement e =(new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("user_pass")));
    	e.sendKeys(password);
    }  

    @Then("^success message is displayed \"([^\"]*)\"$")
    public void success_message_is_displayed(String username) throws Throwable {
    	driver.findElement(By.id("wp-submit")).click();
    	WebElement e =(new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@id='wp-admin-bar-my-account']/a/span")));
    	String comparison = e.getText();
    	Assert.assertTrue("FAILED",comparison.contains(username));
    	
   }

    @Then("^user is prompted for an error$")
    public void user_is_prompted_for_an_error() throws Throwable {
    	driver.findElement(By.id("wp-submit")).click();
    	WebElement e =(new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='login_error']/strong")));

    	Assert.assertTrue("FAILED: A non-existing account can login.", e.isEnabled());
    }
    
    @And("^user forgot password \"([^\"]*)\"$")
    public void user_forgot_password(String email) throws Throwable{
    	driver.findElement(By.id("wp-submit")).click();
    	WebElement a =(new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Lost your password?")));
    	a.click();
    	WebElement e =(new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("user_login")));
    	e.sendKeys(email);
    	driver.findElement(By.xpath("//button[@value='Reset password']")).click();
    }
    
    @And("^user fixes password \"([^\"]*)\"$")
    public void user_fixes_password(String password) throws Throwable{
    	try {
			driver.navigate().to("https://accounts.google.com/signin/v2/identifier?hl=en&continue=https%3A%2F%2Fmail.google.com%2Fmail&service=mail&flowName=GlifWebSignIn&flowEntry=AddSession");
			WebElement e =(new WebDriverWait(driver,20)).until(ExpectedConditions.presenceOfElementLocated(By.id("identifierId")));
			e.sendKeys(Keys.CLEAR);
			driver.findElement(By.id("identifierId")).sendKeys("syncjohn.qa4@gmail.com");
			driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
			driver.findElement(By.name("password")).sendKeys(Keys.CLEAR);
			driver.findElement(By.name("password")).sendKeys("Sync@dmin2019");
			driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
			WebElement a =(new WebDriverWait(driver,20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[6]/div/div/div/span/span")));
			a.click();
			WebElement b =(new WebDriverWait(driver,20)).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Click here to reset your password")));
			b.click();
		} catch (Exception error) {
			driver.navigate().to("http://demo.websiteprojectupdates.com/web11");
			error.printStackTrace();
		}
    }

    @When("^user navigates to Login Register Page$")
    public void When_user_navigates_to_Login_Register_Page() throws Throwable{
    	driver.navigate().to("http://demo.websiteprojectupdates.com/web11/my-account/");
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertTrue("Login/Register Page not found!", title.contains("My Account"));
    }
    
    @And("^user enters username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void user_enters_username_password(String username, String password) throws Throwable{
		WebElement a =(new WebDriverWait(driver,20)).until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		WebElement b = driver.findElement(By.id("password"));
		a.sendKeys(username);
		b.sendKeys(password);
		
    }
    
    @And("^user logs in and wait for confirmation$")
    public void user_logs_in_and_wait_for_confirmation() {
    	WebElement c = driver.findElement(By.name("login"));
    	c.click();
    }
    
    @Then("^user is confirm to arrived in the user profile$")
    public void user_is_confirm_to_arrived_in_the_user_profile() {
    	WebElement a =(new WebDriverWait(driver,20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='post-8']/div/div/p/strong")));
    	String name = a.getText();
    	Assert.assertTrue("Unregistered User.", name.contains("test_user"));
    }
    
	
}