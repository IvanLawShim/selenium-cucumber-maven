package seleniumgluecode;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class test {
	
    public static WebDriver driver;
    
    @Before public void setUp() {
    	System.setProperty("webdriver.chrome.driver","C:\\Users\\Christian\\Documents\\Projects\\Selenium Test\\Demo Selenium\\Software\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
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
    	driver.findElement(By.id("user_login")).sendKeys(username);

    }

    @And("^user enters password \\\"([^\\\"]*)\\\"$")
    public void user_enters_password(String password) throws Throwable {
    	driver.findElement(By.id("user_pass")).sendKeys(password);
    }  

    @Then("^success message is displayed \"([^\"]*)\"$")
    public void success_message_is_displayed(String username) throws Throwable {
    	driver.findElement(By.id("wp-submit")).click();
    	String comparison = driver.findElement(By.xpath("//li[@id='wp-admin-bar-my-account']/a/span")).getText();
    	
    	Assert.assertTrue("FAILED",comparison.contains(username));
    	
   }

    @Then("^user is prompted for an error$")
    public void user_is_prompted_for_an_error() throws Throwable {
    	driver.findElement(By.id("wp-submit")).click();
    	Assert.assertTrue("FAILED: A non-existing account can login.", driver.findElement(By.xpath("//div[@id='login_error']/strong")).isEnabled());
    }
}