package example;

import java.util.Scanner;

public class ReverseString {
	static String s;
	static String str="";
	static int store;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
reverse();
 }
public static void reverse() {
	 Scanner in = new Scanner(System.in); 
	 System.out.println("Enter a string/number to check if it is a palindrome");  
     s = in.nextLine();
for(int i=s.length()-1;i>=0;i--)
   {
	str=str+s.charAt(i);
	
    }
System.out.print(str);
 }

public static void reverseNumber() {
	int reversedNumber=0;
	int remainder;
	 Scanner in = new Scanner(System.in); 
	 System.out.println("Enter a number");  
    store = in.nextInt();
    while(store != 0)
    {
        remainder = store%10;
        reversedNumber = reversedNumber*10 + remainder;
        store =store/10;
    }
    System.out.println(reversedNumber);
}



public static void rev()
{
	 String reversedString = "";
		String z= "i love my country";
		String y[]=z.split(" ");
						
		for(int i=0;i<y.length;i++)
		{
			
			 String reverseWord = "";
			 //String reversestring="";
		    String d=y[i];
		    

			//System.out.println(d.length()-1);
			//int j =0;
			for(int j=d.length()-1;j>=0;j--)
			{
				reverseWord = reverseWord + d.charAt(j);
				
				
			}
			
		  reversedString = reversedString + reverseWord + " ";
		  
		 }
		
		System.out.print(reversedString+" ");
}
}