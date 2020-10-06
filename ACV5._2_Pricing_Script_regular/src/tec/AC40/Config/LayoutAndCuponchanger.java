package tec.AC40.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LayoutAndCuponchanger {
	
	         public static String Url=Config.url;
             public static String driverlocation=System.getProperty("user.dir")+"\\TestData\\Drivers\\chromedriver.exe";
             public static String chromedriver="webdriver.chrome.driver";
			 public static WebDriver driver;
			 public static String ordernumber;
			 public static String RootUsername="at4100";//dynamic change
			 public static String Rootpassword="welcome";
			 public static String SuperadminUsername="at4100l2";//dynamic change
			 public static String SuperAdminPassword="welcome";
			 public static String AdminUsername=Config.AdminName;
			 public static String AdminPassword=Config.AdminPwd;
		     static String Layout=Config.LayoutType; 
			 static WebDriverWait wait;
			 public static String cuponcode=Config.PromotionCode;
			 public static Actions act;
			 public static int i=0;
			 public static Keyboard s;
			 public static By OrgunitName= By.xpath("//span[text()='atadminr1']");//dynamic change
			 
			 
			 //classic Root layout 
			 public static By CompanyMenu=By.id("lnkNav_COMPANY");
			 public static By Orgunits=By.xpath("//*[@id='lnkCOMMON_ORGUNITS']");
			 public static By OrgunitsSearchfield=By.id("btnSearch");
			 public static By OrgunitEdit=By.xpath("//a[text()='Edit']");
			 public static By ExistedOrgunit=By.xpath("//*[@id='main']/div/div[1]/div[6]/article/div[7]/span[1]/span");
			 public static By OrgunitSaveButton=By.id("btnSave");
			 public static By OrgunitEmulator=By.xpath("//input[@id='txtemulatesearch']");
			 public static By Orunitchangedsucessmsg=By.id("spnSuccessMessage");
			 //Classic layout Admin
			 public static By ManageOrgunitC=By.id("lnkMANAGE_ORGUNIT");
			 public static By ProductsC=By.id("lnkNav_PRODUCTS");
			 public static By PromotionsC=By.id("lnkMANAGE_PROMOTIONS");
			 
			 //Layout1 Admin Links
			 public static By CompanyL1=By.id("ParentMenu_1");
			 public static By ManageOrgunitL1=By.xpath("//*[text()='Manage OrgUnit']");
			 public static By ProductsL1=By.id("ParentMenu_32");
			 public static By PromotionsL1=By.xpath("//b[text()='Promotions']");
			 public static By Orgunit=By.xpath("//b[text()='OrgUnits']");
			 
			 //Layout2 Admin Links
			 public static By CompanyL2=By.id("nav_COMPANY");
			 public static By ManageOrgunitL2=By.xpath("//i[text()='MANAGE ORGUNIT']");
			 public static By ProductsL2=By.id("nav_PRODUCTS");
			 public static By PromotionsL2=By.id("lnkMANAGE_PROMOTIONS");
			 
			 
			 //Common Links for Layout1&Layou2&Classic
			 public static By Username=By.id("username");
			 public static By Password=By.id("password");
			 public static By LoginButton=By.id("btnLogin");
			 public static By Misllenious=By.id("lnkMiscellaneous");
			 public static By HomepageCustomization=By.id("lnkCustomizeHomePage");
			 public static By DefaultLayoutbutton=By.id("btnDefaultCustomizeHome");
			 public static By saveButton=By.id("btnSaveCustomizeHome");
			 public static By CloseButton=By.id("btnCloseCustomizeHome");
			 public static By BacktoHomeButton=By.id("lnkBackToHome");
			 public static By PromotionSearchField=By.id("btnSearch");
			 public static By PromotionEditLink=By.xpath("//a[text()='Edit']");
			 public static By Couponcode=By.id("txt_code");
			 public static By SaveCupon=By.id("btnSave");
			 public static By Successfulmsgfrompromocode=By.id("spnSuccessMessage");
			 public static By HomePageCustomizationSucessmsg=By.id("lblCustomizeHomeSuccessMessage");
			 //public static By 
			 
			 //layout1
			 
			 //Layout2
			 
			public static void waiting4sec() throws InterruptedException{
				Thread.sleep(4000);
			}
			public static void waiting10sec() throws InterruptedException{
				Thread.sleep(10000);
			}
			
			public static void waiting2sec() throws InterruptedException{
				Thread.sleep(2000);
			}
				  
				   public static void main(String []args) throws InterruptedException{
				    	layoutchangeandcouponcode();
				    }
				    
			
			public static void layoutchangeandcouponcode() throws InterruptedException{
				     System.setProperty(chromedriver,driverlocation);
				     driver= new ChromeDriver();
				     System.out.println("=======================Driver is Launched================");
				     driver.get(Url);
				     System.out.println(driver.getTitle());
				     Assert.assertTrue(driver.getTitle().contains("AccuConnect"));
				     driver.manage().window().maximize();
					 act=new Actions(driver);
					 wait = new WebDriverWait(driver, 130);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(Username));
					 waiting2sec();
				     driver.findElement(Username).sendKeys(AdminUsername);    
				     driver.findElement(Password).sendKeys(AdminPassword);  
				     driver.findElement(LoginButton).click(); 
				     waiting4sec();
				     wait.until(ExpectedConditions.visibilityOfElementLocated(CompanyL1));
				     
				     //hiding the layout changer code.
				    /* waiting4sec();
				     
				     driver.findElement(CompanyL1).click();
				     System.out.println("Clicked the COMPANY icon");
				     wait.until(ExpectedConditions.visibilityOfElementLocated(Orgunit));
				     waiting4sec();
				     
				     driver.findElement(Orgunit).click();
				     System.out.println("Clicked the ORGUNITS icon");
				     wait.until(ExpectedConditions.visibilityOfElementLocated(OrgunitsSearchfield));
				     waiting2sec();
				     
				     driver.findElement(OrgunitsSearchfield).sendKeys(SuperadminUsername);
				     System.out.println("Selected the  Superadmin to change the layout");
				     waiting2sec();
				     driver.findElement(OrgunitEdit).click();
				     wait.until(ExpectedConditions.visibilityOfElementLocated(ExistedOrgunit));
				     waiting4sec();
				     String ExistedLayout=driver.findElement(ExistedOrgunit).getText();
				     System.out.println("Existed layout is======"+ExistedLayout);
				     waiting4sec();
				     s=((RemoteWebDriver) driver).getKeyboard();
				    
				     if(!(ExistedLayout.contains((Layout)))){
				    	  driver.findElement(ExistedOrgunit).click();	
				    	  waiting4sec();
						  System.out.println("changed the Existing layout to =="+Layout);
						  s.sendKeys(Layout);
						  waiting2sec();
						  s.sendKeys(Keys.ENTER);
						  System.out.println("Changed the Layout");
			    	      driver.findElement(OrgunitSaveButton).click();
			    	       i++;
			    	       //Try to Print the Save message
			    	  
			    	  } 
				    else{
				    	     System.out.println("No Need To Chnage The Layout BCZ both Actual & Expected LayoutS are SAME");
				    	     driver.findElement(OrgunitEmulator).sendKeys(AdminUsername);
				    	     waiting2sec();
					         s.sendKeys(Keys.ENTER);	
					         waiting10sec();
				    } 
				     
				     
			    if(i==1){
			    	         wait.until(ExpectedConditions.visibilityOfElementLocated(Orunitchangedsucessmsg));
			    	         System.out.println(" I value is==="+i);
			    	         waiting4sec();
			    	         System.out.println("Going To Click the HOME PAGE CUSTOMIZATION link under company misslenious");
				             driver.findElement(OrgunitEmulator).sendKeys(AdminUsername);
				             waiting2sec();
				             s.sendKeys(Keys.ENTER);
				     
				if(Layout.equalsIgnoreCase("Classic")){
					
				    	 wait.until(ExpectedConditions.visibilityOfElementLocated(CompanyMenu));
				    	 waiting2sec();
					     System.out.println("Clicked the COMPANY menu");
					     driver.findElement(CompanyMenu).click();
					     wait.until(ExpectedConditions.visibilityOfElementLocated(ManageOrgunitC));
					     waiting2sec();
					     driver.findElement(ManageOrgunitC).click();
					     System.out.println("Clicked the MANAGE ORG UNIT Tab");
			    	  }
			    else if(Layout.equalsIgnoreCase("Layout1")){
			    	
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(CompanyL1));
			    		 waiting2sec();
			 		    System.out.println("Clicked the COMPANY menu");
			 		     driver.findElement(CompanyL1).click();
			 		     wait.until(ExpectedConditions.visibilityOfElementLocated(ManageOrgunitL1));
			 		    waiting2sec();
					     driver.findElement(ManageOrgunitL1).click();
					     System.out.println("Clicked the MANAGE ORG UNIT Tab");
			    	  }
			    else if(Layout.equalsIgnoreCase("Layout2")){
			    	
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(CompanyL2));
			    		 waiting2sec(); 
			 		     System.out.println("Clicked the COMPANY menu");
			 		     driver.findElement(CompanyL2).click();
			 		     wait.until(ExpectedConditions.visibilityOfElementLocated(ManageOrgunitL2));
			 		     waiting2sec(); 
					     driver.findElement(ManageOrgunitL2).click();
					     System.out.println("Clicked the MANAGE ORG UNIT Tab");
			    	  }
				     
						waiting4sec();
						wait.until(ExpectedConditions.visibilityOfElementLocated(Misllenious));
						waiting2sec();
						driver.findElement(Misllenious).click();
						System.out.println("Clicked the Misslenious Tab");
						wait.until(ExpectedConditions.visibilityOfElementLocated(HomepageCustomization));
						waiting2sec();
						driver.findElement(HomepageCustomization).click();
						wait.until(ExpectedConditions.visibilityOfElementLocated(DefaultLayoutbutton));
						waiting2sec();
						driver.findElement(DefaultLayoutbutton).click();
						waiting2sec();
						System.out.println("Clicked the DefaultLayoutbutton");
						driver.findElement(saveButton).click();
						
						wait.until(ExpectedConditions.visibilityOfElementLocated(HomePageCustomizationSucessmsg));
						// Try to get the save message
						//lblCustomizeHomeSuccessMessage   id
						waiting2sec();
						System.out.println("Message We are Getting====="+driver.findElement(HomePageCustomizationSucessmsg).getText());
						driver.findElement(CloseButton).click();
						waiting4sec();
						
						driver.findElement(BacktoHomeButton).click();
						waiting2sec();
						wait.until(ExpectedConditions.visibilityOfElementLocated(OrgunitName));
			    	  
			    	 }
				     
			             waiting2sec();
			             
			             System.out.println(Layout);*/
				    	 if(Layout.equalsIgnoreCase("Classic")){//Classic
				    		 wait.until(ExpectedConditions.visibilityOfElementLocated(ProductsC));
				    		 waiting2sec();
				    		    driver.findElement(ProductsC).click();
				    		    waiting4sec();
					    	    driver.findElement(PromotionsC).click();
				    	 }
					     else if(Layout.equalsIgnoreCase("Layout1")){
					    	 
					    	    driver.findElement(ProductsL1).click();
					    	    waiting4sec();
					    	    driver.findElement(PromotionsL1).click();
					    	
					     }
					     else if(Layout.equalsIgnoreCase("Layout2")){
					    	    driver.findElement(ProductsL2).click();
					    	    waiting4sec();
					    	    driver.findElement(PromotionsL2).click();
					     }
				    	 
				    	 wait.until(ExpectedConditions.visibilityOfElementLocated(PromotionSearchField));
				    	 waiting2sec();
				    	 driver.findElement(PromotionSearchField).sendKeys("Couponcode");
				    	 waiting4sec();
				    	 driver.findElement(PromotionEditLink).click();
				    	 waiting2sec();
				    	 wait.until(ExpectedConditions.visibilityOfElementLocated(Couponcode));
				    	 waiting2sec();
					     String promo= driver.findElement(Couponcode).getAttribute("value");
					     waiting2sec();
					     System.out.println("Existed cuponcode is===="+promo);
					     if(!promo.contains((cuponcode))){	    	 
					    	 driver.findElement(Couponcode).clear();
					    	 waiting2sec();
					    	 driver.findElement(Couponcode).sendKeys(cuponcode); 
					    	 waiting2sec();
					    	 driver.findElement(SaveCupon).click();
					    	 System.out.println("Changed cuponcode is===="+cuponcode); 
					    	 wait.until(ExpectedConditions.visibilityOfElementLocated(Successfulmsgfrompromocode));
					    	 waiting2sec();
					    	 //Try to get the Sucessfull message
					    	 System.out.println("Message We are Getting====="+driver.findElement(Successfulmsgfrompromocode).getText());
					    	 driver.quit();
					      }else{
					    	 
					    	 System.out.println("No need to Change the coupon code both Actual&Expected are Same.");
					    	 driver.quit();
					      }
					      
			    	 }
					     




}

