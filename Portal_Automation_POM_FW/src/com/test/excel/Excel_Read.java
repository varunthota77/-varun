package com.test.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.test.hooks.Config;

public class Excel_Read {
	
	
	public static String Username;
	public static String Password;
	public static String FundName;
	public static String FundDesc;
	public static String Funding_Model;
	public static String Reserve_Balance;
	public static String Reimbursement;
	public static String Promotion;
	public static String Fund_Type;
	public static String Country;
	public static String Currency;
	public static String Reimbursement_method;
	public static String FundPeriod_Assignment;
	public static String Start_Date;
	public static String End_date;
	public static String Fund_Budget_Amount;
	public static String Payment;
	public static String Period_Measure;
	

	
	public static void load_Excel_data(int RowNumber) throws Exception
	{
		try {
		File f=new File("TestData/Data.xlsx");
		FileInputStream fi= new FileInputStream(f);
		
		XSSFWorkbook wb=new XSSFWorkbook(fi);
		XSSFSheet sheet=wb.getSheetAt(0);
	
		for(int i=RowNumber;i<=RowNumber;i++)
		{
			
			Username				=sheet.getRow(i).getCell(0).getStringCellValue();
			Password				=sheet.getRow(i).getCell(1).getStringCellValue();
			FundName				=sheet.getRow(i).getCell(2).getStringCellValue();
			FundDesc				=sheet.getRow(i).getCell(3).getStringCellValue();
			Funding_Model			=sheet.getRow(i).getCell(4).getStringCellValue();
			Reserve_Balance			=sheet.getRow(i).getCell(5).getStringCellValue();
			Reimbursement			=sheet.getRow(i).getCell(6).getStringCellValue();
			Promotion				=sheet.getRow(i).getCell(7).getStringCellValue();
			Fund_Type				=sheet.getRow(i).getCell(8).getStringCellValue();
			Country		    		=sheet.getRow(i).getCell(9).getStringCellValue();
			Currency				=sheet.getRow(i).getCell(10).getStringCellValue();
			Reimbursement_method	=sheet.getRow(i).getCell(11).getStringCellValue();
			FundPeriod_Assignment	=sheet.getRow(i).getCell(12).getStringCellValue();
			Start_Date		 		=sheet.getRow(i).getCell(13).getStringCellValue();
			End_date				=sheet.getRow(i).getCell(14).getStringCellValue();
			Fund_Budget_Amount		=sheet.getRow(i).getCell(15).getStringCellValue();
			Payment					=sheet.getRow(i).getCell(16).getStringCellValue();
			Period_Measure			=sheet.getRow(i).getCell(17).getStringCellValue();
			
			
			System.out.println(" The Current Execution Row Number -> "+ i +"");
	
		}
		}catch (Exception e) {
			System.out.println("Excel Class error is: "+ e);
		}
		
	}
	
	
	

	
	
	
	
	
	
	
	

}
