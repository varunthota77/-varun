package tec.ACV5.config;

import org.openqa.selenium.By;

import tec.ACV5.suite.orderFlow;

public class property {

	public static By UserName = By.id("username");
	public static By Password = By.id("password");
	public static By LoginButton = By.id("btnLogin"); 
	public static By ForgetPassword = By.id("lnkForgotPassword");
	public static By AdminHomeLink = By.id("lnkBackToHome");
	
	public static By HomeLinkL1 = By.xpath("//img[contains(@src,'home.png')]");
	
	public static By HomeLinkL2 = By.linkText("Home");
	public static By UserNameverification = By.xpath("//header[@id='divHeader']/section/aside[2]/div/h2");
	
	// *****************  User Details ********************
	
	// **************** Admin Details ********************
	
	// ********** Admin Home page **********************
		
	public static By CompanyLink = By.id("lnkNav_COMPANY");
	public static By ProductsLink = By.id("lnkNav_PRODUCTS");
	public static By SecurityLink = By.id("lnkNav_SECURITY");
	public static By MIscellaneousLink = By.id("lnkNav_MISCELLANEOUS");
	public static By MIscellaneousLinkl2 = By.xpath("//span[contains(.,'MISCELLANEOUS')]");
	public static By MIscellaneousLink1 = By.linkText("Miscellaneous");
	public static By UsersIconC = By.xpath("//a[@id='lnkUSERS']/img");
	public static By Orderelements = By.xpath("//i[contains(.,'ORDER ELEMENTS')]");
	public static By Orderelementsc = By.xpath("//img[@title='Order Elements']");
	public static By Orderelements1 = By.linkText("Order Elements");
	public static By Orderelementsl2 = By.xpath("//a[@id='lnkORDER_ELEMENTS']/span/b");

			
			//Layout1
			
			//public static By CompanyIconL1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[2]/a/span[1]/img");
			//public static By ProductsIconL1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[3]/a");
			//public static By ManageOrguntiIconL1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[2]/ul/li[1]/a");
			
			//public static By PricingIconL1 = By.xpath("//div[@id='divHeaderIcons']/ul/li[2]/ul/li[4]/a/b");
			//public static By TaxIconL1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[2]/ul/li[5]/a");
			//public static By ManageProductsIconL1 = By.xpath(".//*[@id='divHeaderIcons']/ul/li[3]/ul/li[1]/a");
			//public static By PromotionsIconL1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[3]/ul/li[5]/a");
			
			//public static By SecurityLinkL1 = By.xpath("//div[@id='divHeaderIcons']/ul/li[4]/a/span/img");
			//public static By UserLinkL1 = By.cssSelector("a[title=\"Users\"] > b");
			
			public static By CompanyIconL1 = By.xpath("//img[contains(@src,'Company.png')]");
			public static By ManageOrguntiIconL1 = By.xpath("//img[contains(@src,'ManageOrgUnits.png')]");
			public static By ProductsIconL1 = By.xpath("//img[contains(@src,'Products_Icon.png')]");
			public static By PricingIconL1 = By.xpath("//img[contains(@src,'Pricing.png')]");
			public static By TaxIconL1 = By.xpath("//img[contains(@src,'Taxes.png')]");
			public static By ManageProductsIconL1 = By.xpath("//img[contains(@src,'ManageProducts.png')]");
			public static By PromotionsIconL1 = By.xpath("//img[contains(@src,'ManagePromotions.png')]");
			
			public static By SecurityLinkL1 = By.xpath("//img[contains(@src,'Security.png')]");
			public static By UserLinkL1 = By.xpath("//img[contains(@src,'Users.png')]");
			
			public static By SecurityLinkL2 = By.xpath("//li[@id='nav_SECURITY']/a/span");
			public static By UserLinkL2 = By.xpath("//a[@id='lnkUSERS']/span/b/img");
			
			
			public static By UsersSearchTextBox =  By.id("txtKendoSearch");
			
			public static By productTab =By.xpath("//img[contains(@src,'Content/themes/Layout1/images/Products_Icon.png')]");
			public static By Manageproducts =By.xpath("//b[contains(text(),'Manage Products')]");
			public static By productsearchbutton =By.xpath("//*[@id='btnSearch']");
			public static By dialslink =By.linkText("Dials");
			public static By productinfo =By.xpath("//a[contains(text(),'Product Info')]");
			public static By productstatus =By.xpath("//span[contains(text(),'ACTIVE')]");
			public static By onlinelink =By.cssSelector("span.km-switch-handle");
			public static By okpopup =By.id("popup_ok");
			public static By dialstab =By.xpath("//a[contains(text(),'Dials')]");
			public static By logout =	By.id("Logout");
			public static By productname =By.xpath("//a[contains(text(),'"+orderFlow.productName+"')]");
			public static By ordername =By.xpath("//*[@id='txtOrderName']");
			public static By orderdesc =By.xpath("//*[@id='txtOrderDescription']");
			public static By closedial =By.xpath("//*[@id='ProductDialGird']/div[2]/table/tbody/tr[1]/td[1]");
}
