package tec.AC40.Config;
import org.openqa.selenium.By;

public class Property {
	
	
	//******************  Common Details ****************
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
			
			//Layout2
			
			public static By CompanyIconL2 = By.xpath("//*[@id='nav_COMPANY']/a");
			public static By ProductsIconL2 = By.xpath("//*[@id='nav_PRODUCTS']/a");
			
			public static By ManageOrguntiIconL2 = By.xpath("//*[@id='lnkMANAGE_ORGUNIT']/span/i");
			public static By PricingIconL2 = By.xpath("//*[@id='lnkPRICING']/span/i");
			public static By TaxIconL2 = By.xpath("//*[@id='lnkTAXES']/span/i");
			public static By ManageProductsIconL2 = By.xpath("//*[@id='lnkMANAGE_PRODUCTS']/span/i");
			public static By PromotionsIconL2 = By.xpath("//*[@id='lnkMANAGE_PROMOTIONS']/span/i");
			public static By FullfilmentlocationL2 = By.xpath("//*[@id='lnkFULFILLMENT_LOCATION']/span/i");
			
			public static By UsersIconL2 = By.xpath("//img[@alt='Users']");
			public static By usersSearch = By.id("txtKendoSearch");
			public static By UserCoopFund = By.linkText("Co-Op Fund");
			public static By AddCoopFundButton = By.id("lnkAddCoopfund");
			public static By CoopfundSearchTextBox = By.id("txtSearchText");
			
			public static By TotalFunds = By.xpath("//div[@id='CoopFundGridDiv']/table/tbody/tr/td[1]");
			public static By CoopFundsUsed = By.xpath("//div[@id='CoopFundGridDiv']/table/tbody/tr/td[2]");
			public static By AvailableFunds = By.xpath("//div[@id='CoopFundGridDiv']/table/tbody/tr/td[3]");
			
			public static By TotalFunds2 = By.xpath("//div[@id='CoopFundGridDiv']/table/tbody/tr[1]/td[1]");
			public static By CoopFundsUsed2 = By.xpath("//div[@id='CoopFundGridDiv']/table/tbody/tr[1]/td[2]");
			public static By AvailableFunds2 = By.xpath("//div[@id='CoopFundGridDiv']/table/tbody/tr[1]/td[3]");
			
			public static By EnableProductionCheckBox = By.id("chkbxisProduction");
			public static By reorderoption1 = By.xpath("//*[@id='chkEnableReorderOption']");
			public static By approveredit = By.xpath("//*[@id='chkbxenableOrderEditByApprover']");
			public static By approveredit1 = By.id("chkbxenableApprovalProcess");
			// ********** Company link ***********************  
			
			public static By ManageOrgunitIcon = By.xpath("//a[@id='lnkMANAGE_ORGUNIT']/img");
			public static By ProductionIcon = By.xpath("//a[@id='lnkPRODUCTION']/img");
			public static By FulfillmentLocationsIcon = By.xpath("//a[@id='lnkFULFILLMENT_LOCATION']/img");
			//public static By FulfillmentLocationsIconl1 = By.xpath("//div[@id='divHeaderIcons']/ul/li[2]/ul/li[8]/a/span");
			public static By FulfillmentLocationsIconl1 = By.xpath("//img[contains(@src,'Locations.png')]");
			public static By PricingIcon = By.xpath("//a[@id='lnkPRICING']/img");
			public static By TaxesIcon = By.xpath("//a[@id='lnkTAXES']/img");
			public static By AttributesIcon = By.xpath("//a[@id='lnkAttributes']/img");
			public static By ContactFieldsIcon = By.xpath("//a[@id='lnkContactFields']/img");
			
	
			
					// ******************** Manage Orgunit ********************
			public static By CalculateTaxCheckBox = By.id("chkbxenableCalculateTax");
			public static By EnablePromotionsORDiscounts = By.id("chkbxEnablePromotions");
			public static By TaxProviderDropDown = By.xpath("//span[@aria-owns='ddlTaxProviders_listbox']");
			public static By TaxProviderList = By.xpath("//div[@id='ddlTaxProviders-list']");
			public static By SelectedTaxProvider = By.xpath("//span[@aria-owns='ddlTaxProviders_listbox']/span/span");
			
			public static By CurrencyDecimaldropdown = By.xpath("//span[@aria-owns='ddlCurrencydecimals_listbox']/span");
			//for layout1
			//public static By CurrencyDecimaldropdown = By.xpath(".//*[@id='GeneralConfigFieldSet']/div[30]/div[2]/div/span/span/span[2]/span");
			public static By CurrencyDecimalSelection1 = By.xpath("//ul[@id='ddlCurrencydecimals_listbox']/li[1]");
			public static By CurrencyDecimalSelection2 = By.xpath("//ul[@id='ddlCurrencydecimals_listbox']/li[2]");
			public static By CurrencyDecimalSelection3 = By.xpath("//ul[@id='ddlCurrencydecimals_listbox']/li[3]");
			public static By CurrencyDecimalSelection4 = By.xpath("//ul[@id='ddlCurrencydecimals_listbox']/li[4]");
			//for classic
			public static By OrderAmoutDecimal = By.xpath("//span[@aria-owns='ddlOrderAmountDecimals_listbox']/span");
			//for layout1
			//public static By OrderAmoutDecimal= By.xpath(".//*[@id='GeneralConfigFieldSet']/div[32]/div[2]/div/span/span/span[2]/span");
			public static By OrderAmountSelection1 = By.xpath("//ul[@id='ddlOrderAmountDecimals_listbox']/li[1]");
			public static By OrderAmountSelection2 = By.xpath("//ul[@id='ddlOrderAmountDecimals_listbox']/li[2]");
			public static By OrderAmountSelection3 = By.xpath("//ul[@id='ddlOrderAmountDecimals_listbox']/li[3]");
			public static By OrderAmountSelection4 = By.xpath("//ul[@id='ddlOrderAmountDecimals_listbox']/li[4]");
			//for classic 
			public static By weightdecimaldropdown = By.xpath("//span[@aria-owns='ddlWeightdecimals_listbox']/span");
			public static By weightdecimalselection1 = By.xpath("//*[@id='ddlWeightdecimals_listbox']/li[1]");
			public static By weightdecimalselection2 = By.xpath("//*[@id='ddlWeightdecimals_listbox']/li[2]");
			public static By weightdecimalselection3 = By.xpath("//*[@id='ddlWeightdecimals_listbox']/li[3]");
			public static By weightdecimalselection4 = By.xpath("//*[@id='ddlWeightdecimals_listbox']/li[4]");
			
			
			
			public static By ShippingisTaxableYes = By.id("rdbtnTaxableShippingYes");
			public static By ShippingisTaxableNO = By.id("rdbtnTaxableShippingNo");
			public static By PostageTaxableYes =By.id("rdbtnTaxablePostageYes") ;
			public static By PostageTaxableNO = By.id("rdbtnTaxablePostageNo");
			
			public static By EnableZeroAmountOrder = By.id("chkbxEnableZeroAmountOrder");
			public static By ShowBillingAddressToZeroAmountYES = By.id("rdbtnZeroAmountOrderYes");
			public static By ShowBillingAddressToZeroAmountNO = By.id("rdbtnZeroAmountOrderNo");
			
			public static By ExternalpricingDDL = By.xpath("//span[@aria-owns='ddlExternalPricingProvider_listbox']/span");
			public static By EnternalpricingDDLSelect = By.xpath("//ul[@id='ddlExternalPricingProvider_listbox']/li");
			public static By ExternalpricingDDLExternalPrice = By.xpath("//ul[@id='ddlExternalPricingProvider_listbox']/li[2]");
			
			public static By GeneralConfigSave = By.id("btnSave");
			
			public static By GeneralConfigSaveSuccessMsg = By.id("lblSuccessMessage");
			
			
		//************ shipping for enable weight settings**********//
			
			public static By generalconfigshipping = By.id("lnkShipping");
			public static By Enableweightpackage = By.id("chkApplyWeightPerPackage");
			public static By ordershippinghandlingfee = By.id("chkUseOrderShippingOrHandlingFee");
			public static By feeentertextbox = By.xpath("(//input[@type='text'])[2]");
			public static By weightshippingsave = By.id("lnkSaveShippingConfig");
			public static By weightshippingsavemsg = By.id("spnShippingSuccessMessage");
			public static By upsdropdown = By.xpath("//*[@id='lnkUPSInfo']/span");
			public static By shippingmethodselectbox = By.id("chkUPSAll");
			public static By upsnextdayselectbox = By.xpath("//input[starts-with(@id,'chkUpsShippingMethodName')]");
			public static By Uspsdropdown = By.xpath("//a[@id='lnkUSPSInfo']/span");
			public static By Uspsshippingselctbox = By.id("chkUSPSAll");
			public static By UspsPriorityselectbox = By.xpath("//input[starts-with(@id,'chkUSPSShippingMethodName')]");
			public static By shippingbasis = By.xpath("//div[@id='divShippingSettings']/div/div[4]/fieldset/div/div/span");
			public static By Shippingpicebasis = By.xpath("//span[@aria-owns='ddlShippingPriceBasis_listbox']/span");
			public static By ShippingpicebasisIndividual = By.xpath("(//ul[@id='ddlShippingPriceBasis_listbox']/li[2])[2]");
			public static By ShippingpicebasisConsolidated = By.xpath("(//ul[@id='ddlShippingPriceBasis_listbox']/li[3])[2]");
			public static By ShippingpicebasisIndividual2 = By.xpath("(//ul[@id='ddlShippingPriceBasis_listbox']/li[2])[1]");
			public static By ShippingpicebasisConsolidated2 = By.xpath("(//ul[@id='ddlShippingPriceBasis_listbox']/li[3])[1]");
			
			public static By Defaultlocation = By.xpath("//div[2]/div/span/span");
			public static By DefaultPackageWeight = By.xpath("//div[@id='divDefalutWeightConsolidatedShipping']/div/span/span/span");
			
			//***************** Payment Settings ************************
			
			public static By PaymentSettingsTab = By.linkText("Payment Settings");
			
			public static By BillingCheckBox = By.id("chkBilling");
			public static By BillingSubOption = By.name("rdBill");
			
			public static By CoOpFund = By.id("chkCoopFund");
			public static By CoopFundSubOption = By.name("rdCoopFund");
			public static By MoneyOnAccountSQL = By.xpath("//input[starts-with(@id,'Money on Account SQL_')]");
			
			public static By CreditCardStatus = By.id("chkCreditCard");
			
			
			public static By CCAutoNet = By.xpath("//input[starts-with(@id, 'Authorize.Net_')]");
			public static By CCCybSou = By.xpath("//input[starts-with(@id, 'CyberSource_')]");
			public static By CCPayPal = By.xpath("//input[starts-with(@id, 'PayPal_')]");
			public static By CCXipay = By.xpath("//input[starts-with(@id, 'XiPay_')]");
			
			public static By CCAonly = By.id("rdauthorizeonly");
			public static By CCACap = By.id("rdauthorizeandcapture");
			public static By CCAChaLat = By.id("rdauthorizeandchargelater");
			
			public static By ShipAddSameAsBillAddYes = By.id("rdbYes");
			public static By ShipAddSameAsBillAddNO = By.id("rdbNo");
			
			public static By CostCentrStatus = By.id("chkEnableCostCenter");
			
			public static By GiftcardStatus = By.id("chkGiftCard");
			public static By GiftcardSQLRadioButton = By.name("rdGift");
			
