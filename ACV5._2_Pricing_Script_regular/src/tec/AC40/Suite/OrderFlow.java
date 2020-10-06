 package tec.AC40.Suite;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Assume;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import tec.AC40.Common.Commonclass;
import tec.AC40.Common.Wait;
import tec.AC40.Config.Config;
import tec.AC40.Config.LayoutAndCuponchanger;
import tec.AC40.Config.Property;
import tec.AC40.ExcelFiles.Xls_Reader;
import tec.AC40.Utility.RW_File;
import tec.AC40.Utility.Testutil;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;

import com.google.common.base.Function;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
		
public class OrderFlow extends Commonclass 
{
	
/**
 * 
 *  OrderFlow class starts here
 *  We get order number in order summary page and use same number 
 *  In user, approver, and PS view orders page
 *
 **/
	
 public static String OrderNumber = null;
 	
 // Below three keys are useful to maintain execution row  (case/scenario)  details in Result excel file. 
 
 public static String TestCase1= null;
 public static String TestStep1= null;
 public static String Parameters1= null;
 	   
 /**
 *  
 * 	Below key uses in when we have a condition "Shipping Address same as Billing Address" NO
 * 	And when user check "Shipping Address same as Billing address" check box.
 * 	We maintain value in this key in order check out page and uses in entire script.
 *
 **/
 
 public static String ShipAddSameAsBillAddSub = null;
 	
 // Image number initially assigned to zero then it will increase +1 value for every error. It will increase this
 // value up to complete particular row execution, Once next row execution starts again it re-initialize to zero
 
 public static int ImageNumber= 0;
 
 // Below variable are related to Base price script
 
 public static int BasePriceIncrementValue = 0;
 public static int BasePriceDownload = 0;
 public static boolean IsBaseDiscountZero = false;
 	
 @BeforeClass
 public void root() throws InterruptedException{
 LayoutAndCuponchanger.layoutchangeandcouponcode();
 }
 
 
 @BeforeTest
 public void CreateFolder()
 {
	 //
 }
 public void beforeTest() throws IOException 
 {
   if (Testutil.isSkip(datatable_suite1, "OrderFlow")) 
   {
     // skip the test
	 Assume.assumeTrue(false);
   }
 }
	
 /**
 * 
 * Reading data from excel file (Data source) -- Row by Row (For each row entire pricing flow execute once).
 * The below AccuPrice method contain number of string and these are equal to total column's in data sheet.
 * And number of columns in data and AccuPrice() method must have same count of parameters other wise script not executes. 
 *  
 * */
 
 @Test(dataProvider = "testParameterData")	
 public void AccuPrice(String TestCase, String TestStep, String Parameters, String ProdutType, String OrderType,  
	String OrderBase, String PaymentType, String PaymentSubOpt, String CostCenter, String ShipAddSameAsBillAdd, 
	String WeightPerPackage, String CalculateTaxCondition, String EnablePromotionsORDiscounts,
	String EnableZeroAmountOrder,String IsExternalPricingON,String BasePrice, String Quantity, String ItemPerPrice, 
	String FlatRate, String DownloadPrice, String SubTotal, String DiscountPercentage, String Discount,
	String DiscountCalculationFromSubTotal, String ShippingPrice, String FRShippingPriceUser,
	String Addons, String Postage,  String TotalPrice, 
	String PromotionDiscountPercentage, String PromotionCoupon, String DiscountPrice,  
	String Tax, String PriceAfterApplyingCoupon, String PriceAfterCalculatingTax,String OrderBaseShipping, 
	String Total, String DecimalValue,String Weightdecimalvalue,String Weighttype,String Weightdecimaltext,
	String OrderAmountValue, String IsShippingTaxable, 
	String IsPostageTaxable, String SubtotalWithoutPostageORShippingPrice, String PostagePrice, 
	String PromotionDiscountAfterSubtractingFromSubTotal, String ShippingPriceAfterApplyingTax, 
	String ShippingPricePerPiece, String AddonPricePerPiece, String Payment1Price, String AmtAfterPayment1, 
	String Payment2Price, String AmtAfterPayment2, String Payment3Price,
	String AmtAfterPayment3, String Payment4Price, String AmtAfterPayment4, String Payment5Price,
	String userordershippingorhandlingfee, String Priceafterapplyinghandilingfee,
	String FullfilmentShippingOrHandlingFeeTypeAmountPercent,
	String FullfilmentShippingMarkupFeeAmountPercentTypeAmountPercent,String FullfilmentShippingOrHandlingFee,
	String FullfilmentShippingMarkupFee,String Priceafterapplyingfulfillmentshippingmarkupfee,String IfShippingaddressIseditble,
	String Priceaftercalculatingtaxwithoutcoupn,String IsTaxExempt,String Reorderflow,String ApproverEdit,String LandingPageOption,String LandingPage,
	String LandingPageProduct,String PresetLPSettings,String LandingPageProductPP,String LandingPageProductFR,String LandingPageSubtotal,
	String LandingPageDiscount,String LandingPageDiscountValue,String LDiscuntCalulationFromSubtotal,String SubtotalAfterPurlPrice,String SiteNameType,String ZeroAmountorder,
	String Orderelements,String OrderelementsAddOns,String OrderelementsBillingAddress,String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
	String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,String OrderelementsCheckout,
	String OrderelementsJobTicket,String OrderelementsPrintPopup,String OrderTypeSplitShip)throws Exception, 
	NoSuchElementException, InterruptedException, NumberFormatException, NullPointerException
	{// Test annotation starts here
	 
	 
	 
	
 	// System.out.println("** Extent reports working fine **");
	 
	 
	 try
	 {// try block starts here
		
		int i=1;
		
        // We use the below variables in Result file (.csv)and assign every time new values to these three keys.
		TestCase1= TestCase;
		TestStep1 = TestStep;
		Parameters1 = Parameters; 
		
		i=i+1;
        
		//Assign config file promotion code value to one variable (i.e. PromotionCode).
		//It has added newly and it helps to get data form config file instead of data sheet.
		String PromotionCode = Config.PromotionCode;
        
		int TestStepvalue1  = 0;
		//The below pricing values conversion starts only whenever we have Test case column value contain "TC"
		//If we do not have "TC" value in Test step we considered that it is comment in excel file
		if(TestCase.contains("TC"))
		{// Test case if starts here
		// Converting TestStep Value in to number
		String TestStepvalue = TestStep;
		TestStepvalue1 = Double.valueOf(TestStepvalue).intValue();
       
        //Converting price values based on decimal value
       
		String ItemPerPrice1 = Decimalsetting(ItemPerPrice, DecimalValue);
		ItemPerPrice = ItemPerPrice1; 
		
		String BasePrice1 = Decimalsetting(BasePrice, DecimalValue);
		BasePrice = BasePrice1; 
      
		String Discount1 = Decimalsetting(Discount, DecimalValue);
		Discount = Discount1;
		
		String FlatRate1 = Decimalsetting(FlatRate, DecimalValue);
		FlatRate = FlatRate1;
      
		String DownloadPrice1 = Decimalsetting(DownloadPrice, DecimalValue);
      	DownloadPrice = DownloadPrice1;
      
      	String Weightdecimaltext1= Decimalsetting(Weightdecimaltext, DecimalValue);
      	Weightdecimaltext = Weightdecimaltext1;
      
      	String userordershippingorhandlingfee1 = Decimalsetting(userordershippingorhandlingfee,DecimalValue);
      	userordershippingorhandlingfee = userordershippingorhandlingfee1;
      
      	String FullfilmentShippingOrHandlingFee1 = Decimalsetting(FullfilmentShippingOrHandlingFee,DecimalValue);
      	FullfilmentShippingOrHandlingFee = FullfilmentShippingOrHandlingFee1;
      
      	String FullfilmentShippingMarkupFee1 = Decimalsetting(FullfilmentShippingMarkupFee,DecimalValue);
      	FullfilmentShippingMarkupFee = FullfilmentShippingMarkupFee1;
      
      	String Priceafterapplyingfulfillmentshippingmarkupfee1 = Decimalsetting(Priceafterapplyingfulfillmentshippingmarkupfee,DecimalValue);
      	Priceafterapplyingfulfillmentshippingmarkupfee = Priceafterapplyingfulfillmentshippingmarkupfee1;

      	String SubTotal1 = Decimalsetting(SubTotal, DecimalValue);
      	SubTotal = SubTotal1;
      
      	//String DiscountCalculationFromSubTotal1 = Decimalsetting(DiscountCalculationFromSubTotal, DecimalValue);
      	// DiscountCalculationFromSubTotal = DiscountCalculationFromSubTotal1;
      
      	String ShippingPrice1 = Decimalsetting(ShippingPrice, DecimalValue);
      	ShippingPrice = ShippingPrice1;
      
      	String FRShippingPriceUser1 = Decimalsetting(FRShippingPriceUser, DecimalValue);
      	FRShippingPriceUser = FRShippingPriceUser1;
      
      	String Addons1 = Decimalsetting(Addons, DecimalValue);
      	Addons = Addons1;
      
      	String Postage1 = Decimalsetting(Postage, DecimalValue);
      	Postage = Postage1;
      
      	String PromotionCoupon1 = Decimalsetting(PromotionCoupon, DecimalValue);
      	PromotionCoupon = PromotionCoupon1;
      
      	String DiscountPrice1 = Decimalsetting(DiscountPrice, DecimalValue);
      	DiscountPrice = DiscountPrice1;
      
      	String Tax1 = Decimalsetting(Tax, DecimalValue);
      	Tax = Tax1;
      
      	String OrderBaseShipping1 = Decimalsetting(OrderBaseShipping, DecimalValue);
      	OrderBaseShipping = OrderBaseShipping1;
      
      	String SubtotalWithoutPostageORShippingPrice1 = Decimalsetting(SubtotalWithoutPostageORShippingPrice, DecimalValue);
      	SubtotalWithoutPostageORShippingPrice = SubtotalWithoutPostageORShippingPrice1;
      
      	String PostagePrice1 = Decimalsetting(PostagePrice, DecimalValue);
      	PostagePrice = PostagePrice1;
      
      	String PromotionDiscountAfterSubtractingFromSubTotal1 = Decimalsetting(PromotionDiscountAfterSubtractingFromSubTotal, DecimalValue);
      	PromotionDiscountAfterSubtractingFromSubTotal =PromotionDiscountAfterSubtractingFromSubTotal1;
      
      	String ShippingPriceAfterApplyingTax1 = Decimalsetting(ShippingPriceAfterApplyingTax, DecimalValue);
      	ShippingPriceAfterApplyingTax = ShippingPriceAfterApplyingTax1;
      
      	String ShippingPricePerPiece1 = Decimalsetting(ShippingPricePerPiece, DecimalValue);
      	ShippingPricePerPiece = ShippingPricePerPiece1;
      
      	String AddonPricePerPiece1 = Decimalsetting(AddonPricePerPiece, DecimalValue);
      	AddonPricePerPiece =AddonPricePerPiece1;
      
      	String Payment1Price1 = Decimalsetting(Payment1Price, DecimalValue);
      	Payment1Price = Payment1Price1;
      
      	String AmtAfterPayment11 = Decimalsetting(AmtAfterPayment1, DecimalValue);
      	AmtAfterPayment1 = AmtAfterPayment11;
      
      	String Payment2Price1 = Decimalsetting(Payment2Price, DecimalValue);
      	Payment2Price = Payment2Price1;
      
      	String AmtAfterPayment21 = Decimalsetting(AmtAfterPayment2, DecimalValue);
      	AmtAfterPayment2 = AmtAfterPayment21;
      
      	String Payment3Price1 = Decimalsetting(Payment3Price, DecimalValue);
      	Payment3Price = Payment3Price1;
    	String LandingPageSubtotal1 = Decimalsetting(LandingPageSubtotal, DecimalValue);
    	LandingPageSubtotal = LandingPageSubtotal1;
       }// Test step contains "TC" verification condition ends here 
        
		// Below code related to particular row execution
		
		//Converting start row string value to integer value (We are taken start and end row values as Strings
		//because end row value need supports 'n' value (n means end row).
		int StartRowValue = Double.valueOf(Config.ExecutionStartRow).intValue();
		int EndRowVlaue = 0;
		// If we enter end row value is n then system get the excel last row number and use it for end value.  
		if(Config.ExecutionEndRow.equals("n"))
		{
    	  EndRowVlaue = Double.valueOf(Xls_Reader.SheetRowcount).intValue();
		}
		else
		{
    	  EndRowVlaue = Double.valueOf(Config.ExecutionEndRow).intValue();
		}
		//Calculate the selected rows array length
		int SelectedRowsArrayCount = Config.SelectedRows.length;
		
		if((TestStepvalue1 >= StartRowValue && TestStepvalue1 <= EndRowVlaue && SelectedRowsArrayCount == 0) ||
    		   (Arrays.asList(Config.SelectedRows).contains((TestStepvalue1))))
        {// Particular row execution if starts here
         if(TestCase.contains("TC"))
         {// Test case if starts here 
        	 //************** Admin settings starts here ****************************
       	 
        	 // The below code related to time stamp
        	 DateFormat dateFormat = new SimpleDateFormat("_yyyy-MMM-dd_h-mm-ss_a");
     		 Date date = new Date();
     		 System.out.println("Time Stamp : "+dateFormat.format(date));
     		 
/** Close browsers before going start script execution (We are adding this code to overcome the number 
* of browsers opening issue 
* 
*/
     		 
     		 String os = System.getProperty("os.name");
        	 if(os.contains("Windows")) 
        	 {
        		 if(Config.browser.equals("GC"))
        		 {
        			// Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        			 System.out.println(" Don't kill browser");
        		 }
        		 else if(Config.browser.equals("IE"))
        		 {
        			 Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        		 }
        		 else if(Config.browser.equals("FF"))
        		 {
        			 Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
        		 }
        		 // Below code kill's the chrome driver .exe file from back end
        		 Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        	 }
        	 
        	 // If below condition is satisfied, entire console data will save in "Note pad" under Project ErrorLog folder
        	 if(Config.IsConsoleErrorSave.equalsIgnoreCase("Yes"))
        	 {
        	 System.setErr(new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"\\ErrorLog\\"+Commonclass.SheetNameErrorLog+".txt", true), true));
        	 System.setOut(new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"\\ErrorLog\\"+Commonclass.SheetNameErrorLog+".txt", true), true));
        	 }
      
         	 et.log(LogStatus.INFO, "Extent report working fine");
        	 tec.AC40.Common.Wait.wait5Second();        	 
        	 
        	 //Written below condition for Expected Discount creation whenever we have Base price
     			if(Discount.equals("0")||Discount.equals("0.0")|| Discount.equals("0.00")||
     			Discount.equals("0.000")||	Discount.equals("0.0000"))
     			{	
     				//System.out.println("Discount value : "+Discount);
     			    IsBaseDiscountZero = true;
     			}
        	
     		// Below line displays Test case and step details and useful to separate any two cases
     		
         	 System.out.println("****** ******"+" Test Case Number --> "+TestStep+"******* *******");
         	 et.log(LogStatus.INFO, "****** ******"+" Test Case Number --> "+TestStep+"******* *******");
        	 ImageNumber= 0;
        	 
    		 /** Admin login function **/

        	 StartBrowser();  //Run in local browser
        	// CBT_Browser();   //Run in CBT Cloud
        	 System.out.println("CBT Method works");
    		 adminLogin();
    		 System.out.println("Admin Login done");

    		 
    		  
     		 if(DecimalValue.isEmpty()) 
			 {
    		 	System.out.println("********** Decimal value is Empty ***********");
			 }
			 else  
			 {
			    //System.out.println("Decimal value is:"+DecimalValue);
				DecimalvalueSetting(DecimalValue, Tax, IsShippingTaxable, OrderAmountValue,Weightdecimalvalue,Weighttype,
				userordershippingorhandlingfee,IsPostageTaxable,PaymentSubOpt,PaymentType, CalculateTaxCondition, 
				EnablePromotionsORDiscounts,FullfilmentShippingOrHandlingFee,FullfilmentShippingMarkupFee,OrderBase,
				EnableZeroAmountOrder, TestStep, CostCenter, ShipAddSameAsBillAdd, WeightPerPackage,IsExternalPricingON,Reorderflow, ApproverEdit,OrderType);
			 }	
         
			 // Product price setting
        	   ItemPerPrice(ItemPerPrice, FlatRate,Weighttype,Weightdecimaltext);
        	   
        	 //landing page
  			 if(LandingPageOption.equalsIgnoreCase("YES"))
  			 {
  				//landing page Product price setting
  				LandingPageItemPerPrice(LandingPageProductPP, LandingPageProductFR,Weighttype,Weightdecimaltext,LandingPageProduct,PresetLPSettings,SiteNameType);
  				 // Discount price setting
  				 LandingPageDiscount(LandingPageDiscountValue,LandingPageDiscount);
  			}
  			 
        	 // Base price setting      
        	    BasePriceSetting(BasePrice, ProdutType, LandingPageProduct, LandingPageOption, LandingPage, IsExternalPricingON);
        	    
      		 // Discount price setting 
        	 	Discount(Discount, DiscountPercentage);
        	 	
			 // Addon price setting
			 	AddonPrice(Addons, AddonPricePerPiece);
			 	
			 // Postage price setting
			   PostageSetting(PostagePrice);
			   
			 // Coupon code price setting			
			   CouponCodePrice(PromotionDiscountPercentage, PromotionCoupon);
			   
