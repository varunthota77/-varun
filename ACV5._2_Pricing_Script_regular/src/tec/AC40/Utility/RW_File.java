package tec.AC40.Utility;



import java.io.File;


import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import tec.AC40.Common.*;
import tec.AC40.Config.Config;
import tec.AC40.Suite.OrderFlow;

public class RW_File {

 private static FileWriter fw;
 private static BufferedWriter bw;
 static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
 static Date date = new Date();
 //private static String time = Config.wd+Config.ProjectName+"\\Results\\"+Commonclass.SheetName;
 private static String time = System.getProperty("user.dir")+"\\Results\\"+Commonclass.SheetName;
 public static String FolderPath = null;
 private static String TestFile =time;

public static void CreateFile() throws IOException {
  //Create File In D: Driver.  
 
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_h-mm-ss_a");
	 Date date = new Date();
	// System.out.println("Time Stamp : "+dateFormat.format(date));
	 
	 // Create folder for error images (and also create folder name)
	 if(Config.TakeScreenShot.equalsIgnoreCase("Yes"))
	 {
		 FolderPath = System.getProperty("user.dir")+"\\ScreenShots\\"+dateFormat.format(date);
		 new File(FolderPath).mkdir();	
	 }
	
	
  File FC = new File(TestFile); //Created object of java File class.
  FC.createNewFile();//Create file.
 // File Fc1 = new File(FolderPath).mkdirs();
 
  //Fc1.createNewFile();
  fw = new FileWriter(TestFile);
  //fw = new FileWriter(TestFile);
   bw = new BufferedWriter(fw);
   bw.append("Test case , Test Step , Parameters , Page Name , Price Type , Expected Vlaue , Actual Vlaue, Status, OrderNumber, Comments");
   bw.newLine();
  // System.out.println(time1);
 }

public static void WriteResult(String Exceptedvalue, String ActualValue, 
		String PageName, String PriceType) throws IOException {
	//String OrderNo = OrderFlow.OrderNumber;
	 
	
	bw.append(OrderFlow.TestCase1+","+OrderFlow.TestStep1+","+OrderFlow.Parameters1+","+PageName+","+PriceType+",");
	  bw.append("\'"+Exceptedvalue+",");
	 
	  bw.append("\'"+ActualValue+","+"FAIL"+","+OrderFlow.OrderNumber);
	  bw.newLine();
	 
	  bw.flush();
	 
	 }

public static void WriteResultSuccess() throws IOException {
	
	  
	bw.append(OrderFlow.TestCase1+","+OrderFlow.TestStep1+","+OrderFlow.Parameters1+","+" , , , , "+"PASS"+","+OrderFlow.OrderNumber);
	  
	  bw.newLine();
	
	  bw.flush();
	
	 }

public static void WriteResultSuccessThirdPartyTax(String ActualOverAllTaxValue) throws IOException {
	
	  
	bw.append(OrderFlow.TestCase1+","+OrderFlow.TestStep1+","+OrderFlow.Parameters1+","+" , , , , "+"Verified"+","+OrderFlow.OrderNumber+","+
	"The script verifies Tax value whether it is number or not. We are unable to verify the exact value because it will come from third party. We need to verify the Tax value ("+ActualOverAllTaxValue+") manually with the help of Order number in application and 3rd Party .");
	  
	  bw.newLine();
	  bw.flush();
	
	 }
public static void WriteResultSuccessThirdPartyShipping(String VOupdatedshippingprice) throws IOException {
	
	  
	bw.append(OrderFlow.TestCase1+","+OrderFlow.TestStep1+","+OrderFlow.Parameters1+","+" , , , , "+"Verified"+","+OrderFlow.OrderNumber+","+
	"The script verifies Shipping value whether it is number or not. We are unable to verify the exact value because it will come from third party. We need to verify the Shipping value ("+VOupdatedshippingprice+") manually with the help of Order number in application and 3rd Party .");
	  
	  bw.newLine();
	  bw.flush();
	
	 }


public static void Closefile() throws IOException, NullPointerException {
	 bw.close();
	 fw.close();
	 }

public static void WriteResultFail() throws IOException{
	
	  
		bw.append(OrderFlow.TestCase1+","+OrderFlow.TestStep1+","+OrderFlow.Parameters1+","+" , , , , "+"Fail"+","+OrderFlow.OrderNumber);
		  
		  bw.newLine();
		
		  bw.flush();
}
  }

