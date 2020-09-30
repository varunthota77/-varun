package example;
import java.util.Scanner;
public class anu {
public static String anu="";
public static void main(String[] args) {
// TODO Auto-generated method stub
//String a="anusha raavi";
	String temp="";
	String temp2="";
	String ch="";
System.out.println("enter capitalize string :");
Scanner sc=new Scanner(System.in);
String a=sc.nextLine();
//int length=a.length();
String c[]=a.split(" ");
for(int i=0;i<c.length;i++)
{
	for(int j=0;j<c[i].length();j++)
	{
		temp=c[i];
		int temp1=temp.length()-1;
		if(j==0)
	{			
	 ch=String.valueOf(temp.charAt(j));
	System.out.print(ch.toUpperCase());
	if(j==temp1)
	{
		System.out.print(" ");
	}
	}
		else
		{
			ch=String.valueOf(temp.charAt(j));
			System.out.print(ch.toLowerCase());
			if(j==temp1)
			{
				System.out.print(" ");
			}
		}

}

}



/*for(int i=0;i<c.length;i++)
{
	
		temp=c[i];
		int temp1=temp.length()-1;
		for(int x=temp1;x>=0;x--)
		{
			System.out.print(temp.charAt(x));
			if(x==0)
			{
				System.out.print(" ");
			}
		}
	
	
}
System.out.println(temp2);*/
}

}