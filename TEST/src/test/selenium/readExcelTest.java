package test.selenium;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readExcelTest {
public static String s;
public static String f;
 public static String TD="TD4";
 public static List<String> al=new ArrayList<String>(); 
    public static void readExcel(String filePath,String fileName,String sheetName) throws IOException, InterruptedException{

    //Create an object of File class to open xlsx file

    File file =    new File(filePath+"\\"+fileName);

    //Create an object of FileInputStream class to read excel file

    FileInputStream inputStream = new FileInputStream(file);

    Workbook guru99Workbook = null;

    //Find the file extension by splitting file name in substring  and getting only extension name

    String fileExtensionName = fileName.substring(fileName.indexOf("."));

    //Check condition if the file is xlsx file

    if(fileExtensionName.equals(".xlsx")){

    //If it is xlsx file then create object of XSSFWorkbook class

    //guru99Workbook = new XSSFWorkbook(inputStream);

    }

    //Check condition if the file is xls file

    else if(fileExtensionName.equals(".xls")){

        //If it is xls file then create object of XSSFWorkbook class

        guru99Workbook = new HSSFWorkbook(inputStream);

    }

    //Read sheet inside the workbook by its name

    Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);

    //Find number of rows in excel file

    int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();

    //Create a loop over all the rows of excel file to read it

    for (int i = 0; i < rowCount+1; i++) {

        Row row = guru99Sheet.getRow(i);

        //Create a loop to print cell values in a row

        for (int j = 0; j <= 0; j++) {

            //Print Excel data in console
          if(row.getCell(j).getStringCellValue().equalsIgnoreCase(TD))
            {
	
	        for (int y = 1; y < row.getLastCellNum(); y++) {

	          //  System.out.print(row.getCell(y).getStringCellValue()+"|| ");
	            List<String> al=new ArrayList<String>();  
	            al.add(row.getCell(y).getStringCellValue()); 
	            System.out.println(al.get(0));
	            String str=al.get(0);
	            s=str;
	       //  for(String s:al)
	     //    {  
	    //      	 System.out.print(" "+s);  
	      //   }  
	       
	        
	        
	   //     System.out.println();

	//System.out.print(row.getCell(j).getStringCellValue()+"|| ");
 }
}

       // System.out.println();

        }}
    
 }

    //Main function is calling readExcel function to read data from excel file

    public static void main(String...strings) throws IOException, InterruptedException{

    //Create an object of ReadGuru99ExcelFile class

    readExcelTest objExcelFile = new readExcelTest();

    //Prepare the path of excel file
   String filePath = System.getProperty("user.dir")+"/TestData/";
f=filePath;
    //String filePath = "D:\\New folder\\First\\src\\package1\\";

    //Call read file method of the class to read data

    objExcelFile.readExcel(filePath,"LoginData.xls","Sheet1");

    }

}