package example;

import java.util.Scanner;

public class reversenumber {
	static int store;
	public static void main(String args[]) {
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

}