			public static By DirectORExpressStatus = By.id("chkDirectExpress");
			public static By PayPalCreditStatus = By.xpath("//label[contains(text(),'PayPal Credit')]/preceding-sibling::input[@name='chkDirectOption']");
			public static By PayPalExternalCheckOut = By.xpath("//label[contains(text(),'PayPal External Checkout')]/preceding-sibling::input[@name='chkDirectOption']");
			public static By PayUbizExtrenalCheckOut = By.xpath("//label[contains(text(),'PayUbiz External Checkout')]/preceding-sibling::input[@name='chkDirectOption']");
			//for payubiz payment method locators
			public static By payubizcardnumber = By.id("ccard_number");
			public static By payubiznameoncard = By.id("cname_on_card");
			public static By payubizcvv = By.id("ccvv_number");
			public static By payUbizexpirymonthdd = By.id("cexpiry_date_month");
			public static By payUbizexpiryyeardd = By.id("cexpiry_date_year");
			//public static By payUbizmnthselection = 
			public static By payubizpaynow = By.id("pay_button");
			public static By Payubizamountlabel = By.id("amountTag");
			public static By Payubizamount = By.id("totalAmount");
			
			
			public static By CFFIFO = By.id("rdbFifo");
			public static By CFExpiryDate = By.id("rdbExpDate");
			
			public static By CostCenterStatus = By.id("chkEnableCostCenter");
			public static By DisplayAllCostCenterYes = By.id("rdoCostCentersYes");
			
			public static By PaymentSettingsSave = By.id("lnkPaymentConfigSave");
			public static By PaymentSaveSuccessMsg = By.id("spnSuccessMessage");
			
			public static By ListSettingsTab = By.id("lnkListConfig");
			public static By RestrictedFieldsFormatCheckBox = By.id("chkbxRestrictFieldFormat");
			public static By ListSettingsSaveButton = By.id("btnListConfigSave");
			
							// ******************** Miscellaneous ********************************
			
							public static By Miscellaneoustab = By.xpath("//*[@id='lnkMiscellaneous']");
							public static By PostagePricingLink = By.id("lnkPostagePrice");
							public static By PostageEditLink = By.xpath("//a[contains(text(),'Edit')]");
							public static By PostagePriceDownArrow = By.xpath("//div[@id='divPostagePricingActionData']/div[2]/div/div/div[2]/span/span/span/span[2]/span");
							public static By PostageName = By.id("txt_PostageName");
							public static By PostagePrice = By.xpath("(//input[@type='text'])[5]");
							public static By PostagePriceUpdate = By.id("btnPostagePricingAction");
							public static By PostagepriceSuccessMsg = By.id("lblSuccessMessage");
							public static By PostagSearch = By.id("btnSearch");
							
							
							// ************************ Addon Details *****************************
							 
							public static By AddonLink = By.id("lnkAddons");
							public static By AddNewAddon = By.id("lnkAddNewAddon");
							public static By AddonName = By.xpath("");
							public static By AddonSelected = By.id("rdoSelectedYes");
							public static By AddonSelectTypeDownArrow = By.xpath("//div[@id='divuicontroltype']/span/span/span[2]/span");
							public static By AddonSelectValue = By.xpath("//ul[@id='ddluicontroltype_listbox']/li[4]");
					
							// ************************* Shipping Details *****************************
							
							public static By MngOrgShippingLink = By.id("lnkShipping");
							public static By ShippingBasisValue = By.cssSelector("span.k-input");
							public static By ShippingBasisDownArrow = By.cssSelector("span.k-icon.k-i-arrow-s");
							public static By ShippingPriceBasisDwonArrow = By.xpath("//div[@id='divShippingSettings']/div/div[4]/fieldset/div/div[2]/span/span/span[2]/span");
							public static By ShippingConfigSave = By.id("lnkSaveShippingConfig");
							public static By shippingSaveSuccessMsg = By.id("spnShippingSuccessMessage");
							public static By shippingbacktohome = By.xpath("//a[contains(text(),'Back to Home')]");
							
			
					// ******************* Production  ************************
			
			
					// ******************* FulfillmentLocation ****************
			
			
				
					// ******************* Pricing ****************************
			 
			public static By PricingSearchBox = By.id("btnSearch");
			public static By priceDetailsLink = By.xpath("//a[contains(text(),'Price Details')]");
			public static By PriceEditLink = By.xpath("//a[contains(text(),'Edit')]");
			public static By PriceEntertextBox = By.xpath("(//input[@type='text'])[7]");
			public static By PriceDetailsDownArrow = By.xpath("//div[@id='divPriceDetails']/div[2]/div/div/div[3]/span/span/span/span[2]/span");
			public static By PriceMinimumQuantity = By.xpath("(//input[@type='text'])[3]");
			public static By PriceTypeDropDown = By.xpath("//div[@id='divPriceDetails']/div[2]/div/div/div[4]/span/span/span[2]/span");
			public static By PriceTypeValueSelection = By.xpath("//ul[@id='ddlutype_listbox']/li[3]");
			public static By PriceTypeValueSelectionFlat = By.xpath("//ul[@id='ddlutype_listbox']/li[2]");
			public static By PriceDetailsSaveButton = By.id("savePriceDetailAddOrEdit");
			public static By priceSaveSuccessMsg = By.id("lblSuccessMessage");
			public static By Weightentertextbox = By.xpath("//*[@id='divShippingWeight']/span[1]/span/input[1]");
			public static By shippingtypedropdown = By.xpath("//*[@id='divShippingUnitType']/span[1]/span/span[2]/span");
			public static By shippingtypeddselection1 = By.xpath("//div[@id='divShippingUnitType']/span/span/span");
			public static By selectshippingdropdoen = By.xpath("//*[@id='ddlshipunittype_listbox']/li[1]");
			//public static By shippingtypeddselection2 = By.xpath("//ul[@id='ddlshipunittype_listbox']/li[2]");
			//public static By shippingtypeddselection3 = By.xpath("//ul[@id='ddlshipunittype_listbox']/li[3]");
			public static By DownloadPriceDownArrow = By.xpath("//div[@id='divDownloadPrice']/div/span/span/span/span/span[2]/span");
			public static By ShipToMyAddressDisplayName = By.xpath("//input[@id='txtShiptomyAddress']");
		
			public static By Price3Perpiece = By.cssSelector("div[id*='ddlutype-list'] li:nth-child(3)");
			public static By Price3FlatRate = By.cssSelector("div[id*='ddlutype-list'] li:nth-child(2)");
			
					// ******************* Taxes *****************************
			
			public static By TaxEditLink = By.xpath("//a[contains(text(),'Edit')]");
			public static By TaxPriceDownArrow = By.xpath("//div[@id='divManageTaxes']/div[2]/div/div/div[2]/span/span/span/span[2]/span");
			public static By TaxCountry = By.id("lblAuthorityType");
			public static By TaxValue = By.xpath("(//input[@type='text'])[3]");
			//public static By TaxValue1 = By.xpath("(//input[@type='text'])[3]");
			public static By TaxSaveButton = By.id("saveManageTaxes");
			public static By TaxSaveSuccessMsg = By.id("lblSuccessMessage");
			
					// ********************* Attributes **********************
			
			
			
			
					// ********************** ContactFields ******************
			
			
		    // ********************  Fullfillmet locations ************
			 
			public static By FulFillmentEdit = By.xpath("//a[contains(text(),'Edit')]");
			public static By ShiipingHandilingEdit = By.xpath("//div[@id='fulfillmaindiv']/div[2]/div[8]/span/span/span/input");
		    public static By ShippingMarkupEdit = By.xpath("(//input[@type='text'])[17]");
		    public static By FullfillmentSave = By.id("btnSave");
		    public static By FullfillmentSucessMessage = By.id("spnSuccessMessage");
		    public static By Backtohome = By.id("btnBackToHome");
		    public static By ShippingDropDown = By.xpath("//div[@id='fulfillmaindiv']/div[2]/div[8]/span/span[2]/span/span[2]/span");
		    public static By MarkupDropDown = By.xpath("//div[@id='fulfillmaindiv']/div[2]/div[9]/span/span[2]/span/span[2]/span");
		    public static By ShippingDDAmount = By.xpath("//ul[@id='shippingHandlingFeeType_listbox']/li[2]");
		    public static By ShippingDDPercent = By.xpath("//ul[@id='shippingHandlingFeeType_listbox']/li");
		    public static By MarkupDdAmount = By.xpath("//ul[@id='shippingMarkupFeeType_listbox']/li[2]");
		    public static By MarkupDDPercent = By.xpath("//ul[@id='shippingMarkupFeeType_listbox']/li");
		    
		    
		    
			
			
			// ************************* Products link *************************
			
			public static By ManageProductsIcon = By.xpath("//*[@id='lnkMANAGE_PRODUCTS']/img");
			public static By CategoriesIcon = By.xpath("//a[@id='lnkCategories']/img");
			public static By AssetSourcesIcon = By.xpath("//a[@id='lnkAssetsources']/img");
			public static By PromotionsIcon = By.xpath("//a[@id='lnkMANAGE_PROMOTIONS']/img");
			public static By InventoryItemsIcon = By.xpath("//img[@alt='Inventory Item']");
			
			
			        // ********** Manage Products Icon details **************
					public static By ProductsSearchBox = By.id("btnSearch");
					public static By ShippingLink = By.xpath("//a[contains(text(),'Shipping')]");
					public static By ShippingTab = By.id("lnkShipping");
					public static By ListTypesTab = By.id("lnkListTypes");
					public static By EditLink = By.xpath("//a[contains(text(),'Edit')]");
					public static By EditGurl = By.xpath("//tr[2]/td[4]/a");
					public static By ProductOffline = By.cssSelector("span.km-switch-label-on");
					public static By ProductStatus = By.xpath("//span[@aria-owns='ddlStatus_listbox']/span/span");
					public static By ProductStatusBGColor = By.cssSelector("");
					public static By ProductAlertOK =  By.id("popup_ok");
					public static By PricingTab = By.id("lnkPricing");
					public static By LandingPagepricecode=By.xpath("//div[@id='divpricecode']/span[2]/span/span");
					public static By pricingselectlabel=By.xpath("//ul[@id='ddlFormFields_listbox']/li[1]");
					public static By pricecodeSavebtn = By.id("btnSave");
				    public static By PricecodeSaveSuccessMsg=By.id("lblSuccessMessage");
					public static By ProductInfoProductName = By.id("txtProductName");
					public static By ProductCodeTextBox = By.id("productCode");
					public static By BasePriceTextBoxStatic = By.xpath("//span[contains(text(),'Base Price')]/following::span[1]");
					public static By BasePriceTextBoxBroadcast=By.xpath("(//input[@type='text'])[20]");
					public static By BasePriceTextBoxDynamic = By.xpath("(//input[@type='text'])[36]");
					public static By ProductInfoSave = By.id("btnProductSave");
					public static By ProductInfoSuccessMsg = By.id("lbl_successText");
					public static By ShippingEditlink = By.xpath("//a[contains(text(),'Edit')]");
			        public static By ShippingPrice = By.xpath("(//input[@type='text'])[6]");
			        public static By ShippingMinQty = By.xpath("(//input[@type='text'])[2]");
			        public static By ShipUnitTypeDropDown = By.xpath("//div[4]/span/span/span[2]/span");
			        public static By ShipUnitTypeDropDown1 = By.cssSelector("span.k-select > span.k-icon.k-i-arrow-s");
			        public static By ShipUnitTypePerPice = By.xpath("//div[6]/div/ul/li[3]");
			        public static By ShipUnitTypeflatRate = By.xpath("//div[6]/div/ul/li[2]");
			        public static By ShipPriceDetailsSave = By.xpath("//a[@id='savePriceDetailAddOrEdit']");
			        public static By ShipPriceSaveSuccessMsg = By.id("lbl_successText");
			        public static By ProductOnlineLink = By.cssSelector("span.km-switch-handle");
			        public static By shippingdropdownText = By.cssSelector("span.k-input");
					public static By TaxExemptCheckBoxStatic=By.xpath("//input[@id='chkIsTaxExempt']");
			        public static By ProductInfolink = By.xpath("//a[@id='lnkProductInfo']");
			        public static By ProductStatusValue = By.xpath("//div[@id='divSettings']/div/div/article/div[28]/span/span/span");
			        public static By ListTypeEditLink = By.xpath("//a[contains(text(),'List Types')]");
			        public static By ListTypesSave = By.xpath("//input[@id='saveProductListtypes']");
			        public static By ListSavedSuccessMsg = By.id("lbl_successText");
			        public static By DownloadPriceTextBox = By.xpath("//div[@id='divDownloadPrice']/div/span/span/span/input");
			        public static By DownloadOrPrintRatioButton = By.id("rdbtnDownloadPrint");
			        public static By StaticDownloadPriceTextBox = By.xpath("//div[@id='divDownloadPrice']/div/span/span/span/input");
			        public static By ShiptomultipleLocations = By.id("chkShipToMultipleLocations");
			        
