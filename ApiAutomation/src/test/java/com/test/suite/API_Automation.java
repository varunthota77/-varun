package com.test.suite;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class API_Automation {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.out.println("Test started");
for(int i=1;i<=10;i++)
{
	String firstname= "api"+i;
	String username= "user_api"+i;
	String password= "welcome";
	String body="{\"CustomCostCenter\":\"\",\"LoginPassword\":\""+password+"\","
	+ "\"UserId\":0,\"OrgUnitId\":59,\"ContactId\":null,\"UserGroupNam"
	+ "es\":\"\",\"UserDisplayAllCostCenters\":false,\"LstObjectCostCent"
	+ "ers\":[],\"DeletedCustomCostCenters\":[],\"UserCreationType\":3,\""
	+ "FirstName\":\""+firstname+"\",\"LastName\":\"apiuser4\",\"LoginName\":\""
	+ ""+username+"\",\"HintQuestion\":\"apiuser1\",\"HintAnswer\":\"apiuser1\","
	+ "\"StatusType\":\"ACTIVE\",\"SetLoginExpiry\":false,\"LoginExpiryDate\""
	+ ":null,\"ResetPassword\":false,\"ProfileUpdatedByUser\":false,\""
	+ "\":0,\"ContactFields\":[{\"ContactFieldPk\":57,\"OrgUnitId\":0,\""
	+ "\":4,\"DisplayLabel\":\"Address\",\"DisplayOrder\":1,\"IsRequired\":fa"
	+ "lse,\"ContactFieldValue\":\"\",\"ValueInfo\":null,\"Alternatevalue\":nu"
	+ "ll,\"ContactFieldTypeName\":\"ADDRESS1\",\"MaxLength\":128,\"RowNumber\":"
	+ "0,\"RowActType\":null,\"IsSearchable\":true,\"RegularExpression\":\"\",\""
	+ "ErrorMessage\":\"\",\"OtherStateText\":null,\"OtherCountryText\":null},"
	+ "{\"ContactFieldPk\":58,\"OrgUnitId\":0,\"ContactFieldType\":5,\"DisplayLabel\":"
	+ "\"Address 2\",\"DisplayOrder\":2,\"IsRequired\":false,\"ContactFieldValue\":\"\","
	+ "\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":\"ADDRESS2\","
	+ "\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":true,"
	+ "\"RegularExpression\":\"\",\"ErrorMessage\":\"\",\"OtherStateText\":null,"
	+ "\"OtherCountryText\":null},{\"ContactFieldPk\":61,\"OrgUnitId\":0,\"ContactFieldType\""
	+ ":8,\"DisplayLabel\":\"Country\",\"DisplayOrder\":2,\"IsRequired\":false,\""
	+ "ContactFieldValue\":\"1\",\"ValueInfo\":\"\",\"Alternatevalue\":null,\""
	+ "ContactFieldTypeName\":\"COUNTRY\",\"MaxLength\":\"\",\"RowNumber\":0,\""
	+ "RowActType\":null,\"IsSearchable\":false,\"RegularExpression\":\"\",\""
	+ "ErrorMessage\":\"\",\"OtherStateText\":null,\"OtherCountryText\":null},"
	+ "{\"ContactFieldPk\":1783,\"OrgUnitId\":0,\"ContactFieldType\":1,\"DisplayLabel\":"
	+ "\"Anusha 1st name\",\"DisplayOrder\":2,\"IsRequired\":false,\"ContactFieldValue\":"
	+ "\"\",\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":"
	+ "\"CONTACT_NAME\",\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,"
	+ "\"IsSearchable\":false,\"RegularExpression\":null,\"ErrorMessage\":null,"
	+ "\"OtherStateText\":null,\"OtherCountryText\":null},{\"ContactFieldPk\":1784,"
	+ "\"OrgUnitId\":0,\"ContactFieldType\":19,\"DisplayLabel\":\"Last names\","
	+ "\"DisplayOrder\":2,\"IsRequired\":false,\"ContactFieldValue\":\"\","
	+ "\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":"
	+ "\"FLEX2\",\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\""
	+ ":false,\"RegularExpression\":null,\"ErrorMessage\":null,\"OtherStateText\":"
	+ "null,\"OtherCountryText\":null},{\"ContactFieldPk\":59,\"OrgUnitId\":0,\""
	+ "ContactFieldType\":6,\"DisplayLabel\":\"City\",\"DisplayOrder\":3,\"IsRequired\":false,\"ContactFieldValue\":\"\",\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":\"CITY\",\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":false,\"RegularExpression\":null,\"ErrorMessage\":null,\"OtherStateText\":null,\"OtherCountryText\":null},{\"ContactFieldPk\":60,\"OrgUnitId\":0,\"ContactFieldType\":7,\"DisplayLabel\":\"State\",\"DisplayOrder\":4,\"IsRequired\":false,\"ContactFieldValue\":\"\",\"ValueInfo\":\"\",\"Alternatevalue\":null,\"ContactFieldTypeName\":\"STATE\",\"MaxLength\":\"\",\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":false,\"RegularExpression\":null,\"ErrorMessage\":null,\"OtherStateText\":null,\"OtherCountryText\":null},{\"ContactFieldPk\":62,\"OrgUnitId\":0,\"ContactFieldType\":9,\"DisplayLabel\":\"Zip1\",\"DisplayOrder\":5,\"IsRequired\":false,\"ContactFieldValue\":\"\",\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":\"ZIP\",\"MaxLength\":10,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":false,\"RegularExpression\":null,\"ErrorMessage\":null,\"OtherStateText\":null,\"OtherCountryText\":null},{\"ContactFieldPk\":63,\"OrgUnitId\":0,\"ContactFieldType\":10,\"DisplayLabel\":\"Email\",\"DisplayOrder\":7,\"IsRequired\":false,\"ContactFieldValue\":\"\",\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":\"EMAIL\",\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":false,\"RegularExpression\":\"^([a-zA-Z0-9_\\\\-\\\\.]+)@((\\\\[[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.)|(([a-zA-Z0-9\\\\-]+\\\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\\\]?)$\",\"ErrorMessage\":\"\",\"OtherStateText\":null,\"OtherCountryText\":null},{\"ContactFieldPk\":64,\"OrgUnitId\":0,\"ContactFieldType\":12,\"DisplayLabel\":\"Mobile No\",\"DisplayOrder\":8,\"IsRequired\":false,\"ContactFieldValue\":\"\",\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":\"PHONE\",\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":true,\"RegularExpression\":\"\",\"ErrorMessage\":\"\",\"OtherStateText\":null,\"OtherCountryText\":null},{\"ContactFieldPk\":1745,\"OrgUnitId\":0,\"ContactFieldType\":2,\"DisplayLabel\":\"Ashok atthi\",\"DisplayOrder\":14,\"IsRequired\":true,\"ContactFieldValue\":\"apiuser\",\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":\"FIRST_NAME\",\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":true,\"RegularExpression\":\"\",\"ErrorMessage\":\"\",\"OtherStateText\":null,\"OtherCountryText\":null},{\"ContactFieldPk\":1746,\"OrgUnitId\":0,\"ContactFieldType\":3,\"DisplayLabel\":\"Last Name\",\"DisplayOrder\":15,\"IsRequired\":true,\"ContactFieldValue\":\"apiuser\",\"ValueInfo\":null,\"Alternatevalue\":null,\"ContactFieldTypeName\":\"LAST_NAME\",\"MaxLength\":128,\"RowNumber\":0,\"RowActType\":null,\"IsSearchable\":false,\"RegularExpression\":\"\",\"ErrorMessage\":\"\",\"OtherStateText\":null,\"OtherCountryText\":null}],\"UserRoleNames\":\"User\",\"CreatorUserId\":213}";
	
RestAssured.baseURI="http://192.168.1.121/acv4.1.0.0_responsive/";
Response res=given().
header("Authorization","Bearer D-_Im9CotxtpiidZ9WIaBPHGYUI7rSKVD-nANwjEOyZbABPWQtdZUUnUukCPczBpC0UIuU8ezaYBxHYhDubiIR7JGct5pLpMJJqY18Bng4C5z2SeS6YgZvXojFhErRgJ6Y0a4_KPRdJkWurWl4asr-06TqsR485myqna6fVRvbyGekc5NallwrE1EWJOehpp_1OjT9rvXXMZCK412tpDLjUSSyShuKdh3e1imb8aG6xhqH0AEFhFEAsVcaMOL-Hz_Ruc4bMSJz4IV-zr2zCrb0Uh_Ahnf_-9C2d2fShRA4VLD7TMXObFj8bveG47lZIjtDXZJtn4g38osy1UwtaoDpPKkwuXNuvkMpsTbTpD3bgR6pFIxk6tUmTfK2CAdPtDaWuSMk1WZl_I-ceE_CMtHXAf9FqwZL8SAeVN3XpDfZ0").
header("Content-Type","application/json").
body(body).
when().
post("api/users").
then().
assertThat().statusCode(200).
and().extract().response();
String ResponseBody =res.asString();
System.out.println("the response is:"+ResponseBody);
}
System.out.println("Test Ended");
	}
}
