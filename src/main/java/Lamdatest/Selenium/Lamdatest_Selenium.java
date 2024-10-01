package Lamdatest.Selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class Lamdatest_Selenium {

	
	
	
	//private static final String chrome = null;
	WebDriver driver;
	
	
	
	@BeforeClass
	//@Parameters({"browser","version","platform"})
	void setup ()throws MalformedURLException
	{
		
		
		EdgeOptions browserOptions = new EdgeOptions();
		browserOptions.setPlatformName("Windows 10");
		browserOptions.setBrowserVersion("latest");
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username", "prasathvp475");
		ltOptions.put("accessKey", "h8pE2NqubanZJpMGGk57oxLz2Ys3c684NKLjYkpXSfR2GmiIz9");
		ltOptions.put("project", "Selenium Java 101");
		ltOptions.put("w3c", true);
		ltOptions.put("plugin", "java-testNG");
		browserOptions.setCapability("LT:Options", ltOptions);
		driver=new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"),browserOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
//	@BeforeSuite
//	public void configSetUp(String browser, String version, String platform) throws MalformedURLException
//	{
//		
//	}
	
	@Test(priority = 1)
	void Test_Scenerio_1()  
	{
		driver.get("https://www.lambdatest.com/selenium-playground");
		driver.findElement(By.linkText("Simple Form Demo")).click();
		String currentUrl=driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("simple-form-demo"),"URL does not contains simple demo form");
		String inputText="Welcome to LambdaTest";
		driver.findElement(By.xpath("//input[@id='user-message']")).sendKeys(inputText);
		driver.findElement(By.id("showInput")).click();
		String displayedText=driver.findElement(By.xpath("//p[@id='message']")).getText();
		Assert.assertEquals(inputText, displayedText);
	}
	
	@Test(priority = 2)
	void Test_Scenerio_2()  
	{
		driver.navigate().to("https://www.lambdatest.com/selenium-playground");
		driver.findElement(By.partialLinkText("Drop Sliders")).click();
		Actions act=new Actions(driver);
		WebElement drag=driver.findElement(By.cssSelector("input[value='15']"));
		act.dragAndDropBy(drag, 212, 0).perform();
		String output=driver.findElement(By.id("rangeSuccess")).getText();
		Assert.assertEquals(output,"95");
	}
	
	@Test(priority = 3)
	void Test_Scenerio_3() throws InterruptedException
	{
		driver.navigate().to("https://www.lambdatest.com/selenium-playground");
		driver.findElement(By.partialLinkText("Input Form Submit")).click();
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		Thread.sleep(3000);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String alertMessage=js.executeScript("return document.getElementById('name').validationMessage;").toString();
		Assert.assertEquals(alertMessage,"Please fill out this field.");
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("John");
		driver.findElement(By.id("inputEmail4")).sendKeys("abcd@gmail.com");
		driver.findElement(By.id("inputPassword4")).sendKeys("John@abc");
		driver.findElement(By.id("company")).sendKeys("Company1");
		driver.findElement(By.cssSelector("#websitename")).sendKeys("www.example.com");
		WebElement countryDropDown=driver.findElement(By.xpath("//select[@name='country']"));
		Select countries=new Select(countryDropDown);
		countries.selectByVisibleText("United States");
		driver.findElement(By.id("inputCity")).sendKeys("New York");
		driver.findElement(By.id("inputAddress1")).sendKeys("3rd street");
		driver.findElement(By.id("inputAddress2")).sendKeys("4rd street");
		driver.findElement(By.id("inputState")).sendKeys("Alaska");
		driver.findElement(By.id("inputZip")).sendKeys("10001");
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		String result=driver.findElement(By.cssSelector(".success-msg.hidden")).getText();
		Assert.assertEquals(result,"Thanks for contacting us, we will get back to you shortly.");
		
		Thread.sleep(2000);	
	}
	
	@AfterClass
	void closeBrowser()
	{
		driver.quit();
	}
	

}