			        //Landing page PresetLPSettings
			        public static By PresetLPSettingscheck = By.id("chkUsePresetLPSettings");  
			        public static By siteoption = By.xpath("//input[@id='rdbSiteNameType_Custom']");
			        public static By sitename = By.xpath("//input[@id='txtSiteName']");
			        public static By userlandingfield = By.xpath("//div[@id='divLstRecepients']/select");
			        public static By LandingPageimg = By.xpath("//img[@alt='Product Details']");
			        public static By Customsitecheck = By.id("btnCheckSiteAvailability");
			        
			        public static By None = By.id("rdbLandingPage_None");
			        public static By Optional = By.id("rdbLandingPage_Optional");
			   	    public static By Required = By.id("rdbLandingPage_Required");
			   	    public static By SelectProductslink = By.id("lnkLpProducts");
			   	    public static By searchProduct = By.id("btnLPListSearch");
			   	    public static By SelectLandingProduct = By.xpath("//div[@id='divLandingpageProducts']/table/tbody/tr/td/input");
			   	    public static By LandingProductSelect = By.id("lnkSaveLP");
			   	    public static By LandingProducts = By.id("divLPProducts");  
			   	    public static By LandingURL = By.xpath("//div[@id='divLandingPageProperties']/span/span/span");
			   	    public static By LandingKeyfield = By.xpath("//div[@id='divLandingPageKeyField']/span/span/span");
			   	    public static By LandingURLoption = By.xpath("//ul[@id='ddlLandingPageUrlField_listbox']/li[4]");
			   	    public static By LandingKeyoption = By.xpath("//ul[@id='ddlLandingPageKeyField_listbox']/li[5]");
			        
			
				// **************** Promotions details ************************
			
			public static By PromotionsSearch = By.id("btnSearch");
			public static By PromotionsEdit = By.xpath("//a[contains(text(),'Edit')]");
			public static By PromotionvalueDownArrow = By.xpath("//div[@id='main']/div/div/div[4]/div[6]/span/span/span/span/span[2]/span");
			public static By PromotionValue = By.xpath("(//input[@type='text'])[4]");
			public static By PromotionSave = By.id("btnSave");
			public static By PromotionSuccessMsg = By.id("spnSuccessMessage");
			public static By PromotionName = By.id("txt_name");
			public static By PromotionDiscountNO = By.id("rbpercentno");
			public static By PromotionDisocuntYes = By.id("rbpercentyes");
			public static By PromotionDisocuntActive = By.id("rbactiveyes");
			public static By PromotionDiscountDeactive = By.id("rbactiveno"); 
			public static By PromotionCouponDownArrow = By.xpath("//div[@id='main']/div/div/div[4]/div[6]/span/span/span/span/span[2]/span");
			public static By PromotionCouponName = By.id("txt_name");
			public static By PromotionCouponValue = By.xpath("(//input[@type='text'])[4]");
			
	
	// Login Page
	// public static By loginlink = By.linkText("LOGIN");
	
	// DashBoard
	public static By CRM = By.id("ContactLookup");
	// public static By CRM =
	// By.xpath("//a[@href='http://ypdirect.accuconnect.com/performance/List.accv3/ATTListLookup']");

	// Contacts
	public static By COntactslink = By.id("Contacts");
	// public static By COntactslink =
	// By.xpath("//a[@href='http://ypdirect.accuconnect.com/performance/List.accv3/ATTContactLookup']");
	// public static By COntactslink =
	// By.xpath("(//a[contains(text(),'Contacts')])[2]");
	public static By ADD_Link = By.linkText("Add");
	public static By New_Contact = By.id("NewContact");
	
	//Login Page
	public static By username = By.id("username");
	public static By pwd = By.id("password");
	public static By Loginbtn = By.id("btnLogin");
	
	public static By OrgunitNameLink =  By.id("lnkHomeref");
	
	//Classic
	public static By Products = By.id("lnkNav_PRODUCTS");
	public static By CreateNewIcon = By.xpath("//a[@id='lnkNEWORDER']/img");
	
	//Layout1
	//public static By ProjectsIcon = By.xpath("//*[@id='divHeaderIcons']/ul/li[2]/a/span[1]");
	public static By ProjectsIcon = By.xpath("//img[contains(@src,'Caregories.png')]");
	public static By HomeImageL1 = By.xpath("//img[contains(@src,'image_a.jpg')]");
	
	//Layout2
	public static By MyProjectsIcon = By.xpath("//*[@id='nav_MNU_MYPROJECTS']/a");
	public static By CreateNewIconL2 = By.xpath("//*[@id='lnkNEWORDER']/span/i");
	public static By ProductSearchL2 = By.id("txtProductSearchText");
	
	//Layout1
	//public static By DynamicPrintNamel1 = By.xpath("//em[@class='productNameThumb'][contains(text(),'Dynamic Print')]");
	//public static By DynamicEmailNamel1 = By.xpath("//em[@class='productNameThumb'][contains(text(),'Dynamic Email')]");
	//public static By StaticPrintNamel1 = By.xpath("//em[@class='productNameThumb'][contains(text(),'Static Print')]");
	//public static By StaticInventoryNamel1 = By.xpath("//em[@class='productNameThumb'][contains(text(),'Static Inventory')]");
	//public static By BroadcastNamel1 = By.xpath("//em[@class='productNameThumb'][contains(text(),'Broadcast')]");
	
	public static By DynamicPrintNamel1 = By.xpath("//a[@title='Dynamic Print']");
	public static By DynamicEmailNamel1 = By.xpath("//a[@title='Dynamic Email']");
	public static By StaticPrintNamel1 = By.xpath("//a[@title='Static Print']");
	public static By StaticInventoryNamel1 = By.xpath("//a[@title='Static Inventory']");
	public static By BroadcastNamel1 = By.xpath("//a[@title='Broadcast']");
	
	
	//for classic
	public static By DynamicPrintName = By.xpath("//a[contains(text(),'Dynamic Print')]");
	public static By DynamicEmailName = By.xpath("//a[contains(text(),'Dynamic Email')]");
	public static By StaticPrintName = By.xpath("//a[contains(text(),'Static Print')]");
	public static By StaticInventoryName = By.xpath("//a[contains(text(),'Static Inventory')]");
	public static By BroadcastName = By.xpath("//a[contains(text(),'Broadcast')]");
	public static By Landingpageyes = By.id("btnnavigatelandingpageyes");
	public static By PurlName = By.linkText("PURL");
	public static By GurlName = By.linkText("GURL");
	public static By PGurlName = By.linkText("PGURL");


	//Layout2
	
	
	// Product Details link
	public static By ProductDetailslink = By.id("lnkProductQuickView");
	
	//public static By ProductDetailslink1 = By.xpath("(//a[@id='lnkProductQuickView'])[1]");
	public static By ProductDetailslink21 = By.xpath("(//a[@id='lnkProductQuickView'])[2]");
	//public static By ProductDetailslink3 = By.xpath("(//a[@id='lnkProductQuickView'])[3]");
	
	public static By ProductDetailslink1 = By.xpath("//a[@id='lnkProductQuickView']/img");
	public static By ProductDetailslink2 = By.xpath("//a[@id='lnkProductQuickView']/img");
	public static By ProductDetailslink3 = By.xpath("(//a[@id='lnkProductQuickView']/img)[2]");
	
	public static By ProductDiscout = By.id("lblProdDiscount");
	public static By ProductBasePrice = By.id("lblFromPrice");
	public static By ProuctBasePriceL2 =  By.id("lblFromPrice");
	//public static By Layout2ProductDisocut2 = By.xpath("(//span[@id='lblProdDiscount'])[2]");
	public static By Layout2ProductDisocut2 = By.id("lblProdDiscount");
	//public static By Layout2ProductDisocut3 = By.xpath("(//span[@id='lblProdDiscount'])[3]");
	public static By Layout2ProductDisocut3 = By.id("lblProdDiscount");
	public static By TotalPopupData = By.cssSelector("#divProductDetails > div.body_container");
	public static By ProductNamePrdDetails = By.id("lblProdName");
	public static By ProductNamePrdDetails2 = By.id("lblProdName");
    public static By ProductItemPrice = By.id("lblUnitPrice");
    public static By ProductItemPriceExternalPrice1 = By.xpath("//div[@id='divProductInfo']/table/tbody/tr[2]/td[3]");
    public static By ProductItemPriceExternalPrice2 = By.xpath("//div[@id='divProductInfo']/table/tbody/tr[3]/td[3]");
    public static By ProductItemPriceExternalPrice3 = By.xpath("//div[@id='divProductInfo']/table/tbody/tr[4]/td[3]");
    public static By ProductItemPriceExternalPrice4 = By.xpath("//div[@id='divProductInfo']/table/tbody/tr[5]/td[3]");
    public static By ProductItemPriceExternalPrice5 = By.xpath("//div[@id='divProductInfo']/table/tbody/tr[6]/td[3]");
    public static By ProductItemPriceExternalPrice6 = By.xpath("//div[@id='divProductInfo']/table/tbody/tr[7]/td[3]");
    
    
   // public static By Layout2ProductItemPrice2 = By.xpath("(//div[@id='divProductInfo']/table/tbody/tr[2]/td[3])[2]");
    public static By Layout2ProductItemPrice2 = By.id("lblUnitPrice");
    //public static By Layout2ProductItemPrice3 = By.xpath("(//div[@id='divProductInfo']/table/tbody/tr[2]/td[3])[3]");
    public static By Layout2ProductItemPrice3 = By.id("lblUnitPrice");
    
    
	public static By ProductClose = By.xpath("//div[@id='divQuickView']/div[3]/a");
	
	//public static By ProductCloseL1 = By.xpath("//a[@id='closeQuickView']");
	public static By ProductCloseL1 = By.id("closeQuickView");
	public static By ProductCloseL21 = By.xpath("(//a[contains(text(),'Close')])[6]");
	public static By ProductCloseL22 = By.xpath("(//a[contains(text(),'Close')])[9]");
	//public static By ProductCloseL23 = By.xpath("(//a[@id='closeQuickView'])[3]");
	public static By ProductCloseL23 = By.xpath("//li[@id='lnkCloseQuickView']/a");
	
	//public static By OrderName = By.id("txtOrderName");
	public static By OrderName = By.id("txtOrderName");
	public static By OrderDesc = By.id("txtOrderDescription");
	public static By BroadCastSubjectline = By.id("txtSubjectLine");
	public static By PersonalizeFromName = By.id("txtFromName");
	public static By PersonalizeFromEmail = By.id("txtFromEmail");
	
