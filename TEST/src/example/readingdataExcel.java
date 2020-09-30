package example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class readingdataExcel {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
FileInputStream f=new FileInputStream("D:\\AccuSelenium\\Ram\\TEST\\TestData\\LoginData.xls");
HSSFWorkbook w=new HSSFWorkbook(f);
HSSFSheet s=w.getSheet("sheet1");
System.out.println(s.getRow(1));
}
}