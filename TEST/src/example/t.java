package example;

import java.util.Arrays;

public class t {

public static void main(String[] args) {
// TODO Auto-generated method stub
	int[] intArray = { 9,9,9,9,9,9};
	String t="";
	String strArray[] = new String[intArray.length];

	for (int i = 0; i < intArray.length; i++)
	{
		strArray[i] = String.valueOf(intArray[i]);
//System.out.print(strArray[i]);
 t=t.concat(strArray[i]);
	}
 int x=Integer.parseInt(t);
 x=x+1;
// System.out.println(x);
 String z=Integer.toString(x);
 System.out.print("{");
 for(int j=0;j<z.length();j++)
 {
 	if(j==z.length()-1)
 	{
 	System.out.print(z.charAt(j));
 	}
 	else
 	{
 		System.out.print(z.charAt(j)+",");	
 	}
 }
 System.out.print("}");
 
 
 
/*String y="9,9,9,9,9";
String temp=y.replace(",","");
int x=Integer.parseInt(temp);
x=x+1;
//System.out.println(x);
String z=Integer.toString(x);
System.out.print("{");
for(int i=0;i<z.length();i++)
{
	if(i==z.length()-1)
	{
	System.out.print(z.charAt(i));
	}
	else
	{
		System.out.print(z.charAt(i)+",");	
	}
}
System.out.print("}");
*/
	}
}
