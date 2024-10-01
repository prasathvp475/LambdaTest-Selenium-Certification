package Lamdatest.Selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DemoDemo3 {

    public WebDriver driver;
    public String gridURL = "https://hub.lambdatest.com/wd/hub"; // LambdaTest Grid URL
    
    // Setup Method
    
    public void setup(String browser, String version, String platform) throws MalformedURLException {
    	
    	HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", "prasathvp475");
        ltOptions.put("accessKey", "h8pE2NqubanZJpMGGk57oxLz2Ys3c684NKLjYkpXSfR2GmiIz9");
        ltOptions.put("project", "Selenium Java 101");
        ltOptions.put("w3c", true);
        ltOptions.put("name", "Test Scenerio 3 "+browser+" in "+platform); // test name
        ltOptions.put("build", "Input Form Submit");
        ltOptions.put("network", true); 
        ltOptions.put("video", true); 
        ltOptions.put("console", true); 
        ltOptions.put("visual", true);
        
    	switch (browser) {
            case "Chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setBrowserVersion(version);
                chromeOptions.setPlatformName(platform);
                chromeOptions.setCapability("LT:Options", ltOptions);          
                driver = new RemoteWebDriver(new URL(gridURL), chromeOptions);
                break;
            case "MicrosoftEdge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setBrowserVersion(version);
                edgeOptions.setPlatformName(platform);
                edgeOptions.setCapability("LT:Options", ltOptions);                     
                driver = new RemoteWebDriver(new URL(gridURL), edgeOptions);
                break;
            case "Firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBrowserVersion(version);
                firefoxOptions.setPlatformName(platform);
                firefoxOptions.setCapability("LT:Options", ltOptions);               
                driver = new RemoteWebDriver(new URL(gridURL), firefoxOptions);
                break;
            case "Internet Explorer":
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.setBrowserVersion(version);
                ieOptions.setPlatformName(platform);
                ieOptions.setCapability("LT:Options", ltOptions);               
                driver = new RemoteWebDriver(new URL(gridURL), ieOptions);
                break;
        }
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.lambdatest.com/selenium-playground");
        if(platform.equalsIgnoreCase("macOS Sierra"))
        {
        	try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                driver.findElement(By.xpath("//button[contains(text(),'Allow all')]")).click();

            } catch (Exception e) {
                System.out.println("Cookie consent dialog not found, continuing the test.");
            }
        }
    }

    

    @DataProvider(name="browserData")
    public Object[][] browserData() {
        return new Object[][] {
            { "Chrome", "88.0", "Windows 10" },
            { "MicrosoftEdge", "87.0", "macOS Sierra" },
            { "Firefox", "82.0", "Windows 7" },
            { "Internet Explorer", "11.0", "Windows 10" }
        };
    }

    // Test for Input Form Submit
    @Test(dataProvider = "browserData",priority=1) //20 sec timeout
    public void openInputForm(String browser, String version, String platform) throws MalformedURLException, InterruptedException {
        setup(browser, version, platform); 

        
        
        
        driver.navigate().to("https://www.lambdatest.com/selenium-playground");
		driver.findElement(By.partialLinkText("Input Form Submit")).click();
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		Thread.sleep(2000);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String alertMessage=js.executeScript("return document.getElementById('name').validationMessage;").toString();
		SoftAssert sa=new SoftAssert();
		sa.assertEquals(alertMessage,"Please fill in the fields.");
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
		sa.assertAll();
        driver.quit();

    }
    

    

    
}
