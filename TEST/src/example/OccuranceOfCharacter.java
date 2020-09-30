package example;

import java.util.Scanner;

public class OccuranceOfCharacter {
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
String s="tecra systems";
String store;
int count=0;
int length=s.length();
Scanner in = new Scanner(System.in); 
System.out.println("Enter a character");  
store = in.nextLine();
String a[]=s.split("");
if(store.length()>1)
{
	System.out.println("please enter only one character");
}
else
{
for(int i=length-1;i>=0;i--)
{
	if(a[i].equalsIgnoreCase(store))
	{
		count++;
	}
}
System.out.println(count);
	}
  }
}
