package example;

import java.io.*;
import java.util.Scanner;
public class temp {
	 static String text ;
	  static  String text1 ;
	  static  String text2="" ;
	 static String s;
	 public static void main(String[] args) throws IOException {
   
    int lineNumber;
    try {
      FileReader readfile = new FileReader("myfile.txt");
      BufferedReader readbuffer = new BufferedReader(readfile);
      System.out.println("enter the line number");
    Scanner sc=new Scanner(System.in);
    int num=sc.nextInt();
    System.out.println("enter the old character");
    String old=sc.next();
    System.out.println("enter the new character");
    String neww=sc.next();
      for (lineNumber = 1; lineNumber < 10; lineNumber++) {
    	  if(lineNumber==num)
    	  {
    		text=  readbuffer.readLine();
    		if(text.contains(old))
    		{
    			text1=text.replace(old, neww);
    			System.out.println(text1);
    		
    		}
    		else
			{
				 System.out.println("Not found");
			}
    	  }
    	  else
    	  {
    		  
    		  readbuffer.readLine();
    		 
    	  }
      } 
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    FileWriter readfile = new FileWriter("myfile.txt");
    BufferedWriter readbuffer = new BufferedWriter(readfile);
             }
}