package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.*;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class App {
    public static HashMap<String, String> map=new HashMap<String,String>();
    public static String Course_id,Program_id,Cohort_schedule_id,Course_name;
    public static void main(String[] args) throws IOException {
        // Base URL of the API

        String baseUrl = "https://learn.isb.edu";
        File file = new File(System.getProperty("user.dir")+"/TestData/"+"\\"+"Data.xls");
        FileInputStream inputStream = new FileInputStream(file);
        Workbook Workbook = null;
        Workbook = new HSSFWorkbook(inputStream);
        Sheet Sheet = Workbook.getSheet("Sheet1");
        int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();


        try {
            for (int i = 1; i <= rowCount; i++) {
                Row row = Sheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    map.put(Sheet.getRow(0).getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
                    //    System.out.print(row.getCell(j).getStringCellValue()+"|| ");
                }
                Course_name=map.get("Course_name");
                Course_id=map.get("Course_id");
                Program_id=map.get("Program_id");
                Cohort_schedule_id=map.get("Cohort_schedule_id");

                JSONObject requestBody = new JSONObject();
                requestBody.put("course_id", ""+Course_id+"");
                requestBody.put("program_id", ""+Program_id+"" );
                requestBody.put("cohort_schedule_id", ""+Cohort_schedule_id+"");

                Response response = RestAssured.given()
                        .contentType("application/json")
                        .body(requestBody.toString())
                        .post(baseUrl + "/backend/program_manager/get_users_course_progress");

                String jsonResponse = response.getBody().asString();



                String filePath = "response_" + Course_name + ".json";
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    fileWriter.write(jsonResponse);
                    System.out.println("JSON response saved to " + filePath);
                } catch (IOException e) {
                    System.err.println("Error writing JSON response to file: " + e.getMessage());
                }
                File jsonFile = new File(System.getProperty("user.dir")+"\\"+"response_" + Course_name + ".json");
                String jsonContent = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);



                Response response1 = RestAssured.given()
                        .multiPart("file",jsonFile,"multipart/form-data")
                        .formParam("MultipleWorksheets", "true")
                        .formParam("UploadOptions", "JSON")
                        .post("https://api.products.aspose.app/cells/conversion/api/ConversionApi/Convert?outputType=XLSX");
                JSONObject jsonResponse1 = new JSONObject(response1.getBody().asString());

                // Fetch and store the value of the desired field
                String fieldValue = jsonResponse1.getString("FileName");
                String folderValue = jsonResponse1.getString("FolderName");
                String downloadURL = "https://api.products.aspose.app/cells/conversion/api/Download/"+folderValue+"?file="+fieldValue;
                System.out.println(jsonResponse1);
                System.out.println(fieldValue);
                System.out.println(folderValue);
                System.out.println(downloadURL);

                WebDriverManager.chromedriver().setup();
                WebDriver driver=new ChromeDriver();
                driver.get(downloadURL);
                Thread.sleep(10000);
                driver.close();
                Thread.sleep(5000);
                try {
                    // Load the Excel file
                    FileInputStream fis = new FileInputStream("\\C:\\Users\\rajes\\Downloads\\"+fieldValue);
                    Workbook workbook = new XSSFWorkbook(fis);

                    // Get the desired sheet
                    Sheet sheet = workbook.getSheet("Sheet1"); // Replace "Sheet1" with your sheet name

                    // Iterate through rows in reverse order
                    for (int rowIndex = sheet.getLastRowNum(); rowIndex >= 0; rowIndex--) {
                        Row row1 = sheet.getRow(rowIndex);
                        if (row1 != null) {
                            // Iterate through cells in the row in reverse order
                            for (int cellIndex = row1.getLastCellNum() - 1; cellIndex >= 0; cellIndex--) {
                                Cell cell = row1.getCell(cellIndex);
                                // Check if the cell is blank
                                if (cell != null && cell.getCellType() == CellType.BLANK) {
                                    // Shift the following cells up
                                    for (int shiftIndex = cellIndex + 1; shiftIndex < row1.getLastCellNum(); shiftIndex++) {
                                        Cell shiftedCell = row1.getCell(shiftIndex);
                                        if (shiftedCell != null) {
                                            row1.createCell(shiftIndex - 1, shiftedCell.getCellType());
                                            row1.getCell(shiftIndex - 1).setCellValue(shiftedCell.getStringCellValue());
                                            row1.removeCell(shiftedCell);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Save the modified Excel file
                    FileOutputStream fos = new FileOutputStream("output_excel_file3.xlsx");
                    workbook.write(fos);
                    fos.close();
                    fis.close();

                    System.out.println("Blank cells removed and cells shifted up successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
