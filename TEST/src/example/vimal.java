package example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class vimal {
static int count=0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
String s="Automation testing varun vimal Automation";
//int [] arr = new int [s.length()];
for(int i=0;i<s.length();i++)
{
	for(int j=0;j<=i;j++)
	{
	System.out.print(s.charAt(j));
}
	System.out.println();
}
String s1[]=s.split(" ");
int [] arr = new int [s1.length];
for(int k=0;k<s1.length;k++)
{
	for(int l=0;l<s1[k].length();l++)
	{
//System.out.println(	s1[k].charAt(l));
	if(s1[k].charAt(l)=='a'||s1[k].charAt(l)=='e'||s1[k].charAt(l)=='i'||s1[k].charAt(l)=='0'||s1[k].charAt(l)=='u')
	{
count++;	
	}
	}
	arr[k]=	count;
	count=0;

	}
//System.out.println(arr[1]);
/*for(int t=0;t<s1.length;t++)
{
	
System.out.println(s1[t]+"===>"+arr[t]);
	
}*/
int dupl[]=arr.clone();
Arrays.sort(dupl);
System.out.println(Arrays.toString(dupl));
for(int t=s1.length;t>0;t--)
{
	
System.out.println(s1[t]+"===>"+arr[t]);
	
}
	}


	}


