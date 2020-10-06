package tec.ACV5.common;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.base.Function;

import tec.ACV5.suite.orderFlow;
public class Wait {
	public static void wait2Second() throws InterruptedException {
		Thread.sleep(2500);
	}

	public static void wait5Second() throws InterruptedException {
		Thread.sleep(5000);
	}

	public static void wait10Second() throws InterruptedException {
		Thread.sleep(10000);
	}

	public static void wait15Second() throws InterruptedException {
		Thread.sleep(15000);
	}

	public static void wait25Second() throws InterruptedException {
		Thread.sleep(25000);
	}

	public static void wait40Second() throws InterruptedException {
		Thread.sleep(40000);
	}

	public static void wait60Second() throws InterruptedException {
		Thread.sleep(50000);
	}
	public static void  Fluentwait(By Element){
		FluentWait	 waitfl = new FluentWait<WebDriver>(orderFlow.driver);
		 waitfl.withTimeout(300, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		 waitfl.until(ExpectedConditions.elementToBeClickable(Element));
		 waitfl.until(ExpectedConditions.presenceOfElementLocated(Element));
		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Element));
		 waitfl.until(new Function<WebDriver, WebElement>() 
		 {
			public WebElement apply(WebDriver driver) {
			return driver.findElement(Element);
			}
		 });
		 }

}

