package Lamdatest.Selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoDemo {

    public WebDriver driver;
    public String gridURL = "https://hub.lambdatest.com/wd/hub"; 
    
    // Setup Method
    
    public void setup(String browser, String version, String platform) throws MalformedURLException {
    	
    	HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", "prasathvp475");
        ltOptions.put("accessKey", "h8pE2NqubanZJpMGGk57oxLz2Ys3c684NKLjYkpXSfR2GmiIz9");
        ltOptions.put("project", "Selenium Java 101");
        ltOptions.put("w3c", true);
        ltOptions.put("name", "Test Scenario 1 "+browser+" in "+platform); // test name
        ltOptions.put("build", "Simple Form Demo");
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
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.lambdatest.com/selenium-playground");
        if(platform.equalsIgnoreCase("macOS Sierra"))
        {
        	try {
                
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

                
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
    @Test(dataProvider = "browserData") //20 sec timeout not working properly
    public void openInputForm(String browser, String version, String platform) throws MalformedURLException, InterruptedException {
        setup(browser, version, platform); 

        
        
        
		driver.findElement(By.linkText("Simple Form Demo")).click();
		String currentUrl=driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("simple-form-demo"),"URL does not contains simple demo form");
		String inputText="Welcome to LambdaTest";
		driver.findElement(By.xpath("//input[@id='user-message']")).sendKeys(inputText);
		driver.findElement(By.id("showInput")).click();
		String displayedText=driver.findElement(By.xpath("//p[@id='message']")).getText();
		Assert.assertEquals(inputText, displayedText);
		  driver.quit();  

    }
    
   

    

    
}
