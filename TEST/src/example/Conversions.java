package example;

import java.util.Arrays;

public class Conversions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntArrayToStringArray(args);
	}
	
	public static void IntArrayToStringArray(String[] args)
	{
		// input primitive integer array
		int[] intArray = { 1, 2, 3, 4 ,5 };

		String strArray[] = new String[intArray.length];

		for (int i = 0; i < intArray.length; i++)
			strArray[i] = String.valueOf(intArray[i]);

		System.out.println(Arrays.toString(strArray));
	}
public static void stringtoint()
{
	String s="1001";  
	//Converting String into int using Integer.parseInt()  
	int i=Integer.parseInt(s);  
	//Printing value of i  
	System.out.println(i);  
	}

public static void inttostring()
{
	int i=10;  
	String s=String.valueOf(i);
	System.out.println(i);  
}
public static void stringtochar()
{
	String s="1";  
	char c=s.charAt(0);
	System.out.println(s);
}

public static void chartostring()

{
	char c='S';  
	String s=String.valueOf(c);  
	System.out.println("String is: "+s);
}


}


