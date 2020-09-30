package example;

import java.io.*;
public class specificFileLineReading {
	 static String text ;
	  static  String text1 ;
	  static  String text2="" ;
	 static String s;
	 public static void main(String[] args) throws IOException {
   
    int lineNumber;
    try {
      FileReader readfile = new FileReader("myfile.txt");
      BufferedReader readbuffer = new BufferedReader(readfile);
    
      for (lineNumber = 1; lineNumber < 10; lineNumber++) {
        
          text = readbuffer.readLine();
          if(text.equalsIgnoreCase("GO"))
          {
        	  text=readbuffer.readLine();
        	  for(int i=1;i<=3;i++)
        	  {
        		  s=readbuffer.readLine();
        	  
        	  while(!s.equalsIgnoreCase("GO"))
        	  {
        		  text1=s;
        		text2=  text2.concat(" "+text1);
        		//  text.concat(text1);
        		//  System.out.println(text1);
        		 break;
        	  }
        	 }
        	  break;
          }
        } 
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    String Temp=text.concat(" "+text2);
    System.out.println(Temp);
  //  System.out.println(text2);
            	/* FileReader readfile = new FileReader("myfile.txt");
                 BufferedReader readbuffer = new BufferedReader(readfile);
                 for (int lineNumber = 1; lineNumber < 10; lineNumber++) {
         String s=    readbuffer.readLine();
             if(s.equalsIgnoreCase("GO"))
             {
            	s= readbuffer.readLine();
             }
                 }
             System.out.println(s);*/
             }
}