	public static By Next1 = By.id("btnNext");
	
	
	// View pricing link
	
	public static By Viewpricinglink = By.id("lnkViewPricing");
	
	
	//public static By ViewPricingItemPrice =By.xpath("//div[@id='divProductPriceInfo']/div/div/table/tbody/tr[2]/td[3]");
	public static By ViewPricingItemPriceEP2 = By.xpath("//table[@*='GrayGrid']/tbody/tr[2]/td[3]");
	public static By ViewPricingItemPriceEP3 = By.xpath("//table[@*='GrayGrid']/tbody/tr[3]/td[3]");
	public static By ViewPricingItemPriceEP4 = By.xpath("//table[@*='GrayGrid']/tbody/tr[4]/td[3]");
	public static By ViewPricingItemPriceEP5 = By.xpath("//table[@*='GrayGrid']/tbody/tr[5]/td[3]");
	public static By ViewPricingItemPriceEP6 = By.xpath("//table[@*='GrayGrid']/tbody/tr[6]/td[3]");
	
	public static By ViewPricingItemPrice =By.xpath("//table[@*='GrayGrid']/tbody/tr/td[3]");
	
	
	public static By ViewPricingClose = By.id("btnPriceClose");
	
	
	// Ship to my address
	public static By ShipToMyAddress = By.id("DeliveryMode_SHIP_TO_MY_ADDRESS");
	public static By ShipToMyAddressDownload = By.id("rdbShipToItemProcessType_2");
	public static By ShipToMyAddressDownLoadPrint = By.id("rdbShipToItemProcessType_3");
	
	public static By Layout2Download = By.name("rdbItemProcessType");
	
	public static By Layout1and2shippingwithdownload = By.id("chkDownload");
	public static By Layout2ShippingRefresh = By.xpath("//span[@id='divShipMethods']/span/span/span[2]/span");
	public static By Layout2ShippingSelect = By.xpath("//ul[@id='ddlShipping_listbox']/li[2]");
	
	
	public static By Quantity = By.id("txtQunatity");
	//public static By ShippingRefresh = By.xpath("//span[@id='divShipMethods']/span/span/span");
	public static By ShippingRefresh = By.xpath("//*[@aria-owns='ddlShipping_listbox']/span/span");
	public static By PhoneTextBox = By.xpath("//input[starts-with(@id, 'SHIP_CF_PHONE-')]");
	public static By ShippingMethodsLink = By.id("lnkShippingMethods");
	public static By MultiShippingMethodsLink = By.id("lnkMultiShipMethods");
	public static By ShippingSelect = By.xpath("//*[@id='ddlShipping_listbox']/li[2]");
	public static By SelectedShippingValue = By.xpath("//*[@aria-owns='ddlShipping_listbox']/span/span");
	public static By OrderBaseSelectedShippingValue = By.xpath("//*[@id='shippingFieldSet']/div/span/span/span");
	public static By OrderBaseSelectedShippingValueL1 = By.xpath("//span[@aria-owns='ddlShippingMethods_listbox']/span/span");
	//for ups and fedex services shipping methods
	public static By Upsshippingmethod = By.xpath("//*[@id='ddlShipping_listbox']/li[5]");
	public static By UspsShippingmethod = By.xpath("//*[@id='ddlShipping_listbox']/li[5]");
	
	public static By ShipToMultipleLocationsC = By.xpath("//input[@id='DeliveryMode_SHIP_TO_MULTIPLE_LOCATIONS']");
	public static By ShipToMultipleLocations = By.linkText("Ship to Multiple Locations");
	public static By AddNewShippingAddress = By.xpath("//a[@id='lnkAddNewShipping']");
	public static By ShipMultipleLocationsAddress1 = By.xpath("//input[starts-with(@id,'SHIP_ADDRESS1-')]");
	public static By ShipMultipleLocationsCity = By.xpath("//input[starts-with(@id,'SHIP_CITY-')]");
	public static By ShipMultipleLocationsZip = By.xpath("//input[starts-with(@id,'SHIP_ZIP-')]");
	public static By ShipMultiPleLocationsCountryDDL = By.xpath("//span[@aria-owns='ddlShipCountry_listbox']/span/span");
	public static By ShipMultiPleLocationsStateDDL = By.xpath("//span[@aria-owns='ddlShipStates_listbox']/span/span");
	
	public static By ShipMultipleLocationsQuantity = By.xpath("//input[@id='txtQuantity']");
	//public static By ShippingSelectMultiplelocations = By.xpath("//select[@id='ddlShippingMethods']");
	//public static By ShippingMultiplelocationsClick = By.xpath("//div[@id='divMultiShipMethods']/span/span/span[2]/span");
	public static By ShippingMultiplelocationsClick = By.xpath("//*[@aria-owns='ddlShippingMethods_listbox']/span/span");
	
	public static By ShippingSelectMultiplelocations = By.xpath("//*[@id='ddlShippingMethods_listbox']/li[2]");
	public static By SelectedMultipleShippingValue = By.xpath("//*[@aria-owns='ddlShippingMethods_listbox']/span/span");
	//for ups and fedex type shipping methods
	//for added shipping adress
	public static By addedupsShippingSelectMultiplelocations = By.xpath("//*[@id='ddlShippingMethods_listbox']/li[5]");
	//for saved shipping adress
	public static By savedupsShippingSelectMultiplelocations = By.xpath("//*[@id='ddlSavedshippingmethonds_0_listbox']/li[4]");
	
	public static By ShippingMultiplelocationsSave = By.id("btnAddNewSave");
	
	  // Shipping UPS related elements
	public static By ShippingMethodsDropDownList = By.cssSelector("div.k-animation-container > #ddlShipping-list");
	
	
	//Select list details
	public static By Listsearchbox = By.id("btnListSearch");
	public static By Listcheckbox = By.xpath("//input[starts-with(@id, 'chkListId_')]");
	public static By DropshipmentWithList = By.id("DeliveryMode_SHIPMENT_WITH_LIST");
	
	public static By ContinueSelectList = By.id("lnkSelectListContinue");
	
	//public static By Addonprice = By.xpath("//div[2]/li/div");
	public static By Addonprice = By.xpath("//div[starts-with(@id, 'divCheckbox_')]");
	
	// View summary price information details
	
	//public static By userAddonCheckbox = By.xpath("//div[2]/li/div/input");
	public static By userAddonCheckbox = By.xpath("//input[starts-with(@id, 'chkAttributeId-')]");
	
	//public static By UserAddonCheckBoxDSWL = By.xpath("//div[1]/li/div/input");
	public static By UserAddonCheckBoxDSWL = By.xpath("//input[starts-with(@id, 'chkAttributeId-')]");
	public static By userAddonSelectList = By.xpath("//div[@id='divAccuItemDetails']/li[6]/select");
	
	public static By VSTQuantity = By.id("lblTotalQuantity");
	
	public static By Layout2VSQuantity = By.id("lblTotalQuantity");
	
	public static By VSItemPrice = By.id("lblItemPrice");
	public static By VSSubTotal = By.id("lblSubTotal");
	public static By VSDiscountApplied = By.id("lblDiscountAmount");
	public static By VSDiscountPercent = By.id("lblDiscountValue");
	public static By Layout2VSDiscountPercent = By.id("lblDiscountValue");
	public static By Layout2VSDiscountPercent1 = By.id("lblDiscountValue");
	
	public static By VSAddonos = By.id("spnAddonamount");
	public static By VSPostagePrice = By.id("spnPostagePrice");
	public static By VSShippingPrice = By.id("lblShippingPrice");
	public static By VSDownloadPrice = By.xpath("//small[starts-with(@id, 'lblDownloadPrice')]");
	public static By VSLayout2DownloadPrice = By.xpath("(//label[@id='lblDownloadPrice'])[2]");
	public static By VSLayout1DownloadPrice = By.xpath("//label[starts-with(@id, 'lblDownloadPrice')]");
	public static By VSTotalPrice = By.id("spntotalamount");
	
	public static By ViewSummaryData = By.xpath("//label[@id='lblDiscountValue']/preceding-sibling::span");
	
	// Shopping cart page details
	
	//public static By SCartQuantity = By.id("AddtoShoppingCartList.1.2");
	//public static By SCartItemPrice = By.id("AddtoShoppingCartList.1.4"); 
	//public static By SCartAddonPrice =  By.id("AddtoShoppingCartList.1.5");
	//public static By SCartPostagePrice =  By.id("AddtoShoppingCartList.1.6");
	//public static By SCartDiscount = By.id("AddtoShoppingCartList.1.7");
	//public static By SCartAmount = By.id("AddtoShoppingCartList.1.8");
	
	public static By SCartQuantity = By.xpath("//table/tbody/tr/td[4]");
	public static By SCartItemPrice = By.xpath("//table/tbody/tr/td[6]"); 
	public static By SCartAddonPrice =  By.xpath("//table/tbody/tr/td[7]"); 
	public static By SCartAddonPrice1 =  By.xpath("//td[7]/div");
	public static By SCartPostagePrice =  By.xpath("//table/tbody/tr/td[8]"); 
	public static By SCartPostagePrice1 =  By.xpath("//td[8]/div");
	public static By SCartDiscount = By.xpath("//table/tbody/tr/td[9]");
	public static By SCartAmount = By.xpath("//table/tbody/tr/td[10]");
	public static By SCartTotal = By.id("lblDisplayTotalAmount");
	public static By SCartItemPrice2 = By.xpath("//td[6]/div");
	public static By SCartLandingPrice2 =  By.xpath("//td[6]/div[2]");
	public static By SCartItemPrice1 = By.xpath("//td[5]/span"); 
	public static By SCartLandingPrice1 =  By.xpath("//td[5]/span[2]");
	public static By SCartDiscount1 = By.xpath("//td[8]/span");
	public static By SCartDiscount2 = By.xpath("//td[9]/div");
	public static By SCartLandingDiscount1 = By.xpath("//td[8]/span[2]");
	public static By SCartLandingDiscount2 = By.xpath("//td[9]/div[2]");
	public static By SCartLandingAmount1 = By.xpath("//td[9]/span[2]");
	public static By SCartLandingAmount2= By.xpath("//td[9]/span[2]");
	public static By SCartAmount2 = By.xpath("//td[10]/div");
	public static By SCartAmount1 = By.xpath("//td[9]/span"); 
	public static By SCartAmount11 = By.xpath("//td[10]/div");
	public static By SCartLandingAmount11 = By.xpath("//td[10]/div[2]");
	public static By SCartTotal1= By.id("lblDisplayTotalAmount");
	public static By SCartDownLoadPostagePrice = By.xpath("//table/tbody/tr/td[7]");
	public static By SCartDownLoadDiscount = By.xpath("//table/tbody/tr/td[8]");
	public static By SCartDownLoadAmount = By.xpath("//table/tbody/tr/td[9]");
	
	public static By ScartItemPriceBroadcast = By.xpath("//table/tbody/tr/td[5]");
	public static By ScartAddonPriceBroadcast = By.xpath("//td[6]");
	
	// Enable promotions or Discounts off Condition
	public static By SCartDownLoadAmountOFF = By.xpath("//table/tbody/tr/td[8]");
	public static By SCartAmountOFF = By.xpath("//table/tbody/tr/td[9]");
	public static By SCartDownLoadAmountOFF1 = By.xpath("//td[8]/div");
	public static By SCartAmountOFF1 = By.xpath("//td[9]/div");
	public static By SCartLandingDownLoadAmountOFF1 = By.xpath("//td[8]/div[2]");
	public static By SCartLandingAmountOFF1 = By.xpath("//td[9]/div[2]");
	//public static By AddToCart = By.id("lnkAddtoCart");
	public static By AddToCart = By.id("lnkAddtoCart");
	public static By Checkout = By.id("btnCheckout_1");
	public static By VSPageProof = By.id("frmProof");
	
