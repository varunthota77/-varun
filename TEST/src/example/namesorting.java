package example;

import java.util.ArrayList;
import java.util.List;

public class namesorting {
	static String a[]={"varun","lavan","manoj","ramprasad","ztrdr","fytf","htdi","rampr"};
	static int x=1;
	static String temp;
	static List l=new ArrayList<>();
	public static void main(String args[])
	
	{
	for(int i=0;i<=a.length-1;i++)
	{
		for(int j=i+1;j<=a.length-1;j++)
		{
			
			
			if(a[i].charAt(0)>=a[j].charAt(0))
			{
				temp=a[i];
				a[i]=a[j];
				a[j]=temp;
				
			}
			
		}
	}
	for(int k=0;k<=a.length-1;k++)
	{
	System.out.println(a[k]);
}
	}
}