			 // Tax price setting
			 if(Tax.isEmpty())
			 {
				System.out.println("Tax value is empty ");
				et.log(LogStatus.INFO,"********** Tax value is empty ***********");
			 }
			 else
			 {
				String[] CalculateTaxConditions = CalculateTaxCondition.split("_");
				if(CalculateTaxConditions[0].equals("---Select---") || CalculateTaxConditions[0].equals("Vertex"))
				{
					//
				}
				else
				{
					TaxSettings(Tax);
				}	
			 }
			 // Item base shipping price setting
     		 if(OrderType.equals("DynShipTOMultipleLocations"))
			 {
     		 	ShippingPrice3(OrderType, ShippingPrice, ShippingPricePerPiece, ProdutType);
			 }
			 else if(ShippingPrice.equals("0") || ShippingPrice.equals("0.00") 
			|| ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000"))
			 {
				
			 }
			 else
			 {
				ShippingPrice3(OrderType, ShippingPrice, ShippingPricePerPiece, ProdutType);
			 }
     
			 // Download price setting
			 if(DownloadPrice.equals("0") || DownloadPrice.equals("0.00") || DownloadPrice.equals("0.000") || 
					DownloadPrice.equals("0.0000"))
			 {
				
			 }
			 else
			 {
				if(OrderType.equals("DynDownload")     || OrderType.equals("DynDownloadShipping") 
				|| OrderType.equals("StaticDownload") || OrderType.equals("StaticDownloadShipping"))
				{
				DownloadPriceSetting(DownloadPrice, DecimalValue, ProdutType);
				}
			 }	
			 if(OrderBase.equals("Item"))
			 {
			  if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
						|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
					{
					  ListSetting(ProdutType);
					}
			 }
			 // Order base shipping setting and Order base setting like Item or Order
			 if(OrderBaseShipping.equals("0") || OrderBaseShipping.equals("0.00") || OrderBaseShipping.equals("0.000") || 
					OrderBaseShipping.equals("0.0000"))
			 {
				//
			 }
			 else 
			 {
			 	ShippingPriceSetting(OrderBaseShipping,ShippingPricePerPiece);
			 }
			 
			 if(PaymentType.equals("Co-Op Fund"))
			 {
			 	//String[]  
				CaptureCoOpfundDetails(PaymentSubOpt);
			 }
			 //Exempt Tax
			 IsTaxExemptSettings(IsTaxExempt,ProdutType);
			 
			 //for fullfillment location shipping/handilingfee settings
			 if((userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
		     		  userordershippingorhandlingfee.equals("0.0000"))&&(!(FullfilmentShippingOrHandlingFee.equals("0.00")
		     	 ||FullfilmentShippingOrHandlingFee.equals("0.000")||FullfilmentShippingOrHandlingFee.equals("0.0000"))))
			 	
			 {//fullfillment detail setting
			 	//System.out.println("fullfillmentdeatails");
				fullfillmentdetails(FullfilmentShippingOrHandlingFee,FullfilmentShippingMarkupFee,FullfilmentShippingOrHandlingFeeTypeAmountPercent,
			 			FullfilmentShippingMarkupFeeAmountPercentTypeAmountPercent);
			 }
			 else
			 {
			 	//System.out.println("deleting fullfillment details");
			 	deleteingfullfillmentdetails(FullfilmentShippingOrHandlingFee,FullfilmentShippingMarkupFee,FullfilmentShippingOrHandlingFeeTypeAmountPercent,
			 			FullfilmentShippingMarkupFeeAmountPercentTypeAmountPercent);
			 } 
	         //oredr elements
			if(Orderelements.equalsIgnoreCase("yes")){
				 OrderElementsSettings(Orderelements,OrderelementsAddOns,OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
						 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,
						 OrderelementsJobTicket,OrderelementsPrintPopup);
			 }
			 // Admin completed 
			  
      //  et.log(LogStatus.PASS, " Admin Logout is Done");
			 tec.AC40.Common.Wait.wait5Second();
			 
			 //WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			 // Admin logged out  
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Alogout));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Alogout));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
			    public WebElement apply(WebDriver driver) {
			    return driver.findElement(Property.Alogout);
				}
			 });
			 
			 tec.AC40.Common.Wait.wait2Second();
			 d.findElement(Property.Alogout).click();
			 et.log(LogStatus.PASS, " Admin Logout is Done");
			 tec.AC40.Common.Wait.wait5Second();
			 MouseAdjFunction();
			 // ******************** Admin Setting completed. ************************************************
 			 
			 // Adjust shipping price value based on order base and per piece or flat rate
        	 if(ShippingPricePerPiece.equals("0") || ShippingPricePerPiece.equals("0.00") || ShippingPricePerPiece.equals("0.000") || 
        			 ShippingPricePerPiece.equals("0.0000"))
             {
           	  //System.out.println("No Need to assign value");
             }
        	 else
        	 {
        	 	 if(OrderBaseShipping.equals("0") || OrderBaseShipping.equals("0.00") || OrderBaseShipping.equals("0.000") ||
        				 OrderBaseShipping.equals("0.0000"))
        		 {
        			 ShippingPrice = ShippingPricePerPiece;
        		 }
        		 else
        		 {
        			 OrderBaseShipping = ShippingPricePerPiece;
        		 }
        	 }
        	 
        	 // Adjust Item price based on per piece or flat rate
        	 double ItemPrice1 = Double.valueOf(ItemPerPrice).doubleValue();
			 
        	 if(ItemPrice1 == 0)
        	 {
        		 ItemPerPrice = FlatRate;
        	 }
        	 if(LandingPageOption.equalsIgnoreCase("YES"))
        	 {
            double LandingPageProductPP1 = Double.valueOf(LandingPageProductPP).doubleValue();
        	 if(LandingPageProductPP1 == 0)
        	 {
        		 LandingPageProductPP = LandingPageProductFR;
        	 }
        	 }
        	 
        	 //************************************  User flow Starts here **********************
	     	 // User Login method
			 userLogin();
			 et.log(LogStatus.PASS, " User Login is Done");
			 MouseAdjFunction();// it is adjust the mouse point
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShoppingCartLinkC));
			 
			 // Delete shopping cart items before placing order, if we have any
			 Thread.sleep(8000);
			 d.findElement(Property.ShoppingCartLinkC).click();
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ContinueShoppingLinkC));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ContinueShoppingLinkC));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
				public WebElement apply(WebDriver driver) {
				return driver.findElement(Property.ContinueShoppingLinkC);
				}
			 });
			 
			 tec.AC40.Common.Wait.wait2Second();
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ErrorMsgInShoppingCart));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {	
				public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ErrorMsgInShoppingCart);
				}
			 });
			 
			 tec.AC40.Common.Wait.wait2Second();
			 if( d.findElement(Property.ErrorMsgInShoppingCart).isDisplayed())
			 {
				//System.out.println("Element is displayed : ");
				d.findElement(Property.OrgunitName).click();
			 }
			 else
			 {
			 	//System.out.println("Element is not displayed : ");
			 	d.findElement(Property.EmptyCartLinkC).click();
			 	tec.AC40.Common.Wait.wait2Second();
			 	d.findElement(Property.EmptyCartConfirmOKButton).click();
			 	//tec.AC40.Common.Wait.wait5Second();
			 	waitfl.until(ExpectedConditions.elementToBeClickable(Property.ErrorMsgInShoppingCart));
			 	waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ErrorMsgInShoppingCart));
			 	waitfl.until(new Function<WebDriver, WebElement>() 
				{
					public WebElement apply(WebDriver driver) {
					return driver.findElement(Property.ErrorMsgInShoppingCart);
					}
				});
			 	
			 	tec.AC40.Common.Wait.wait5Second();
			 	d.findElement(Property.OrgunitName).click();
			 }
			 MouseAdjFunction();
			 if(Config.LayoutType.equals("Classic"))
			 {
			 	waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Products));
			 	waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Products));
			 	
			 	waitfl.until(new Function<WebDriver, WebElement>() 
				{
					public WebElement apply(WebDriver driver) {
					return driver.findElement(Property.Products);
					}
				});
			 	
			 	tec.AC40.Common.Wait.wait2Second();
			 	d.findElement(Property.Products).click();
			 	waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CreateNewIcon));
			 	waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CreateNewIcon));
			 	
			 	waitfl.until(new Function<WebDriver, WebElement>() 
				{
					public WebElement apply(WebDriver driver) {
					return driver.findElement(Property.CreateNewIcon);
					}
				});
			 	
			 	tec.AC40.Common.Wait.wait2Second();
			 	d.findElement(Property.CreateNewIcon).click();
			 }
			 else if(Config.LayoutType.equals("Layout1"))
			 {
				 
			 	waitfl.until(ExpectedConditions.elementToBeClickable(Property.HomeImageL1));
			 	waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProjectsIcon));
			 	// We are provided more wait time because script fails in this area frequently
			 	
			 	waitfl.until(new Function<WebDriver, WebElement>() 
				{
					public WebElement apply(WebDriver driver) {
					return driver.findElement(Property.HomeImageL1);
					}
				});
			 	
			 	//tec.AC40.Common.Wait.wait5Second();
			 	tec.AC40.Common.Wait.wait5Second();
			 	d.findElement(Property.ProjectsIcon).click();
			 } 
			 // Mouse over function is required for only Layout2 
			 else if(Config.LayoutType.equals("Layout2"))
			 {
				 MouseAdjFunction();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.MyProjectsIcon));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MyProjectsIcon));
				waitfl.until(new Function<WebDriver, WebElement>() 
				{
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.MyProjectsIcon);
				   }
				});
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.MyProjectsIcon).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CreateNewIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CreateNewIconL2));
				waitfl.until(new Function<WebDriver, WebElement>() 
				{
					public WebElement apply(WebDriver driver) {
					return driver.findElement(Property.CreateNewIconL2);
					}
				});
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.CreateNewIconL2).click();
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section.image_block[data-prod='"+ProdutType+"']")));
				
				tec.AC40.Common.Wait.wait2Second();
		        WebElement ProductImage = d.findElement(By.cssSelector("section.image_block[data-prod='"+ProdutType+"']"));
		        String ProductImagename = ProductImage.getAttribute("name");
		        String[] productid = ProductImagename.split("-");
		        String productidvalue = productid[1];
		        WebElement GreenTick = d.findElement(By.xpath("//img[@id='personalize-"+productidvalue+"-0-false']")); 
		        Actions builder = new Actions(d);
		        Action mouseOverHome = builder.moveToElement(ProductImage).build();
		        tec.AC40.Common.Wait.wait2Second();
		        mouseOverHome.perform();
		        Thread.sleep(2000);
		        GreenTick.click();
		        tec.AC40.Common.Wait.wait5Second();
		        if(LandingPageOption.equalsIgnoreCase("YES")){
		        	if(LandingPage.equals("Optional"))
					{
		        		d.findElement(Property.Landingpageyes).click();
					}
					else if(LandingPage.equals("Required"))
					{
						System.out.println("Landing page required");
						et.log(LogStatus.INFO,"Landing page required");
					}
		        	
					
					tec.AC40.Common.Wait.wait5Second();
					
					if(LandingPageProduct.equalsIgnoreCase("PURL")){
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section.image_block[data-prod='"+LandingPageProduct+"']")));
						
						tec.AC40.Common.Wait.wait2Second();
				        WebElement ProductImage1 = d.findElement(By.cssSelector("section.image_block[data-prod='"+LandingPageProduct+"']"));
				        String ProductImagename1 = ProductImage1.getAttribute("name");
				        String[] productid1 = ProductImagename1.split("-");
				        String productidvalue1 = productid1[1];
				        WebElement GreenTick1 = d.findElement(By.xpath("//img[@id='personalize-"+productidvalue1+"-0-false']")); 
				        Actions builder1 = new Actions(d);
				        Action mouseOverHome1 = builder1.moveToElement(ProductImage1).build();
				        tec.AC40.Common.Wait.wait2Second();
				        mouseOverHome1.perform();
				        Thread.sleep(2000);
				        GreenTick1.click();
				        tec.AC40.Common.Wait.wait5Second();
					}else if(LandingPageProduct.equalsIgnoreCase("GURL")){
                        waitfl.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section.image_block[data-prod='"+LandingPageProduct+"']")));
						tec.AC40.Common.Wait.wait2Second();
				        WebElement ProductImage1 = d.findElement(By.cssSelector("section.image_block[data-prod='"+LandingPageProduct+"']"));
				        String ProductImagename1 = ProductImage1.getAttribute("name");
				        String[] productid1 = ProductImagename1.split("-");
				        String productidvalue1 = productid1[1];
				        WebElement GreenTick1 = d.findElement(By.xpath("//img[@id='personalize-"+productidvalue1+"-0-false']")); 
				        Actions builder1 = new Actions(d);
				        Action mouseOverHome1 = builder1.moveToElement(ProductImage1).build();
				        tec.AC40.Common.Wait.wait2Second();
				        mouseOverHome1.perform();
				        Thread.sleep(2000);
				        GreenTick1.click();
					}else if(LandingPageProduct.equalsIgnoreCase("PGURL")){
                        waitfl.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section.image_block[data-prod='"+LandingPageProduct+"']")));
						
						tec.AC40.Common.Wait.wait2Second();
				        WebElement Product = d.findElement(By.cssSelector("section.image_block[data-prod='"+LandingPageProduct+"']"));
				        String Productname = Product.getAttribute("name");
				        String[] productid1 = Productname.split("-");
				        String productidvalue1 = productid1[1];
				        WebElement GreenTick1 = d.findElement(By.xpath("//img[@id='personalize-"+productidvalue1+"-0-false']")); 
				        Actions builder1 = new Actions(d);
				        Action mouseOverHome1 = builder1.moveToElement(Product).build();
				        tec.AC40.Common.Wait.wait2Second();
				        mouseOverHome1.perform();
				        Thread.sleep(2000);
				        GreenTick1.click();
					}
		 		 }
					
					
					
			 }
			 
			 if(Config.LayoutType.equals("Layout1"))
			 {// Layout1 product selection code starts here
			 	// Product selection in select product page
			 	if(ProdutType.equals("Dynamic Print"))
			 	{
			 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.DynamicPrintNamel1));
			 		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.DynamicPrintNamel1));
			 		waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.DynamicPrintNamel1);
						}
					});
			 		tec.AC40.Common.Wait.wait2Second();
			 		d.findElement(Property.DynamicPrintNamel1).click();
			 		tec.AC40.Common.Wait.wait5Second();
			 		if(LandingPageOption.equalsIgnoreCase("YES")){
			 			if(LandingPage.equalsIgnoreCase("Optional"))
			 			{
						d.findElement(Property.Landingpageyes).click();
			 			}
			 			else{
			 				System.out.println("Landing page Required");
			 				et.log(LogStatus.INFO	,"Landing page Required");
			 			}
						tec.AC40.Common.Wait.wait5Second();
						if(LandingPageProduct.equalsIgnoreCase("PURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("GURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.GurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.GurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.GurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.GurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("PGURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PGurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PGurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PGurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PGurlName).click();
						}
			 		 }
			 	}
			 	else if(ProdutType.equals("Static Print"))
			 	{
			 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.StaticPrintNamel1));
			 		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.StaticPrintNamel1));
			 		waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.StaticPrintNamel1);
						}
					});
			 		tec.AC40.Common.Wait.wait2Second();
			 		d.findElement(Property.StaticPrintNamel1).click();
			 	}
			 	else if(ProdutType.equals("Dynamic Email"))
			 	{
			 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.DynamicEmailNamel1));
			 		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.DynamicEmailNamel1));
			 		waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.DynamicEmailNamel1);
					    }
					});
			 		tec.AC40.Common.Wait.wait2Second();
			 		d.findElement(Property.DynamicEmailNamel1).click();
			 	}
			 	else if(ProdutType.equals("Static Inventory"))
			 	{
			 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.StaticInventoryNamel1));
			 		//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.StaticInventoryNamel1));
			 		waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
					    return driver.findElement(Property.StaticInventoryNamel1);
						}
					});
			 		tec.AC40.Common.Wait.wait2Second();
			 		Wait.Fluentwait(Property.StaticInventoryNamel1);
			 		d.findElement(Property.StaticInventoryNamel1).click();
			 	}
			 	else if(ProdutType.equals("Broadcast"))
			 	{
			 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.BroadcastNamel1));
			 		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.BroadcastNamel1));
			 		waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.BroadcastNamel1);
						}
					});
			 		tec.AC40.Common.Wait.wait5Second();
			 		d.findElement(Property.BroadcastNamel1).click();
			 		tec.AC40.Common.Wait.wait5Second();
			 		if(LandingPageOption.equalsIgnoreCase("YES")){
			 			
			 			if(LandingPage.equalsIgnoreCase("Optional"))
			 			{
						d.findElement(Property.Landingpageyes).click();
			 			}
			 			else{
			 				System.out.println("Landing page Required");
							et.log(LogStatus.INFO,"Landing page required");

			 			}
						tec.AC40.Common.Wait.wait5Second();
						if(LandingPageProduct.equalsIgnoreCase("PURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("GURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.GurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.GurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.GurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.GurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("PGURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PGurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PGurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PGurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PGurlName).click();
						}
			 		 }
			 	}
			 }//Layout1 product selection code ends here
			 else if(Config.LayoutType.equals("Classic"))
			 {
				if(ProdutType.equals("Dynamic Print"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.DynamicPrintName));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.DynamicPrintName));
					waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.DynamicPrintName);
						}
					});
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.DynamicPrintName).click();
					tec.AC40.Common.Wait.wait5Second();
					if(LandingPageOption.equalsIgnoreCase("YES")){
						if(LandingPage.equalsIgnoreCase("Optional"))
			 			{
						d.findElement(Property.Landingpageyes).click();
			 			}
			 			else{
			 				System.out.println("Landing page Required");
			 				et.log(LogStatus.INFO,"Landing page required");
			 	
			 			}
						tec.AC40.Common.Wait.wait5Second();
						if(LandingPageProduct.equalsIgnoreCase("PURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("GURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.GurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.GurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.GurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.GurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("PGURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PGurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PGurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PGurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PGurlName).click();
						}
			 		 }
				}
				else if(ProdutType.equals("Static Print"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.StaticPrintName));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.StaticPrintName));
					waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.StaticPrintName);
						}
					});
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.StaticPrintName).click();
				}
				else if(ProdutType.equals("Dynamic Email"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.DynamicEmailName));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.DynamicEmailName));
					waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.DynamicEmailName);
						}
					});
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.DynamicEmailName).click();
				}
				else if(ProdutType.equals("Static Inventory"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.StaticInventoryName));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.StaticInventoryName));
					waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.StaticInventoryName);
						}
					});
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.StaticInventoryName).click();
				}
				else if(ProdutType.equals("Broadcast"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.BroadcastName));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.BroadcastName));
					waitfl.until(new Function<WebDriver, WebElement>() 
					{
						public WebElement apply(WebDriver driver) {
						return driver.findElement(Property.BroadcastName);
						}
					});
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.BroadcastName).click();
					tec.AC40.Common.Wait.wait5Second();
					if(LandingPageOption.equalsIgnoreCase("YES")){
						d.findElement(Property.Landingpageyes).click();
						tec.AC40.Common.Wait.wait5Second();
						if(LandingPageProduct.equalsIgnoreCase("PURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("GURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.GurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.GurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.GurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.GurlName).click();
						}else if(LandingPageProduct.equalsIgnoreCase("PGURL")){
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.PGurlName));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PGurlName));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.PGurlName);
								}
							});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.PGurlName).click();
						}
			 		 }
				}
			 }
			
			 // Convert pricing values based on Order Amount value
			      
			 String FlatRate2 = Decimalsetting(FlatRate, OrderAmountValue);
			 FlatRate = FlatRate2;
			      
			 String DownloadPrice2 = Decimalsetting(DownloadPrice, OrderAmountValue);
			 DownloadPrice = DownloadPrice2;
			      
			 String SubTotal2 = Decimalsetting(SubTotal, OrderAmountValue);
			 SubTotal = SubTotal2;
			      
			 String DiscountCalculationFromSubTotal2 = Decimalsetting(DiscountCalculationFromSubTotal, OrderAmountValue);
			 DiscountCalculationFromSubTotal = DiscountCalculationFromSubTotal2;
			      
			 String ShippingPrice2 = Decimalsetting(ShippingPrice, OrderAmountValue);
			 ShippingPrice = ShippingPrice2;
			      
			 String FRShippingPriceUser2 = Decimalsetting(FRShippingPriceUser, OrderAmountValue);
			 FRShippingPriceUser = FRShippingPriceUser2;
			    
			 String Postage2 = Decimalsetting(Postage, OrderAmountValue);
			 Postage = Postage2;
			 String TotalPrice2 = Decimalsetting(TotalPrice, OrderAmountValue);
			 TotalPrice = TotalPrice2;
			 String PromotionCoupon2 = Decimalsetting(PromotionCoupon, OrderAmountValue);
			 PromotionCoupon = PromotionCoupon2;
			 String DiscountPrice2 = Decimalsetting(DiscountPrice, OrderAmountValue);
			 DiscountPrice = DiscountPrice2;
			 String Tax2 = Decimalsetting(Tax, OrderAmountValue);
			 Tax = Tax2;
			 String PriceAfterApplyingCoupon2 = Decimalsetting(PriceAfterApplyingCoupon, OrderAmountValue);
			 PriceAfterApplyingCoupon = PriceAfterApplyingCoupon2;
			 String PriceAfterCalculatingTax2 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
			 PriceAfterCalculatingTax = PriceAfterCalculatingTax2;
			 String OrderBaseShipping2 = Decimalsetting(OrderBaseShipping, OrderAmountValue);
			 OrderBaseShipping = OrderBaseShipping2;
			 String Totala2 = Decimalsetting(Total, OrderAmountValue);
			 Total = Totala2;
			 String SubtotalWithoutPostageORShippingPrice2 = Decimalsetting(SubtotalWithoutPostageORShippingPrice, OrderAmountValue);
			 SubtotalWithoutPostageORShippingPrice = SubtotalWithoutPostageORShippingPrice2;
			 String PostagePrice2 = Decimalsetting(PostagePrice, OrderAmountValue);
			 PostagePrice = PostagePrice2;
			 String PromotionDiscountAfterSubtractingFromSubTotal2 = Decimalsetting(PromotionDiscountAfterSubtractingFromSubTotal, OrderAmountValue);
			 PromotionDiscountAfterSubtractingFromSubTotal =PromotionDiscountAfterSubtractingFromSubTotal2;
			 String ShippingPriceAfterApplyingTax2 = Decimalsetting(ShippingPriceAfterApplyingTax, OrderAmountValue);
			 ShippingPriceAfterApplyingTax = ShippingPriceAfterApplyingTax2;
			 String ShippingPricePerPiece2 = Decimalsetting(ShippingPricePerPiece, OrderAmountValue);
			 ShippingPricePerPiece = ShippingPricePerPiece2;
			 String AddonPricePerPiece2 = Decimalsetting(AddonPricePerPiece, OrderAmountValue);
			 AddonPricePerPiece =AddonPricePerPiece2;
			 String Payment1Price2 = Decimalsetting(Payment1Price, OrderAmountValue);
			 Payment1Price = Payment1Price2;
			 String AmtAfterPayment12 = Decimalsetting(AmtAfterPayment1, OrderAmountValue);
			 AmtAfterPayment1 = AmtAfterPayment12;
			 String Payment2Price2 = Decimalsetting(Payment2Price, OrderAmountValue);
			 Payment2Price = Payment2Price2;
			 String AmtAfterPayment22 = Decimalsetting(AmtAfterPayment2, OrderAmountValue);
			 AmtAfterPayment2 = AmtAfterPayment22;
			 String Payment3Price2 = Decimalsetting(Payment3Price, OrderAmountValue);
			 Payment3Price = Payment3Price2;
			
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderName));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderName));
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
				public WebElement apply(WebDriver driver) {
				return driver.findElement(Property.OrderName);
				}
			 });
			 tec.AC40.Common.Wait.wait2Second();
			 int productdetailsnumber = 0;
			 productdetailsnumber = 1; //This variable uses in only Layout2 (Product details pop up number is different)
			 // Product details verification in personalize page
			 
			 String Quantity1 = String.format("%.0f", new BigDecimal(Quantity));
			 
			 String ExternalPricingItemPrice=PersonalizeProductDetails(ItemPerPrice, Discount, DiscountPercentage, productdetailsnumber,
					EnablePromotionsORDiscounts, IsExternalPricingON, Quantity1, BasePrice);
			 if(IsExternalPricingON.equalsIgnoreCase("Yes"))
			 {
				 ItemPerPrice = ExternalPricingItemPrice;
			 }
			 // Enter Order and description for order
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderName));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderName));
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
				public WebElement apply(WebDriver driver) {
				return driver.findElement(Property.OrderName);
				}
			 });
			 tec.AC40.Common.Wait.wait2Second();
			 d.findElement(Property.OrderName).clear();
			 d.findElement(Property.OrderName).sendKeys("Order"+dateFormat.format(date)+TestStep);
			
			 d.findElement(Property.OrderDesc).clear();
			 d.findElement(Property.OrderDesc).sendKeys("OrderNamedesc");
			
			 if(ProdutType.equals("Broadcast"))
			 {
			 	d.findElement(Property.BroadCastSubjectline).clear();
			 	d.findElement(Property.BroadCastSubjectline).sendKeys("Subject line");
			 	d.findElement(Property.PersonalizeFromEmail).clear();
			 	d.findElement(Property.PersonalizeFromEmail).sendKeys("dathareddyboge@gmail.com");
			 
			 	d.findElement(Property.Next1).click();
				 tec.AC40.Common.Wait.wait2Second();
			 }
			 
			 
			 if(LandingPageOption.equalsIgnoreCase("YES")){
				 
			     if(PresetLPSettings.equalsIgnoreCase("YES")){
				   // System.out.println("******do nothing********");
				    tec.AC40.Common.Wait.wait2Second();
				    if(ProdutType.equals("Broadcast"))
					 {
				    	//System.out.println("******do nothing********");
					 }
				    else
				    {
				    d.findElement(Property.Next1).click();
				    tec.AC40.Common.Wait.wait2Second();  
				     }
			         }
			    else if(PresetLPSettings.equalsIgnoreCase("No")){
			 
			        waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.LandingPageimg));
			        waitfl.until(new Function<WebDriver, WebElement>() 
			        {
				     public WebElement apply(WebDriver driver) {
				     return driver.findElement(Property.LandingPageimg);
				     }
			          });
			         tec.AC40.Common.Wait.wait5Second();
			      
			 
				      if (SiteNameType.equalsIgnoreCase("Custom")){
					     d.findElement(Property.siteoption).click();
					     tec.AC40.Common.Wait.wait2Second();
					     d.findElement(Property.sitename).clear();
					     d.findElement(Property.sitename).sendKeys(dateFormat.format(date));
					     tec.AC40.Common.Wait.wait2Second();
					     d.findElement(Property.Customsitecheck).click();
					     tec.AC40.Common.Wait.wait5Second();
				      }
					     if (LandingPageProduct.equalsIgnoreCase("PURL")){
					   
					    	 d.findElement(Property.userlandingfield).click();
					      	 tec.AC40.Common.Wait.wait2Second();
					      	 Keyboard LP = ((RemoteWebDriver) d).getKeyboard();
					      	 LP.pressKey(Keys.DOWN);
					      	 LP.pressKey(Keys.DOWN);
					      	 LP.pressKey(Keys.DOWN);
					      	 LP.pressKey(Keys.ENTER);
					     }
					     else{
						    	 
					    	   tec.AC40.Common.Wait.wait2Second();
						      	System.out.println("selected landing page is not PURL");
						      	et.log(LogStatus.INFO,"selected landing page is not PURL");
				      	
					     }
			 
				      	 tec.AC40.Common.Wait.wait5Second();
				      	 d.findElement(Property.Next1).click();
				      	 tec.AC40.Common.Wait.wait2Second();
			    	}
			 	}
			 else{
				 if (ProdutType.equals("Broadcast"))
			       {
			      
			       }
			      else{
			       tec.AC40.Common.Wait.wait5Second();
			       d.findElement(Property.Next1).click();
			       tec.AC40.Common.Wait.wait2Second();
			       }
			 }
			 
			 
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Listsearchbox));
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
				public WebElement apply(WebDriver driver) {
				return driver.findElement(Property.Listsearchbox);
				}
			 });
			 
			 tec.AC40.Common.Wait.wait2Second();
			
			 productdetailsnumber = 2;
			 // Product details verification in select list page
			 PersonalizeProductDetails(ItemPerPrice, Discount, DiscountPercentage, productdetailsnumber,
					EnablePromotionsORDiscounts, IsExternalPricingON, Quantity1, BasePrice);
			 tec.AC40.Common.Wait.wait2Second();
			 if(IsExternalPricingON.equalsIgnoreCase("NO"))
			 {
				 ViewPricingDetails(ItemPerPrice, Discount, DiscountPercentage,
					 IsExternalPricingON, Quantity1);
			 }
			 if(OrderBase.equalsIgnoreCase("Split Ship") && (OrderTypeSplitShip.equalsIgnoreCase("Ship items to multiple adresses")||OrderType.equals("ShipToMyAddress"))){
					d.findElement(Property.ShipToMyAddress).click();
	                tec.AC40.Common.Wait.wait2Second();
	                String Quantity11 = String.format("%.0f", new BigDecimal(Quantity));
					d.findElement(Property.Quantity).clear();
					
					d.findElement(Property.Quantity).sendKeys(""+Quantity11);
			 }
			 else if(OrderType.equals("DynDropShipment with List"))
			 { 
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.DropshipmentWithList).click();
					tec.AC40.Common.Wait.wait2Second();
					if(Quantity1.equals("7"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList7);
					}
					else if(Quantity1.equals("10"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList10);
					}
					else if(Quantity1.equals("2"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList2);
					}
					else if(Quantity1.equals("3"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList3);
					}
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.Listcheckbox).click();
					tec.AC40.Common.Wait.wait10Second();
					if(OrderBase.equals("Item"))
					{
						//
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait5Second();
						waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						tec.AC40.Common.Wait.wait5Second();
						if(Weighttype.equals("KGS") || Weighttype.equals("LBS"))
						{
							//System.out.println("Third party shipping ");
							tec.AC40.Common.Wait.wait25Second();
						}
						waitfl.until(ExpectedConditions.elementToBeClickable(Property.PhoneTextBox));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PhoneTextBox));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.PhoneTextBox);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait5Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.Upsshippingmethod);
								}
							});
							tec.AC40.Common.Wait.wait10Second();
							d.findElement(Property.Upsshippingmethod).click();
						}
						else
						{
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							System.out.println("1 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
							et.log(LogStatus.INFO, "1 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice, Priceafterapplyingfulfillmentshippingmarkupfee);
						}
				
					}
				}
				else
				{
					d.findElement(Property.DropshipmentWithList).click();
					tec.AC40.Common.Wait.wait2Second();
					if(Quantity1.equals("7"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList7);
					}
					else if(Quantity1.equals("10"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList10);
					}
					else if(Quantity1.equals("2"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList2);
					}
					else if(Quantity1.equals("3"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList3);
					}
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.Listcheckbox).click();
					tec.AC40.Common.Wait.wait10Second();
					if(OrderBase.equals("Item"))
					{
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
						}
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.Upsshippingmethod);
								}
							});
							tec.AC40.Common.Wait.wait10Second();
							d.findElement(Property.Upsshippingmethod).click();
						}
						else
						{
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
						}
					}
				}
			 }
			 else if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
			 {
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMyAddress).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.ShipToMyAddressDownload).click();
				}
				else
				{
					d.findElement(Property.Layout2Download).click();
					tec.AC40.Common.Wait.wait5Second();
				}
			 }
			 else if(OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping"))
			 {
				d.findElement(Property.ShipToMyAddress).click();
				tec.AC40.Common.Wait.wait5Second();
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMyAddressDownLoadPrint).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity1);
					tec.AC40.Common.Wait.wait5Second();
					if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					if(OrderBase.equals("Item"))
					{
						//d.findElement(Property.ShippingMethodsLink).click();
						//tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Quantity));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Quantity));
						//d.findElement(Property.Quantity).click();
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.Quantity);
							}
						});
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							d.findElement(Property.Upsshippingmethod).click();
						}
						else
						{
							tec.AC40.Common.Wait.wait5Second();
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							System.out.println("2 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
                                  et.log(LogStatus.INFO,"2 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
						}
				
					}
				}
				else
				{
					d.findElement(Property.Layout1and2shippingwithdownload).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity1);
					tec.AC40.Common.Wait.wait5Second();
					if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					if(OrderBase.equals("Item"))
					{
						//d.findElement(Property.ShippingMethodsLink).click();
						//tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Quantity));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Quantity));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{//div[@id='divShipMethods']/span/span/span
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.Quantity);
							}
						});
						//d.findElement(Property.Quantity).click();
						Wait.Fluentwait(Property.PhoneTextBox);
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						Wait.Fluentwait(Property.ShippingSelect);
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						Wait.Fluentwait(Property.ShippingSelect);
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
							d.findElement(Property.Upsshippingmethod).click();
							tec.AC40.Common.Wait.wait15Second();
						}
						else
						{
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
						}
					
					}
				}
			 }
			 else if(OrderType.equals("ShipToMyAddress"))
			 {
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMyAddress).click();
					tec.AC40.Common.Wait.wait5Second();
					if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					tec.AC40.Common.Wait.wait10Second();
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity1);
					tec.AC40.Common.Wait.wait5Second();
					 if((OrderelementsCheckout.equalsIgnoreCase("Yes")&&OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(OrderelementsCheckout.equalsIgnoreCase("Yes")&&OrderelementsShippingMethod.equalsIgnoreCase("NO"))){
						 /*waitfl.until(ExpectedConditions.elementToBeClickable(Property.ContinueSelectList));
						 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ContinueSelectList));
						 waitfl.until(new Function<WebDriver, WebElement>() 
								 {
								   public WebElement apply(WebDriver driver) {
								   return driver.findElement(Property.ContinueSelectList);
								 }
								 });

						 d.findElement(Property.ContinueSelectList).click();*/
					 }else{
						 if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
							{
								tec.AC40.Common.Wait.wait25Second();
							}
							
							if(OrderBase.equals("Item"))
							{				

								waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
								waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
								waitfl.until(new Function<WebDriver, WebElement>() 
								{
									public WebElement apply(WebDriver driver) {
									return driver.findElement(Property.ShippingRefresh);
									}
								});
								d.findElement(Property.ShippingRefresh).click();
								tec.AC40.Common.Wait.wait5Second();
								waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingRefresh));
								waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
								waitfl.until(new Function<WebDriver, WebElement>() 
								{
									public WebElement apply(WebDriver driver) {
									return driver.findElement(Property.ShippingRefresh);
									}
								});
								tec.AC40.Common.Wait.wait2Second();
								if(Weighttype.equals("KGS")||Weighttype.equals("LBS") || 
										IsExternalPricingON.equalsIgnoreCase("YES"))
								{
									tec.AC40.Common.Wait.wait25Second();
								}
								waitfl.until(ExpectedConditions.elementToBeClickable(Property.PhoneTextBox));
								waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PhoneTextBox));
										waitfl.until(new Function<WebDriver, WebElement>() {
											public WebElement apply(WebDriver driver) {
												return driver
														.findElement(Property.PhoneTextBox);
											}
										});
								tec.AC40.Common.Wait.wait2Second();
								d.findElement(Property.PhoneTextBox).click();
								tec.AC40.Common.Wait.wait2Second();
								d.findElement(Property.ShippingRefresh).click();
								tec.AC40.Common.Wait.wait2Second();
								waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
								waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
										waitfl.until(new Function<WebDriver, WebElement>() {
											public WebElement apply(WebDriver driver) {
												return driver
														.findElement(Property.ShippingSelect);
											}
										});
								tec.AC40.Common.Wait.wait5Second();
								if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
								{
									tec.AC40.Common.Wait.wait2Second();
									waitfl.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Property.Upsshippingmethod));
									waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
											waitfl.until(new Function<WebDriver, WebElement>() {
												public WebElement apply(WebDriver driver) {
													return driver
															.findElement(Property.Upsshippingmethod);
												}
											});
									d.findElement(Property.Upsshippingmethod).click();
								}
								else
								{
									tec.AC40.Common.Wait.wait2Second();
									waitfl.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Property.ShippingSelect));
									waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
											waitfl.until(new Function<WebDriver, WebElement>() {
												public WebElement apply(WebDriver driver) {
													return driver
															.findElement(Property.ShippingSelect);
												}
											});
									d.findElement(Property.ShippingSelect).click();
									String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
									String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
									//System.out.println("5 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
									VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
								}
							}
					 }

					
				}
				else 
				{
					d.findElement(Property.ShipToMyAddress).click();
					tec.AC40.Common.Wait.wait5Second();if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity1);
					tec.AC40.Common.Wait.wait5Second();
					if(OrderBase.equals("Item"))
					{
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
								waitfl.until(new Function<WebDriver, WebElement>() {
									public WebElement apply(WebDriver driver) {
										return driver
												.findElement(Property.ShippingRefresh);
									}
								});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
						}
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
								waitfl.until(new Function<WebDriver, WebElement>() {
									public WebElement apply(WebDriver driver) {
										return driver
												.findElement(Property.ShippingRefresh);
									}
								});
						tec.AC40.Common.Wait.wait2Second();
						Wait.Fluentwait(Property.PhoneTextBox);
						//waitfl.until(ExpectedConditions.elementToBeClickable(Property.PhoneTextBox));
						tec.AC40.Common.Wait.wait10Second();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait5Second();
						
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait5Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
							waitfl.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Property.Upsshippingmethod));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.Upsshippingmethod);
										}
									});
							d.findElement(Property.Upsshippingmethod).click();
							tec.AC40.Common.Wait.wait15Second();
						}
						else
						{
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.ShippingSelect);
										}
									});
							tec.AC40.Common.Wait.wait2Second();
							d.findElement(Property.ShippingSelect).click();
							
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							System.out.println("6 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
							et.log(LogStatus.INFO, "6 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
						}
					}
				}
			 }
			 else if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
					|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
			 {
				 if(OrderBase.equalsIgnoreCase("Split Ship")){
		                tec.AC40.Common.Wait.wait2Second();
		                String Quantity11 = String.format("%.0f", new BigDecimal(Quantity));
						d.findElement(Property.Quantity).clear();
						
						d.findElement(Property.Quantity).sendKeys(""+Quantity11);
				 }else{

				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMultipleLocationsC).click();
				}
				else
				{
					d.findElement(Property.ShipToMultipleLocations).click();
				}
				tec.AC40.Common.Wait.wait2Second();
				int Quantity2 = Integer.parseInt(Quantity1);
				int Quantity21 =  0;
				int Quantity22 = 0;
				if(Quantity2 > 10)
				{
					Quantity21 = Quantity2 -5;
					Quantity22 = 5;
				}
				else
				{
					Quantity21 = Quantity2;
				}
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.AddNewShippingAddress).click();
				tec.AC40.Common.Wait.wait5Second();
				
				d.findElement(Property.ShipMultipleLocationsAddress1).sendKeys("38345 W.10 Mile Road");
				d.findElement(Property.ShipMultipleLocationsCity).sendKeys("Formington Hills");
				d.findElement(Property.ShipMultipleLocationsZip).sendKeys("78732");
                d.findElement(Property.ShipMultiPleLocationsCountryDDL).click();
                tec.AC40.Common.Wait.wait2Second();
                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				kb.sendKeys("USA");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShipMultiPleLocationsStateDDL).click();
	            tec.AC40.Common.Wait.wait2Second();
	            kb.sendKeys("TEXAS");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
	            
	            
				d.findElement(Property.ShipMultipleLocationsQuantity).clear();
				
				d.findElement(Property.ShipMultipleLocationsQuantity).sendKeys(""+Quantity21);
				tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.MultiShippingMethodsLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsClick));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsClick));
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.ShippingMultiplelocationsClick);
							}
						});
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				//tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait10Second();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingSelectMultiplelocations));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelectMultiplelocations));
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.ShippingSelectMultiplelocations);
							}
						});
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait25Second();
				}
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.ShipMultipleLocationsQuantity).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait2Second();
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					d.findElement(Property.addedupsShippingSelectMultiplelocations).click();
				}
				else
				{
				d.findElement(Property.ShippingSelectMultiplelocations).click();
				
				String SelectListActualShipping1 = d.findElement(Property.SelectedMultipleShippingValue).getText();
				String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
				System.out.println("3 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
				et.log(LogStatus.INFO, "3 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
				VerfiyShippingAmountShipToMultipleLocations(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee,
						Quantity21,Quantity2,OrderAmountValue,ShippingPricePerPiece);
				}
				
				tec.AC40.Common.Wait.wait5Second();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsSave));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsSave));
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.ShippingMultiplelocationsSave);
							}
						});
				d.findElement(Property.ShippingMultiplelocationsSave).click();
				tec.AC40.Common.Wait.wait10Second();
				
				
				// Second shipping address settings starts
				if(Quantity2 > 10)
				{
				d.findElement(Property.AddNewShippingAddress).click();
				tec.AC40.Common.Wait.wait5Second();
				
				d.findElement(Property.ShipMultipleLocationsAddress1).sendKeys("38345 W.10 Mile Road");
				d.findElement(Property.ShipMultipleLocationsCity).sendKeys("Formington Hills");
				d.findElement(Property.ShipMultipleLocationsZip).sendKeys("78732");
                d.findElement(Property.ShipMultiPleLocationsCountryDDL).click();
                tec.AC40.Common.Wait.wait2Second();
				kb.sendKeys("USA");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShipMultiPleLocationsStateDDL).click();
	            tec.AC40.Common.Wait.wait2Second();
	            kb.sendKeys("TEXAS");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
				
				d.findElement(Property.ShipMultipleLocationsQuantity).clear();
				
				d.findElement(Property.ShipMultipleLocationsQuantity).sendKeys(""+Quantity22);
				tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.MultiShippingMethodsLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsClick));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsClick));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.ShippingMultiplelocationsClick);
								}
							});
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				//tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait10Second();
				//waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingSelectMultiplelocations2ndtime));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelectMultiplelocations2ndtime));
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait25Second();
				}
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.ShipMultipleLocationsQuantity).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait2Second();
				
				tec.AC40.Common.Wait.wait5Second();
				//org.openqa.selenium.Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.ENTER);
				
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait2Second();
				}
				else
				{
				String SelectListActualShipping3 = d.findElement(Property.SelectedMultipleShippingValue).getText();
				String[] SelectListActualShipping4 = SelectListActualShipping3.split(" ");
				System.out.println("4 SelectListActualShipping4 :"+SelectListActualShipping4[2]);
				et.log(LogStatus.INFO, "4 SelectListActualShipping4 :"+SelectListActualShipping4[2]);
				VerfiyShippingAmountShipToMultipleLocations(SelectListActualShipping4[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee,
						Quantity22,Quantity2,OrderAmountValue,ShippingPricePerPiece);
				}
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsSave));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsSave));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.ShippingMultiplelocationsSave);
								}
							});
				d.findElement(Property.ShippingMultiplelocationsSave).click();
				tec.AC40.Common.Wait.wait10Second();
				}
			 }}
			 else
			 {
				if(Quantity.equals("7") || Quantity.equals("7.0") || Quantity.equals("7.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList7);
				}
				else if(Quantity1.equals("10") || Quantity.equals("10.0") || Quantity.equals("10.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList10);
				}
				else if(Quantity1.equals("2") || Quantity.equals("2.0") || Quantity.equals("2.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList2);
				}
				else if(Quantity1.equals("3") || Quantity.equals("3.0") || Quantity.equals("3.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList3);
				}
				
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.Listcheckbox).click();
			 }
             
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ContinueSelectList));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ContinueSelectList));
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ContinueSelectList);
					 }
					 });

			 d.findElement(Property.ContinueSelectList).click();
			 tec.AC40.Common.Wait.wait10Second();
			 tec.AC40.Common.Wait.wait5Second();
			 
			/* waitfl.until(ExpectedConditions.elementToBeClickable(Property.AddToCart));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.AddToCart));
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.AddToCart);
					 }
					 });
			 tec.AC40.Common.Wait.wait5Second();*/
			if(ProdutType.equals("Dynamic Print"))
			{
			/* waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.VSPageProof);
					 }
					 });*/
			}
			
			 tec.AC40.Common.Wait.wait10Second();
			 // It is use full whenever we have multiple shipping locations
		     if(FRShippingPriceUser.equals("0.00") || FRShippingPriceUser.equals("0.000") || FRShippingPriceUser.equals("0.0000")
		    		  || FRShippingPriceUser.equals("0"))
		     {
		     	  //
		     }
		     else
		     {
		     	  ShippingPrice = FRShippingPriceUser;
		     }
		     //System.out.println(" ShippingPrice :"+ShippingPrice);
       
			 productdetailsnumber = 3;
			 PersonalizeProductDetails(ItemPerPrice, Discount, DiscountPercentage, productdetailsnumber,
					EnablePromotionsORDiscounts, IsExternalPricingON, Quantity1,BasePrice);
			 tec.AC40.Common.Wait.wait2Second();
			 
			 	if(BasePrice.equals("0")|| BasePrice.equals("0.0") || BasePrice.equals("0.00") ||
						BasePrice.equals("0.000") || BasePrice.equals("0.0000"))
				{
					// Application displays base price value in else condition only
				}
				else
				{
				BasePriceIncrementValue = 0;
				String subtotal = SubTotalCalculation(ItemPerPrice,FlatRate,Quantity1,DiscountCalculationFromSubTotal,
							OrderAmountValue, BasePrice, OrderType, DownloadPrice);
				SubTotal = subtotal;
				if(BasePriceIncrementValue == 1)
				{
					if(OrderType.contains("Download"))
					{
						// in this time item price is differnt from subtotal
						System.out.println("Dwonaload+Shipping If condition");
						et.log(LogStatus.INFO, "Dwonaload+Shipping If condition");
						double DownloadPrice1 =  Double.parseDouble(DownloadPrice);
						double subtotal1 = Double.parseDouble(subtotal);
						double ItemPrice = subtotal1 - DownloadPrice1;
						ItemPerPrice = ""+ItemPrice;
						String ItemPerPrice1 = Decimalsetting(ItemPerPrice, OrderAmountValue);
						ItemPerPrice = ItemPerPrice1;
						
					}
					else
					{
						System.out.println("Dwonaload+Shipping else condition");
						et.log(LogStatus.INFO, "Dwonaload+Shipping else condition");
						ItemPerPrice = ""+subtotal;
						
					}
				
				DiscountCalculationFromSubTotal = "0.00";
				Discount = "0.00";
				DownloadPrice = "0.00";
				String DiscountCalculationFromSubTotal1 = Decimalsetting(DiscountCalculationFromSubTotal, OrderAmountValue);
				DiscountCalculationFromSubTotal = DiscountCalculationFromSubTotal1;
				
				String Discount1 = Decimalsetting(Discount, OrderAmountValue);
				Discount = Discount1;
				
				
				}
				if(BasePriceDownload == 1)
				{
					ItemPerPrice = "0.00";
					String ItemPerPrice1 = Decimalsetting(ItemPerPrice, OrderAmountValue);
					ItemPerPrice = ItemPerPrice1;
				}
				
				}
			 
			 
			 if(OrderType.equals("DropShipment with List"))
			 {
			 	d.findElement(Property.UserAddonCheckBoxDSWL).click();
			 }
			 else if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") 
					|| ProdutType.equals("Dynamic Email"))
			 {
			 
			 }
			 else
			 {
			 	waitfl.until(ExpectedConditions.elementToBeClickable(Property.userAddonCheckbox));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.userAddonCheckbox));
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.userAddonCheckbox);
						 }
						 });
				d.findElement(Property.userAddonCheckbox).click();
			 }
			 tec.AC40.Common.Wait.wait2Second();
			 if(Addons.equals("0") || Addons.equals("0.00") || Addons.equals("0.000") || Addons.equals("0.0000"))
			 {
			 
			 }
			 else
			 {
			 	AddonPriceverify(Addons, AddonPricePerPiece, TestCase, TestStep,Parameters);
			 }
			 
			 if(AddonPricePerPiece.equals("0") || AddonPricePerPiece.equals("0.00") || AddonPricePerPiece.equals("0.000") || AddonPricePerPiece.equals("0.0000"))
             {
          	  //System.out.println("No Need to assign value");
             }
		 	 else
			 {
			 	Addons = AddonPricePerPiece;
       	 	 }
			 
			 // Discount and Addon values modification based on Order amount value setting
			
			 String Discount2 = Decimalsetting(Discount, OrderAmountValue);
		     Discount = Discount2;

		     String Addons2 = Decimalsetting(Addons, OrderAmountValue);
		     Addons = Addons2;
		     String SubTotalEP = null;
		     String TotalPriceEP = null;
		     if(IsExternalPricingON.equalsIgnoreCase("Yes"))
		     {
		    	String ItemPerPriceWithDollar = d.findElement(Property.VSItemPrice).getText();
		    	 ItemPerPrice = ItemPerPriceWithDollar.substring(1,ItemPerPriceWithDollar.length());
		    	 SubTotal = ExternalPricingSubTotalCalculation(Quantity1, ItemPerPrice,FlatRate);
		    	 SubTotalEP = SubTotal;
		    	 String SubTotal3 = Decimalsetting(SubTotal, OrderAmountValue);
		    	 SubTotal = SubTotal3;
		    	 
		    	 if(DiscountPercentage.equals("Y"))
		    	 {
		    		 DiscountCalculationFromSubTotal = CalculateDiscount(SubTotal,Discount,OrderAmountValue); 
		    		 TotalPrice = ExternalPricingTotalPriceCalculation(SubTotal,DiscountCalculationFromSubTotal,Addons,Postage,ShippingPrice,DownloadPrice);
		    	 }
		    	 else if(DiscountPercentage.equals("N"))
		    	 {
		    	 TotalPrice = ExternalPricingTotalPriceCalculation(SubTotal,Discount,Addons,Postage,ShippingPrice,DownloadPrice);
		    	 }
		    	 TotalPriceEP =  TotalPrice;
		    	 String TotalPrice3 = Decimalsetting2(TotalPrice, OrderAmountValue);
		    	 TotalPrice = TotalPrice3;
			 }
		     
		     if(IsExternalPricingON.equalsIgnoreCase("Yes"))
		     {
		     String SubTotal3 = Decimalsetting2(SubTotal, OrderAmountValue);
	    	 SubTotal = SubTotal3;
		     }
         
			 ViewSummaryPriceInformation(Quantity, ItemPerPrice, SubTotal, Discount, Addons, Postage, TotalPrice, DiscountPercentage,
				DiscountCalculationFromSubTotal, ShippingPrice, DownloadPrice, OrderType, TestCase, TestStep, Parameters, 
				ProdutType, OrderBase, EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,
				Priceafterapplyingfulfillmentshippingmarkupfee,OrderAmountValue,LandingPageOption,DecimalValue, BasePriceIncrementValue,Orderelements,OrderelementsAddOns,
				OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
				 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup);
		
			 d.findElement(Property.AddToCart).click();
			 tec.AC40.Common.Wait.wait10Second();
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Checkout));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Checkout));
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.Checkout);
					 }
					 });
			 tec.AC40.Common.Wait.wait2Second();
			 
			 ShoppingCartPriceInformation(Quantity,SubTotal, ItemPerPrice, Discount, Addons, Postage, TotalPrice, DiscountPercentage, ShippingPrice,
				OrderType, TestCase, TestStep, Parameters, ProdutType,OrderBase,Weighttype, EnablePromotionsORDiscounts,DiscountCalculationFromSubTotal,
				Priceafterapplyingfulfillmentshippingmarkupfee,OrderAmountValue,LandingPageOption,LandingPageProduct,LandingPageProductPP,
				LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,DiscountCalculationFromSubTotal,DecimalValue);
			 Wait.Fluentwait(Property.Checkout);
			 tec.AC40.Common.Wait.wait10Second();
			 d.findElement(Property.Checkout).click();
			 tec.AC40.Common.Wait.wait5Second();			 

			 tec.AC40.Common.Wait.wait5Second();

			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderCheckoutGridDwonArrow));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderCheckoutGridDwonArrow));
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.OrderCheckoutGridDwonArrow);
					 }
					 });
			 //	d.findElement(Property.OrderCheckoutGridDwonArrow).isDisplayed();
			 tec.AC40.Common.Wait.wait2Second();
			 tec.AC40.Common.Wait.wait5Second(); 
			 d.findElement(Property.OrderCheckoutGridDwonArrow).click();
			 tec.AC40.Common.Wait.wait5Second();
			 tec.AC40.Common.Wait.wait2Second();
			 if(OrderBase.equalsIgnoreCase("Split Ship") && OrderTypeSplitShip.equalsIgnoreCase("Ship items to multiple adresses")){
					d.findElement(Property.MultipleShipping).click() ;
					 tec.AC40.Common.Wait.wait5Second();
				 int Quantity2 = Integer.parseInt(Quantity1);
					int Quantity21 =  0;
					int Quantity22 = 0;
					if(Quantity2 > 10)
					{
						Quantity21 = Quantity2 -5;
						Quantity22 = 5;
					}
					else
					{
						Quantity21 = Quantity2;
					}
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.splitquantity).click();
					d.findElement(Property.splitquantity).clear();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.splitquantity).sendKeys(""+Quantity21);
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.shippingdropdown).click() ;
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.btnSelectedAddresses).click() ;
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.AddNewSpiltShipAddress).click() ;
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.rdbtn_ShippingContact).click() ;
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.SelectedAddresses).click() ;
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.shippingdropdown).click() ;
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.btnSelectedAddresses).click() ;				
			 }
			 
			 
			 //Shipping Address Same as Billing Address
			 if(OrderelementsBillingAddress.equalsIgnoreCase("Yes")||Orderelements.equalsIgnoreCase("NO")){
				 
			 if(ZeroAmountorder.equalsIgnoreCase("NO")){
			 if((OrderBase.equalsIgnoreCase("Order")||OrderBase.equalsIgnoreCase("Split Ship"))&& (OrderType.equals("DynDropShipment with List") ||
			 		OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
			 		|| OrderType.equals("ShipToMyAddress")) && IfShippingaddressIseditble.equalsIgnoreCase("NO"))
			 {
				 
				String[] ShipAddSameAsBillAddArrary = ShipAddSameAsBillAdd.split("_");	
				ShipAddSameAsBillAdd = 	ShipAddSameAsBillAddArrary[0];
				int ShipAddSameAsBillAddSize = 	ShipAddSameAsBillAddArrary.length;
				//System.out.println("ShipAddSameAsBillAddSize :"+ShipAddSameAsBillAddSize);
				//Check box related code
				boolean SameAsBillAddStatus = d.findElement(Property.SameAsBillAddStatus).isSelected();
				if(ShipAddSameAsBillAdd.equals("YES"))
				{
					ShipAddSameAsBillAddSub = " ";
					if(SameAsBillAddStatus == true)
					{
						System.out.println("As expected Same as Billing Add check box selected successfully");
						et.log(LogStatus.PASS,"As expected Same as Billing Add check box selected successfully");

					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "CostCenter";
						String PageName = "Order checkout";
						System.out.println("<----- In Ordercheckout page Same as Billing Add check not selected ------>"+ErrorNumber);
					et.log(LogStatus.ERROR, "<----- In Ordercheckout page Same as Billing Add check not selected ------>"+ErrorNumber);
						System.out.println("Actual value is :"+"Same as Billing address check box NOT selected to user");
						et.log(LogStatus.ERROR, "Actual value is :"+"Same as Billing address check box NOT selected to user");
						System.out.println("Expected value is :"+"Same as Billing address check box selected to user");
						et.log(LogStatus.PASS,"Expected value is :"+"Same as Billing address check box selected to user");
						RW_File.WriteResult("Same as Billing address check box selected to user", "Same as Billing address check box NOT selected to user", PageName, PriceType);
					}
				}
				else if(ShipAddSameAsBillAdd.equals("NO"))
				{
					ShipAddSameAsBillAddSub = " ";
					if(SameAsBillAddStatus == false)
					{
						//System.out.println("As expected Same as Billing Add check box NOT selected successfully");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "CostCenter";
						String PageName = "Order checkout";
						System.out.println("<----- In Ordercheckout page Same as Billing Add check selected ------>"+ErrorNumber);
						et.log(LogStatus.ERROR,"<----- In Ordercheckout page Same as Billing Add check selected ------>"+ErrorNumber);
						
						System.out.println("Actual value is :"+"Same as Billing address check box selected to user");
						et.log(LogStatus.PASS,"Actual value is :"+"Same as Billing address check box selected to user");
						System.out.println("Expected value is :"+"Same as Billing address check box NOT selected to user");
						et.log(LogStatus.FAIL, "Expected value is :"+"Same as Billing address check box NOT selected to user");
						RW_File.WriteResult("Same as Billing address check box NOT selected to user", "Same as Billing address check box selected to user", PageName, PriceType);
					}
				}
			
				//Verify Both Shipping and Billing address are same or not first Time
				String BillingAddressValue = d.findElement(Property.BillingAddressValue).getText();
				String ShippingAddressValue = d.findElement(Property.ShippingAddressValue).getText();		
				//System.out.println("BillingAddressValue :"+BillingAddressValue);
				//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
			 
				// Verify Both Shipping and Billing address values --First time 
				String ExpectedBillAdd1 = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\n Edit  |  Show My Addresses";
				String ExpectedShippAdd1 = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\n Edit";
				
				
				System.out.println("TESTING ADDRESS:: "+ ExpectedShippAdd1 );
				
				
				String ExpectedShippAdd1b = null;
				if(Config.LayoutType.equals("Layout1"))
				{
				ExpectedShippAdd1b = "38345 W.10 Mile Road"+"\n suite 115"+"\n Formington Hills, Texas 78732"+"\n Edit  |  Show My Addresses ";
				}
				else
				{
				ExpectedShippAdd1b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit |  Show My Addresses  ";
				}
				
				if(ShipAddSameAsBillAdd.equals("YES"))
				{
					if(BillingAddressValue.equals(ExpectedBillAdd1) && 
						(ShippingAddressValue.equals(ExpectedShippAdd1)))
					{
						//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "ShipAddSameAsBillAdd";
						String PageName = "Order checkout";
						System.out.println("<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ------->"+ErrorNumber);
						et.log(LogStatus.ERROR, "<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ------->"+ErrorNumber);
						System.out.println("Actual Billg value is :"+BillingAddressValue);
						et.log(LogStatus.INFO, "Actual Billg value is :"+BillingAddressValue);
						
						System.out.println("Actual Shipping value is :"+ShippingAddressValue);
						et.log(LogStatus.INFO, "Actual Shipping value is :"+ShippingAddressValue);
						
						System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
						et.log(LogStatus.INFO,"ExpectedBillAdd1 :"+ExpectedBillAdd1);
						
						System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
						et.log(LogStatus.INFO,"ExpectedShippAdd1 :"+ExpectedShippAdd1);
						RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
					}
				}
				else
				{
					if(BillingAddressValue.equals(ExpectedBillAdd1) && 
						(ShippingAddressValue.equals(ExpectedShippAdd1b)))
					{
						//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "ShipAddSameAsBillAdd";
						String PageName = "Order checkout";
						System.out.println("<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links -------->"+ErrorNumber);
						et.log(LogStatus.ERROR,"<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links -------->"+ErrorNumber);
						System.out.println("Actual Billg value is :"+BillingAddressValue);
						et.log(LogStatus.INFO,"Actual Billg value is :"+BillingAddressValue);
						
						System.out.println("Actual Shipping value is :"+ShippingAddressValue);
						et.log(LogStatus.INFO,"Actual Shipping value is :"+ShippingAddressValue);
						
						System.out.println("Expected Billing Add :"+ExpectedBillAdd1);
						et.log(LogStatus.INFO,"Expected Billing Add :"+ExpectedBillAdd1);
						System.out.println("Expected Shippint Add :"+ExpectedShippAdd1b);
						et.log(LogStatus.INFO,"Expected Shippint Add :"+ExpectedShippAdd1b);
						RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
					}
				}
			
				d.findElement(Property.BillingEditLink).click();
				
				tec.AC40.Common.Wait.wait5Second();
				if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.BillingPopAdd1TextBoxL1));
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.BillingPopAdd1TextBoxL1);
							 }
							 });
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.BillingPopAdd1TextBoxL1).clear();
					d.findElement(Property.BillingPopAdd1TextBoxL1).sendKeys("38345 W.10 Mile Roada");
					d.findElement(Property.BillingPopAdd2TextBoxL1).clear();
					d.findElement(Property.BillingPopAdd2TextBoxL1).sendKeys("suite 115b");
				}
				else
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.BillingPopAdd1TextBox));
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.BillingPopAdd1TextBox);
							 }
							 });
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.BillingPopAdd1TextBox).clear();
					d.findElement(Property.BillingPopAdd1TextBox).sendKeys("38345 W.10 Mile Roada");
					d.findElement(Property.BillingPopAdd2TextBox).clear();
					d.findElement(Property.BillingPopAdd2TextBox).sendKeys("suite 115b");
				}
				
				d.findElement(Property.BillingPopSaveButton).click();
				tec.AC40.Common.Wait.wait10Second();
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait2Second();
				}
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.BillingPopCancelButton));
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.BillingPopCancelButton));
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.BillingPopCancelButton);
						 }
						 });
				tec.AC40.Common.Wait.wait10Second();
				d.findElement(Property.BillingPopCancelButton).click();
				tec.AC40.Common.Wait.wait5Second();
			 
				String ExpectedBillAdd2 = "38345 W.10 Mile Roada"+"\nsuite 115b"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
				String ExpectedShippAdd2 = "38345 W.10 Mile Roada"+"\nsuite 115b"+"\nFormington Hills, Texas 78732"+"\nEdit";
				String ExpectedShippAdd2b = null;
				if(Config.LayoutType.equals("Layout1"))
				{
					ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
				}
				else
				{
					ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit |  Show My Addresses  ";	
				}
				String BillingAddressValue2 = d.findElement(Property.BillingAddressValue).getText();
				String ShippingAddressValue2 = d.findElement(Property.ShippingAddressValue).getText();		
				//System.out.println("BillingAddressValue2 :"+BillingAddressValue2);
				//System.out.println("ShippingAddressValue2 :"+ShippingAddressValue2);
			 
				if(ShipAddSameAsBillAdd.equals("YES"))
				{
					if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
						(ShippingAddressValue2.equals(ExpectedShippAdd2)))
					{
						//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "ShipAddSameAsBillAdd";
						String PageName = "Order checkout";
						System.out.println("<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --------->"+ErrorNumber);
						et.log(LogStatus.ERROR,"<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --------->"+ErrorNumber);
						System.out.println("Actual Billg value is :"+BillingAddressValue2);
						System.out.println("Actual Shipping value is :"+ShippingAddressValue2);
						System.out.println("ExpectedBillAdd2 :"+ExpectedBillAdd2);
						System.out.println("ExpectedShippAdd2 :"+ExpectedShippAdd2);
						RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
					}
				}
				else
				{
					ShipAddSameAsBillAddSub = " ";
					if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
						(ShippingAddressValue2.equals(ExpectedShippAdd2b)))
					{
						//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "ShipAddSameAsBillAdd";
						String PageName = "Order checkout";
						System.out.println("<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ----->"+ErrorNumber);
						et.log(LogStatus.ERROR,"<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ----->"+ErrorNumber);
						System.out.println("Actual Billg value is :"+BillingAddressValue2);
						System.out.println("Actual Shipping value is :"+ShippingAddressValue2);
						System.out.println("Expected Billing Value :"+ExpectedBillAdd2);
						System.out.println("Expected Shipping Value :"+ExpectedShippAdd2b);
						RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
					}
				}
			
				//Below code related to click on Shipping address same as Billing address check
				//box in user flow when admin Shipping Address same as Billing is "NO"
				
				if(ShipAddSameAsBillAdd.equals("NO") && (ShipAddSameAsBillAddSize == 2))
				{
					ShipAddSameAsBillAddSub = ShipAddSameAsBillAddArrary[1];
				
					if(ShipAddSameAsBillAddSub.equals("UserChkBoxYES"))
					{
						ShipAddSameAsBillAddSub = ShipAddSameAsBillAddArrary[1];
						d.findElement(Property.SameAsBillAddStatus).click();
						tec.AC40.Common.Wait.wait10Second();
						BillingAddressValue2 = d.findElement(Property.BillingAddressValue).getText();
						ShippingAddressValue2 = d.findElement(Property.ShippingAddressValue).getText();		
						//System.out.println("BillingAddressValue :"+BillingAddressValue2);
						//System.out.println("ShippingAddressValue :"+ShippingAddressValue2);
					
						if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
							(ShippingAddressValue2.equals(ExpectedShippAdd2)))
						{
							//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
						}
						else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "ShipAddSameAsBillAdd";
							String PageName = "Order checkout";
							System.out.println("<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ---->"+ErrorNumber);
							et.log(LogStatus.ERROR,"<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ---->"+ErrorNumber);
							System.out.println("Actual Billg value is :"+BillingAddressValue2);
							System.out.println("Actual Shipping value is :"+ExpectedShippAdd2);
							RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
				
						}
					}
					else if(ShipAddSameAsBillAddSub.equals("UserSelectedShip"))
					{
						d.findElement(Property.ShippingAddressSelectLink).click();
						tec.AC40.Common.Wait.wait5Second();
						d.findElement(Property.ShippingContactsPopupUseLink).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingContactsPopUpSaveButton).click();
						tec.AC40.Common.Wait.wait10Second();
						BillingAddressValue2 = d.findElement(Property.BillingAddressValue).getText();
						ShippingAddressValue2 = d.findElement(Property.ShippingAddressValue).getText();		
						//System.out.println("BillingAddressValue :"+BillingAddressValue2);
						//System.out.println("ShippingAddressValue :"+ShippingAddressValue2);
						
						if(Config.LayoutType.equals("Layout1"))
						{
							ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
						}
						else
						{
							ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit |  Show My Addresses  ";
						}
						
						if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
							(ShippingAddressValue2.equals(ExpectedShippAdd2b)))
						{
							//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
						}
						else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "ShipAddSameAsBillAdd";
							String PageName = "Order checkout";
		    				System.out.println("<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --->"+ErrorNumber);
		    				et.log(LogStatus.ERROR,"<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --->"+ErrorNumber);
		    				System.out.println("Actual Billg value is :"+BillingAddressValue2);
		    				System.out.println("Actual Shipping value is :"+ShippingAddressValue2);
		    				System.out.println("Expected Billing value is :"+ExpectedBillAdd2);
		    				System.out.println("Expected Shipping valuen is :"+ExpectedShippAdd2b);
		    				RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
						}
					}
				}
			 }
			 }
			
			 if((Weighttype.equalsIgnoreCase("KGS")||Weighttype.equalsIgnoreCase("LBS"))&&OrderBase.equalsIgnoreCase("Order") )
			 {
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ordershippingdropdown));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ordershippingdropdown));
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ordershippingdropdown);
						 }
						 });
				d.findElement(Property.ordershippingdropdown).click();
				tec.AC40.Common.Wait.wait2Second();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ordershippingmethodselection));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ordershippingmethodselection));
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ordershippingmethodselection);
						 }
						 });
				d.findElement(Property.ordershippingmethodselection).click();
				tec.AC40.Common.Wait.wait5Second();
			 }
			 if(ZeroAmountorder.equalsIgnoreCase("YES")){
				 d.findElement(By.xpath("//*[@id='chkSameAsBilling']")).click();
			 }
			 if(OrderBase.equalsIgnoreCase("Order") && ShipAddSameAsBillAdd.equalsIgnoreCase("NO") && IsShippingTaxable.equalsIgnoreCase("NO") && IfShippingaddressIseditble.equalsIgnoreCase("YES"))
			 {
				
				d.findElement(Property.ShippingEditLink).click();
				tec.AC40.Common.Wait.wait5Second();
				if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingPopAdd1TextBoxL1));
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ShippingPopAdd1TextBoxL1);
							 }
							 });
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.ShippingPopAdd1TextBoxL1).clear();
					d.findElement(Property.ShippingPopAdd1TextBoxL1).sendKeys("38345 W.10 Mile Roada");
					d.findElement(Property.ShippingPopAdd2TextBoxL1).clear();
					d.findElement(Property.ShippingPopAdd2TextBoxL1).sendKeys("suite 115b");
					d.findElement(Property.ShippingPopZipTextBoxL1).clear();
					d.findElement(Property.ShippingPopZipTextBoxL1).sendKeys("49806");
	                tec.AC40.Common.Wait.wait2Second();
	                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
	                d.findElement(Property.ShippingPopStateDropdowL1).click();
		            tec.AC40.Common.Wait.wait2Second();
		            kb.sendKeys("Michigan");
					kb.pressKey(Keys.ENTER);
					tec.AC40.Common.Wait.wait2Second();
				}
				else
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingPopAdd1TextBox));
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ShippingPopAdd1TextBox);
							 }
							 });
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.ShippingPopAdd1TextBox).clear();
					d.findElement(Property.ShippingPopAdd1TextBox).sendKeys("38345 W.10 Mile Roada");
					d.findElement(Property.ShippingPopAdd2TextBox).clear();
					d.findElement(Property.ShippingPopAdd2TextBox).sendKeys("suite 115b");
					d.findElement(Property.ShippingPopZipTextBox).clear();
					d.findElement(Property.ShippingPopZipTextBox).sendKeys("49806");
	                tec.AC40.Common.Wait.wait2Second();
	                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
	                d.findElement(Property.ShippingPopStateDropdow).click();
		            tec.AC40.Common.Wait.wait2Second();
		            kb.sendKeys("Michigan");
					kb.pressKey(Keys.ENTER);
					tec.AC40.Common.Wait.wait2Second();
				}
				d.findElement(Property.ShippingPopSaveButton).click();
				tec.AC40.Common.Wait.wait10Second();
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait2Second();
				}
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingPopCancelButton));
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingPopCancelButton));
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ShippingPopCancelButton);
						 }
						 });
				tec.AC40.Common.Wait.wait10Second();
				d.findElement(Property.ShippingPopCancelButton).click();
				tec.AC40.Common.Wait.wait5Second();
				//comparing tax based on shipping address
				String PageName="Shippingeditcase";
				String ExpectedTaxPercentage="("+Tax+"%)";
				String ActualTaxPercentage=d.findElement(Property.OCTaxPercentage).getText();
				if(ExpectedTaxPercentage.equals(ActualTaxPercentage))
				{
					//!System.out.println("Both Tax percentages are saeme ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxPercentage";
					System.out.println("<------------- Order Checkout Both Tax percentages are different ------------>"+ErrorNumber);
					et.log(LogStatus.ERROR,"<------------- Order Checkout Both Tax percentages are different ------------>"+ErrorNumber);
					System.out.println("ActualOCTaxPercentage : "+ActualTaxPercentage);
					System.out.println("ExpectedTaxpercentage : "+ExpectedTaxPercentage);
					RW_File.WriteResult(ExpectedTaxPercentage, ActualTaxPercentage,  PageName, PriceType);
				}
				String ExpectedtaxAmount=Config.Currency+Priceaftercalculatingtaxwithoutcoupn;
				String ActualtaxAmount=d.findElement(Property.OCTaxAmount).getText();
				if(ExpectedtaxAmount.equals(ActualtaxAmount))
				{
					//!System.out.println("Both PriceAfterCalculatingTax are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "PriceAfterCalculatingTax";
					System.out.println("<----------- Order checkout Bothe PriceAfterCalculatingTax are different -------->"+ErrorNumber);
					et.log(LogStatus.ERROR,"<----------- Order checkout Bothe PriceAfterCalculatingTax are different -------->"+ErrorNumber);
					System.out.println("ExpectedtaxAmount : "+ExpectedtaxAmount);
					System.out.println("ActualtaxAmount : "+ActualtaxAmount);
					RW_File.WriteResult(ExpectedtaxAmount, ActualtaxAmount,  PageName, PriceType);
					
				}
				tec.AC40.Common.Wait.wait10Second();
				}
			 if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
				OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
				|| OrderType.equals("ShipToMyAddress")) && !(Weighttype.equals("KGS")||Weighttype.equals("LBS")))
			 {
				String SelectListActualShipping1 = null;
				if(Config.LayoutType.equals("Layout1"))
				{
					SelectListActualShipping1 = d.findElement(Property.OrderBaseSelectedShippingValueL1).getText();
				}
				else
				{
					SelectListActualShipping1 = d.findElement(Property.OrderBaseSelectedShippingValue).getText();
				}
				String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
				VerfiyOrderBaseShippingAmount(SelectListActualShipping2[2],OrderBaseShipping, 
					Priceafterapplyingfulfillmentshippingmarkupfee);
			 }
         }else{
        	 System.out.println("billing address is un checked in orderelements");
        	 et.log(LogStatus.INFO, "billing address is un checked in orderelements");
         }
			 if(IsExternalPricingON.equalsIgnoreCase("Yes"))
		     {
				   SubTotal = SubTotalEP;
				   TotalPrice = TotalPriceEP;
		     }
			 
			 
			 if(IsExternalPricingON.equalsIgnoreCase("Yes"))
			 {
				 if(OrderBase.equalsIgnoreCase("Order"))
				 {
					 if(PromotionDiscountPercentage.equals("Y"))
					 {
						PromotionDiscountAfterSubtractingFromSubTotal = PromotionvalueCalculation(TotalPrice,PromotionCoupon);
						double TotalPrice1 = Double.parseDouble(TotalPrice);
						double PromotionDiscountAfterSubtractingFromSubTotal1 = Double.parseDouble(PromotionDiscountAfterSubtractingFromSubTotal);
						double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionDiscountAfterSubtractingFromSubTotal1;
						PriceAfterCalculatingTax = TaxCalculation1(Tax, PriceAfterSubtractCouponCode,OrderBaseShipping,userordershippingorhandlingfee);
						String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
						PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
					 
						Total = TotalPriceCalculation1(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax, OrderBaseShipping,userordershippingorhandlingfee);
						String Total3 = Decimalsetting(Total, OrderAmountValue);
						Total = Total3;
						
						String Total4 = Decimalsetting2(Total, OrderAmountValue);
						 Total = Total4;
					 }
					 else
					 {
						 double TotalPrice1 = Double.parseDouble(TotalPrice);
						 double PromotionCoupon1 = Double.parseDouble(PromotionCoupon);
						 double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionCoupon1;
						 PriceAfterCalculatingTax = TaxCalculation1(Tax, PriceAfterSubtractCouponCode, OrderBaseShipping,userordershippingorhandlingfee);
						 String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
						 PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
					 
						 Total = TotalPriceCalculation1(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax, OrderBaseShipping, userordershippingorhandlingfee);
						 String Total3 = Decimalsetting(Total, OrderAmountValue);
						 Total = Total3;
						 
						 String Total4 = Decimalsetting2(Total, OrderAmountValue);
						 Total = Total4;
					 }
				 }
				 else
				 {
					 if(PromotionDiscountPercentage.equals("Y"))
					 {
						 PromotionDiscountAfterSubtractingFromSubTotal = PromotionvalueCalculation(TotalPrice,PromotionCoupon);
						double TotalPrice1 = Double.parseDouble(TotalPrice);
						double PromotionDiscountAfterSubtractingFromSubTotal1 = Double.parseDouble(PromotionDiscountAfterSubtractingFromSubTotal);
						double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionDiscountAfterSubtractingFromSubTotal1;
						PriceAfterCalculatingTax = TaxCalculation(Tax, PriceAfterSubtractCouponCode);
						String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
						PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
					 
						Total = TotalPriceCalculation(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax);
						String Total3 = Decimalsetting(Total, OrderAmountValue);
						Total = Total3;
						
						String Total4 = Decimalsetting2(Total, OrderAmountValue);
						 Total = Total4;
					 }
					 else
					 {
						 double TotalPrice1 = Double.parseDouble(TotalPrice);
						 double PromotionCoupon1 = Double.parseDouble(PromotionCoupon);
						 double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionCoupon1;
						 PriceAfterCalculatingTax = TaxCalculation(Tax, PriceAfterSubtractCouponCode);
						 String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
						 PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
					 
						 Total = TotalPriceCalculation(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax);
						 String Total3 = Decimalsetting(Total, OrderAmountValue);
						 Total = Total3;
						 
						 String Total4 = Decimalsetting2(Total, OrderAmountValue);
						 Total = Total4;
					 }
				 }
				 String SubTotal5 = Decimalsetting2(SubTotal, OrderAmountValue);
				 SubTotal = SubTotal5;
				 
				 String TotalPrice5 = Decimalsetting2(TotalPrice, OrderAmountValue);
				 TotalPrice = TotalPrice5;
			 }
			 //System.out.println("Total  c :"+Total);
			 //System.out.println("SubTotal c :"+SubTotal);
			 

			 OrderCheckOutPriceInformantion(Quantity,ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
				PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
				PromotionDiscountAfterSubtractingFromSubTotal, PromotionDiscountPercentage, DiscountCalculationFromSubTotal,
				OrderType, DownloadPrice, TestCase, TestStep,Parameters, ProdutType,OrderBase,OrderBaseShipping,CalculateTaxCondition,
				EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,SubTotal,OrderAmountValue,userordershippingorhandlingfee,
				Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable, PriceAfterApplyingCoupon,IsTaxExempt,DecimalValue,LandingPageOption,
				LandingPageProduct,LandingPageProductPP,LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,SubTotal,Orderelements,OrderelementsAddOns,
				OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
				 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout);
			
			 // Billing address display verification
			
			 String  BillingAddValue=null;
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{
			 BillingAddValue = d.findElement(By.id("billingFieldSet")).getText();
			 }
			 //System.out.println("****BillingAddValue :"+BillingAddValue);
			 //String BillingAddDisplayStatus = d.findElement(By.id("billingFieldSet")).getAttribute("style");
			 //System.out.println("****BillingAddDisplayStatus :"+BillingAddDisplayStatus);
			 String PageName = "OrderCheckout";
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{

			 BillingAddressVerification(EnableZeroAmountOrder, BillingAddValue, PageName, TotalPrice);
			 }
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.OCTotal));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OCTotal));
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.OCTotal);
					 }
					 });
			 d.findElement(Property.OCTotal).click();
			 tec.AC40.Common.Wait.wait2Second();
			 int paymentLength = 0;
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsPaymentDetail.equalsIgnoreCase("NO")){}else{

			 //int TotalValue = Integer.parseInt(Total);
			
			 //SelectPaymentTypeInCheckOutPage(OrderBase, PaymentType, TestStep);
			 if(Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000") || Total.equals("0"))
			 {
				//No need to apply payment methods whenever we have total amount is zero
			 }
			 else
			 {
				 boolean paymentsListBoxCount =false;
				 String[] PaymentsTypes = null;
				 if(PaymentType.contains(","))
				 {
					 PaymentsTypes = PaymentType.split(",");
					 paymentLength = PaymentsTypes.length;
					 System.out.println("paymentLength : "+paymentLength);
					 et.log(LogStatus.INFO, "paymentLength : "+paymentLength);
				
					 for(int s=0; s <paymentLength; s++)
					 {
						 
						 if(paymentLength == (s+1))
						 {
							 String BalanceAmount =  d.findElement(Property.OCRemainingBalance).getText();
							 String BalanceAmount1 = BalanceAmount.substring(1,BalanceAmount.length());
							 if(s == 0)
							 {
								 Payment1Price = BalanceAmount1;
							 }
							 else if(s ==1)
							 {
								 Payment2Price = BalanceAmount1;
							 }
							 else if(s == 2)
							 {
								 Payment3Price = BalanceAmount1;
							 }
							 else if(s == 3)
							 {
								 Payment4Price = BalanceAmount1;
							 }
						 }
						 
						 System.out.println("Order flow s value: "+s);
						 et.log(LogStatus.INFO,"Order flow s value: "+s);
						 switch(s)
						 {
						 	case 0: //System.out.println("First case");
						 			SelectPaymentTypeInCheckOutPage(OrderBase, PaymentsTypes[s], TestStep, Payment1Price,
									PaymentSubOpt, paymentsListBoxCount, s, paymentLength);	
						 			break;
						 	case 1: //System.out.println("Second Case");
						 			SelectPaymentTypeInCheckOutPage(OrderBase, PaymentsTypes[s], TestStep, Payment2Price, 
									PaymentSubOpt, paymentsListBoxCount, s, paymentLength);	
						 			break;
						 	case 2: //System.out.println("Third Case");
						 			SelectPaymentTypeInCheckOutPage(OrderBase, PaymentsTypes[s], TestStep, Payment3Price, 
									PaymentSubOpt,paymentsListBoxCount, s, paymentLength);	
						 			break;
						 	case 3: //System.out.println("Fourth Case");
						 			SelectPaymentTypeInCheckOutPage(OrderBase, PaymentsTypes[s], TestStep, Payment4Price, 
									PaymentSubOpt, paymentsListBoxCount, s,paymentLength);	
						 			break;
						 	case 4: //System.out.println("Fifth Case");
						 			SelectPaymentTypeInCheckOutPage(OrderBase, PaymentsTypes[s], TestStep, Payment5Price,
									PaymentSubOpt, paymentsListBoxCount, s, paymentLength);	
						 			break;
						 }
					
						 if(PaymentsTypes[s].equals("Credit Card"))
						 {
							 paymentsListBoxCount= true;
						 }
						 
						
						 
					 }
				 }
				 else
				 {
					 int x = 9;
					 SelectPaymentTypeInCheckOutPage(OrderBase, PaymentType, TestStep, Total,
						PaymentSubOpt, paymentsListBoxCount, x, paymentLength);	
				 }
         
				 int DecimalValue1= Double.valueOf(OrderAmountValue).intValue();
				 String DecimalValue2 = "0.00";
				 if(DecimalValue1 == 2)
					 DecimalValue2 = "0.00";
				 else if(DecimalValue1 == 3)
					 DecimalValue2 = "0.000";
				 else if(DecimalValue1 == 4)
					 DecimalValue2 = "0.0000";
				 if(PaymentType.contains(","))
				 {
					 VerifyMultiAppliedAndRemaingPayments(Payment1Price, Payment2Price, Payment3Price, 
							 Payment4Price, Payment5Price, DecimalValue2, 
						PaymentType, paymentLength);
				 }
				 else
				 {
					 VerifyAppliedAndRemaingPayments(Total, DecimalValue2, CalculateTaxCondition,OrderBase,Weighttype,
					 SubTotal,PromotionCoupon,Addons,DiscountCalculationFromSubTotal,OrderAmountValue,
					 userordershippingorhandlingfee, TotalPrice, IsShippingTaxable, Tax, PriceAfterApplyingCoupon);
				 }
			 }
			 }
			 tec.AC40.Common.Wait.wait2Second();
		  		d.findElement(Property.AgreementCheck).click();
		    
		  		// Cost Center related code
		  	
		  	 //String StyleValue = d.findElement(Property.CostCenterBox).getAttribute("style");
		  	 //System.out.println("StyleValue :"+StyleValue);
		  		 boolean IsCostcenterDisplayed = false ;
		  	 if(Config.LayoutType.equals("Classic"))
		  	 {
		  		 IsCostcenterDisplayed = d.findElement(Property.CostCenterTag).isDisplayed();
		  	 }
		  	 else if(Config.LayoutType.equals("Layout1"))
		  	 {
		  		IsCostcenterDisplayed = d.findElement(Property.CostCenterTag).isDisplayed();
		  	 }
		  	 else if(Config.LayoutType.equals("Layout2"))
		  	 {
		  		IsCostcenterDisplayed = d.findElement(Property.CostCenterTag).isDisplayed();
		  	 }
		  	 //System.out.println("IsCostcenterDisplayed :"+IsCostcenterDisplayed);
		  	 if(CostCenter.equals("YES"))
		  	 {
				d.findElement(Property.SubmitOrder).click();
				tec.AC40.Common.Wait.wait5Second();
				
				if((IsCostcenterDisplayed == true))
				{
					//System.out.println("Cost Center option displayed successfully");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "CostCenter";
					PageName = "Order checkout";
	    			System.out.println("<----- In Ordercheckout page cost center not displayed ------>"+ErrorNumber);
	    			et.log(LogStatus.ERROR,"<----- In Ordercheckout page cost center not displayed ------>"+ErrorNumber);
	    			System.out.println("Actual value is :"+"Cost center option NOT displayed to user");
	    			System.out.println("Expected value is :"+"Cost center option displayed to user");
	    			RW_File.WriteResult("Cost center option displayed to user", "Cost center option NOT displayed to user", PageName, PriceType);
				}
				
				// Select cost center option in order flow
				Thread.sleep(3500);
				d.findElement(Property.CostCenterDropDown).click();
				tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.CostCenterDropDownValue).click();
				Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.ENTER);
				Thread.sleep(500);
				
		  	 }
		  	 else if(CostCenter.equals("NO"))
		  	 {
				if(IsCostcenterDisplayed == false)
				{
					//System.out.println("Cost Center option NOT displayed to user successfully");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "CostCenter";
					PageName = "Order checkout";
	    			System.out.println("<----- In Ordercheckout page cost center displayed ------>"+ErrorNumber);
	    			et.log(LogStatus.ERROR,"<----- In Ordercheckout page cost center displayed ------>"+ErrorNumber);
	    			System.out.println("Actual value is :"+"Cost center option displayed to user");
	    			System.out.println("Expected value is :"+"Cost center option NOT displayed to user");
	    			RW_File.WriteResult("Cost center option NOT displayed to user", "Cost center option displayed to user", PageName, PriceType);
				}
				
		  	 }
			
		  	 d.findElement(Property.SubmitOrder).click();
		  	 tec.AC40.Common.Wait.wait5Second();
		  	  
		  	  //PaypalCredit();
		  	 
		  	MouseAdjFunction();
		  	 
		  	if(PaymentType.contains("PayUbiz External Checkout"))
		  	 {
		  		 tec.AC40.Common.Wait.wait15Second();
		  		payubizpayment(Total);
		  		
		  	}
		  	
		  	 String[] EnableZeroOrder = EnableZeroAmountOrder.split("_");
			
		  	 if((Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000")) && EnableZeroOrder[0].equals("NO"))
		  	 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ErrorMsg));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ErrorMsg));
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ErrorMsg);
						 }
						 });
				tec.AC40.Common.Wait.wait2Second();
				String ErrorMessage = d.findElement(Property.ErrorMsg).getText();
				System.out.println("ErrorMessage :"+ErrorMessage);
				et.log(LogStatus.ERROR,"ErrorMessage :"+ErrorMessage);
				
				boolean ErrorMessageStatus = d.findElement(Property.ErrorMsg).isEnabled();
				if(ErrorMessage.equals("Amount should be greater than zero") && ErrorMessageStatus == true)
				{
					// Expected error msg displayed
					//!System.out.println("Expected error msg displayed successfully");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "AppliedPayment";
					PageName = "Order checkout";
        			System.out.println("<----- Ordercheckout Both Error messages are different ------>"+ErrorNumber);
        			et.log(LogStatus.ERROR,"<----- Ordercheckout Both Error messages are different ------>"+ErrorNumber);
        			System.out.println("Actual Error msg is :"+ErrorMessage);
        			System.out.println("Expected Error Msg is :"+"Amount should be greater than zero");
        			RW_File.WriteResult("Amount should be greater than zero", ErrorMessage, PageName, PriceType);
				}
		  	 }
			
		  	 if((Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000")) && EnableZeroOrder[0].equals("NO"))
		  	 {
				//No need to below code when total values is zero
		  	 }
		  	 else
		  	 {
		  		 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PrintLink));
		  		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PrintLink));
		  		waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PrintLink);
						 }
						 });
		  		 //d.findElement(Property.PrintLink).isDisplayed();
		  		 tec.AC40.Common.Wait.wait2Second();
		  	
		  		 String GrandTotal = OrderSummaryPagePriceInformantion(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
					PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
					PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
					DownloadPrice, OrderType, TestCase, TestStep,Parameters, ProdutType, OrderBase, OrderBaseShipping,
					CalculateTaxCondition,EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
					OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
					PriceAfterApplyingCoupon,LandingPageOption,LandingPageProduct,LandingPageProductPP,LandingPageDiscount,
					LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
					OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
					 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,IsTaxExempt);
		  		 tec.AC40.Common.Wait.wait5Second();
		  		 PageName = "Order Summary";
		  		 
				 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{

		  		 if(Config.LayoutType.equals("Layout1"))
		  		 {
		  			BillingAddValue = d.findElement(By.xpath("//div[@id='divOrderCheckout']/div[2]/div/div[2]")).getText();
		  		 }
		  		 else if(Config.LayoutType.equals("Layout2"))
		  		 {
		  			BillingAddValue = d.findElement(By.xpath("//section[@id='divOrderCheckout']/article[2]/div/section/div/section/div/section/aside[2]")).getText(); 
		  		 }
		  		 else
		  		 {
		  			 BillingAddValue = d.findElement(By.xpath("//div[@id='main']/div/div/div[2]/div/section[2]")).getText();
		  		 }
				 
		  		 BillingAddressVerificationOrderSummary(EnableZeroAmountOrder, BillingAddValue, PageName);
				 }
		  		 String parentHandle = d.getWindowHandle();
		  		 d.findElement(Property.PrintLink).click();
		  		 //tec.AC40.Common.Wait.wait2Second();
		  		 //d.findElement(Property.PrintConfirmationOK).click();
		  		 tec.AC40.Common.Wait.wait5Second();
		  	
		  		 String AppPageName1 = "OS Print Popup";

		  		 String ActualShippingPrice=PrintPopUpPriceVerification(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
					PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
					PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
					DownloadPrice, OrderType, TestCase, TestStep,Parameters, OrderBase, OrderBaseShipping,
					Payment1Price, Payment2Price, Payment3Price, Payment4Price, Payment5Price, PaymentType, CalculateTaxCondition,
					AppPageName1, EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
					OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
					PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub,
					paymentLength,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,LandingPageDiscount,
					LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
					OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
					 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,ProdutType,IsTaxExempt);
		  		 //System.out.println("Print ActualShippingPrice :"+ActualShippingPrice);
		  	
		  		 d.switchTo().window(parentHandle);
		  		 tec.AC40.Common.Wait.wait2Second();
		  		 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{
		  	
		  		 // Order summary page Shipping Address is same as Billing Address data verification
		  		 if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
					OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
					|| OrderType.equals("ShipToMyAddress"))&& IfShippingaddressIseditble.equalsIgnoreCase("NO"))
		  		 {
		  			 AppPageName1 = "Order summary page";	
		  			 //Verify Both Shipping and Billing address are same or not first Time
		  			 String BillingAddressValue = d.findElement(Property.OSBillingAddressValue).getText();
		  			 String ShippingAddressValue = d.findElement(Property.OSShippingAddressValue).getText();		
		  			 //System.out.println("BillingAddressValue :"+BillingAddressValue);
		  			 //System.out.println("ShippingAddressValue :"+ShippingAddressValue);
			
		  			 ShipAddSameAsBillAdd(BillingAddressValue,ShippingAddressValue,AppPageName1,
					    ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
		  		 }
		  		 }
		  		 if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
							OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
							|| OrderType.equals("ShipToMyAddress"))&& IfShippingaddressIseditble.equalsIgnoreCase("YES"))
				  		 {
				  			 AppPageName1 = "Order summary page";	
				  			 //Verify Both Shipping and Billing address are same or not first Time
				  			 String BillingAddressValue = d.findElement(Property.OSBillingAddressValue).getText();
				  			 String ShippingAddressValue = d.findElement(Property.OSShippingAddressValue).getText();		
				  			 //System.out.println("BillingAddressValue :"+BillingAddressValue);
				  			 //System.out.println("ShippingAddressValue :"+ShippingAddressValue);
					
				  			 ShipAddNotSameAsBillAdd(BillingAddressValue,ShippingAddressValue,AppPageName1,
							    ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
				  		 }
		  	
		  		MouseAdjFunction();
		  		 //String OrderNumber = "150";
		  		 OrderNumber = d.findElement(Property.GetOrderNumber ).getText();
		  		 System.out.println("OrderNumber : "+OrderNumber);
		  		 et.log(LogStatus.INFO,"OrderNumber : "+OrderNumber);
		  		 if(Config.LayoutType.equals("Classic"))
		  		 {
		  			 d.findElement(Property.UserBackToHome).click();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserReportsLink));
		  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserReportsLink));
		  			 waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.UserReportsLink);
							 }
							 });
		  			 //d.findElement(Property.UserReportsLink).isDisplayed();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 d.findElement(Property.UserReportsLink).click();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserViewOrderImageIcon));
		  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserViewOrderImageIcon));
		  			waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.UserViewOrderImageIcon);
							 }
							 });
		  			 //d.findElement(Property.UserViewOrderImageIcon).isDisplayed();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 d.findElement(Property.UserViewOrderImageIcon).click();
		  		 }
		  		 else if(Config.LayoutType.equals("Layout1"))
		  		 {
		  			 d.findElement(Property.UserBackToHomeLayout1).click();
		  			 tec.AC40.Common.Wait.wait5Second();
		  			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.HomeImageL1));
		  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.HomeImageL1));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.HomeImageL1);
								}
							});
		  			 //d.findElement(Property.UserReportsLinkLayout1).isDisplayed();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 d.findElement(Property.UserReportsLinkLayout1).click();
		  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserViewOrderImageIconLayout1));
		  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserViewOrderImageIconLayout1));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.UserViewOrderImageIconLayout1);
								}
							});
		  			 //	d.findElement(Property.UserViewOrderImageIconLayout1).isDisplayed();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 d.findElement(Property.UserViewOrderImageIconLayout1).click();
		  		 }
		  		 else if(Config.LayoutType.equals("Layout2"))
		  		 {
		  			 d.findElement(Property.UserBackToHomeLayout2).click();
		  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserReportsLinkLayout2));
		  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserReportsLinkLayout2));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.UserReportsLinkLayout2);
								}
							});
		  			 //d.findElement(Property.UserReportsLinkLayout2).isDisplayed();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 d.findElement(Property.UserReportsLinkLayout2).click();
		  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserViewOrderImageIconLayout2));
		  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserViewOrderImageIconLayout2));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.UserViewOrderImageIconLayout2);
								}
							});
		  			 //d.findElement(Property.UserViewOrderImageIconLayout2).isDisplayed();
		  			 tec.AC40.Common.Wait.wait2Second();
		  			 d.findElement(Property.UserViewOrderImageIconLayout2).click();
		  		 }
		  	
		  	 tec.AC40.Common.Wait.wait10Second();
		  	 waitfl.until(ExpectedConditions.elementToBeClickable(Property.GridOrderDateHeader));
		  	 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.GridOrderDateHeader));
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.GridOrderDateHeader);
							}
						});
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 d.findElement(Property.OrderNumberIcon).click();
		  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderNumberTextbox));
		  	 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderNumberTextbox));
		  	 
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.OrderNumberTextbox);
							}
						});
		  	 //d.findElement(Property.OrderNumberTextbox).isDisplayed();
		  	 tec.AC40.Common.Wait.wait2Second();
		  	 d.findElement(Property.OrderNumberTextbox).sendKeys(OrderNumber);
		  	 Thread.sleep(1000);
		  	 d.findElement(Property.OrderSearchSubmit).click();
		  	 tec.AC40.Common.Wait.wait5Second();
		  	
		 	 String AppPageName = "User View Orders";
			 // Verify the price values in user view order page grid
		 	 //System.out.println("ShippingPrice :"+ShippingPrice);
		 	 //System.out.println("OrderBaseShipping :"+OrderBaseShipping);
		 	 //System.out.println("ShippingPricePerPiece :"+ShippingPricePerPiece);
		  	 ViewOrderGridVerification(SubTotal, Total, OrderBase, ShippingPrice, OrderBaseShipping, Postage, AppPageName,
		  			ShippingPricePerPiece, GrandTotal, ActualShippingPrice, Weighttype, Priceafterapplyingfulfillmentshippingmarkupfee,LandingPageOption,LandingPageSubtotal,
		  			DecimalValue,ItemPerPrice,PromotionDiscountPercentage,Addons,Tax,Discount,DiscountPercentage,DiscountCalculationFromSubTotal,PromotionCoupon,
		  			PromotionDiscountAfterSubtractingFromSubTotal,LDiscuntCalulationFromSubtotal,PriceAfterCalculatingTax,Quantity,
		  			OrderType,DownloadPrice,LandingPageProductPP,BasePriceIncrementValue,ProdutType,LandingPageDiscountValue,EnablePromotionsORDiscounts,CalculateTaxCondition,
					userordershippingorhandlingfee,OrderAmountValue,PriceAfterApplyingCoupon,TotalPrice,IsShippingTaxable,Orderelements,OrderelementsAddOns,
					OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
					 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout);
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 d.findElement(By.linkText(OrderNumber)).click();
		  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserPrintOrder));
		  	 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserPrintOrder));
		  	 
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.UserPrintOrder);
							}
						});
		  	 //d.findElement(Property.UserPrintOrder).isDisplayed();
		  	 tec.AC40.Common.Wait.wait2Second();
		  	
		  	 UserViewOrdersPagePriceverification(SubTotal, Addons, Discount, PromotionCoupon, ShippingPrice, Postage,
		  		PriceAfterCalculatingTax, Total, Quantity, ItemPerPrice, FlatRate, DownloadPrice, OrderType, TestCase, TestStep, 
				Parameters, PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountPercentage, 
				DiscountCalculationFromSubTotal, OrderBase, OrderBaseShipping, CalculateTaxCondition, AppPageName,
				EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,SubTotal,OrderAmountValue,
				userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,Tax,
				TotalPrice, PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd,
				ShipAddSameAsBillAddSub,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,
				LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,LandingPageSubtotal,DecimalValue,LDiscuntCalulationFromSubtotal,Orderelements,OrderelementsAddOns,
				OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
				 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,IsTaxExempt);
		  	 tec.AC40.Common.Wait.wait2Second();
		  	 parentHandle = d.getWindowHandle();
		  	 d.findElement(Property.UserPrintOrder).click();
		  	 //tec.AC40.Common.Wait.wait2Second();
		  	 //d.findElement(Property.PrintConfirmationOK).click();
		  	 tec.AC40.Common.Wait.wait10Second();
		  	
		  	 AppPageName1 = "User Print Popup";
		  	
		  	 PrintPopUpPriceVerification(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
				PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
				PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
				DownloadPrice, OrderType, TestCase, TestStep,Parameters, OrderBase, OrderBaseShipping,
				Payment1Price, Payment2Price, Payment3Price, Payment4Price, Payment5Price, PaymentType, CalculateTaxCondition,
				AppPageName1, EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
				OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
				PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub,
				paymentLength,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,
				LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
				OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
				 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,ProdutType,IsTaxExempt);
		  	 d.switchTo().window(parentHandle);
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 //Reorder flow
		  	 if(Reorderflow.equalsIgnoreCase("YES")){
		  		tec.AC40.Common.Wait.wait5Second();
		  		if(Config.LayoutType.equals("Layout1"))
		  		{
			  	 d.findElement(Property.Reorderselecteditem).click();
			  	 tec.AC40.Common.Wait.wait5Second();
			  	 d.findElement(Property.UserViewOrdersReorderselecteditem).click();
		  		}
		  		else if (Config.LayoutType.equals("Layout2")){
		  			tec.AC40.Common.Wait.wait5Second();
				  	 d.findElement(Property.Reorderselecteditem1).click();
				  	 tec.AC40.Common.Wait.wait5Second();
				  	 d.findElement(Property.UserViewOrdersReorderselecteditem).click();
		  		}
		  		else if (Config.LayoutType.equals("Classic")){
		  			tec.AC40.Common.Wait.wait5Second();
				  	 d.findElement(Property.Reorderselecteditem1).click();
				  	 tec.AC40.Common.Wait.wait5Second();
				  	 d.findElement(Property.UserViewOrdersReorderselecteditem).click();
		  		}
			  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Checkout));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Checkout));
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.Checkout);
						 }
						 });
				 tec.AC40.Common.Wait.wait2Second();
				 
				 ShoppingCartPriceInformation(Quantity,SubTotal, ItemPerPrice, Discount, Addons, Postage, TotalPrice, DiscountPercentage, ShippingPrice,
							OrderType, TestCase, TestStep, Parameters, ProdutType,OrderBase,Weighttype, EnablePromotionsORDiscounts,DiscountCalculationFromSubTotal,
							Priceafterapplyingfulfillmentshippingmarkupfee,OrderAmountValue,LandingPageOption,LandingPageProduct,LandingPageProductPP,
							LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,DiscountCalculationFromSubTotal,DecimalValue);
						 
						 d.findElement(Property.Checkout).click();
						 tec.AC40.Common.Wait.wait5Second();
						 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderCheckoutGridDwonArrow));
						 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderCheckoutGridDwonArrow));
						 waitfl.until(new Function<WebDriver, WebElement>() 
								 {
								   public WebElement apply(WebDriver driver) {
								   return driver.findElement(Property.OrderCheckoutGridDwonArrow);
								 }
								 });
						 //	d.findElement(Property.OrderCheckoutGridDwonArrow).isDisplayed();
						 tec.AC40.Common.Wait.wait2Second();
						 d.findElement(Property.OrderCheckoutGridDwonArrow).click();
						 tec.AC40.Common.Wait.wait2Second();
						 
						 
						 
						 //Shipping Address Same as Billing Address
						 
						 if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
						 		OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
						 		|| OrderType.equals("ShipToMyAddress")) && IfShippingaddressIseditble.equalsIgnoreCase("NO"))
						 {
							String[] ShipAddSameAsBillAddArrary = ShipAddSameAsBillAdd.split("_");	
							ShipAddSameAsBillAdd = 	ShipAddSameAsBillAddArrary[0];
							int ShipAddSameAsBillAddSize = 	ShipAddSameAsBillAddArrary.length;
							//System.out.println("ShipAddSameAsBillAddSize :"+ShipAddSameAsBillAddSize);
							//Check box related code
							boolean SameAsBillAddStatus = d.findElement(Property.SameAsBillAddStatus).isSelected();
							if(ShipAddSameAsBillAdd.equals("YES"))
							{
								ShipAddSameAsBillAddSub = " ";
								if(SameAsBillAddStatus == true)
								{
									//System.out.println("As expected Same as Billing Add check box selected successfully");
								}
								else
								{
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									String PriceType = "CostCenter";
									String PageName1 = "Order checkout";
									System.out.println("<----- In Ordercheckout page Same as Billing Add check not selected ------>"+ErrorNumber);
									et.log(LogStatus.ERROR,"<----- In Ordercheckout page Same as Billing Add check not selected ------>"+ErrorNumber);
									System.out.println("Actual value is :"+"Same as Billing address check box NOT selected to user");
									System.out.println("Expected value is :"+"Same as Billing address check box selected to user");
									RW_File.WriteResult("Same as Billing address check box selected to user", "Same as Billing address check box NOT selected to user", PageName, PriceType);
								}
							}
							else if(ShipAddSameAsBillAdd.equals("NO"))
							{
								ShipAddSameAsBillAddSub = " ";
								if(SameAsBillAddStatus == false)
								{
									//System.out.println("As expected Same as Billing Add check box NOT selected successfully");
								}
								else
								{
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									String PriceType = "CostCenter";
									String PageName1 = "Order checkout";
									System.out.println("<----- In Ordercheckout page Same as Billing Add check selected ------>"+ErrorNumber);
									et.log(LogStatus.ERROR,"<----- In Ordercheckout page Same as Billing Add check selected ------>"+ErrorNumber);
									System.out.println("Actual value is :"+"Same as Billing address check box selected to user");
									System.out.println("Expected value is :"+"Same as Billing address check box NOT selected to user");
									RW_File.WriteResult("Same as Billing address check box NOT selected to user", "Same as Billing address check box selected to user", PageName, PriceType);
								}
							}
						
							//Verify Both Shipping and Billing address are same or not first Time
							String BillingAddressValue = d.findElement(Property.BillingAddressValue).getText();
							String ShippingAddressValue = d.findElement(Property.ShippingAddressValue).getText();		
							//System.out.println("BillingAddressValue :"+BillingAddressValue);
							//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
						 
							// Verify Both Shipping and Billing address values --First time 
							String ExpectedBillAdd1 = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
							String ExpectedShippAdd1 = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit";
							String ExpectedShippAdd1b = null;
							if(Config.LayoutType.equals("Layout1"))
							{
							ExpectedShippAdd1b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
							}
							else
							{
							ExpectedShippAdd1b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit |  Show My Addresses  ";
							}
							
							if(ShipAddSameAsBillAdd.equals("YES"))
							{
								if(BillingAddressValue.equals(ExpectedBillAdd1) && 
									(ShippingAddressValue.equals(ExpectedShippAdd1)))
								{
									//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
								}
								else
								{
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									String PriceType = "ShipAddSameAsBillAdd";
									String PageName1 = "Order checkout";
									System.out.println("<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ------->"+ErrorNumber);
									et.log(LogStatus.ERROR,"<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ------->"+ErrorNumber);
									System.out.println("Actual Billg value is :"+BillingAddressValue);
									System.out.println("Actual Shipping value is :"+ShippingAddressValue);
									System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
									System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
									RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
								}
							}
							else
							{
								if(BillingAddressValue.equals(ExpectedBillAdd1) && 
									(ShippingAddressValue.equals(ExpectedShippAdd1b)))
								{
									//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
								}
								else
								{
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									String PriceType = "ShipAddSameAsBillAdd";
									String PageName1 = "Order checkout";
									System.out.println("<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links -------->"+ErrorNumber);
									et.log(LogStatus.ERROR,"<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links -------->"+ErrorNumber);
									System.out.println("Actual Billg value is :"+BillingAddressValue);
									System.out.println("Actual Shipping value is :"+ShippingAddressValue);
									System.out.println("Expected Billing Add :"+ExpectedBillAdd1);
									System.out.println("Expected Shippint Add :"+ExpectedShippAdd1b);
									RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
								}
							}
						
							d.findElement(Property.BillingEditLink).click();
							tec.AC40.Common.Wait.wait5Second();
							if(Config.LayoutType.equals("Layout1"))
							{
								waitfl.until(ExpectedConditions.elementToBeClickable(Property.BillingPopAdd1TextBoxL1));
								waitfl.until(new Function<WebDriver, WebElement>() 
										 {
										   public WebElement apply(WebDriver driver) {
										   return driver.findElement(Property.BillingPopAdd1TextBoxL1);
										 }
										 });
								tec.AC40.Common.Wait.wait2Second();
								d.findElement(Property.BillingPopAdd1TextBoxL1).clear();
								d.findElement(Property.BillingPopAdd1TextBoxL1).sendKeys("38345 W.10 Mile Roada");
								d.findElement(Property.BillingPopAdd2TextBoxL1).clear();
								d.findElement(Property.BillingPopAdd2TextBoxL1).sendKeys("suite 115b");
							}
							else
							{
								waitfl.until(ExpectedConditions.elementToBeClickable(Property.BillingPopAdd1TextBox));
								waitfl.until(new Function<WebDriver, WebElement>() 
										 {
										   public WebElement apply(WebDriver driver) {
										   return driver.findElement(Property.BillingPopAdd1TextBox);
										 }
										 });
								tec.AC40.Common.Wait.wait2Second();
								d.findElement(Property.BillingPopAdd1TextBox).clear();
								d.findElement(Property.BillingPopAdd1TextBox).sendKeys("38345 W.10 Mile Roada");
								d.findElement(Property.BillingPopAdd2TextBox).clear();
								d.findElement(Property.BillingPopAdd2TextBox).sendKeys("suite 115b");
							}
							
							d.findElement(Property.BillingPopSaveButton).click();
							tec.AC40.Common.Wait.wait10Second();
							if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
							{
								tec.AC40.Common.Wait.wait2Second();
							}
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.BillingPopCancelButton));
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.BillingPopCancelButton));
							waitfl.until(new Function<WebDriver, WebElement>() 
									 {
									   public WebElement apply(WebDriver driver) {
									   return driver.findElement(Property.BillingPopCancelButton);
									 }
									 });
							tec.AC40.Common.Wait.wait10Second();
							d.findElement(Property.BillingPopCancelButton).click();
							tec.AC40.Common.Wait.wait5Second();
						 
							String ExpectedBillAdd2 = "38345 W.10 Mile Roada"+"\nsuite 115b"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
							String ExpectedShippAdd2 = "38345 W.10 Mile Roada"+"\nsuite 115b"+"\nFormington Hills, Texas 78732"+"\nEdit";
							String ExpectedShippAdd2b = null;
							if(Config.LayoutType.equals("Layout1"))
							{
								ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
							}
							else
							{
								ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit |  Show My Addresses  ";	
							}
							String BillingAddressValue2 = d.findElement(Property.BillingAddressValue).getText();
							String ShippingAddressValue2 = d.findElement(Property.ShippingAddressValue).getText();		
							//System.out.println("BillingAddressValue2 :"+BillingAddressValue2);
							//System.out.println("ShippingAddressValue2 :"+ShippingAddressValue2);
						 
							if(ShipAddSameAsBillAdd.equals("YES"))
							{
								if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
									(ShippingAddressValue2.equals(ExpectedShippAdd2)))
								{
									//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
								}
								else
								{
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									String PriceType = "ShipAddSameAsBillAdd";
									String PageName1 = "Order checkout";
									System.out.println("<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --------->"+ErrorNumber);
									et.log(LogStatus.ERROR,"<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --------->"+ErrorNumber);
									System.out.println("Actual Billg value is :"+BillingAddressValue2);
									System.out.println("Actual Shipping value is :"+ShippingAddressValue2);
									System.out.println("ExpectedBillAdd2 :"+ExpectedBillAdd2);
									System.out.println("ExpectedShippAdd2 :"+ExpectedShippAdd2);
									RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
								}
							}
							else
							{
								ShipAddSameAsBillAddSub = " ";
								if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
									(ShippingAddressValue2.equals(ExpectedShippAdd2b)))
								{
									//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
								}
								else
								{
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									String PriceType = "ShipAddSameAsBillAdd";
									String PageName1 = "Order checkout";
									System.out.println("<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ----->"+ErrorNumber);
									et.log(LogStatus.ERROR,"<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ----->"+ErrorNumber);
									System.out.println("Actual Billg value is :"+BillingAddressValue2);
									System.out.println("Actual Shipping value is :"+ShippingAddressValue2);
									System.out.println("Expected Billing Value :"+ExpectedBillAdd2);
									System.out.println("Expected Shipping Value :"+ExpectedShippAdd2b);
									RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
								}
							}
						
							//Below code related to click on Shipping address same as Billing address check
							//box in user flow when admin Shipping Address same as Billing is "NO"
							if(ShipAddSameAsBillAdd.equals("NO") && (ShipAddSameAsBillAddSize == 2))
							{
								ShipAddSameAsBillAddSub = ShipAddSameAsBillAddArrary[1];
							
								if(ShipAddSameAsBillAddSub.equals("UserChkBoxYES"))
								{
									ShipAddSameAsBillAddSub = ShipAddSameAsBillAddArrary[1];
									d.findElement(Property.SameAsBillAddStatus).click();
									tec.AC40.Common.Wait.wait10Second();
									BillingAddressValue2 = d.findElement(Property.BillingAddressValue).getText();
									ShippingAddressValue2 = d.findElement(Property.ShippingAddressValue).getText();		
									//System.out.println("BillingAddressValue :"+BillingAddressValue2);
									//System.out.println("ShippingAddressValue :"+ShippingAddressValue2);
								
									if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
										(ShippingAddressValue2.equals(ExpectedShippAdd2)))
									{
										//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
									}
									else
									{
										ErrorNumber = ErrorNumber+1;
										captureScreenshot();
										String PriceType = "ShipAddSameAsBillAdd";
										String PageName1 = "Order checkout";
										System.out.println("<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ---->"+ErrorNumber);
										et.log(LogStatus.ERROR,"<----- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links ---->"+ErrorNumber);
										System.out.println("Actual Billg value is :"+BillingAddressValue2);
										System.out.println("Actual Shipping value is :"+ExpectedShippAdd2);
										RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
							
									}
								}
								else if(ShipAddSameAsBillAddSub.equals("UserSelectedShip"))
								{
									d.findElement(Property.ShippingAddressSelectLink).click();
									tec.AC40.Common.Wait.wait5Second();
									d.findElement(Property.ShippingContactsPopupUseLink).click();
									tec.AC40.Common.Wait.wait2Second();
									d.findElement(Property.ShippingContactsPopUpSaveButton).click();
									tec.AC40.Common.Wait.wait10Second();
									BillingAddressValue2 = d.findElement(Property.BillingAddressValue).getText();
									ShippingAddressValue2 = d.findElement(Property.ShippingAddressValue).getText();		
									//System.out.println("BillingAddressValue :"+BillingAddressValue2);
									//System.out.println("ShippingAddressValue :"+ShippingAddressValue2);
									
									if(Config.LayoutType.equals("Layout1"))
									{
										ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit  |  Show My Addresses";
									}
									else
									{
										ExpectedShippAdd2b = "38345 W.10 Mile Road"+"\nsuite 115"+"\nFormington Hills, Texas 78732"+"\nEdit |  Show My Addresses  ";
									}
									
									if(BillingAddressValue2.equals(ExpectedBillAdd2) && 
										(ShippingAddressValue2.equals(ExpectedShippAdd2b)))
									{
										//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
									}
									else
									{
										ErrorNumber = ErrorNumber+1;
										captureScreenshot();
										String PriceType = "ShipAddSameAsBillAdd";
										String PageName1 = "Order checkout";
					    				System.out.println("<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --->"+ErrorNumber);
					    				et.log(LogStatus.ERROR,"<------- In Ordercheckout page Billig and Shipping address are different or Ship address have Eidt | Select links --->"+ErrorNumber);
					    				System.out.println("Actual Billg value is :"+BillingAddressValue2);
					    				System.out.println("Actual Shipping value is :"+ShippingAddressValue2);
					    				System.out.println("Expected Billing value is :"+ExpectedBillAdd2);
					    				System.out.println("Expected Shipping valuen is :"+ExpectedShippAdd2b);
					    				RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
									}
								}
							}
						 }
						
						 if((Weighttype.equalsIgnoreCase("KGS")||Weighttype.equalsIgnoreCase("LBS"))&&OrderBase.equalsIgnoreCase("Order") )
						 {
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.ordershippingdropdown));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ordershippingdropdown));
							waitfl.until(new Function<WebDriver, WebElement>() 
									 {
									   public WebElement apply(WebDriver driver) {
									   return driver.findElement(Property.ordershippingdropdown);
									 }
									 });
							d.findElement(Property.ordershippingdropdown).click();
							tec.AC40.Common.Wait.wait2Second();
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.ordershippingmethodselection));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ordershippingmethodselection));
							waitfl.until(new Function<WebDriver, WebElement>() 
									 {
									   public WebElement apply(WebDriver driver) {
									   return driver.findElement(Property.ordershippingmethodselection);
									 }
									 });
							d.findElement(Property.ordershippingmethodselection).click();
							tec.AC40.Common.Wait.wait5Second();
						 }
						 if(OrderBase.equalsIgnoreCase("Order") && ShipAddSameAsBillAdd.equalsIgnoreCase("NO") && IsShippingTaxable.equalsIgnoreCase("NO") && IfShippingaddressIseditble.equalsIgnoreCase("YES"))
						 {
							
							d.findElement(Property.ShippingEditLink).click();
							tec.AC40.Common.Wait.wait5Second();
							if(Config.LayoutType.equals("Layout1"))
							{
								waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingPopAdd1TextBoxL1));
								waitfl.until(new Function<WebDriver, WebElement>() 
										 {
										   public WebElement apply(WebDriver driver) {
										   return driver.findElement(Property.ShippingPopAdd1TextBoxL1);
										 }
										 });
								tec.AC40.Common.Wait.wait2Second();
								d.findElement(Property.ShippingPopAdd1TextBoxL1).clear();
								d.findElement(Property.ShippingPopAdd1TextBoxL1).sendKeys("38345 W.10 Mile Roada");
								d.findElement(Property.ShippingPopAdd2TextBoxL1).clear();
								d.findElement(Property.ShippingPopAdd2TextBoxL1).sendKeys("suite 115b");
								d.findElement(Property.ShippingPopZipTextBoxL1).clear();
								d.findElement(Property.ShippingPopZipTextBoxL1).sendKeys("49806");
				                tec.AC40.Common.Wait.wait2Second();
				                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				                d.findElement(Property.ShippingPopStateDropdowL1).click();
					            tec.AC40.Common.Wait.wait2Second();
					            kb.sendKeys("Michigan");
								kb.pressKey(Keys.ENTER);
								tec.AC40.Common.Wait.wait2Second();
							}
							else
							{
								waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingPopAdd1TextBox));
								waitfl.until(new Function<WebDriver, WebElement>() 
										 {
										   public WebElement apply(WebDriver driver) {
										   return driver.findElement(Property.ShippingPopAdd1TextBox);
										 }
										 });
								tec.AC40.Common.Wait.wait2Second();
								d.findElement(Property.ShippingPopAdd1TextBox).clear();
								d.findElement(Property.ShippingPopAdd1TextBox).sendKeys("38345 W.10 Mile Roada");
								d.findElement(Property.ShippingPopAdd2TextBox).clear();
								d.findElement(Property.ShippingPopAdd2TextBox).sendKeys("suite 115b");
								d.findElement(Property.ShippingPopZipTextBox).clear();
								d.findElement(Property.ShippingPopZipTextBox).sendKeys("49806");
				                tec.AC40.Common.Wait.wait2Second();
				                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				                d.findElement(Property.ShippingPopStateDropdow).click();
					            tec.AC40.Common.Wait.wait2Second();
					            kb.sendKeys("Michigan");
								kb.pressKey(Keys.ENTER);
								tec.AC40.Common.Wait.wait2Second();
							}
							d.findElement(Property.ShippingPopSaveButton).click();
							tec.AC40.Common.Wait.wait10Second();
							if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
							{
								tec.AC40.Common.Wait.wait2Second();
							}
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingPopCancelButton));
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingPopCancelButton));
							waitfl.until(new Function<WebDriver, WebElement>() 
									 {
									   public WebElement apply(WebDriver driver) {
									   return driver.findElement(Property.ShippingPopCancelButton);
									 }
									 });
							tec.AC40.Common.Wait.wait10Second();
							d.findElement(Property.ShippingPopCancelButton).click();
							tec.AC40.Common.Wait.wait5Second();
							//comparing tax based on shipping address
							String PageName1="Shippingeditcase";
							String ExpectedTaxPercentage="("+Tax+"%)";
							String ActualTaxPercentage=d.findElement(Property.OCTaxPercentage).getText();
							if(ExpectedTaxPercentage.equals(ActualTaxPercentage))
							{
								//!System.out.println("Both Tax percentages are saeme ");
							}
							else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "TaxPercentage";
								System.out.println("<------------- Order Checkout Both Tax percentages are different ------------>"+ErrorNumber);
								et.log(LogStatus.ERROR,"<------------- Order Checkout Both Tax percentages are different ------------>"+ErrorNumber);
								System.out.println("ActualOCTaxPercentage : "+ActualTaxPercentage);
								System.out.println("ExpectedTaxpercentage : "+ExpectedTaxPercentage);
								RW_File.WriteResult(ExpectedTaxPercentage, ActualTaxPercentage,  PageName, PriceType);
							}
							String ExpectedtaxAmount=Config.Currency+Priceaftercalculatingtaxwithoutcoupn;
							String ActualtaxAmount=d.findElement(Property.OCTaxAmount).getText();
							if(ExpectedtaxAmount.equals(ActualtaxAmount))
							{
								//!System.out.println("Both PriceAfterCalculatingTax are same ");
							}
							else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "PriceAfterCalculatingTax";
								System.out.println("<----------- Order checkout Bothe PriceAfterCalculatingTax are different -------->"+ErrorNumber);
								et.log(LogStatus.ERROR,"<----------- Order checkout Bothe PriceAfterCalculatingTax are different -------->"+ErrorNumber);
								System.out.println("ExpectedtaxAmount : "+ExpectedtaxAmount);
								System.out.println("ActualtaxAmount : "+ActualtaxAmount);
								RW_File.WriteResult(ExpectedtaxAmount, ActualtaxAmount,  PageName, PriceType);
								
							}
							tec.AC40.Common.Wait.wait10Second();
							}
						 if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
							OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
							|| OrderType.equals("ShipToMyAddress")) && !(Weighttype.equals("KGS")||Weighttype.equals("LBS")))
						 {
							String SelectListActualShipping1 = null;
							if(Config.LayoutType.equals("Layout1"))
							{
								SelectListActualShipping1 = d.findElement(Property.OrderBaseSelectedShippingValueL1).getText();
							}
							else
							{
								SelectListActualShipping1 = d.findElement(Property.OrderBaseSelectedShippingValue).getText();
							}
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							VerfiyOrderBaseShippingAmount(SelectListActualShipping2[2],OrderBaseShipping, 
								Priceafterapplyingfulfillmentshippingmarkupfee);
						 }
						
						 if(IsExternalPricingON.equalsIgnoreCase("Yes"))
					     {
							   SubTotal = SubTotalEP;
							   TotalPrice = TotalPriceEP;
					     }
						 
						 
						 if(IsExternalPricingON.equalsIgnoreCase("Yes"))
						 {
							 if(OrderBase.equalsIgnoreCase("Order"))
							 {
								 if(PromotionDiscountPercentage.equals("Y"))
								 {
									PromotionDiscountAfterSubtractingFromSubTotal = PromotionvalueCalculation(TotalPrice,PromotionCoupon);
									double TotalPrice1 = Double.parseDouble(TotalPrice);
									double PromotionDiscountAfterSubtractingFromSubTotal1 = Double.parseDouble(PromotionDiscountAfterSubtractingFromSubTotal);
									double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionDiscountAfterSubtractingFromSubTotal1;
									PriceAfterCalculatingTax = TaxCalculation1(Tax, PriceAfterSubtractCouponCode,OrderBaseShipping,userordershippingorhandlingfee);
									String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
									PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
								 
									Total = TotalPriceCalculation1(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax, OrderBaseShipping,userordershippingorhandlingfee);
									String Total3 = Decimalsetting(Total, OrderAmountValue);
									Total = Total3;
									
									String Total4 = Decimalsetting2(Total, OrderAmountValue);
									 Total = Total4;
								 }
								 else
								 {
									 double TotalPrice1 = Double.parseDouble(TotalPrice);
									 double PromotionCoupon1 = Double.parseDouble(PromotionCoupon);
									 double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionCoupon1;
									 PriceAfterCalculatingTax = TaxCalculation1(Tax, PriceAfterSubtractCouponCode, OrderBaseShipping,userordershippingorhandlingfee);
									 String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
									 PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
								 
									 Total = TotalPriceCalculation1(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax, OrderBaseShipping, userordershippingorhandlingfee);
									 String Total3 = Decimalsetting(Total, OrderAmountValue);
									 Total = Total3;
									 
									 String Total4 = Decimalsetting2(Total, OrderAmountValue);
									 Total = Total4;
								 }
							 }
							 else
							 {
								 if(PromotionDiscountPercentage.equals("Y"))
								 {
									 PromotionDiscountAfterSubtractingFromSubTotal = PromotionvalueCalculation(TotalPrice,PromotionCoupon);
									double TotalPrice1 = Double.parseDouble(TotalPrice);
									double PromotionDiscountAfterSubtractingFromSubTotal1 = Double.parseDouble(PromotionDiscountAfterSubtractingFromSubTotal);
									double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionDiscountAfterSubtractingFromSubTotal1;
									PriceAfterCalculatingTax = TaxCalculation(Tax, PriceAfterSubtractCouponCode);
									String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
									PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
								 
									Total = TotalPriceCalculation(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax);
									String Total3 = Decimalsetting(Total, OrderAmountValue);
									Total = Total3;
									
									String Total4 = Decimalsetting2(Total, OrderAmountValue);
									 Total = Total4;
								 }
								 else
								 {
									 double TotalPrice1 = Double.parseDouble(TotalPrice);
									 double PromotionCoupon1 = Double.parseDouble(PromotionCoupon);
									 double PriceAfterSubtractCouponCode = TotalPrice1 - PromotionCoupon1;
									 PriceAfterCalculatingTax = TaxCalculation(Tax, PriceAfterSubtractCouponCode);
									 String PriceAfterCalculatingTax3 = Decimalsetting(PriceAfterCalculatingTax, OrderAmountValue);
									 PriceAfterCalculatingTax = PriceAfterCalculatingTax3;
								 
									 Total = TotalPriceCalculation(PriceAfterSubtractCouponCode, PriceAfterCalculatingTax);
									 String Total3 = Decimalsetting(Total, OrderAmountValue);
									 Total = Total3;
									 
									 String Total4 = Decimalsetting2(Total, OrderAmountValue);
									 Total = Total4;
								 }
							 }
							 String SubTotal5 = Decimalsetting2(SubTotal, OrderAmountValue);
							 SubTotal = SubTotal5;
							 
							 String TotalPrice5 = Decimalsetting2(TotalPrice, OrderAmountValue);
							 TotalPrice = TotalPrice5;
						 }
						 //System.out.println("Total  c :"+Total);
						 //System.out.println("SubTotal c :"+SubTotal);
						 
						 
						 OrderCheckOutPriceInformantion(Quantity,ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
							PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
							PromotionDiscountAfterSubtractingFromSubTotal, PromotionDiscountPercentage, DiscountCalculationFromSubTotal,
							OrderType, DownloadPrice, TestCase, TestStep,Parameters, ProdutType,OrderBase,OrderBaseShipping,CalculateTaxCondition,
							EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,SubTotal,OrderAmountValue,userordershippingorhandlingfee,
							Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable, PriceAfterApplyingCoupon,IsTaxExempt,DecimalValue,LandingPageOption,LandingPageProduct,LandingPageProductPP,
							LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,SubTotal,Orderelements,OrderelementsAddOns,OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
							 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout);
						
						 // Billing address display verification
						
						 String BillingAddValue1=null;
						 if((OrderelementsCheckout.equalsIgnoreCase("YES")&&OrderelementsBillingAddress.equalsIgnoreCase("NO"))||((OrderelementsCheckout.equalsIgnoreCase("NO")&&OrderelementsBillingAddress.equalsIgnoreCase("NO")))){
							 
						 }else
						 {
							 BillingAddValue1 = d.findElement(By.id("billingFieldSet")).getText();

						 }
						 //System.out.println("****BillingAddValue :"+BillingAddValue);
						 //String BillingAddDisplayStatus = d.findElement(By.id("billingFieldSet")).getAttribute("style");
						 //System.out.println("****BillingAddDisplayStatus :"+BillingAddDisplayStatus);
						 String PageName1 = "OrderCheckout";
						 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsCheckout.equalsIgnoreCase("NO")){
							 
						 }else{
							 BillingAddressVerification(EnableZeroAmountOrder, BillingAddValue1, PageName, TotalPrice);
 
						 }
						 
						 waitfl.until(ExpectedConditions.elementToBeClickable(Property.OCTotal));
						 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OCTotal));
						 waitfl.until(new Function<WebDriver, WebElement>() 
								 {
								   public WebElement apply(WebDriver driver) {
								   return driver.findElement(Property.OCTotal);
								 }
								 });
						 d.findElement(Property.OCTotal).click();
						 tec.AC40.Common.Wait.wait2Second();
						 
						 //int TotalValue = Integer.parseInt(Total);
						 int paymentLength1 = 0;
						 //SelectPaymentTypeInCheckOutPage(OrderBase, PaymentType, TestStep);
						 if(Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000") || Total.equals("0"))
						 {
							//No need to apply payment methods whenever we have total amount is zero
						 }
						 else
						 {
							 boolean paymentsListBoxCount =false;
							 String[] PaymentsTypes = null;
							 if(PaymentType.contains(","))
							 {
								 PaymentsTypes = PaymentType.split(",");
								 paymentLength1 = PaymentsTypes.length;
								 System.out.println("paymentLength : "+paymentLength1);
								 et.log(LogStatus.ERROR,"paymentLength : "+paymentLength1);
							
								 for(int s=0; s <paymentLength1; s++)
								 {
									 
									 if(paymentLength1 == (s+1))
									 {
										 String BalanceAmount =  d.findElement(Property.OCRemainingBalance).getText();
										 String BalanceAmount1 = BalanceAmount.substring(1,BalanceAmount.length());
										 if(s == 0)
										 {
											 Payment1Price = BalanceAmount1;
										 }
										 else if(s ==1)
										 {
											 Payment2Price = BalanceAmount1;
										 }
										 else if(s == 2)
										 {
											 Payment3Price = BalanceAmount1;
										 }
										 else if(s == 3)
										 {
											 Payment4Price = BalanceAmount1;
										 }
									 }
									 
									 System.out.println("Order flow s value: "+s);
									 et.log(LogStatus.ERROR,"Order flow s value: "+s);
									 switch(s)
									 {
									 	case 0: //System.out.println("First case");
									 			SelectPaymentTypeInCheckOutPage1(OrderBase, PaymentsTypes[s], TestStep, Payment1Price,
												PaymentSubOpt, paymentsListBoxCount, s, paymentLength);	
									 			break;
									 	case 1: //System.out.println("Second Case");
									 			SelectPaymentTypeInCheckOutPage1(OrderBase, PaymentsTypes[s], TestStep, Payment2Price, 
												PaymentSubOpt, paymentsListBoxCount, s, paymentLength);	
									 			break;
									 	case 2: //System.out.println("Third Case");
									 			SelectPaymentTypeInCheckOutPage1(OrderBase, PaymentsTypes[s], TestStep, Payment3Price, 
												PaymentSubOpt,paymentsListBoxCount, s, paymentLength);	
									 			break;
									 	case 3: //System.out.println("Fourth Case");
									 			SelectPaymentTypeInCheckOutPage1(OrderBase, PaymentsTypes[s], TestStep, Payment4Price, 
												PaymentSubOpt, paymentsListBoxCount, s,paymentLength);	
									 			break;
									 	case 4: //System.out.println("Fifth Case");
									 			SelectPaymentTypeInCheckOutPage1(OrderBase, PaymentsTypes[s], TestStep, Payment5Price,
												PaymentSubOpt, paymentsListBoxCount, s, paymentLength);	
									 			break;
									 }
								
									 if(PaymentsTypes[s].equals("Credit Card"))
									 {
										 paymentsListBoxCount= true;
									 }
									 
									
									 
								 }
							 }
							 else
							 {
								 int x = 9;
								 SelectPaymentTypeInCheckOutPage1(OrderBase, PaymentType, TestStep, Total,
									PaymentSubOpt, paymentsListBoxCount, x, paymentLength1);	
							 }
			         
							 int DecimalValue1= Double.valueOf(OrderAmountValue).intValue();
							 String DecimalValue2 = "0.00";
							 if(DecimalValue1 == 2)
								 DecimalValue2 = "0.00";
							 else if(DecimalValue1 == 3)
								 DecimalValue2 = "0.000";
							 else if(DecimalValue1 == 4)
								 DecimalValue2 = "0.0000";
							 if(PaymentType.contains(","))
							 {
								 VerifyMultiAppliedAndRemaingPayments(Payment1Price, Payment2Price, Payment3Price, 
										 Payment4Price, Payment5Price, DecimalValue2, 
									PaymentType, paymentLength);
							 }
							 else
							 {
								 VerifyAppliedAndRemaingPayments(Total, DecimalValue2, CalculateTaxCondition,OrderBase,Weighttype,
								 SubTotal,PromotionCoupon,Addons,DiscountCalculationFromSubTotal,OrderAmountValue,
								 userordershippingorhandlingfee, TotalPrice, IsShippingTaxable, Tax, PriceAfterApplyingCoupon);
							 }
						 }
						 tec.AC40.Common.Wait.wait2Second();
					  		d.findElement(Property.AgreementCheck).click();
					    
					  		// Cost Center related code
					  	
					  	 //String StyleValue = d.findElement(Property.CostCenterBox).getAttribute("style");
					  	 //System.out.println("StyleValue :"+StyleValue);
					  	 boolean IsCostcenterDisplayed1 = false ;
					  	 if(Config.LayoutType.equals("Classic"))
					  	 {
					  		 IsCostcenterDisplayed = d.findElement(Property.CostCenterTag).isDisplayed();
					  	 }
					  	 else if(Config.LayoutType.equals("Layout1"))
					  	 {
					  		IsCostcenterDisplayed = d.findElement(Property.CostCenterTag).isDisplayed();
					  	 }
					  	 else if(Config.LayoutType.equals("Layout2"))
					  	 {
					  		IsCostcenterDisplayed = d.findElement(Property.CostCenterTag).isDisplayed();
					  	 }
					  	 //System.out.println("IsCostcenterDisplayed :"+IsCostcenterDisplayed);
					  	 if(CostCenter.equals("YES"))
					  	 {
							d.findElement(Property.SubmitOrder).click();
							tec.AC40.Common.Wait.wait5Second();
							
							if((IsCostcenterDisplayed == true))
							{
								//System.out.println("Cost Center option displayed successfully");
							}
							else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "CostCenter";
								PageName = "Order checkout";
				    			System.out.println("<----- In Ordercheckout page cost center not displayed ------>"+ErrorNumber);
				    			et.log(LogStatus.ERROR,"<----- In Ordercheckout page cost center not displayed ------>"+ErrorNumber);
				    			System.out.println("Actual value is :"+"Cost center option NOT displayed to user");
				    			System.out.println("Expected value is :"+"Cost center option displayed to user");
				    			RW_File.WriteResult("Cost center option displayed to user", "Cost center option NOT displayed to user", PageName, PriceType);
							}
							
							// Select cost center option in order flow
							d.findElement(Property.CostCenterDropDown).click();
							tec.AC40.Common.Wait.wait2Second();
							//d.findElement(Property.CostCenterDropDownValue).click();
							Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
							kb.pressKey(Keys.DOWN);
							Thread.sleep(500);
							kb.pressKey(Keys.DOWN);
							Thread.sleep(500);
							kb.pressKey(Keys.ENTER);
							Thread.sleep(500);
							
					  	 }
					  	 else if(CostCenter.equals("NO"))
					  	 {
							if(IsCostcenterDisplayed == false)
							{
								System.out.println("Cost Center setting in disable");
							}
							else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "CostCenter";
								PageName = "Order checkout";
				    			System.out.println("<----- In Ordercheckout page cost center displayed ------>"+ErrorNumber);
				    			et.log(LogStatus.ERROR,"<----- In Ordercheckout page cost center displayed ------>"+ErrorNumber);
				    			System.out.println("Actual value is :"+"Cost center option displayed to user");
				    			System.out.println("Expected value is :"+"Cost center option NOT displayed to user");
				    			RW_File.WriteResult("Cost center option NOT displayed to user", "Cost center option displayed to user", PageName, PriceType);
							}
							
					  	 }
						
					  	 d.findElement(Property.SubmitOrder).click();
					  	 tec.AC40.Common.Wait.wait5Second();
					  	  
					  	  //PaypalCredit();
					  	 
					  	MouseAdjFunction();
					  	 
					  	if(PaymentType.contains("PayUbiz External Checkout"))
					  	 {
					  		 tec.AC40.Common.Wait.wait15Second();
					  		payubizpayment(Total);
					  		
					  	}
					  	
					  	 String[] EnableZeroOrder1 = EnableZeroAmountOrder.split("_");
						
					  	 if((Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000")) && EnableZeroOrder[0].equals("NO"))
					  	 {
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ErrorMsg));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ErrorMsg));
							waitfl.until(new Function<WebDriver, WebElement>() 
									 {
									   public WebElement apply(WebDriver driver) {
									   return driver.findElement(Property.ErrorMsg);
									 }
									 });
							tec.AC40.Common.Wait.wait2Second();
							String ErrorMessage = d.findElement(Property.ErrorMsg).getText();
							System.out.println("ErrorMessage :"+ErrorMessage);
							boolean ErrorMessageStatus = d.findElement(Property.ErrorMsg).isEnabled();
							if(ErrorMessage.equals("Amount should be greater than zero") && ErrorMessageStatus == true)
							{
								// Expected error msg displayed
								//!System.out.println("Expected error msg displayed successfully");
							}
							else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "AppliedPayment";
								PageName = "Order checkout";
			        			System.out.println("<----- Ordercheckout Both Error messages are different ------>"+ErrorNumber);
			        			et.log(LogStatus.ERROR,"<----- Ordercheckout Both Error messages are different ------>"+ErrorNumber);
			        			System.out.println("Actual Error msg is :"+ErrorMessage);
			        			System.out.println("Expected Error Msg is :"+"Amount should be greater than zero");
			        			RW_File.WriteResult("Amount should be greater than zero", ErrorMessage, PageName, PriceType);
							}
					  	 }
						
					  	 if((Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000")) && EnableZeroOrder[0].equals("NO"))
					  	 {
							//No need to below code when total values is zero
					  	 }
					  	 else
					  	 {
					  		 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PrintLink));
					  		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PrintLink));
					  		waitfl.until(new Function<WebDriver, WebElement>() 
									 {
									   public WebElement apply(WebDriver driver) {
									   return driver.findElement(Property.PrintLink);
									 }
									 });
					  		 //d.findElement(Property.PrintLink).isDisplayed();
					  		 tec.AC40.Common.Wait.wait2Second();
					  	
					  		 String GrandTotal1 = OrderSummaryPagePriceInformantion(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
								PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
								PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
								DownloadPrice, OrderType, TestCase, TestStep,Parameters, ProdutType, OrderBase, OrderBaseShipping,
								CalculateTaxCondition,EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
								OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
								PriceAfterApplyingCoupon,LandingPageOption,LandingPageProduct,LandingPageProductPP,LandingPageDiscount,
								LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
								OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
								 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,IsTaxExempt);
					  		 tec.AC40.Common.Wait.wait5Second();
					  		 PageName = "Order Summary";
					  		 
							 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{

					  		 if(Config.LayoutType.equals("Layout1"))
					  		 {
					  			BillingAddValue1 = d.findElement(By.xpath("//div[@id='divOrderCheckout']/div[2]/div/div[2]")).getText();
					  		 }
					  		 else if(Config.LayoutType.equals("Layout2"))
					  		 {
					  			BillingAddValue1 = d.findElement(By.xpath("//section[@id='divOrderCheckout']/article[2]/div/section/div/section/div/section/aside[2]")).getText(); 
					  		 }
					  		 else
					  		 {
					  			 BillingAddValue1 = d.findElement(By.xpath("//div[@id='main']/div/div/div[2]/div/section[2]")).getText();
					  		 }
					  		 BillingAddressVerificationOrderSummary(EnableZeroAmountOrder, BillingAddValue, PageName);
					  	
							 }
					  		 String parentHandle1 = d.getWindowHandle();
					  		 d.findElement(Property.PrintLink).click();
					  		 //tec.AC40.Common.Wait.wait2Second();
					  		 //d.findElement(Property.PrintConfirmationOK).click();
					  		 tec.AC40.Common.Wait.wait5Second();
					  	
					  		 String AppPageName2 = "OS Print Popup";

					  		 String ActualShippingPrice1=PrintPopUpPriceVerification(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
								PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
								PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
								DownloadPrice, OrderType, TestCase, TestStep,Parameters, OrderBase, OrderBaseShipping,
								Payment1Price, Payment2Price, Payment3Price, Payment4Price, Payment5Price, PaymentType, CalculateTaxCondition,
								AppPageName1, EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
								OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
								PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub,
								paymentLength,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,
								LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
								OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
								 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,ProdutType,IsTaxExempt);
					  		 //System.out.println("Print ActualShippingPrice :"+ActualShippingPrice);
					  	
					  		 d.switchTo().window(parentHandle);
					  		 tec.AC40.Common.Wait.wait2Second();
					  	
					  	
					  		 // Order summary page Shipping Address is same as Billing Address data verification
					  		 if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
								OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
								|| OrderType.equals("ShipToMyAddress"))&& IfShippingaddressIseditble.equalsIgnoreCase("NO"))
					  		 {
					  			 AppPageName1 = "Order summary page";	
					  			 //Verify Both Shipping and Billing address are same or not first Time
					  			 String BillingAddressValue = d.findElement(Property.OSBillingAddressValue).getText();
					  			 String ShippingAddressValue = d.findElement(Property.OSShippingAddressValue).getText();		
					  			 //System.out.println("BillingAddressValue :"+BillingAddressValue);
					  			 //System.out.println("ShippingAddressValue :"+ShippingAddressValue);
						
					  			 ShipAddSameAsBillAdd(BillingAddressValue,ShippingAddressValue,AppPageName1,
								    ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
					  		 }
					  		 if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
										OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
										|| OrderType.equals("ShipToMyAddress"))&& IfShippingaddressIseditble.equalsIgnoreCase("YES"))
							  		 {
							  			 AppPageName1 = "Order summary page";	
							  			 //Verify Both Shipping and Billing address are same or not first Time
							  			 String BillingAddressValue = d.findElement(Property.OSBillingAddressValue).getText();
							  			 String ShippingAddressValue = d.findElement(Property.OSShippingAddressValue).getText();		
							  			 //System.out.println("BillingAddressValue :"+BillingAddressValue);
							  			 //System.out.println("ShippingAddressValue :"+ShippingAddressValue);
								
							  			 ShipAddNotSameAsBillAdd(BillingAddressValue,ShippingAddressValue,AppPageName1,
										    ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
							  		 }
					  	
					  		MouseAdjFunction();
					  		 //String OrderNumber = "150";
					  		 OrderNumber = d.findElement(Property.GetOrderNumber ).getText();
					  		 System.out.println("OrderNumber : "+OrderNumber);
					  		 if(Config.LayoutType.equals("Classic"))
					  		 {
					  			 d.findElement(Property.UserBackToHome).click();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserReportsLink));
					  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserReportsLink));
					  			 waitfl.until(new Function<WebDriver, WebElement>() 
										 {
										   public WebElement apply(WebDriver driver) {
										   return driver.findElement(Property.UserReportsLink);
										 }
										 });
					  			 //d.findElement(Property.UserReportsLink).isDisplayed();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 d.findElement(Property.UserReportsLink).click();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserViewOrderImageIcon));
					  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserViewOrderImageIcon));
					  			waitfl.until(new Function<WebDriver, WebElement>() 
										 {
										   public WebElement apply(WebDriver driver) {
										   return driver.findElement(Property.UserViewOrderImageIcon);
										 }
										 });
					  			 //d.findElement(Property.UserViewOrderImageIcon).isDisplayed();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 d.findElement(Property.UserViewOrderImageIcon).click();
					  		 }
					  		 else if(Config.LayoutType.equals("Layout1"))
					  		 {
					  			 d.findElement(Property.UserBackToHomeLayout1).click();
					  			 tec.AC40.Common.Wait.wait5Second();
					  			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.HomeImageL1));
					  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.HomeImageL1));
										waitfl.until(new Function<WebDriver, WebElement>() {
											public WebElement apply(WebDriver driver) {
												return driver
														.findElement(Property.HomeImageL1);
											}
										});
					  			 //d.findElement(Property.UserReportsLinkLayout1).isDisplayed();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 d.findElement(Property.UserReportsLinkLayout1).click();
					  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserViewOrderImageIconLayout1));
					  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserViewOrderImageIconLayout1));
										waitfl.until(new Function<WebDriver, WebElement>() {
											public WebElement apply(WebDriver driver) {
												return driver
														.findElement(Property.UserViewOrderImageIconLayout1);
											}
										});
					  			 //	d.findElement(Property.UserViewOrderImageIconLayout1).isDisplayed();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 d.findElement(Property.UserViewOrderImageIconLayout1).click();
					  		 }
					  		 else if(Config.LayoutType.equals("Layout2"))
					  		 {
					  			 d.findElement(Property.UserBackToHomeLayout2).click();
					  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserReportsLinkLayout2));
					  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserReportsLinkLayout2));
										waitfl.until(new Function<WebDriver, WebElement>() {
											public WebElement apply(WebDriver driver) {
												return driver
														.findElement(Property.UserReportsLinkLayout2);
											}
										});
					  			 //d.findElement(Property.UserReportsLinkLayout2).isDisplayed();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 d.findElement(Property.UserReportsLinkLayout2).click();
					  			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserViewOrderImageIconLayout2));
					  			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserViewOrderImageIconLayout2));
										waitfl.until(new Function<WebDriver, WebElement>() {
											public WebElement apply(WebDriver driver) {
												return driver
														.findElement(Property.UserViewOrderImageIconLayout2);
											}
										});
					  			 //d.findElement(Property.UserViewOrderImageIconLayout2).isDisplayed();
					  			 tec.AC40.Common.Wait.wait2Second();
					  			 d.findElement(Property.UserViewOrderImageIconLayout2).click();
					  		 }
					  	
					  	 tec.AC40.Common.Wait.wait10Second();
					  	 waitfl.until(ExpectedConditions.elementToBeClickable(Property.GridOrderDateHeader));
					  	 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.GridOrderDateHeader));
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.GridOrderDateHeader);
										}
									});
					  	 tec.AC40.Common.Wait.wait5Second();
					  	 d.findElement(Property.OrderNumberIcon).click();
					  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderNumberTextbox));
					  	 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderNumberTextbox));
					  	 
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.OrderNumberTextbox);
										}
									});
					  	 //d.findElement(Property.OrderNumberTextbox).isDisplayed();
					  	 tec.AC40.Common.Wait.wait2Second();
					  	 d.findElement(Property.OrderNumberTextbox).sendKeys(OrderNumber);
					  	 Thread.sleep(1000);
					  	 d.findElement(Property.OrderSearchSubmit).click();
					  	 tec.AC40.Common.Wait.wait5Second();
					  	
					 	 String AppPageName3 = "User View Orders";
						 // Verify the price values in user view order page grid
					 	 //System.out.println("ShippingPrice :"+ShippingPrice);
					 	 //System.out.println("OrderBaseShipping :"+OrderBaseShipping);
					 	 //System.out.println("ShippingPricePerPiece :"+ShippingPricePerPiece);
					  	 ViewOrderGridVerification(SubTotal, Total, OrderBase, ShippingPrice, OrderBaseShipping, Postage, AppPageName,
						  			ShippingPricePerPiece, GrandTotal, ActualShippingPrice, Weighttype, Priceafterapplyingfulfillmentshippingmarkupfee,LandingPageOption,LandingPageSubtotal,
						  			DecimalValue,ItemPerPrice,PromotionDiscountPercentage,Addons,Tax,Discount,DiscountPercentage,DiscountCalculationFromSubTotal,PromotionCoupon,
						  			PromotionDiscountAfterSubtractingFromSubTotal,LDiscuntCalulationFromSubtotal,PriceAfterCalculatingTax,Quantity,
						  			OrderType,DownloadPrice,LandingPageProductPP,BasePriceIncrementValue,ProdutType,LandingPageDiscountValue,EnablePromotionsORDiscounts,CalculateTaxCondition,
									userordershippingorhandlingfee,OrderAmountValue,PriceAfterApplyingCoupon,TotalPrice,IsShippingTaxable,Orderelements,OrderelementsAddOns,
									OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
									 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout);
					  	
					  	 d.findElement(By.linkText(OrderNumber)).click();
					  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserPrintOrder));
					  	 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserPrintOrder));
					  	 
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.UserPrintOrder);
										}
									});
					  	 //d.findElement(Property.UserPrintOrder).isDisplayed();
					  	 tec.AC40.Common.Wait.wait2Second();
					  	
					  	 UserViewOrdersPagePriceverification(SubTotal, Addons, Discount, PromotionCoupon, ShippingPrice, Postage,
					  		PriceAfterCalculatingTax, Total, Quantity, ItemPerPrice, FlatRate, DownloadPrice, OrderType, TestCase, TestStep, 
							Parameters, PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountPercentage, 
							DiscountCalculationFromSubTotal, OrderBase, OrderBaseShipping, CalculateTaxCondition, AppPageName,
							EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,SubTotal,OrderAmountValue,
							userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,Tax,
							TotalPrice, PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd,
							ShipAddSameAsBillAddSub,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,
							LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,LandingPageSubtotal,DecimalValue,LDiscuntCalulationFromSubtotal,Orderelements,OrderelementsAddOns,
							OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
							 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,IsTaxExempt);
					  	 tec.AC40.Common.Wait.wait2Second();
					  	 parentHandle = d.getWindowHandle();
					  	 d.findElement(Property.UserPrintOrder).click();
					  	 //tec.AC40.Common.Wait.wait2Second();
					  	 //d.findElement(Property.PrintConfirmationOK).click();
					  	 tec.AC40.Common.Wait.wait10Second();
					  	 AppPageName1 = "User Print Popup";
					  	 PrintPopUpPriceVerification(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
							PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
							PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
							DownloadPrice, OrderType, TestCase, TestStep,Parameters, OrderBase, OrderBaseShipping,
							Payment1Price, Payment2Price, Payment3Price, Payment4Price, Payment5Price, PaymentType, CalculateTaxCondition,
							AppPageName1, EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
							OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
							PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub,
							paymentLength,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,
							LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
							OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
							 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,ProdutType,IsTaxExempt);
					  	 d.switchTo().window(parentHandle);
					  	 tec.AC40.Common.Wait.wait5Second();	 
			  	 
		  	 }
		  	 }
		  	 else{
		  		 //System.out.println("NO Reorder");
		  	 }
		  	 
		  	 
		  	 d.findElement(Property.logout).click();
		  	 tec.AC40.Common.Wait.wait2Second();
		  	
		  	 
		  	 //Approver login*/
			
		  	 ApproverLogin();
		  	 MouseAdjFunction();
		  	 tec.AC40.Common.Wait.wait2Second();
		  	 if(Config.LayoutType.equals("Classic"))
		  	 {
		  		 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserReportsLink));
		  		 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.UserReportsLink);
						 }
						 });
		  		 d.findElement(Property.ApproverClassicPendingOrdersLink).click();
		  		 tec.AC40.Common.Wait.wait2Second();
		  	 }
		  	 else if(Config.LayoutType.equals("Layout1"))
		  	 {
		  		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ApproverLayout1PendingOrdersLink));
		  		waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ApproverLayout1PendingOrdersLink);
						 }
						 });
		  		d.findElement(Property.ApproverLayout1PendingOrdersLink).click();
			  	tec.AC40.Common.Wait.wait2Second();
			  		Thread.sleep(6500);
			  		 d.findElement(By.id("lnkAllOrders")).click();
			  		// System.out.println("All orders button clicks perfect ");
			  		//et.log(LogStatus.INFO," All orders button clicks perfect ");
				  		Thread.sleep(6500);
		  	 }
		  	 else if(Config.LayoutType.equals("Layout2"))
		  	 {
		  		d.findElement(Property.ApproverLayout2PendingOrdersIcon).click();
			  	tec.AC40.Common.Wait.wait2Second();
		  	 }
		  	waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.APPPendingOrdersCount));
		  	waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.APPPendingOrdersCount));
		  	waitfl.until(new Function<WebDriver, WebElement>() 
			{
				public WebElement apply(WebDriver driver) 
				{
				 return driver.findElement(Property.APPPendingOrdersCount);
				 }
			 });
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 String PendingOrders1 = d.findElement(Property.APPPendingOrdersCount).getText();
		  	 int pendingOrdersValue1 = Double.valueOf(PendingOrders1).intValue();
		  	 if(pendingOrdersValue1 == 0)
		  	 {
		  		tec.AC40.Common.Wait.wait5Second();
		  		waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderNumberIcon));
		  		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderNumberIcon));
		  		waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.OrderNumberIcon);
						 }
						 });
		  	 }
		  	 else
		  	 {
		  		tec.AC40.Common.Wait.wait10Second();
		  		waitfl.until(ExpectedConditions.elementToBeClickable(Property.GridOrderDateHeader));
		  		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.GridOrderDateHeader));
		  		waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.GridOrderDateHeader);
						 }
						 });
		  	 }
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 
		  	 //clcik on all orders icon
		  
		  	 d.findElement(Property.OrderNumberIcon).click();
		  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderNumberTextbox));
		  	 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderNumberTextbox));
		  	 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.OrderNumberTextbox);
					 }
					 });
		  	 //d.findElement(Property.OrderNumberTextbox).isDisplayed();
		  	 tec.AC40.Common.Wait.wait2Second();
		  	 d.findElement(Property.OrderNumberTextbox).sendKeys(OrderNumber);
		  	 Thread.sleep(1000);
		  	 d.findElement(Property.OrderSearchSubmit).click();
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 AppPageName = "Approver View Orders";
		  	 // Verify the price values in Approver view order page grid
		  	 ViewOrderGridVerification(SubTotal, Total, OrderBase, ShippingPrice, OrderBaseShipping, Postage, AppPageName,
			  			ShippingPricePerPiece, GrandTotal, ActualShippingPrice, Weighttype, Priceafterapplyingfulfillmentshippingmarkupfee,LandingPageOption,LandingPageSubtotal,
			  			DecimalValue,ItemPerPrice,PromotionDiscountPercentage,Addons,Tax,Discount,DiscountPercentage,DiscountCalculationFromSubTotal,PromotionCoupon,
			  			PromotionDiscountAfterSubtractingFromSubTotal,LDiscuntCalulationFromSubtotal,PriceAfterCalculatingTax,Quantity,
			  			OrderType,DownloadPrice,LandingPageProductPP,BasePriceIncrementValue,ProdutType,LandingPageDiscountValue,EnablePromotionsORDiscounts,CalculateTaxCondition,
						userordershippingorhandlingfee,OrderAmountValue,PriceAfterApplyingCoupon,TotalPrice,IsShippingTaxable,Orderelements,OrderelementsAddOns,
						OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
						 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout);
			 waitfl.until(ExpectedConditions.elementToBeClickable(By.linkText(OrderNumber)));
		  	 //d.findElement(Property.UserPrintOrder).isDisplayed();
		  	 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(By.linkText(OrderNumber));
					 }
					 });
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 d.findElement(By.linkText(OrderNumber)).click();
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserPrintOrder));
		  	 //d.findElement(Property.UserPrintOrder).isDisplayed();
		  	 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.UserPrintOrder);
					 }
					 });
		  	 
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 
		  	 if(ApproverEdit.equalsIgnoreCase("YES")){        //vinod
		  		if(Config.LayoutType.equals("Layout1"))
		  		{
		  		    if(ProdutType.equals("Static Print"))
                    {
		  	        d.findElement(By.xpath("//div[2]/ul/li[6]/a/div")).click();
                    }
                    else if(ProdutType.equals("Dynamic Print"))
                    {
            	    d.findElement(By.xpath("//li[7]/a/div")).click();
                    }
                    else if (ProdutType.equals("Static Inventory"))
                    {
            	    d.findElement(By.xpath("//div[2]/ul/li[6]/a/div")).click();
                    }
		  		}
                else if(Config.LayoutType.equals("Layout2")){
                	
                	if(ProdutType.equals("Static Print"))
                     {
 		  	        d.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
                     }
                     else if(ProdutType.equals("Dynamic Print"))
                     {
             	    d.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
                     }
                     else if (ProdutType.equals("Static Inventory"))
                     {
             	    d.findElement(By.xpath("//div[2]/ul/li[6]/a/div")).click();
                     }
                }
		  		else if(Config.LayoutType.equals("Classic")){
                	
                	if(ProdutType.equals("Static Print"))
                     {
 		  	        d.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
                     }
                     else if(ProdutType.equals("Dynamic Print"))
                     {
             	    d.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
                     }
                     else if (ProdutType.equals("Static Inventory"))
                     {
             	    d.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
                     }
                }
		  		
		  	 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderName));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderName));
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
				public WebElement apply(WebDriver driver) {
				return driver.findElement(Property.OrderName);
				}
			 });
			 tec.AC40.Common.Wait.wait2Second();
			 int productdetailsnumber1 = 0;
			 productdetailsnumber1 = 1; //This variable uses in only Layout2 (Product details pop up number is different)
			 // Product details verification in personalize page
			 
			 String Quantity11 = String.format("%.0f", new BigDecimal(Quantity));
			 
			 String ExternalPricingItemPrice1=PersonalizeProductDetails(ItemPerPrice, Discount, DiscountPercentage, productdetailsnumber1,
					EnablePromotionsORDiscounts, IsExternalPricingON, Quantity11, BasePrice);
			 if(IsExternalPricingON.equalsIgnoreCase("Yes"))
			 {
				 ItemPerPrice = ExternalPricingItemPrice1;
			 }
			 // Enter Order and description for order
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderName));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.OrderName));
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
				public WebElement apply(WebDriver driver) {
				return driver.findElement(Property.OrderName);
				}
			 });
			 tec.AC40.Common.Wait.wait2Second();
			 d.findElement(Property.OrderName).clear();
			 d.findElement(Property.OrderName).sendKeys("Order"+dateFormat.format(date)+TestStep);
			 d.findElement(Property.OrderDesc).clear();
			 d.findElement(Property.OrderDesc).sendKeys("OrderNamedesc");
			 if(ProdutType.equals("Broadcast"))
			 {
			 	d.findElement(Property.BroadCastSubjectline).clear();
			 	d.findElement(Property.BroadCastSubjectline).sendKeys("Subject line");
			 	d.findElement(Property.PersonalizeFromEmail).clear();
			 	d.findElement(Property.PersonalizeFromEmail).sendKeys("abc@xyz.com");
			 }
			 d.findElement(Property.Next1).click();
			 tec.AC40.Common.Wait.wait2Second();
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Listsearchbox));
			 waitfl.until(new Function<WebDriver, WebElement>() 
			 {
				public WebElement apply(WebDriver driver) {
				return driver.findElement(Property.Listsearchbox);
				}
			 });
			 
			 tec.AC40.Common.Wait.wait2Second();
			
			 productdetailsnumber1 = 2;
			 // Product details verification in select list page
			 PersonalizeProductDetails(ItemPerPrice, Discount, DiscountPercentage, productdetailsnumber1,
					EnablePromotionsORDiscounts, IsExternalPricingON, Quantity1, BasePrice);
			 tec.AC40.Common.Wait.wait2Second();
			 if(IsExternalPricingON.equalsIgnoreCase("NO"))
			 {
				 ViewPricingDetails(ItemPerPrice, Discount, DiscountPercentage,
					 IsExternalPricingON, Quantity11);
			 }
			
			 if(OrderType.equals("DynDropShipment with List"))
			 { 
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.DropshipmentWithList).click();
					tec.AC40.Common.Wait.wait2Second();
					if(Quantity11.equals("7"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList7);
					}
					else if(Quantity11.equals("10"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList10);
					}
					else if(Quantity11.equals("2"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList2);
					}
					else if(Quantity11.equals("3"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList3);
					}
					tec.AC40.Common.Wait.wait5Second();
					//d.findElement(Property.Listcheckbox).click();
					tec.AC40.Common.Wait.wait10Second();
					if(OrderBase.equals("Item"))
					{
						//
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait5Second();
						/*waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});*/
						tec.AC40.Common.Wait.wait5Second();
						if(Weighttype.equals("KGS") || Weighttype.equals("LBS"))
						{
							//System.out.println("Third party shipping ");
							tec.AC40.Common.Wait.wait25Second();
						}
						waitfl.until(ExpectedConditions.elementToBeClickable(Property.PhoneTextBox));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PhoneTextBox));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.PhoneTextBox);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait5Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.Upsshippingmethod);
								}
							});
							tec.AC40.Common.Wait.wait10Second();
							d.findElement(Property.Upsshippingmethod).click();
						}
						else
						{
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
						//	System.out.println("1 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice, Priceafterapplyingfulfillmentshippingmarkupfee);
						}
				
					}
				}
				else
				{
					d.findElement(Property.DropshipmentWithList).click();
					tec.AC40.Common.Wait.wait2Second();
					if(Quantity11.equals("7"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList7);
					}
					else if(Quantity11.equals("10"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList10);
					}
					else if(Quantity11.equals("2"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList2);
					}
					else if(Quantity11.equals("3"))
					{
						d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList3);
					}
					tec.AC40.Common.Wait.wait5Second();
					//d.findElement(Property.Listcheckbox).click();
					tec.AC40.Common.Wait.wait10Second();
					if(OrderBase.equals("Item"))
					{
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
						}
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						Wait.Fluentwait(Property.PhoneTextBox);
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
							waitfl.until(new Function<WebDriver, WebElement>() 
							{
								public WebElement apply(WebDriver driver) {
								return driver.findElement(Property.Upsshippingmethod);
								}
							});
							tec.AC40.Common.Wait.wait10Second();
							d.findElement(Property.Upsshippingmethod).click();
						}
						else
						{
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
						}
					}
				}
			 }
			 else if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
			 {
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMyAddress).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.ShipToMyAddressDownload).click();
				}
				else
				{
					d.findElement(Property.Layout2Download).click();
					tec.AC40.Common.Wait.wait5Second();
				}
			 }
			 else if(OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping"))
			 {
				d.findElement(Property.ShipToMyAddress).click();
				tec.AC40.Common.Wait.wait5Second();
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMyAddressDownLoadPrint).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity11);
					tec.AC40.Common.Wait.wait5Second();
					if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					if(OrderBase.equals("Item"))
					{
						d.findElement(Property.ShippingMethodsLink).click();
						tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Quantity));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Quantity));
						d.findElement(Property.Quantity).click();
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.Quantity);
							}
						});
						Wait.Fluentwait(Property.PhoneTextBox);
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							d.findElement(Property.Upsshippingmethod).click();
						}
						else
						{
							tec.AC40.Common.Wait.wait5Second();
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							//System.out.println("2 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
						}
				
					}
				}
				else
				{
					d.findElement(Property.Layout1and2shippingwithdownload).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity11);
					tec.AC40.Common.Wait.wait5Second();
					if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					if(OrderBase.equals("Item"))
					{
						//d.findElement(Property.ShippingMethodsLink).click();
						//tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Quantity));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Quantity));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{//div[@id='divShipMethods']/span/span/span
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.Quantity);
							}
						});
						//d.findElement(Property.Quantity).click();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingSelect);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
							d.findElement(Property.Upsshippingmethod).click();
							tec.AC40.Common.Wait.wait15Second();
						}
						else
						{
							d.findElement(Property.ShippingSelect).click();
							String SelectListActualShipping1 = d.findElement(Property.SelectedShippingValue).getText();
							String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
							VerfiyShippingAmount(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee);
						}
					
					}
				}
			 }
			 else if(OrderType.equals("ShipToMyAddress"))
			 {
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMyAddress).click();
					tec.AC40.Common.Wait.wait5Second();
					if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					tec.AC40.Common.Wait.wait10Second();
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity11);
					tec.AC40.Common.Wait.wait5Second();
					if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					if(OrderBase.equals("Item"))
					{
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait5Second();
						waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
						waitfl.until(new Function<WebDriver, WebElement>() 
						{
							public WebElement apply(WebDriver driver) {
							return driver.findElement(Property.ShippingRefresh);
							}
						});
						tec.AC40.Common.Wait.wait2Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS") || 
								IsExternalPricingON.equalsIgnoreCase("YES"))
						{
							tec.AC40.Common.Wait.wait25Second();
						}
						waitfl.until(ExpectedConditions.elementToBeClickable(Property.PhoneTextBox));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PhoneTextBox));
								waitfl.until(new Function<WebDriver, WebElement>() {
									public WebElement apply(WebDriver driver) {
										return driver
												.findElement(Property.PhoneTextBox);
									}
								});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait2Second();
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingSelect));
						//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelect));
								waitfl.until(new Function<WebDriver, WebElement>() {
									public WebElement apply(WebDriver driver) {
										return driver
												.findElement(Property.ShippingSelect);
									}
								});
						tec.AC40.Common.Wait.wait5Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait2Second();
							waitfl.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Property.Upsshippingmethod));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.Upsshippingmethod);
										}
									});
									d.findElement(Property.Upsshippingmethod).click();
									tec.AC40.Common.Wait.wait15Second();
								}
								else
								{
									waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Quantity));
									waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Quantity));
											waitfl.until(new Function<WebDriver, WebElement>() {
												public WebElement apply(WebDriver driver) {
													return driver
															.findElement(Property.Quantity);
												}
											});
									tec.AC40.Common.Wait.wait2Second();
									//d.findElement(By.xpath("//ul[@id='ddlShipping_listbox']/li[2])[4]")).click();
					                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
					                kb.pressKey(Keys.DOWN);
					                kb.pressKey(Keys.ENTER);

						}
					}
				}
				else 
				{
					d.findElement(Property.ShipToMyAddress).click();
					tec.AC40.Common.Wait.wait5Second();if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
					{
						tec.AC40.Common.Wait.wait25Second();
					}
					
					d.findElement(Property.Quantity).clear();
					d.findElement(Property.Quantity).sendKeys(Quantity1);
					tec.AC40.Common.Wait.wait5Second();
					if(OrderBase.equals("Item"))
					{
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
								waitfl.until(new Function<WebDriver, WebElement>() {
									public WebElement apply(WebDriver driver) {
										return driver
												.findElement(Property.ShippingRefresh);
									}
								});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait10Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
						}
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingRefresh));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingRefresh));
								waitfl.until(new Function<WebDriver, WebElement>() {
									public WebElement apply(WebDriver driver) {
										return driver
												.findElement(Property.ShippingRefresh);
									}
								});
						tec.AC40.Common.Wait.wait2Second();
						d.findElement(Property.PhoneTextBox).click();
						tec.AC40.Common.Wait.wait5Second();
						tec.AC40.Common.Wait.wait5Second();

						d.findElement(Property.ShippingRefresh).click();
						tec.AC40.Common.Wait.wait5Second();
						if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
						{
							tec.AC40.Common.Wait.wait25Second();
							waitfl.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Property.Upsshippingmethod));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Upsshippingmethod));
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.Upsshippingmethod);
										}
									});
							d.findElement(Property.Upsshippingmethod).click();
							tec.AC40.Common.Wait.wait15Second();
						}
						else
						{
							waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Quantity));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Quantity));
									waitfl.until(new Function<WebDriver, WebElement>() {
										public WebElement apply(WebDriver driver) {
											return driver
													.findElement(Property.Quantity);
										}
									});
							tec.AC40.Common.Wait.wait2Second();
							//d.findElement(By.xpath("//ul[@id='ddlShipping_listbox']/li[2])[4]")).click();
			                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			                kb.pressKey(Keys.DOWN);
			                kb.pressKey(Keys.ENTER);
						}
					}
				}
			 }
			 else if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
					|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
			 {

				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.ShipToMultipleLocationsC).click();
				}
				else
				{
					d.findElement(Property.ShipToMultipleLocations).click();
				}
				tec.AC40.Common.Wait.wait2Second();
				int Quantity2 = Integer.parseInt(Quantity11);
				int Quantity21 =  0;
				int Quantity22 = 0;
				if(Quantity2 > 10)
				{
					Quantity21 = Quantity2 -5;
					Quantity22 = 5;
				}
				else
				{
					Quantity21 = Quantity2;
				}
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.AddNewShippingAddress).click();
				tec.AC40.Common.Wait.wait5Second();
				
				d.findElement(Property.ShipMultipleLocationsAddress1).sendKeys("38345 W.10 Mile Road");
				d.findElement(Property.ShipMultipleLocationsCity).sendKeys("Formington Hills");
				d.findElement(Property.ShipMultipleLocationsZip).sendKeys("78732");
                d.findElement(Property.ShipMultiPleLocationsCountryDDL).click();
                tec.AC40.Common.Wait.wait2Second();
                Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				kb.sendKeys("USA");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShipMultiPleLocationsStateDDL).click();
	            tec.AC40.Common.Wait.wait2Second();
	            kb.sendKeys("TEXAS");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
	            
	            
				d.findElement(Property.ShipMultipleLocationsQuantity).clear();
				
				d.findElement(Property.ShipMultipleLocationsQuantity).sendKeys(""+Quantity21);
				tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.MultiShippingMethodsLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsClick));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsClick));
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.ShippingMultiplelocationsClick);
							}
						});
						
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				//tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait10Second();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingSelectMultiplelocations));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelectMultiplelocations));
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.ShippingSelectMultiplelocations);
							}
						});
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait25Second();
				}
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.ShipMultipleLocationsQuantity).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait2Second();
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					d.findElement(Property.addedupsShippingSelectMultiplelocations).click();
				}
				else
				{
				d.findElement(Property.ShippingSelectMultiplelocations).click();
				
				String SelectListActualShipping1 = d.findElement(Property.SelectedMultipleShippingValue).getText();
				String[] SelectListActualShipping2 = SelectListActualShipping1.split(" ");
				//System.out.println("3 SelectListActualShipping2 :"+SelectListActualShipping2[2]);
				VerfiyShippingAmountShipToMultipleLocations(SelectListActualShipping2[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee,
						Quantity21,Quantity2,OrderAmountValue,ShippingPricePerPiece);
				}
				
				tec.AC40.Common.Wait.wait5Second();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsSave));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsSave));
						waitfl.until(new Function<WebDriver, WebElement>() {
							public WebElement apply(WebDriver driver) {
								return driver
										.findElement(Property.ShippingMultiplelocationsSave);
							}
						});
				d.findElement(Property.ShippingMultiplelocationsSave).click();
				tec.AC40.Common.Wait.wait10Second();
				
				
				// Second shipping address settings starts
				if(Quantity2 > 10)
				{
				d.findElement(Property.AddNewShippingAddress).click();
				tec.AC40.Common.Wait.wait5Second();
				
				d.findElement(Property.ShipMultipleLocationsAddress1).sendKeys("38345 W.10 Mile Road");
				d.findElement(Property.ShipMultipleLocationsCity).sendKeys("Formington Hills");
				d.findElement(Property.ShipMultipleLocationsZip).sendKeys("78732");
                d.findElement(Property.ShipMultiPleLocationsCountryDDL).click();
                tec.AC40.Common.Wait.wait2Second();
				kb.sendKeys("USA");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShipMultiPleLocationsStateDDL).click();
	            tec.AC40.Common.Wait.wait2Second();
	            kb.sendKeys("TEXAS");
				kb.pressKey(Keys.ENTER);
				tec.AC40.Common.Wait.wait2Second();
				
				d.findElement(Property.ShipMultipleLocationsQuantity).clear();
				
				d.findElement(Property.ShipMultipleLocationsQuantity).sendKeys(""+Quantity22);
				tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.MultiShippingMethodsLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsClick));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsClick));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.ShippingMultiplelocationsClick);
								}
							});
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				//tec.AC40.Common.Wait.wait2Second();
				//d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait10Second();
				//waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingSelectMultiplelocations2ndtime));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingSelectMultiplelocations2ndtime));
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait25Second();
				}
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.ShipMultipleLocationsQuantity).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ShippingMultiplelocationsClick).click();
				tec.AC40.Common.Wait.wait2Second();
				
				tec.AC40.Common.Wait.wait5Second();
				//org.openqa.selenium.Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.ENTER);
				
				if(Weighttype.equals("KGS")||Weighttype.equals("LBS"))
				{
					tec.AC40.Common.Wait.wait2Second();
				}
				else
				{
				String SelectListActualShipping3 = d.findElement(Property.SelectedMultipleShippingValue).getText();
				String[] SelectListActualShipping4 = SelectListActualShipping3.split(" ");
				//System.out.println("4 SelectListActualShipping4 :"+SelectListActualShipping4[2]);
				VerfiyShippingAmountShipToMultipleLocations(SelectListActualShipping4[2],ShippingPrice,Priceafterapplyingfulfillmentshippingmarkupfee,
						Quantity22,Quantity2,OrderAmountValue,ShippingPricePerPiece);
				}
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingMultiplelocationsSave));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMultiplelocationsSave));
							waitfl.until(new Function<WebDriver, WebElement>() {
								public WebElement apply(WebDriver driver) {
									return driver
											.findElement(Property.ShippingMultiplelocationsSave);
								}
							});
				d.findElement(Property.ShippingMultiplelocationsSave).click();
				tec.AC40.Common.Wait.wait10Second();
				}
			 }
			 else
			 {
				if(Quantity.equals("7") || Quantity.equals("7.0") || Quantity.equals("7.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList7);
				}
				else if(Quantity11.equals("10") || Quantity.equals("10.0") || Quantity.equals("10.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList10);
				}
				else if(Quantity11.equals("2") || Quantity.equals("2.0") || Quantity.equals("2.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList2);
				}
				else if(Quantity11.equals("3") || Quantity.equals("3.0") || Quantity.equals("3.00"))
				{
					d.findElement(Property.Listsearchbox).sendKeys(Config.PricingList3);
				}
				
				tec.AC40.Common.Wait.wait5Second();
				//d.findElement(Property.Listcheckbox).click();
			 }
             
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ContinueSelectList));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ContinueSelectList));
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ContinueSelectList);
					 }
					 });

			 d.findElement(Property.ContinueSelectList).click();
			 tec.AC40.Common.Wait.wait10Second();
			 /*tec.AC40.Common.Wait.wait5Second();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.AddToCart));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.AddToCart));
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.AddToCart);
					 }
					 });*/
			 tec.AC40.Common.Wait.wait5Second();
			if(ProdutType.equals("Dynamic Print"))
			{
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.VSPageProof);
					 }
					 });
			}
			
			 tec.AC40.Common.Wait.wait10Second();
			 // It is use full whenever we have multiple shipping locations
		     if(FRShippingPriceUser.equals("0.00") || FRShippingPriceUser.equals("0.000") || FRShippingPriceUser.equals("0.0000")
		    		  || FRShippingPriceUser.equals("0"))
		     {
		     	  //
		     }
		     else
		     {
		     	  ShippingPrice = FRShippingPriceUser;
		     }
		     //System.out.println(" ShippingPrice :"+ShippingPrice);
			
			 productdetailsnumber1 = 3;
			 PersonalizeProductDetails(ItemPerPrice, Discount, DiscountPercentage, productdetailsnumber1,
					EnablePromotionsORDiscounts, IsExternalPricingON, Quantity11,BasePrice);
			 tec.AC40.Common.Wait.wait2Second();
			 
			 	if(BasePrice.equals("0")|| BasePrice.equals("0.0") || BasePrice.equals("0.00") ||
						BasePrice.equals("0.000") || BasePrice.equals("0.0000"))
				{
					// Application displays base price value in else condition only
				}
				else
				{
				BasePriceIncrementValue = 0;
				String subtotal = SubTotalCalculation(ItemPerPrice,FlatRate,Quantity11,DiscountCalculationFromSubTotal,
							OrderAmountValue, BasePrice, OrderType, DownloadPrice);
				SubTotal = subtotal;
				if(BasePriceIncrementValue == 1)
				{
					if(OrderType.contains("Download"))
					{
						// in this time item price is differnt from subtotal
						//System.out.println("Dwonaload+Shipping If condition");
						double DownloadPrice1 =  Double.parseDouble(DownloadPrice);
						double subtotal1 = Double.parseDouble(subtotal);
						double ItemPrice = subtotal1 - DownloadPrice1;
						ItemPerPrice = ""+ItemPrice;
						String ItemPerPrice1 = Decimalsetting(ItemPerPrice, OrderAmountValue);
						ItemPerPrice = ItemPerPrice1;
						
					}
					else
					{
						//System.out.println("Dwonaload+Shipping else condition");
						ItemPerPrice = ""+subtotal;
						
					}
				
				DiscountCalculationFromSubTotal = "0.00";
				Discount = "0.00";
				DownloadPrice = "0.00";
				String DiscountCalculationFromSubTotal1 = Decimalsetting(DiscountCalculationFromSubTotal, OrderAmountValue);
				DiscountCalculationFromSubTotal = DiscountCalculationFromSubTotal1;
				
				String Discount1 = Decimalsetting(Discount, OrderAmountValue);
				Discount = Discount1;
				
				
				}
				if(BasePriceDownload == 1)
				{
					ItemPerPrice = "0.00";
					String ItemPerPrice1 = Decimalsetting(ItemPerPrice, OrderAmountValue);
					ItemPerPrice = ItemPerPrice1;
				}
				
				}
			 
			 
			 if(OrderType.equals("DropShipment with List"))
			 {
			 	//d.findElement(Property.UserAddonCheckBoxDSWL).click();
			 }
			 else if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") 
					|| ProdutType.equals("Dynamic Email"))
			 {
			 
			 }
			 else
			 {
			 	waitfl.until(ExpectedConditions.elementToBeClickable(Property.userAddonCheckbox));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.userAddonCheckbox));
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.userAddonCheckbox);
						 }
						 });
				//d.findElement(Property.userAddonCheckbox).click();
			 }
			 tec.AC40.Common.Wait.wait2Second();
			 if(Addons.equals("0") || Addons.equals("0.00") || Addons.equals("0.000") || Addons.equals("0.0000"))
			 {
			 
			 }
			 else
			 {
			 	AddonPriceverify(Addons, AddonPricePerPiece, TestCase, TestStep,Parameters);
			 }
			 
			 if(AddonPricePerPiece.equals("0") || AddonPricePerPiece.equals("0.00") || AddonPricePerPiece.equals("0.000") || AddonPricePerPiece.equals("0.0000"))
             {
          	  //System.out.println("No Need to assign value");
             }
		 	 else
			 {
			 	Addons = AddonPricePerPiece;
       	 	 }
			 
			 // Discount and Addon values modification based on Order amount value setting
			
			 String Discount21 = Decimalsetting(Discount, OrderAmountValue);
		     Discount = Discount21;

		     String Addons21 = Decimalsetting(Addons, OrderAmountValue);
		     Addons = Addons21;
		     String SubTotalEP1 = null;
		     String TotalPriceEP1 = null;
		     if(IsExternalPricingON.equalsIgnoreCase("Yes"))
		     {
		    	String ItemPerPriceWithDollar = d.findElement(Property.VSItemPrice).getText();
		    	 ItemPerPrice = ItemPerPriceWithDollar.substring(1,ItemPerPriceWithDollar.length());
		    	 SubTotal = ExternalPricingSubTotalCalculation(Quantity11, ItemPerPrice,FlatRate);
		    	 SubTotalEP1 = SubTotal;
		    	 String SubTotal3 = Decimalsetting(SubTotal, OrderAmountValue);
		    	 SubTotal = SubTotal3;
		    	 
		    	 if(DiscountPercentage.equals("Y"))
		    	 {
		    		 DiscountCalculationFromSubTotal = CalculateDiscount(SubTotal,Discount,OrderAmountValue); 
		    		 TotalPrice = ExternalPricingTotalPriceCalculation(SubTotal,DiscountCalculationFromSubTotal,Addons,Postage,ShippingPrice,DownloadPrice);
		    	 }
		    	 else if(DiscountPercentage.equals("N"))
		    	 {
		    	 TotalPrice = ExternalPricingTotalPriceCalculation(SubTotal,Discount,Addons,Postage,ShippingPrice,DownloadPrice);
		    	 }
		    	 TotalPriceEP1 =  TotalPrice;
		    	 String TotalPrice3 = Decimalsetting2(TotalPrice, OrderAmountValue);
		    	 TotalPrice = TotalPrice3;
			 }
		     
		     if(IsExternalPricingON.equalsIgnoreCase("Yes"))
		     {
		     String SubTotal3 = Decimalsetting2(SubTotal, OrderAmountValue);
	    	 SubTotal = SubTotal3;
		     }
		     
			 ViewSummaryPriceInformation(Quantity, ItemPerPrice, SubTotal, Discount, Addons, Postage, TotalPrice, DiscountPercentage,
				DiscountCalculationFromSubTotal, ShippingPrice, DownloadPrice, OrderType, TestCase, TestStep, Parameters, 
				ProdutType, OrderBase, EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,
				Priceafterapplyingfulfillmentshippingmarkupfee,OrderAmountValue,LandingPageOption,DecimalValue, BasePriceIncrementValue,Orderelements,OrderelementsAddOns,
				OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
				 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup);
		
			 tec.AC40.Common.Wait.wait10Second();
			 if(Config.LayoutType.equals("Layout1"))
			 {
		  		d.findElement(Property.AgreementCheck).click();
			 }
			 else if(Config.LayoutType.equals("Layout2"))
			 {
				 d.findElement(Property.AgreementCheck1).click(); 
			 }
			 else if (Config.LayoutType.equals("Classic"))
			 {
				 d.findElement(Property.AgreementCheck1).click();
			 }
			 tec.AC40.Common.Wait.wait2Second();
			 d.findElement(By.id("lnkSubmit")).click();
			 tec.AC40.Common.Wait.wait5Second();
			 waitfl.until(ExpectedConditions.presenceOfElementLocated(By.id("link_ApprovedAll")));
			 d.findElement(Property.Approve).click();
		      tec.AC40.Common.Wait.wait5Second();
		       Alert alert = d.switchTo().alert();
		       alert.accept();
		       tec.AC40.Common.Wait.wait5Second();  
		    d.findElement(Property.ApproveSave).click();
		    tec.AC40.Common.Wait.wait5Second();
		    waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ApproveSaveSucMessage));
		    waitfl.until(ExpectedConditions.elementToBeClickable(Property.ApproveSaveSucMessage));
		    waitfl.until(new Function<WebDriver, WebElement>() 
		     {
		       public WebElement apply(WebDriver driver) {
		       return driver.findElement(Property.ApproveSaveSucMessage);
		     }
		     });
		  	
		  	 }
		  	 else{
		  		 //System.out.println("Approver Edit is not enabled");
		  	 }
		  	
		  	
		  	 UserViewOrdersPagePriceverification(SubTotal, Addons, Discount, PromotionCoupon, ShippingPrice, Postage,
		  		PriceAfterCalculatingTax, Total, Quantity, ItemPerPrice, FlatRate, DownloadPrice, OrderType, TestCase, TestStep, 
				Parameters, PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountPercentage, 
				DiscountCalculationFromSubTotal, OrderBase, OrderBaseShipping, CalculateTaxCondition, AppPageName,
				EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,SubTotal,OrderAmountValue,
				userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,Tax, 
				TotalPrice, PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd,
				ShipAddSameAsBillAddSub,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,LandingPageDiscount,
				LandingPageDiscountValue,SubtotalAfterPurlPrice,LandingPageSubtotal,DecimalValue,LDiscuntCalulationFromSubtotal,Orderelements,OrderelementsAddOns,
				OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
				 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,IsTaxExempt);
		  	 tec.AC40.Common.Wait.wait2Second();
		  	 parentHandle = d.getWindowHandle();
		  	 d.findElement(Property.UserPrintOrder).click();
		  	 //tec.AC40.Common.Wait.wait2Second();
		  	 //d.findElement(Property.PrintConfirmationOK).click();
		  	 tec.AC40.Common.Wait.wait10Second();
		  	
		  	 AppPageName1 = "Approver Print Popup";
			  	
		  	 PrintPopUpPriceVerification(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
				PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
				PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
				DownloadPrice, OrderType, TestCase, TestStep,Parameters, OrderBase, OrderBaseShipping,
				Payment1Price, Payment2Price, Payment3Price, Payment4Price, Payment5Price, PaymentType, CalculateTaxCondition,
				AppPageName1, EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
				OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
				PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub,
				paymentLength,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,
				LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
				OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
				 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,ProdutType,IsTaxExempt);
		  	
		  	 d.switchTo().window(parentHandle);
		  	 tec.AC40.Common.Wait.wait2Second();
		  	 d.findElement(Property.logout).click();
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 tec.AC40.Common.Wait.wait5Second();
		  	 	PSLogin();
		  	 	
		  	 	MouseAdjFunction();
		  	 	if(Config.LayoutType.equals("Classic"))
		  	 	{
		  	 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserReportsLink));
		  	 		waitfl.until(ExpectedConditions.elementToBeClickable(Property.UserReportsLink));
		  	 		waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.UserReportsLink);
							 }
							 });
		  	 		d.findElement(Property.PSClassicPendingScheduledOrders).click();
		  	 		tec.AC40.Common.Wait.wait2Second();
		  	 		Thread.sleep(2500);
		  	 	}
		  	 	
		  	 	else if(Config.LayoutType.equals("Layout1"))
		  	 	{
		  	 		Thread.sleep(2500);
		  	 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PSLayout1PendingScheduleIcon));
		  	 		waitfl.until(ExpectedConditions.elementToBeClickable(Property.PSLayout1PendingScheduleIcon));
		  	 		waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.PSLayout1PendingScheduleIcon);
							 }
							 });
		  	 		d.findElement(Property.PSLayout1PendingScheduleIcon).click();
		  	 		tec.AC40.Common.Wait.wait2Second();
		  	 	}
		  	 	else if(Config.LayoutType.equals("Layout2"))
		  	 	{
		  	 		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserBackToHomeLayout2));
		  	 		waitfl.until(ExpectedConditions.elementToBeClickable(Property.UserBackToHomeLayout2));
		  	 		waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.UserBackToHomeLayout2);
							 }
							 });
		  	 		d.findElement(Property.PSLayout2PendingScheduleIcon).click();
			  		tec.AC40.Common.Wait.wait5Second();
		  	 	}
		  	 	tec.AC40.Common.Wait.wait5Second();
		  	 	waitfl.until(ExpectedConditions.elementToBeClickable(Property.PSPendingOrdersCount));
		  	 	waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PSPendingOrdersCount);
						 }
						 });
		  	 	tec.AC40.Common.Wait.wait2Second();
		  	 	String PendingOrders = d.findElement(Property.PSPendingOrdersCount).getText();
		  	 	int pendingOrdersValue = Double.valueOf(PendingOrders).intValue();
		  	 	if(pendingOrdersValue == 0)
		  	 	{
		  	 		waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderNumberIcon));
		  	 		waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.OrderNumberIcon);
							 }
							 });
		  	 	}
		  	 	else
		  	 	{
		  	 		waitfl.until(ExpectedConditions.elementToBeClickable(Property.GridOrderDateHeader));
		  	 		waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.GridOrderDateHeader);
							 }
							 });
		  	 	}
		  	 	tec.AC40.Common.Wait.wait10Second();
		  	 	d.findElement(Property.OrderNumberIcon).click();
		  	 	waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.OrderNumberTextbox));
		  	 	//	d.findElement(Property.OrderNumberTextbox).isDisplayed();
		  	 	waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.OrderNumberTextbox);
						 }
						 });
		  	 	tec.AC40.Common.Wait.wait2Second();
		  	 	d.findElement(Property.OrderNumberTextbox).sendKeys(OrderNumber);
		  	 	d.findElement(Property.OrderSearchSubmit).click();
		  	 	tec.AC40.Common.Wait.wait5Second();
		  	 	AppPageName = "PS View Orders";
		  	
		  	 	// Verify the price values in PS view order page grid
		  	 	ViewOrderGridVerification(SubTotal, Total, OrderBase, ShippingPrice, OrderBaseShipping, Postage, AppPageName,
			  			ShippingPricePerPiece, GrandTotal, ActualShippingPrice, Weighttype, Priceafterapplyingfulfillmentshippingmarkupfee,LandingPageOption,LandingPageSubtotal,
			  			DecimalValue,ItemPerPrice,PromotionDiscountPercentage,Addons,Tax,Discount,DiscountPercentage,DiscountCalculationFromSubTotal,PromotionCoupon,
			  			PromotionDiscountAfterSubtractingFromSubTotal,LDiscuntCalulationFromSubtotal,PriceAfterCalculatingTax,Quantity,
			  			OrderType,DownloadPrice,LandingPageProductPP,BasePriceIncrementValue,ProdutType,LandingPageDiscountValue,EnablePromotionsORDiscounts,CalculateTaxCondition,
						userordershippingorhandlingfee,OrderAmountValue,PriceAfterApplyingCoupon,TotalPrice,IsShippingTaxable,Orderelements,OrderelementsAddOns,
						OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
						 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout);
		  	  waitfl.until(ExpectedConditions.elementToBeClickable(By.linkText(OrderNumber)));
			  	 //d.findElement(Property.UserPrintOrder).isDisplayed();
			  	 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(By.linkText(OrderNumber));
						 }
						 });
			  	 tec.AC40.Common.Wait.wait5Second();
		  	 	d.findElement(By.linkText(OrderNumber)).click();
		  	 	waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserPrintOrder));
		  	 	waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.UserPrintOrder);
						 }
						 });
		  	 	//d.findElement(Property.UserPrintOrder).isDisplayed();
		  	 	tec.AC40.Common.Wait.wait5Second();
		  	 	UserViewOrdersPagePriceverification(SubTotal, Addons, Discount, PromotionCoupon, ShippingPrice, Postage,
		  	 		PriceAfterCalculatingTax, Total, Quantity, ItemPerPrice, FlatRate, DownloadPrice, OrderType, TestCase, TestStep, 
		  	 		Parameters, PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountPercentage, 
		  	 		DiscountCalculationFromSubTotal, OrderBase, OrderBaseShipping, CalculateTaxCondition, AppPageName,
		  	 		EnablePromotionsORDiscounts,Weighttype,DiscountCalculationFromSubTotal,SubTotal,OrderAmountValue,
		  	 		userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,Tax,
					TotalPrice, PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub,
					IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,LandingPageDiscount,
					LandingPageDiscountValue,SubtotalAfterPurlPrice,LandingPageSubtotal,DecimalValue,LDiscuntCalulationFromSubtotal,Orderelements,OrderelementsAddOns,
					OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
					 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,IsTaxExempt);
		  	
		  	 	tec.AC40.Common.Wait.wait2Second();
		  	 	parentHandle = d.getWindowHandle();
		  	 	d.findElement(Property.UserPrintOrder).click();
		  	 	//tec.AC40.Common.Wait.wait2Second();
		  	 	//d.findElement(Property.PrintConfirmationOK).click();
		  	 	tec.AC40.Common.Wait.wait10Second();
		  	
		  	 	AppPageName1 = "PS Print Popup";
			  	
		  	 	PrintPopUpPriceVerification(Quantity, ItemPerPrice, Discount, Addons, Postage, TotalPrice, Total, PromotionCode, 
		  	 		PromotionCoupon, ShippingPrice, Tax, PriceAfterCalculatingTax, AddonPricePerPiece, DiscountPercentage,
		  	 		PromotionDiscountPercentage, PromotionDiscountAfterSubtractingFromSubTotal, DiscountCalculationFromSubTotal,
		  	 		DownloadPrice, OrderType, TestCase, TestStep,Parameters, OrderBase, OrderBaseShipping,
		  	 		Payment1Price, Payment2Price, Payment3Price, Payment4Price, Payment5Price, PaymentType, CalculateTaxCondition,
		  	 		AppPageName1, EnablePromotionsORDiscounts,Weighttype,SubTotal,DiscountCalculationFromSubTotal,
		  	 		OrderAmountValue,userordershippingorhandlingfee,Priceafterapplyingfulfillmentshippingmarkupfee,IsShippingTaxable,
		  	 		PriceAfterApplyingCoupon, EnableZeroAmountOrder, ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub,
		  	 		paymentLength,IfShippingaddressIseditble,LandingPageOption,LandingPageProduct,LandingPageProductPP,
		  	 		LandingPageDiscount,LandingPageDiscountValue,SubtotalAfterPurlPrice,LDiscuntCalulationFromSubtotal,DecimalValue,SubTotal,Orderelements,OrderelementsAddOns,
					OrderelementsBillingAddress,OrderelementsInventoryLocation,OrderelementsInventoryNumber,
					 OrderelementsPaymentDetail,OrderelementsShippingAddress,OrderelementsShippingMethod,OrderelementsSpecialInstructions,OrderelementsCheckout,OrderelementsJobTicket,OrderelementsPrintPopup,ProdutType,IsTaxExempt);
			  	
		  	 	d.switchTo().window(parentHandle);
		  	 	tec.AC40.Common.Wait.wait2Second();
		  	 	if(OrderelementsJobTicket.equalsIgnoreCase("yes")){
		  	 	parentHandle = d.getWindowHandle();
		  	 	
		  	 	d.findElement(Property.jobticket).click();
		  	 	//tec.AC40.Common.Wait.wait2Second();
		  	 	//d.findElement(Property.PrintConfirmationOK).click();
		  	 	tec.AC40.Common.Wait.wait10Second();
		  	 	jobticketPriceVerification(OrderBase,EnableZeroAmountOrder,ProdutType,Quantity,OrderelementsBillingAddress,OrderelementsShippingAddress,OrderelementsCheckout,OrderelementsJobTicket,ShipAddSameAsBillAdd,
		  	 			ShipAddSameAsBillAddSub,AppPageName1,OrderType,IfShippingaddressIseditble);
		  	 	d.switchTo().window(parentHandle);
		  	 	tec.AC40.Common.Wait.wait2Second();
		  	 	}
		  	 	d.findElement(Property.logout).click();
		  	 	tec.AC40.Common.Wait.wait2Second();
		  
		  	 	// Below code will work only Payment method is Co-Op Fund. It verify in which Co-Op Fund value amount is deduct and it 
		  	 	// is exact or not.
		  	 	if(PaymentType.equals("Co-Op Fund"))
		  	 	{
		  	 		
		  	 		adminLogin();
		  	 		tec.AC40.Common.Wait.wait2Second();	
		  	 		try{
		  	 		CompareCOopFundDetails(PaymentSubOpt,Total,DecimalValue);
		  	 		tec.AC40.Common.Wait.wait2Second();	
		  	 		}catch (Exception e) 
		  	 		{
						System.out.println("*** compare coop found catch executed ***");
						et.log(LogStatus.ERROR,"*** compare coop found catch executed ***");
					}
		  	 		tec.AC40.Common.Wait.wait2Second();
		  	 		d.findElement(Property.logout).click();
		  	 		tec.AC40.Common.Wait.wait2Second();
		  	 	}
		  	 } 
		  	 System.out.println("___ ___ ___ ____ _ ____ ____ ____ ______ ____ ___ ____ ____ ____ ____ ____ ____ _____ ____ _____ ____ _");

		  	 if(ErrorNumber == 0)
		  	 {
		  		String[] TaxTypes = CalculateTaxCondition.split("_");
		  		if(TaxTypes[0].equals("Vertex"))
		  		{
		  			RW_File.WriteResultSuccessThirdPartyTax(ActualOverAllTaxValue);
		  			if(ActualOverAllTaxValue.contains("0.00"))
		  			{
		  				ErrorNumber = ErrorNumber+1;
		  				System.out.println("************ Third Party Tax value is ZEROOOOOOOO **************");
		  				et.log(LogStatus.INFO,"************ Third Party Tax value is ZEROOOOOOOO **************");
		  			}
		  			System.out.println("*********Verified*********");
		  			et.log(LogStatus.INFO,"*********Verified*********");
		  			System.out.println("The script verifies Tax value whether it is number or not. We are unable to verify the exact value because it will come from third party. We need to verify the Tax value ("+ActualOverAllTaxValue+") manually with the help of Order number in application and 3rd Party .");
		  			et.log(LogStatus.INFO,"The script verifies Tax value whether it is number or not. We are unable to verify the exact value because it will come from third party. We need to verify the Tax value ("+ActualOverAllTaxValue+") manually with the help of Order number in application and 3rd Party .");
		  			System.out.println("");
		  		}
		  		else if(Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
		  		{
		  			RW_File.WriteResultSuccessThirdPartyShipping(VOupdatedshippingprice);
		  			if(VOupdatedshippingprice.contains("0.00"))
			  		{
			  			ErrorNumber = ErrorNumber+1;
		  				System.out.println("************ Third Party Shipping value is ZEROO  **************");
		  				et.log(LogStatus.INFO,"************ Third Party Shipping value is ZEROO  **************");
			  		}
			  		
					System.out.println("*********Verified*********");
					System.out.println("The script verifies Shipping value whether it is number or not. We are unable to verify the exact value because it will come from third party. We need to verify the Shipping value ("+VOupdatedshippingprice+") manually with the help of Order number in application and 3rd Party .");
					et.log(LogStatus.INFO,"The script verifies Shipping value whether it is number or not. We are unable to verify the exact value because it will come from third party. We need to verify the Shipping value ("+VOupdatedshippingprice+") manually with the help of Order number in application and 3rd Party .");
					System.out.println("");	
		  		}
		  		else if(IsExternalPricingON.equalsIgnoreCase("Yes"))
				 {
		  			System.out.println("*********Verified*********");
					System.out.println("The script verifies External pricing value whether it is number or not. We are unable to verify the exact value because it will come from third party. "
							+ "                             We need to verify the External pricing value ("+ItemPerPrice+") manually with the help of Order number in application and 3rd Party .");
					et.log(LogStatus.INFO ,"The script verifies External pricing value whether it is number or not. We are unable to verify the exact value because it will come from third party. "
							+ "                             We need to verify the External pricing value ("+ItemPerPrice+") manually with the help of Order number in application and 3rd Party .");
					System.out.println(" ");	
				 }
		  		else
		  		{
		  			RW_File.WriteResultSuccess();
					System.out.println("**** *** **Al l the Expected values and Actual values are same, Test PASS*** *** ***");
					et.log(LogStatus.PASS,"**** *** **Al l the Expected values and Actual values are same, Test PASS*** *** ***");
		  		}
		  	 }
		  	 else
		  	 {
				RW_File.WriteResultFail();
				System.out.println("** ** **Actual and Expected values are not same, Test FAIL** ** **");
				et.log(LogStatus.FAIL,"** ** **Actual and Expected values are not same, Test FAIL** ** **");
				System.out.println("Number of errors : "+ErrorNumber);
				et.log(LogStatus.ERROR,"Total Number of errors : "+ErrorNumber);
		  	 }
		  	 stopDriver();
		  	 
			 /**
			  * @@ EXTNER REPORTS CLOSING
			  */
			 er.flush(); //reports saved
			 er.endTest(et);  //reports closed

			
		  	 OrderNumber = null;  //*/
		 }		// Test case if ends here
        }		// Particular row execution if ends here
     }		// try block end here
	 catch (Exception e)
	 {
		 /**
		  * @@ EXTNER REPORTS CLOSING
		  */
		 er.flush(); //reports saved
		 er.endTest(et);  //reports closed
System.out.println("Report are saved and closed");
		
		ErrorNumber = ErrorNumber+1;
		captureScreenshot();
		e.printStackTrace();
	 }
	
	// System.out.println("** Extent reports Ended **");
	 
	}//@Test annotation ends here
 
 @AfterTest
 public void stop() throws IOException, NullPointerException, InterruptedException
 {
	RW_File.Closefile();
	// stopDriver();
	System.out.println("** End**");
 }

 @Test
@DataProvider(name = "testParameterData", parallel = false)
 public static Object[][] testParameterData(Method method) throws Exception 
 {// Data provider annotation usage starts here
	initialize();
	Object data[][] = Testutil.getData(datatable_suite1, "OrderFlow");
	return data;
 } // Data provider annotation ends here
}// Order flow class ends here