	public static By ShoppingCartLinkC = By.id("lnkShoppingcartList");
	public static By ContinueShoppingLinkC = By.xpath("//a[starts-with(@id, 'lnkContinueShopping')]");
	public static By ErrorMsgInShoppingCart = By.id("lblCartErrorMessage");
	public static By OrgunitName = By.id("lnkHomeref");
	
	public static By EmptyCartLinkC = By.id("lnkEmptyCart_2");
	public static By EmptyCartConfirmOKButton = By.id("btnShoppingcartItemDeleteOk");
	// Order check out page details
	
	//public static By OrderCheckoutGrid = By.id("tblOrderCheckOut");
	//public static By OrderCheckOutQuantity = By.id("tblOrderCheckOut.1.2");
	
	public static By OrderCheckoutGridDwonArrow = By.id("imgupdown");
	public static By OrderCheckoutGridQuantity = By.xpath("//table/tbody/tr/td[3]");
	public static By OrderCheckoutGridItemPrice = By.xpath("//table/tbody/tr/td[5]");
	public static By OrderCheckoutGridDiscount = By.xpath("//table/tbody/tr/td[6]");
	public static By OrderCheckoutGridPostage = By.xpath("//table/tbody/tr/td[8]");
	public static By OrderCheckoutGridAmount = By.xpath("//table/tbody/tr/td[9]");
	public static By OrderCheckOutGridDownloadItemPrice = By.xpath("//table/tbody/tr/td[4]");
	public static By OrderCheckOutGridDownloadDiscount = By.xpath("//table/tbody/tr/td[6]");	
	public static By OrderCheckOutGridShippingorPostageBroadCast = By.xpath("//table/tbody/tr/td[7]");
	public static By OrderCheckOutGridDownloadAmount = By.xpath("//table/tbody/tr/td[8]");
	public static By OrderCheckOutGridDownloadAmountS = By.xpath("//table/tbody/tr/td[8]");

	//landing page////////////
	public static By OrderCheckoutGridItemPrice1 = By.xpath("//td[5]/div");
	public static By OrderCheckoutGridDiscount1 = By.xpath("//td[7]/div");
	public static By OrderCheckoutGridAmount1 = By.xpath("//td[9]/div");
	public static By OrderCheckOutGridOrderBaseAmount1 = By.xpath("//td[7]/div");
	public static By OrderCheckOutGridDownloadItemPrice1 = By.xpath("//td[4]/div");
	public static By OrderCheckOutGridDownloadDiscount1 = By.xpath("//td[6]/div");	
	public static By OrderCheckOutGridDownloadAmount1 = By.xpath("//td[8]/div");
	public static By OrderCheckoutGridLandingPageItemPrice1 = By.xpath("//td[5]/div[2]");
	public static By OrderCheckoutGridLandingPageDiscount1 = By.xpath("//td[7]/div[2]");
	public static By OrderCheckoutGridLandingPageAmount1 = By.xpath("//td[9]/div[2]");
	public static By OrderCheckOutGridDownloadLandingPageItemPrice1 = By.xpath("//td[4]/div[2]");
	public static By OrderCheckOutGridLandingPageDownloadDiscount1 = By.xpath("//td[6]/div[2]");	
	public static By OrderCheckOutGridLandingPageDownloadAmount1 = By.xpath("//td[8]/div[2]");
	public static By OrderCheckOutGridOrderBaseLandingPageAmount1 = By.xpath("//td[7]/div[2]");
//Order elements
	public static By OrderCheckOutGridDownloadItemPriceo = By.xpath("//table/tbody/tr/td[4]");
	public static By OrderCheckoutGridItemPriceo = By.xpath("//table/tbody/tr/td[4]");
	public static By OrderCheckOutGridDownloadDiscounto = By.xpath("//table/tbody/tr/td[5]");
	public static By OrderCheckOutGridDownloadDiscounto1 = By.xpath("//table/tbody/tr/td[6]");

	public static By OrderCheckoutGridDiscounto = By.xpath("//table/tbody/tr/td[6]");
	public static By OrderCheckOutGridShippingorPostageBroadCasto = By.xpath("//table/tbody/tr/td[7]");
	public static By OrderCheckoutGridPostageo = By.xpath("//table/tbody/tr/td[7]");
	//public static By OrderCheckOutGridOrderBaseAmounto1 = By.xpath("//table/tbody/tr/td[7]");
	public static By OrderCheckOutGridOrderBaseAmounto = By.xpath("//table/tbody/tr/td[6]");
	public static By OrderCheckOutGridDownloadAmounto = By.xpath("//table/tbody/tr/td[8]");
	public static By OrderCheckoutGridAmounto = By.xpath("//table/tbody/tr/td[8]");
	public static By OrderCheckoutGridQuantitys = By.xpath("//tr[2]/td[2]");
	
	//checkout page
	public static By Savedshiptomyaddress = By.xpath("//a[@href='#SavedShipToMyAddress']");	
	public static By MultipleShipping= By.xpath("//a[@id='a_MultipleShipping']");
	public static By AddNewSpiltShipAddress= By.xpath("//a[@href='#SavedShipToMyAddress']");
	public static By rdbtn_ShippingContact= By.xpath("//input[@name='rdbtn_ShippingContact'][1]");

	

	public static By SameAsBillAddStatus = By.id("chkSameAsBilling");
	public static By BillingAddressValue = By.id("ulBillingAddress");
	public static By ShippingAddressValue = By.id("ulShippingAddress");
	public static By ShippingAddressSelectLink = By.id("lnkSelectShippingAddress");
	
	public static By SplitshipAddress1 = By.xpath("//input[@id='SplitSHIPINFO_ADDRESS1']");
	public static By SplitShipCity = By.xpath("//input[@id='SplitSHIPINFO_CITY']");
	public static By SplitShipZip = By.xpath("//input[@id='SplitSHIPINFO_ZIP']");
	public static By SplitShipCountryDDL = By.xpath(".//*[@id='Contact_4']/div[2]/span/span/span[1]");
	public static By SplitShipStateDDL = By.xpath(".//*[@id='Contact_3']/div[2]/span/span/span[1]");
	public static By SplitShipaddresssave = By.xpath("//input[@name='chkSplitShipToMyaddress']");
	public static By Saveaddress=By.xpath("//button[@name='btnAddNewSave']");
	public static By splitquantity=By.xpath("//input[@placeholder='Quantity']");
	public static By shippingdropdown=By.xpath("//*[@id='divSplitShipEdit']/div[1]/div[1]/span/span/span[1]");
	public static By btnSelectedAddresses=By.xpath("//button[@id='btnSelectedAddresses']");
	public static By SelectedAddresses=By.id("btnGlobalShipAddSave");

//	driver.findElement(By.xpath("//input[@placeholder='Quantity']"));
	//public static By ShippingContactsPopupUseLink = By.linkText("Use");
	public static By ShippingContactsPopupUseLink = By.name("rdbtn_ShippingContact");
	public static By ShippingContactsPopUpSaveButton = By.id("btnGlobalShipAddSave");
	
	public static By OSBillingAddressValue = By.id("divShowLessBillingDetails");
	public static By OSShippingAddressValue =  By.id("divShowLessShipInfo");
	
	public static By PopUpBillingAddress = By.xpath("//tr[16]/td");
	public static By PopUpShippingAddress = By.xpath("//tr[17]/td");
	
	public static By UserViewOrdersShippingDetailsLink = By.xpath("//a[contains(text(),'Shipping Details')]");
	public static By UserViewOrdersShippingDetailsPopUpOK = By.id("btnOrderShippingOk");
	public static By UserViewOrdersBillingAddress = By.id("orderDetailsSection");
	public static By UserViewOrdersShippinAddress = By.xpath("//div[@id='divOrderShipping']/div[2]");
	public static By UserViewOrdersShippinAddressL1 = By.xpath("//div[@id='divOrderShipping']/div/div/div/div[2] ");
	
	public static By BillingEditLink = By.id("lnkEditBilling");
	public static By BillingPopAdd1TextBox = By.xpath("//div[@id='Contact_0']/input");
	public static By BillingPopAdd2TextBox = By.xpath("//div[@id='Contact_1']/input");
	public static By BillingPopAdd1TextBoxL1 = By.xpath("//div[@id='Contact_0']/div[2]/input");
	public static By BillingPopAdd2TextBoxL1 = By.xpath("//div[@id='Contact_1']/div[2]/input");
	
	public static By BillingPopSaveButton = By.id("lnkBillingAddressSave");
	public static By BillingPopCancelButton = By.id("lnkBillingAddressClose");
	public static By ShippingEditLink = By.id("lnkEditShipping");
	public static By ShippingPopAdd1TextBox = By.xpath("//div[@id='Contact_0']/input");
	public static By ShippingPopAdd2TextBox = By.xpath("//div[@id='Contact_1']/input");
	public static By ShippingPopZipTextBox = By.xpath("//div[@id='Contact_5']/input");
	public static By ShippingPopStateDropdowL1 = By.xpath("//div[@id='Contact_3']/div[2]/span/span/span");
	public static By ShippingPopStateDropdow = By.xpath("//div[@id='Contact_3']/span/span/span");
//div[@id='Contact_3']/span/span/span
	//Split Ship 
		public static By ShipToMyAddress_splitship = By.id("a_SingleShipping");
		public static By ShippingRefresh_splitship = By.xpath("//div[@id='divShippingMethods']/span/span/span");
		public static By ShippingSelect_splitship = By.xpath("//ul[@id='ddlShippingMethods_listbox']/li[2]");
		public static By SelectedShippingValue_splitship = By.xpath("//div[@id='divShippingMethods']/span/span/span");

	public static By ShippingPopZipTextBoxL1 = By.xpath("//div[@id='Contact_5']/div[2]/input");
	public static By ShippingPopAdd1TextBoxL1 = By.xpath("//div[@id='Contact_0']/div[2]/input");
	public static By ShippingPopAdd2TextBoxL1 = By.xpath("//div[@id='Contact_1']/div[2]/input");
	
	public static By ShippingPopSaveButton = By.id("lnkShippingAddressSave");
	public static By ShippingPopCancelButton = By.id("lnkShippingAddressClose");

	// for order base third party shippng method selection
	
	//public static By ordershippingdropdown = By.xpath("//*[@id='shippingFieldSet']/span/span/span[2]/span");
	public static By ordershippingdropdown = By.xpath("//span[@aria-owns='ddlShippingMethods_listbox']/span");
	public static By ordershippingmethodselection = By.xpath("//*[@id='ddlShippingMethods_listbox']/li[3]");
	
	//for oredr shipping/handiling fee
	
	public static By OCHandilingfee = By.id("lblHandlingPrice");
	public static By OPhandilingfee = By.id("lblHandlingPrice");
	public static By OPhandilingfeeL1 = By.xpath("(//b[@id='lblTotalShippingAmount'])[2]");
	
	// Enable promotions or Discount OFF conditions
	public static By OCData = By.xpath("//section[@id='divOrderCheckout']/section/div/aside[2]/div[2]/div");
	
	public static By OrderCheckOutGridOrderBaseAmount = By.xpath("//table/tbody/tr/td[7]");
	public static By OrderCheckOutGridOrderBaseAmounts = By.xpath("//table/tbody/tr/td[7]");

		
	public static By PromotionCodeTextBox = By.id("PromotionCode");
	public static By PromotionApplyButton = By.id("lnkApplyPromotion");
	
