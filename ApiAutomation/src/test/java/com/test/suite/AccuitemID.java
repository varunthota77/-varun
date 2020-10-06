package com.test.suite;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AccuitemID {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	
	String body="{\r\n" +
			"\"ResponseBody\": {\r\n" +
			"\"CartId\": 964779,\r\n" +
			"\"CartCount\": 2,\r\n" +
			"\"CartItems\": [\r\n" +
			"{\r\n" +
			"\"AccuItemId\": 12090927,\r\n" +
			"\"AccuItemName\": null,\r\n" +
			"\"DisplayDeliveryDate\": null,\r\n" +
			"\"LstItemAttributeDto\": null,\r\n" +
			"\"ObjectId\": 5872723,\r\n" +
			"\"ObjectName\": \"punchu123\",\r\n" +
			"\"ObjectStatus\": 1,\r\n" +
			"\"ObjectType\": 57,\r\n" +
			"\"OrderItemDescription\": null,\r\n" +
			"\"OrderItemName\": null,\r\n" +
			"\"Quantity\": 600,\r\n" +
			"\"Statusremarks\": null,\r\n" +
			"\"ThumbnailAssetUrl\": null,\r\n" +
			"\"DeliveryModeType\": 3,\r\n" +
			"\"ItemProcessType\": 1,\r\n" +
			"\"ShippingPrice\": 0.0,\r\n" +
			"\"PostagePrice\": 0.0,\r\n" +
			"\"UnitPrice\": 0.0,\r\n" +
			"\"AddOnAmount\": 0.0,\r\n" +
			"\"Amount\": 0.0,\r\n" +
			"\"Discount\": 0.0,\r\n" +
			"\"IsDiscountPercent\": false,\r\n" +
			"\"DownloadPrice\": 0.0,\r\n" +
			"\"ShipHandlingPrice\": 0.0,\r\n" +
			"\"samplePieceQuantity\": 0,\r\n" +
			"\"samplePieceUnitPrice\": 0.0,\r\n" +
			"\"samplesAmount\": 0.0,\r\n" +
			"\"lstItemOptions\": null,\r\n" +
			"\"LPUnitPrice\": 0.0,\r\n" +
			"\"LPPrice\": 0.0,\r\n" +
			"\"LPObjectName\": null,\r\n" +
			"\"objLPAccuItemDto\": null,\r\n" +
			"\"MerchadiseOptionsLabel\": null,\r\n" +
			"\"MerchandiseThumbUrl\": null,\r\n" +
			"\"SamplePieceType\": 0,\r\n" +
			"\"EmulateOnly\": false,\r\n" +
			"\"ProductType\": 8,\r\n" +
			"\"AdditionalOptionType\": 0,\r\n" +
			"\"SellingUOMLabel\": null,\r\n" +
			"\"hsAllProductObjectAttributeDto\": null,\r\n" +
			"\"lstProductAttributes\": null\r\n" +
			"},\r\n" +
			"{\r\n" +
			"\"AccuItemId\": 12749320,\r\n" +
			"\"AccuItemName\": null,\r\n" +
			"\"DisplayDeliveryDate\": null,\r\n" +
			"\"LstItemAttributeDto\": [\r\n" +
			"{\r\n" +
			"\"AttributeId\": 388181,\r\n" +
			"\"AttributeName\": \"59\",\r\n" +
			"\"AttributeLabel\": \"Addon1\",\r\n" +
			"\"AttributeSetType\": 8,\r\n" +
			"\"Selectedattributes\": [\r\n" +
			"{\r\n" +
			"\"SelectedAttributeId\": 388182,\r\n" +
			"\"SelectedAttributeLabel\": \"Addon\",\r\n" +
			"\"Amount\": 2.24\r\n" +
			"}\r\n" +
			"],\r\n" +
			"\"DisplayOrder\": null\r\n" +
			"},\r\n" +
			"{\r\n" +
			"\"AttributeId\": 5954978,\r\n" +
			"\"AttributeName\": \"59\",\r\n" +
			"\"AttributeLabel\": \"listbox addon123\",\r\n" +
			"\"AttributeSetType\": 8,\r\n" +
			"\"Selectedattributes\": [\r\n" +
			"{\r\n" +
			"\"SelectedAttributeId\": 5955978,\r\n" +
			"\"SelectedAttributeLabel\": \"add\",\r\n" +
			"\"Amount\": 0.45\r\n" +
			"}\r\n" +
			"],\r\n" +
			"\"DisplayOrder\": null\r\n" +
			"},\r\n" +
			"{\r\n" +
			"\"AttributeId\": 6013473,\r\n" +
			"\"AttributeName\": \"59\",\r\n" +
			"\"AttributeLabel\": \"checkbox addon\",\r\n" +
			"\"AttributeSetType\": 8,\r\n" +
			"\"Selectedattributes\": [\r\n" +
			"{\r\n" +
			"\"SelectedAttributeId\": 6013474,\r\n" +
			"\"SelectedAttributeLabel\": \"addon123\",\r\n" +
			"\"Amount\": 12.0\r\n" +
			"}\r\n" +
			"],\r\n" +
			"\"DisplayOrder\": null\r\n" +
			"},\r\n" +
			"{\r\n" +
			"\"AttributeId\": 6017033,\r\n" +
			"\"AttributeName\": \"59\",\r\n" +
			"\"AttributeLabel\": \"harshatext1\",\r\n" +
			"\"AttributeSetType\": 8,\r\n" +
			"\"Selectedattributes\": [\r\n" +
			"{\r\n" +
			"\"SelectedAttributeId\": 0,\r\n" +
			"\"SelectedAttributeLabel\": \"test\",\r\n" +
			"\"Amount\": 0.0\r\n" +
			"}\r\n" +
			"],\r\n" +
			"\"DisplayOrder\": null\r\n" +
			"},\r\n" +
			"{\r\n" +
			"\"AttributeId\": 6017036,\r\n" +
			"\"AttributeName\": \"59\",\r\n" +
			"\"AttributeLabel\": \"hlistb2\",\r\n" +
			"\"AttributeSetType\": 8,\r\n" +
			"\"Selectedattributes\": [\r\n" +
			"{\r\n" +
			"\"SelectedAttributeId\": 6017037,\r\n" +
			"\"SelectedAttributeLabel\": \"h\",\r\n" +
			"\"Amount\": 0.45\r\n" +
			"}\r\n" +
			"],\r\n" +
			"\"DisplayOrder\": null\r\n" +
			"}\r\n" +
			"],\r\n" +
			"\"ObjectId\": 3983334,\r\n" +
			"\"ObjectName\": \"Static Print 1235\",\r\n" +
			"\"ObjectStatus\": 1,\r\n" +
			"\"ObjectType\": 57,\r\n" +
			"\"OrderItemDescription\": null,\r\n" +
			"\"OrderItemName\": \"test\",\r\n" +
			"\"Quantity\": 1,\r\n" +
			"\"Statusremarks\": null,\r\n" +
			"\"ThumbnailAssetUrl\": \"http://192.168.1.121//ACv4.1.0.0_responsive_AssetRepo//NQA5AA==\\\\acpra\\\\MgAwADEANwAwADkAMgAxAA==\\\\MwA5ADgAMwAzADMAMwA=_Thumb.jpg\",\r\n" +
			"\"DeliveryModeType\": 3,\r\n" +
			"\"ItemProcessType\": 1,\r\n" +
			"\"ShippingPrice\": 0.0,\r\n" +
			"\"PostagePrice\": 0.0,\r\n" +
			"\"UnitPrice\": 25.68,\r\n" +
			"\"AddOnAmount\": 15.14,\r\n" +
			"\"Amount\": 30.55,\r\n" +
			"\"Discount\": 40.0,\r\n" +
			"\"IsDiscountPercent\": true,\r\n" +
			"\"DownloadPrice\": 0.0,\r\n" +
			"\"ShipHandlingPrice\": 0.0,\r\n" +
			"\"samplePieceQuantity\": 0,\r\n" +
			"\"samplePieceUnitPrice\": 0.0,\r\n" +
			"\"samplesAmount\": 0.0,\r\n" +
			"\"lstItemOptions\": null,\r\n" +
			"\"LPUnitPrice\": 0.0,\r\n" +
			"\"LPPrice\": 0.0,\r\n" +
			"\"LPObjectName\": null,\r\n" +
			"\"objLPAccuItemDto\": null,\r\n" +
			"\"MerchadiseOptionsLabel\": null,\r\n" +
			"\"MerchandiseThumbUrl\": null,\r\n" +
			"\"SamplePieceType\": 0,\r\n" +
			"\"EmulateOnly\": false,\r\n" +
			"\"ProductType\": 2,\r\n" +
			"\"AdditionalOptionType\": 0,\r\n" +
			"\"SellingUOMLabel\": null,\r\n" +
			"\"hsAllProductObjectAttributeDto\": null,\r\n" +
			"\"lstProductAttributes\": null\r\n" +
			"}\r\n" +
			"],\r\n" +
			"\"TotalAmount\": \"$30.55\"\r\n" +
			"},\r\n" +
			"\"ResponseCode\": 0,\r\n" +
			"\"ResponseMessage\": null,\r\n" +
			"\"ResponseUID\": null\r\n" +
			"}";
	

	// TODO Auto-generated method stub
System.out.println("Test started");
RestAssured.baseURI="http://192.168.1.121/acv4.1.0.0_responsive/";
Response res=given().
header("Authorization","Bearer HcTxTU6lyFfnYKiibLj6FDQenlqhlsUQK8rVk7kyo_fT7PorJHqe78MrRj9gUtXsYU8NFEwNcevjtwmDLGPyTjaRoiuoS2kt8teotP7ekQMW74tnmsZwldKNiDN7tpxyRKetoMoeBvWLItYPUkfyApNSqGlN-wJO1783mOvFJYXxWhPq09G2-aiWSp6qjS3dTzcQn2tllOJi3EWkRdCHupuKcSq5d52m0uiju-zczvDW-CWPXcPm1ZqJy-wdzddQm7jEwZlq04UyFoVbDI9EqDl8eXVsyjl6cRS1fcsJVFiJYA8CYvpTHovFRd3Lh-EbQBHoaw0EIMlyd8KdLgj7rK8mX1Md0SJt6uOqggFJrVDd_M3bHtI9EWykPPA3CWTGnCV5wzw_VgKSBse18NDRqY6eeNQ7yGYLNeJ4UldchQs").
header("Content-Type","application/json").

when().
get("api/users/224/shoppingcart?orgUnitId=59&GroupId=0&guestSessionPk=&ClientTimeZoneOffset=330&configuredTimeZone=Eastern%20Standard%20Time&isLayOut1=true&type=get&dataType=json&_=1583409713592&cache=false").
then().
//assertThat().statusCode(200).
and().extract().response();
String ResponseBody =res.asString();
System.out.println("the response is:"+ResponseBody);
JsonPath js=new JsonPath(ResponseBody);
String accuitem=js.getString("ResponseBody.CartItems[0].AccuItemId");
System.out.println("Accuitem id :"+accuitem);
System.out.println("Test Ended");

	
	}

}
