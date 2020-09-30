package example;

/*import java.util.Scanner;
import java.util.Spliterator;

public class duplicatecharacters {
	static String b;
	static int count=0;
	static int i;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s="kadduuuuk";
		String s1[]=s.split("");
		int length=s.length()-1;
		
		for(int i=0;i<=length;i++)
		{
			for(int j=i+1;j<=length;j++)
			{
				if(s1[i].equalsIgnoreCase(s1[j]))
				{
					System.out.println(s1[i]);
					break;
				}
			}
		}
		
		//System.out.println("tezst");
      }
  }*/

import java.util.Scanner;

public class duplicatecharacters {
	static String b;
	static int count=0;
	static int i;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s="Tecra systems";
		
		
		int length=s.length();
		
		String a[]=s.split("");
		
		
		for(int i=0;i<=length-1;i++)
		{
			for(int j=i+1;j<=length-1;j++)
			{
			if(a[i].equalsIgnoreCase(a[j]))
			{
				a[j]="@";
			}
			}	 
			if(a[i].equalsIgnoreCase("@"))
			{
			 a[i]=a[i].replace("@", "");
			}
		
			System.out.print(a[i]);
			}
      }
  }