	public static By OCSubTotal = By.id("lblSubTotalAmt");
	public static By OCPromotionDiscount = By.id("lblPromotionDiscount");
	public static By OCShippingPrice = By.id("lblShippingPrice");
	public static By OCTaxPercentage = By.id("lblTaxPerc");
	public static By OCTaxAmount = By.id("lblTaxAmt");
	public static By OCTotal = By.id("lblTotalAmt");
	public static By OCTotalShippingPrice = By.id("lblShippingPrice");
	public static By OCTotalDiscountPrice = By.id("lblDiscount");
	public static By OCTotalAddonPrice = By.id("lblAccuitemAddonAmount");
	public static By OCTotalPostagePrice = By.id("lblAppliedTotalPostagePrice");
	public static By OCBalanceAmount = By.id("lblTotalPostageAmt");
	
	//public static By PaymentType = By.id("ddlPaymentType");
	//public static By PaymentTypeDownArrow = By.xpath("//div[@id='divPayment']/span/span/span[2]/span");
	public static By OCBalanceAmountTextBox = By.cssSelector("#txtBalanceAmount");
	//public static By OCBalanceAmountTextBox = By.id("lnkMultipaymentlinksAdd");
	public static By OCRemainingBalance = By.id("lblRemaingBalance");
	
	//public static By PaymentTypeDownArrow = By.cssSelector("span.k-icon.k-i-arrow-s");
	//public static By PaymentTypeDownArrow = By.xpath("//div[@id='divPayment']/span/span/span");
	public static By PaymentTypeDownArrow = By.xpath("//*[@aria-owns='ddlPaymentType_listbox']/span/span");
	public static By PaymentTypeDownArrowOrder = By.xpath("//*[@aria-owns='ddlPaymentType_listbox']/span/span");
	public static By PaymentTypeDownArrowOrderBasis = By.xpath("//div[@id='divPayment']/span/span/span[2]/span");
	public static By Layout2PaymentTypeDownArrow = By.xpath("//div[@id='divPayment']/span/span/span/span[2]/span");
	public static By PaymentTypelength = By.id("ddlPaymentType-list");
	public static By PaymentTypeLength1 = By.xpath("//div[@id='ddlPaymentType-list']/ul[@id='ddlPaymentType_listbox']/li[2]");
	public static By PaymentTypelength2 = By.xpath("ddlPaymentType-list");
	public static By PaymentTypelength3 = By.xpath("ddlPaymentType-list");
	public static By PaymentTypeDropDownData2 = By.xpath("(//ul[@id='ddlPaymentType_listbox'])[2]"); 
	
	//public static By AppliedPayment = By.cssSelector("td.paymenttbl.paymenttb2 > span");
	public static By AppliedPayment = By.xpath("//table[@id='GrayGrid']/tbody/tr[2]/td[4]");
	
	public static By AppliedPayment2 = By.xpath("//table[@id='GrayGrid']/tbody/tr[3]/td[4]");
	public static By AppliedPayment3 = By.xpath("//table[@id='GrayGrid']/tbody/tr[4]/td[4]");
	public static By AppliedPayment4 = By.xpath("//table[@id='GrayGrid']/tbody/tr[5]/td[4]");
	public static By AppliedPayment5 = By.xpath("//table[@id='GrayGrid']/tbody/tr[6]/td[4]");
	public static By RemainingBalance = By.id("lblRemaingBalance");
	public static By MultipaymentApplyButton = By.id("lnkMultipaymentlinksAdd");
	
	public static By CreditCardRadiOButton = By.xpath("//input[starts-with(@id, 'rdo_SaveCard_')]");
	
	//public static By PaymentType = By.xpath("//div[@id='divPayment']/span/span/span");
	public static By PONumber = By.id("BILLINGPONumber");
	public static By CreditCardDown = By.xpath("//*[@aria-owns='ddlCreditCardTypes_listbox']/span/span[2]");
	public static By CreditCardDownC = By.xpath("//div[@id='divCardPayment']/section/span/span/span");
	public static By AuthAndChrgLaterRadioButton =  By.xpath("//input[starts-with(@id,'rdo_SaveCard_')]");
	
	public static By CreditCardTypeList = By.xpath("//*[@id='divCardPayment']/div[2]/div[1]/span[1]/span/span[1]");
	public static By CreditCardNumber = By.id("CCCardNumber");
	public static By CreditCardNameOnCard = By.id("CCFirstName");
	public static By CreditCardLastNameOnCard = By.id("CCLastName");

	public static By CreditCardCVVNumber = By.id("CCCVV");
	public static By CreditCardLastName = By.xpath("(//*[@id='CCFirstName'])[1]");
	public static By AgreementCheck = By.id("chkAgreement");
	public static By AgreementCheck1 = By.xpath("//div[@id='divAppEditMessage']/div/input");
	public static By SubmitOrder = By.id("lnkSubmitOrder");
	
	// PayPal external check out attributes
	public static By PPEUserName = By.id("email");
	public static By PPEPassword = By.id("password");
	public static By PPELoginButton = By.id("btnLogin");
	public static By PPEContiueButton = By.id("confirmButtonTop");
	
	public static By ErrorMsg =  By.id("lblErrorMessage");
	
	//public static By CostCenterBox = By.cssSelector("section.ordercheckoutCostCenter.border_color1");
	public static By CostCenterBox = By.xpath("//span[@aria-owns='ddlCostCenter_listbox']/span/span");
	//public static By CostCenterBoxL1 = By.xpath("//span[@aria-owns='ddlCostCenter_listbox']/span/span");
	public static By CostCenterBoxL2 = By.xpath("//span[@aria-owns='ddlCostCenter_listbox']/span/span");
	
	public static By CostCenterDropDown = By.xpath("//*[@aria-owns='ddlCostCenter_listbox']/span");
	public static By CostCenterDropDownValue = By.xpath("//*[@id='ddlCostCenter_listbox']/li[2]");
	
	public static By CostCenterTag = By.xpath("//div[@class='costcenter']");
	
	
	// Order summary page details
	
	public static By OSQuantity = By.xpath("//table/tbody/tr/td[3]");
	public static By OSItemPrice = By.xpath("//table/tbody/tr/td[4]");
	public static By OSDiscount = By.xpath("//table/tbody/tr/td[5]");
	public static By OSPostage = By.xpath("//table/tbody/tr/td[8]");
	public static By OSAmount = By.xpath("//td[9]/div");
	public static By OSDownloadpostage = By.xpath("//table/tbody/tr/td[6]");
	public static By OSDownloadAmount = By.xpath("//table/tbody/tr/td[7]");
	public static By OSDownloadAmountS = By.xpath("//table/tbody/tr/td[8]");

	//Landing page  Order summary page detail
	public static By OSItemPrice1 = By.xpath("//td[4]/div");
	public static By OSDiscount1 = By.xpath("//td[5]/div");
	public static By OSAmount1 = By.xpath("//td[9]/div[2]");
	public static By OSDownloadAmount1 = By.xpath("//td[8]/div");
	//*[@id="tblOrderCheckOut"]/tbody[2]/tr/td[8]
	public static By OSLandigPageItemPrice1 = By.xpath("//td[4]/div[2]");
	public static By OSLandigPageDiscount1 = By.xpath("//td[5]/div[2]");
	public static By OSLandigPageAmount1 = By.xpath("//td[9]/div[2]");
	public static By OSLandigPageDownloadAmount1 = By.xpath("//td[8]/div[2]");
	public static By OSSubtotal = By.id("lblSubTotal");
	public static By OSPromotionDiscount = By.id("lblTotPromoDiscount");
	public static By OSShippingAmount = By.id("lblTotalShippingPrice");
	//public static By OSTaxpercentage = By.xpath("//li[3]/label/b");
	//public static By OSTaxpercentageWhenDiscountZero = By.xpath("//div[@id='main']/div/div/div[2]/div[4]/section[2]/ul/li[2]/label/b");
	public static By OSTaxpercentage = By.id("lblTaxPercent");
	public static By OSTaxpercentageWhenDiscountZero = By.id("lblTaxPercent");
	public static By OSTaxValue = By.id("lblTotalTaxAmount");
	public static By OSShippingValue = By.id("lblTotalShippingPrice");
	public static By OShandilingvalue = By.id("lblHandlingPrice");
	public static By OSHandlingVlaueL1 = By.id("lblHandlingAmmount");
	public static By OSTotal = By.id("lblTotalAmt");
	public static By OSAppliedTotalDiscount = By.id("lblDiscountvalue");
	public static By OSAppliedTotalAddonPrice = By.id("lblTotalAddonAmount");
	public static By OSAppliedTotalPostagePrice = By.id("lblTotalPostageAmt");
	
	
	public static By OSData1 = By.xpath("//section[@id='divOrderCheckout']/article[3]/div/section/div/section[2]/ul");
	//public static By OSData2 = By.xpath("//section[@id='divOrderCheckout']/article[3]/div/section/div/section/ul");
	public static By OSData2 = By.xpath("//span/b[@id='lblDiscountvalue']");
	//ordersummary order elements
	public static By OSAmounto = By.xpath("//table/tbody/tr/td[8]");
	public static By OSDownloadAmounto = By.xpath("//table/tbody/tr/td[8]");
	public static By OSDownloadpostageo = By.xpath("//table/tbody/tr/td[7]");
	public static By OSPostageo = By.xpath("//table/tbody/tr/td[7]");
	public static By OSQuantitys = By.xpath("//tbody[2]/tr/td[2]");
	public static By OSItemPrices = By.xpath("//td[3]/div");
	public static By OSDiscounts = By.xpath("//td[4]/div");

	// Print pop up page details
	
	public static By PrintLink = By.id("lnkPrint");
	public static By PrintConfirmationOK = By.id("popup_ok");
	
	//public static By PrintTotal1 = By.id("lblAmount");
	public static By PrintTotal1 = By.id("printOrderTotal");
	public static By PrintTotal12 = By.xpath("(//b[@id='lblAmount'])[2]");
	public static By PrintTotal13 = By.xpath("(//b[@id='lblAmount'])[3]");
	public static By PrintTotal14 = By.xpath("(//b[@id='lblAmount'])[4]");
	public static By PrintTotal15 = By.xpath("(//b[@id='lblAmount'])[5]");
	
	public static By PrintQuantity = By.xpath("//table/tbody[2]/tr/td[3]");
	public static By PrintItemPrice = By.xpath("//table/tbody[2]/tr/td[4]");
	public static By PrintDiscount = By.xpath("//table/tbody[2]/tr/td[5]");
	public static By PrintAmount = By.xpath("//table/tbody[2]/tr/td[11]");
	public static By PrintQuantityOB = By.xpath("//table/tbody[2]/tr/td[2]");
	public static By PrintItemPriceOB = By.xpath("//table/tbody[2]/tr/td[3]");
	public static By PrintDiscountOB = By.xpath("//table/tbody[2]/tr/td[4]");
	public static By PrintAmountOB = By.xpath("//table/tbody[2]/tr/td[10]");
	public static By PrintItemPriceOBS = By.xpath("//table/tbody[2]/tr/td[4]");
	public static By PrintAmountOBS = By.xpath("//table/tbody[2]/tr/td[11]");

	public static By PrintDiscountOBS = By.xpath("//table/tbody[2]/tr/td[5]");

