package example;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors

import com.gargoylesoftware.htmlunit.javascript.host.file.FileReader;  
public class fileCreation { 
  public static void main(String[] args) { 
    try { 
      File myObj = new File("D:\\varun\\filename.txt"); 
      if (myObj.createNewFile()) { 
        System.out.println("File created: " + myObj.getName()); 
      } else { 
        System.out.println("File already exists."); 
      } 
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace(); 
    } 
    fileWriter();
  } 
  
  
  public static void fileWriter()
  {
	  try{
		  FileWriter writer=new FileWriter("D:\\varun\\filename.txt");
		  writer.write("Files in Java might be tricky, but it is fun enough!");
	      writer.close();
	      System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	  }
	 
  }
  
  
  
	 
  }
