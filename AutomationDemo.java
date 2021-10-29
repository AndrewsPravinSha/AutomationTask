package org.practice.v2;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomationDemo {
	WebDriver driver;

	@BeforeTest
	private void browserIn() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--disable-notifications");
		driver = new ChromeDriver(option);
		driver.navigate().to("http://demo.automationtesting.in/Register.html");

	}

	@Test(enabled = true,priority=1)
	private void fillinDetails() {
		for (int i = 1; i <= 4; i++) {
			inputFiller(i, "Dummy Data");
		}
		for (int j = 10; j <= 11; j++) {
			inputFiller(j, "Dummy Password");
		}
		driver.findElement(By.xpath("//textarea[@ng-model='Adress']")).sendKeys("Dummy Address");
		clickButton(5);
		clickButton(8);
	}

	@Test(enabled = true,priority=3)
	private void selectDetails() {
		WebElement skills = driver.findElement(By.id("Skills"));
		Select select = new Select(skills);
		select.selectByVisibleText("Java");
		WebElement years = driver.findElement(By.id("yearbox"));
		Select select1 = new Select(years);
		select1.selectByValue("1999");
		WebElement month = driver.findElement(By.xpath("//select[@placeholder='Month']"));
		Select select2 = new Select(month);
		select2.selectByVisibleText("June");
		WebElement days = driver.findElement(By.id("daybox"));
		Select select3 = new Select(days);
		select3.selectByValue("26");

	}

	@Test(priority=5)
	private void langDropdown() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.id("msdd")).click();
		List<WebElement> list = driver.findElements(By.xpath("//li[@class='ng-scope']//child::a[text()]"));
		for (WebElement wement : list) {
			if (wement.getText().equals("English")) {
				wement.click();
			}
			if (wement.getText().equals("Hindi")) {
				wement.click();
			}
		}
	}
	@Test(priority=0)
	private void countryDd() {
		WebElement countryBtn = driver.findElement(By.xpath("//span[@role='combobox']"));
		Actions ac = new Actions(driver);
		ac.moveToElement(countryBtn).click().build().perform();
		List<WebElement> list2 = driver.findElements(By.xpath("//li[@role='treeitem']"));
		for (WebElement bement : list2) {
			while (bement.getText().equals("  India")) {
				bement.click();
			}
		}

	}

	@Test(enabled = true,priority=2)
	private void uploadPic() throws AWTException {
		WebElement element = driver.findElement(By.xpath("//input[@type='file']"));
		Actions ac = new Actions(driver);
		ac.moveToElement(element).click().build().perform();
		String srcPath = "C:\\Users\\Andrews Pravin Sha\\Downloads\\defectcyclechart.png";
		StringSelection ss = new StringSelection(srcPath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(90);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}

	@AfterClass
	private void screenshot() throws IOException {
		TakesScreenshot tss = (TakesScreenshot) driver;
		File src = tss.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "\\Screenshots\\ATD.png");
		FileUtils.copyFile(src, dest);
		driver.findElement(By.id("submitbtn")).click();
	}

	private void inputFiller(int indexNo, String details) {
		driver.findElement(By.xpath("(//input)[" + indexNo + "]")).sendKeys(details);
	}

	private void clickButton(int indexNo) {
		driver.findElement(By.xpath("(//input)[" + indexNo + "]")).click();
	}
}