	//Landing page	
	public static By PrintItemPrice1 = By.xpath("//td[4]/span");
	public static By PrintDiscount1 = By.xpath("//td[5]");
	public static By PrintAmount1= By.xpath("//td[11]/span");
	public static By PrintItemPriceOB1 = By.xpath("//td[3]/span");
	public static By PrintDiscountOB1 = By.xpath("//td[4]");
	public static By PrintAmountOB1 = By.xpath("//td[10]/span");
	//orderelements printpopup
	public static By PrintAmounto = By.xpath("//table/tbody[2]/tr/td[10]");
	public static By PrintAmountOBo = By.xpath("//table/tbody[2]/tr/td[9]");
	//Enable promotions or Discounts OFF condition
	public static By PrintAmountOFF = By.xpath("//table/tbody[2]/tr/td[10]");
	public static By PrintAmountOBOFF = By.xpath("//table/tbody[2]/tr/td[9]");
	public static By PrintAmountOBOFFS = By.xpath("//table/tbody[2]/tr/td[11]");

	public static By PrintAmountOFF1 = By.xpath("//td[10]/span");
	public static By PrintAmountOBOFF1 = By.xpath("//td[9]/span");
	public static By PrintSubtotal = By.id("lblOrderSubtotal");
	public static By PrintPrmotionDiscount = By.id("lblPromotionAmount");
	//public static By PrintShippingAmount = By.xpath("css=label > b");
	public static By PrintTaxvalue = By.id("lblTax");
	public static By PrintShippingvalue = By.id("lblTotalShippingAmount");
	public static By PrintTotal2 = By.id("printOrderTotal");
	public static By printAppliedTotalShippingPrice = By.id("lblTotalShippingAmount");
	public static By PrintAppliedTotalDiscount = By.id("lblOrderDiscountAmount");
	public static By PrintAppliedTotalAddonPrice = By.id("lblOrderAddOnAmount");
	public static By PrintAppliedTotalPostagePrice = By.id("lblOrderPostagePrice");
	
	public static By PrintData1 = By.xpath("//tr[12]/td/div/table/tbody/tr/td[3]");
	public static By PrintData2 = By.xpath("//tr[12]/td/div/table/tbody/tr/td/table");
	
	
	public static By logout = By.id("Logout");
    public static By Alogout = By.id("Logout");
    
    // User View orders verification
    public static By GetOrderNumber = By.id("lblOrderNumber");
    public static By UserBackToHome = By.id("lnkBackToHome");
    
    public static By UserBackToHomeLayout1 = By.xpath("//div[@id='lblHome']/label");
    public static By UserBackToHomeLayout2 = By.linkText("Home");
    
    public static By UserReportsLink = By.id("lnkNav_REPORTS");
    public static By UserViewOrderImageIcon = By.xpath("//a[@id='lnkVIEW_ORDERS']/img");
    public static By ApproverClassicPendingOrdersLink = By.id("pendingApprovals");
    public static By PSClassicPendingScheduledOrders = By.id("pendingscheduledorders");
    
    //public static By UserReportsLinkLayout1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[6]/a/span[1]");
    public static By UserReportsLinkLayout1 = By.xpath("//img[contains(@src,'Reports.png')]");
    public static By UserReportsLinkAPPLayout1 = By.xpath("//div[@id='divHeaderIcons']/ul/li[4]/a");
    //public static By ApproverLayout1PendingOrdersLink = By.xpath("//a[@id='pendingApprovals']/span/img");
    public static By ApproverLayout1PendingOrdersLink = By.linkText("Pending Approvals");
    public static By UserReportsLinkPSLayout1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[2]/a");
    public static By UserViewOrderImageIconLayout1 = By.xpath("//img[contains(@src,'vieworders.png')]");
    public static By appReportsLinkLayout1 = By.xpath("//div[@id='divHeaderIcons']/ul/li[4]/a");
    public static By appviewordericonl1 = By.xpath("//div[@id='divHeaderIcons']/ul/li[4]/ul/li/a/b");
    public static By Approve = By.partialLinkText("Approve");
    public static By ApproveSave = By.id("btnOrderAction");
    public static By ApproveSaveSucMessage = By.id("lblSucMessage");
    public static By psviewordericonl1 = By.xpath("//*[@id='divHeaderIcons']/ul/li[2]/ul/li[2]/a");
    
    public static By UserReportsLinkLayout2 = By.xpath("//*[@id='nav_REPORTS']/a");
    public static By ApproverLayout2PendingOrdersIcon = By.xpath("//*[@id='lnkHeaderPENDINGAPPROVALS']");//*[@id='nav_PENDINGAPPROVALS']/a");
    public static By PSLayout2PendingScheduleIcon = By.xpath("//*[@id='nav_PENDINGSCHEDULES']/a");
    public static By UserViewOrderImageIconLayout2 = By.xpath("//*[@id='lnkVIEW_ORDERS']/span/i");
    public static By PSLayout1PendingScheduleIcon = By.xpath("//a[@onclick='javascript:return linkClick(\"#/pendingscheduledorders\")']/span");
    
    public static By GridOrderDateHeader = By.xpath("(//a[contains(@href, 'javscript:')])[1]");
    public static By PSPendingOrdersCount = By.id("spnPendingScheduleCount");
    
  //a[@id='lnkAllOrders']
    
    public static By APPPendingOrdersCount = By.id("spnApprovalOrderCount");
    public static By OrderNumberIcon = By.id("lnkOrdersNumber");
    public static By OrderNumberTextbox = By.id("txtOrderNumber");
    public static By OrderSearchSubmit = By.id("lnkSearchSubmit");

    public static By VOGSubTotal = By.xpath("(//a[contains(@href, 'javscript:')])[2]");
    public static By VOGPOPUPPostage = By.id("lblItemPostagePrice");
    public static By VOGPOPUPShipping = By.id("lblItemShippingPrice");
    public static By VOGPOPUPSubTotal = By.id("lblItemSubTotal");
    public static By VOGPOPUPAddOnPrice = By.id("lblItemAddOnAmount");
    public static By VOGPOPUPDiscount = By.id("lblItemDiscountAmount");
    public static By VOGPOPUPTotalPrice = By.id("lblTotal");
    public static By VOGPOPUPProdQuantity = By.xpath("(//div[2]/div/div/div/div[2]/div/div/div/div[4])[1]/label");
    public static By VOGPOPUPProdItemPrice = By.id("lblItemPrice");
    public static By VOGPOPUPAddOnPrice1 = By.xpath("(//label[@id='lblOrderAddOnAmount'])[3]");
    public static By VOGPOPUPDiscountl = By.id("lblOrderDiscountAmount");   
    public static By VOGPOPUPAddOnPricel = By.id("lblOrderAddOnAmount");
    public static By VOGPOPUPProdQuantityl = By.cssSelector("#lblItemQuantity"); 
    public static By VOGPOPUPPostage1 = By.xpath("(//label[@id='lblShippingPrice'])[2]");
    public static By VOGPOPUPPostagel = By.id("lblOrderPostagePrice");   
    public static By VOGPOPUPShipping1 = By.id("lblShippingPrice");
    public static By VOGPOPUPSubTotal1 = By.id("lblOrderActualPrice");
    public static By VOGPOPUPPromotionDiscount = By.xpath("(//label[@id='lblOrderDiscountAmount'])[2]");
    public static By VOGPOPUPPromotionDiscountl = By.xpath("(//label[@id='lblOrderDiscountAmount'])[2]");   
    public static By VOGPOPUPTaxValue = By.id("lblOrderTax");
    public static By VOGPOPUPTotal = By.id("lblOrderTotal");
    public static By VOGPOPUPDiscount1 = By.id("lblOrderDiscountAmount");  
    public static By VOGPOPUPClose= By.id("btnCloseItemPriceDetails_Footer");
    public static By VOGPOPUPClosel= By.cssSelector("div.modal-footer > #btnCloseItemPriceDetails");
    public static By VOGPOPUPSubTotalLanding = By.id("lblLPPrice");
    public static By VOGPOPUPLandingProdItemPrice = By.id("lblLPItemPrice");
    
   // public static By ClickOnOrderNumberLink = By.id("");
    public static By VOoverallSubTotal = By.id("lblOrderActualPrice");
    public static By VOoverallAddOnPrice = By.id("lblOrderAddOnAmount");
    public static By VOoverallDiscount = By.id("lblOrderDiscountAmount");
    public static By VOoverallPromotionDiscount = By.id("lblPromotionAmount");
    public static By VOoverallShppingPrice = By.id("lblShippingPrice");
    public static By VOoverallPostage = By.id("lblOrderPostagePrice");
    public static By VOoverallTaxValue = By.id("lblTax");
    public static By VOoverallTotalPrice = By.id("lblTotal");
    public static By VOIndProdQuantity = By.id("lblQuantity");
    public static By VOIndProdItemPrice = By.id("lblItemPrice");
    public static By VOIndProdAddonPrice = By.id("lblAddOnAmount");
    public static By VOIndProdDiscount = By.id("lbldiscount");
    public static By VOIndProdlblPostagePrice = By.id("lblPostagePrice");
    public static By VOIndProdItemTotal = By.id("lblItemToatl");


   
   // landingpage
    public static By VOIndProdlandingItemPrice = By.xpath("(//label[@id='lblItemPrice'])[2]");
    public static By VOIndProdlandingDiscount = By.id("lbllpdiscount");
    

    public static By UserPrintOrder = By.linkText("Print Order");
 //   public static By jobticket = By.linkText("Job Ticket");

    public static By jobticket = By.xpath("//a[contains(.,'Job Ticket')]");

    public static By VOData1 = By.cssSelector("aside.total_orderBlock");
    public static By VOData2 = By.cssSelector("div.Reorder > aside");
   // reorder details
    public static By UserViewOrdersReorderselecteditem = By.id("lnkReOrderSelectedItems");
    public static By Reorderselecteditem = By.xpath("//span[@id='secImg']/label/input");
    public static By Reorderselecteditem1 = By.xpath("//section[@id='secImg']/span/input");

// Admin details
	
	// Login page
	 public static By Adminlink = By.id("lnkNav_Admin");
	 public static By AdminIcon = By.id("lnkAdministration");
	
	
	 
	 // Home page
	 public static By Priceicon = By.id("ImgPricing");
	
	 public static By Promotionsicon = By.id("ImgPromotions");

	 public static By TaxIcon = By.id("ImgTaxes");
	 

	 
	 
	 
	 
	 // Price code list page
	 
	 public static By searchbox = By.id("sg_search");
	 public static By Greentick = By.id("btn_go_sg_search");
	 public static By PriceDetaillink = By.linkText("Price Details");
	 public static By Edtilink = By.linkText("Edit");
	 public static By Unitprice = By.id("txtPDUnitPrice");
	 public static By UnitType = By.id("ddlPDUnitType");
	 public static By popupsave = By.id("btnSave");
	 
	 public static By PriceVerify = By.xpath("//tr[@id='1']/td[5]");
	 
	 //Promotion codes list
	 
	 public static By AddPromotion = By.id("btnAdd");
	
	 public static By PromotionDesc = By.id("txtDescription");

	 public static By PromotionStartDateTxtBox = By.id("txtStartDate");
		
	 public static By PromotionStartDate = By.linkText("10");
	 public static By PromotionActive = By.xpath("//div[@id='IsActive']/div[3]/input");
	 //public static By ProductListid = By.id("lstProducts");
	// public static By Dynamicproduct = By.linkText("Dynamic print");
	 public static By SecondProduct = By.xpath("//table[@id='TblList']/tbody/tr[2]/td/select");
	 public static By Addproduct = By.id("btnAdd");
	 public static By SavePromotion = By.id("btnSave");
	 
	 public static By PromotionSearch = By.id("sg_search");
	 public static By PromotionGreenTick = By.id("btn_go_sg_search");		 
	 public static By PromotionEditlink =  By.xpath("//a[contains(text(),'Edit')]");
	 public static By PromotionSelectedProducts = By.xpath("//select[@id='lstSelectedProducts']/option");
	 
	 
	 
	 // Promotion Coupon codes list
	 
	 public static By CouponRadioButton = By.id("rdbtnCoupon");
	 public static By CouponCode = By.name("txtCode");
	 public static By CouponUsageCount = By.id("ddlUsageCount");
	 
	 
	 
	 // Manage products
	 
	 public static By ManageProducts = By.id("ImgManagetemplete");
	 public static By ProductsSearch = By.id("sg_search");
	 public static By ProductsGreenTick = By.id("btn_go_sg_search");
	 public static By Pricelink = By.xpath("//a[contains(text(),'Price')]");
	 public static By AddonCheckbox = By.xpath("//td/div/div/div/input");
	 
	 public static By SavePriceDetails = By.id("btnSaveWizard");
	 public static By productOnline = By.id("lnkOnline");
	 
	 
	 // Manage Orgunit details
	 
	 public static By Miscellaneouslink = By.id("lnkMISCELLANEOUS");
	 
	 public static By Decimalvalues = By.id("ddlCurrencydecimals");
	
	 
	    // Add on price details
	 public static By Addonslink = By.xpath("//a[contains(text(),'Add-ons')]");
	 public static By CreateAddon = By.id("btnAdd");
	 public static By AddonName1 = By.id("label");
	 public static By AddonActive1 = By.xpath("//div[@id='Div6']/div[3]/input");
	 public static By AddonUiControlType = By.id("ddlUiControlType");
	 public static By AddonSave1 = By.id("btnSave");
	 public static By AddonSearch = By.id("sg_search");
	 public static By AddonGreeTick = By.id("btn_go_sg_search");
	 public static By AddonDetails = By.xpath("//a[contains(text(),'Details')]");
	 public static By CreateAddonDetails = By.id("btnAdd");
	 public static By AddonsName2 = By.id("label");
	 public static By AddonActive2 = By.xpath("//div[@id='Div6']/div[3]/input");
	 public static By AddonPriceCode = By.id("data_pricecodes");
	 public static By AddonPriceSave = By.id("btnSave");
	 public static By BackToAddon = By.id("btnCancel");
	 public static By CloseAddonPopup = By.id("btnClose");
	 
	    // Postage pricing
	 
	 public static By PostagePricinglink = By.xpath("//a[contains(text(),'Postage Pricing')]");
	 public static By AddPostagePrice = By.name("btnAdd");
	
	 public static By postagePrice = By.id("txtPrice");
	 public static By PostageSeleted = By.xpath("//div[@id='trIsSelected']/div[3]/input");
	 public static By PostageSave = By.id("btnSave");
	 public static By postagePopupClose = By.id("btnClose");
	 public static By PostageEditlink = By.xpath("//a[contains(text(),'Edit')]");
	 
	 
	 // Taxes details
	 
	 public static By TaxEditlink = By.xpath("//a[contains(text(),'Edit')]");
	 public static By Taxpercent = By.id("txtPercent");
	 public static By TaxSave = By.id("btnSave");
	 
	 
	 
	
	 public static By Links_Home = By.id("");
	 
	 
	// ******************* Landing pre conditions ****************
				public static By PricingNorecord = By.xpath("//*[@id='lblSearchGriderror']");
				public static By MoreActions = By.xpath("//*[@id='ddlMoreActions']/a");
				public static By uplodePrice = By.xpath("//*[@id='lnkUploadPricings']");
				public static By uplodeSave = By.xpath("//*[@id='UploadPricingsModalSave']");
				public static By Priceclose = By.xpath("//button[@id='UploadPricingsModalClose_btn']");
				public static By Response = By.id("lnkResponseChannel");
				public static By RLandingpage = By.id("chklandingPage");
				public static By AccuPURLoption = By.xpath("//div[2]/div/input");
				public static By Accpurlconnection = By.linkText("Connection Info");
				public static By APIstage = By.id("txt_STAGING_APIURL");
				public static By APIlive = By.id("txt_LIVE_APIURL");			
				public static By AuthTokenstage = By.id("txt_STAGING_AuthToken");
				public static By AuthTokenlive = By.id("txt_LIVE_AuthToken");
				public static By Connectionsettsave = By.id("btnPaymentProviderConSave");
				public static By Connectionsettclose = By.id("btnPaymentProviderConCancel");
				public static By ResponseSave = By.id("lnkResponseChannelSave");
				public static By LandingProduct = By.xpath("//div[@id='main']/div/div/div[4]/div/span/span/span");
				public static By NoLandingProduct = By.id("lbl_Text");
				public static By NewLandingProduct = By.id("btnNewProduct");
				public static By ProviderL = By.xpath("//span[@aria-owns='ddlVDPProvider_listbox']/span");
				public static By LItemProces = By.xpath("//div[@id='ItemProcessType']/span/span/span");
				public static By HURL = By.id("txtPageURL");
				public static By Scema = By.xpath("//*[@id='divFlupSchemaDocumentFile']/div/div/div/div/div");
				public static By DominName = By.id("txtDomainName");
				public static By ActiveDays = By.xpath("//div[7]/span/span/input");
				public static By Productcode = By.id("productCode");
				public static By Productname = By.id("txtProductName");
				public static By ProdcutDesciption = By.cssSelector("td.k-editable-area");
				public static By Lcategory1 = By.xpath("//*[@id='lnkCategory']");
				public static By Lcategoryoption = By.xpath("//li/div/span/input");
				public static By LcategorySave = By.id("lnkCategorySave");
				public static By Thumbnail = By.cssSelector("input.k-formatted-value.k-input");
				public static By Thumbnail1 = By.xpath("//div[@id='flupThumbnailImage']/div/div/div/div/div");
				public static By LMinQnt = By.xpath("//div[23]/span/span/input");
				public static By Lleaddays = By.xpath("//div[25]/span/span/input");
				public static By Lstartdate = By.cssSelector("span.k-icon.k-i-calendar");
				public static By Lexpiry = By.xpath("//div[@id='divSettings']/div/div/article/div[38]/span/span/span/span");
				public static By LPricing = By.xpath("//a[contains(text(),'Pricing')]"); 
				public static By Lpricingrd = By.id("rdoButtonProductPricing_1_0");
				public static By Lpricecode = By.cssSelector("span.k-input");
				public static By Lpricesave = By.id("btnSave");
				public static By Llisttype = By.id("lnkListTypes");
				public static By Ldisplayname = By.id("txtSelectListName");
				public static By Ldisplay = By.cssSelector("input.k-formatted-value.k-input");
				public static By LlistSave = By.id("saveProductListtypes");
				public static By LBackToProducts = By.id("backToViewProducts");
				public static By discount2 = By.id("lbl_Text");
				public static By Ldiscount = By.id("btnAddNewPromotion");
				public static By discountname = By.id("txt_name");
				public static By discountdis = By.id("txt_description");
				public static By discountval = By.cssSelector("span.k-icon.k-i-arrow-n");
				public static By discountstr = By.cssSelector("span.k-icon.k-i-calendar");
				public static By discountAct = By.id("rbactiveyes");
				public static By discountPro = By.xpath("//option[contains(.,'GURL')]");
				public static By discountPro1 = By.xpath("//option[contains(.,'PGURL')]");
				public static By discountPro2 = By.xpath("//option[contains(.,'PURL')]");
				public static By discountsel = By.id("btnAddAll");
	
	            //order elements check out
				public static By OrderelementsAddOns = By.id("chk-ADD_ONS-CHECKOUT");
				public static By OrderelementBillingAddress = By.id("chk-BILLING_ADDRESS-CHECKOUT");
				public static By OrderelementsInventoryLocation = By.id("chk-INVENTORY_LOCATION-CHECKOUT");
				public static By OrderelementsInventoryNumber = By.id("chk-INVENTORY_NUMBER-CHECKOUT");
				public static By OrderelementsPaymentDetail = By.id("chk-PAYMENT_DETAIL-CHECKOUT");
				public static By OrderelementsShippingAddress = By.id("chk-SHIPPING_ADDRESS-CHECKOUT");
				public static By OrderelementsShippingMethod = By.id("chk-SHIPPING_METHOD-CHECKOUT");
				public static By OrderelementsSpecialInstructions = By.id("chk-SPECIAL_INSTRUCTIONS-CHECKOUT");
				
				 //order elements job ticket
				public static By OrderelementsAddOnsj = By.id("chk-ADD_ONS-JOB_TICKET");
				public static By OrderelementBillingAddressj = By.id("chk-BILLING_ADDRESS-JOB_TICKET");
				public static By OrderelementsInventoryLocationj = By.id("chk-INVENTORY_LOCATION-JOB_TICKET");
				public static By OrderelementsInventoryNumberj = By.id("chk-INVENTORY_NUMBER-JOB_TICKET");
				public static By OrderelementsPaymentDetailj = By.id("chk-PAYMENT_DETAIL-JOB_TICKET");
				public static By OrderelementsShippingAddressj = By.id("chk-SHIPPING_ADDRESS-JOB_TICKET");
				public static By OrderelementsShippingMethodj = By.id("chk-SHIPPING_METHOD-JOB_TICKET");
				public static By OrderelementsSpecialInstructionsj = By.id("chk-SPECIAL_INSTRUCTIONS-CHECKOUT");
				
				//order elements Manifest File
				
				public static By OrderelementsAddOnsm = By.id("chk-ADD_ONS-MANIFEST_FILE");
				public static By OrderelementBillingAddressm = By.id("chk-BILLING_ADDRESS-MANIFEST_FILE");
				public static By OrderelementsInventoryLocationm = By.id("chk-INVENTORY_LOCATION-MANIFEST_FILE");
				public static By OrderelementsInventoryNumberm = By.id("chk-INVENTORY_NUMBER-MANIFEST_FILE");
				public static By OrderelementsPaymentDetailm = By.id("chk-PAYMENT_DETAIL-MANIFEST_FILE");
				public static By OrderelementsShippingAddressm = By.id("chk-SHIPPING_ADDRESS-MANIFEST_FILE");
				public static By OrderelementsShippingMethodm= By.id("chk-SHIPPING_METHOD-MANIFEST_FILE");
				public static By OrderelementsSpecialInstructionsm = By.id("chk-SPECIAL_INSTRUCTIONS-MANIFEST_FILE");
				//order elements Print Popup 

				public static By OrderelementsAddOnsp = By.id("chk-ADD_ONS-PRINT_POPUP");
				public static By OrderelementBillingAddressp = By.id("chk-BILLING_ADDRESS-PRINT_POPUP");
				public static By OrderelementsInventoryLocationp = By.id("chk-INVENTORY_LOCATION-PRINT_POPUP");
				public static By OrderelementsInventoryNumberp = By.id("chk-INVENTORY_NUMBER-PRINT_POPUP");
				public static By OrderelementsPaymentDetailp = By.id("chk-PAYMENT_DETAIL-PRINT_POPUP");
				public static By OrderelementsShippingAddressp = By.id("chk-SHIPPING_ADDRESS-PRINT_POPUP");
				public static By OrderelementsShippingMethodp= By.id("chk-SHIPPING_METHOD-PRINT_POPUP");
				public static By OrderelementsSpecialInstructionsp = By.id("chk-SPECIAL_INSTRUCTIONS-PRINT_POPUP");
				public static By OrderelementsSaveChangs = By.id("btnSaveChangs");
				public static By OrderelementsSuccessMsg = By.id("lblSuccessMsg");
				public static By OrderelementsBacktohome = By.id("lnkBackToHome");
				public static By jobticketQuantity = By.xpath("//table[4]/tbody/tr[2]/td[4]");
				public static By jobticketBillingAddress = By.xpath("//td/table/tbody/tr[2]/td");
				public static By jobticketShippingAddress = By.xpath("//table[4]/tbody/tr[2]/td");
				
}