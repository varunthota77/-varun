// java program to sort an array and inserting element .

package example;



import java.util.Scanner;
import java.util.Arrays;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.Scanner;
public class abcd extends app{
private static final String textInBold = null;
public static  Scanner in = null;
static int Arraysize, n, pos,neg,flag=0,a[], temp1,  count=0,newArrCount=0;
// static int[] arr;
static  int newarr[] = new int[5];
 static int tempsize=0;
  static String Sortvalue;
 static int arr[] = new int[10];
 static int l=2;
public static void main(String args[])
{
	try
	{
	System.out.print("Please Enter the Elements Size: ");
	Scanner scan = new Scanner(System.in);
	Arraysize = scan.nextInt();
	tempsize=Arraysize-1;
	}
	catch(Exception e)
	{
		System.out.println("Not a valid element Size");
		System.out.println("Please Enter valid array size");
		main(args);
	}
	
Ascdesc();
//System.out.println(Arraysize);

//


}
public static int[] Ascdesc()
{
String YesNo="";

int in, i, pos;


Scanner scan = new Scanner(System.in);


try
{
// input array size

// input array elements
System.out.print("Please Enter the Elements : ");
for(i=0; i<Arraysize; i++)
{
arr[i] = scan.nextInt();
}
}
catch(Exception e)
{
System.out.println("Not a valid Element");
System.out.println("Please enter a valid number");// when in valid input given it shows invalid 
Ascdesc();
System.exit(0);
Arraysize=0;
count++;
}
System.out.println("please enter ascending or descending"); // sort by user input
Sortvalue = scan.next();

	


if(Sortvalue.equalsIgnoreCase("ascending"))
{
for(int x=0;x<arr.length-1;x++)
{
for(int y=x+1;y<arr.length-1;y++)
{
if(arr[x]>=arr[y])
{
int temp=arr[x];
arr[x]=arr[y];
arr[y]=temp;
}
}
}
System.out.println("After sorting the elements are:");// when user select input as ascending order.
for(int l=1;l<=arr.length-1;l++)
{
if(arr[l]==0)
{

}
else
{
System.out.println(arr[l]);
}
}

}



if(Sortvalue.equalsIgnoreCase("descending"))// when user select descending it sort as selected input
{ 
	for(int x=0;x<arr.length-1;x++)
	{
	for(int y=x+1;y<arr.length-1;y++)
	{
if(arr[x]>=arr[y])
{
int temp=arr[x];
arr[x]=arr[y];
arr[y]=temp;
}
}
}

System.out.println("After sorting the elements are:");
for(int l=2;l<=Arraysize+1;l++)
{
System.out.println(arr[arr.length-l]);

}


}




for(int k=0;k<=100;k++)
{
if(count>0)
{
break;
}
System.out.println("Do You want to insert New elements ?");
System.out.println("Enter Yes or No");
YesNo = scan.next();
if(YesNo.equalsIgnoreCase("yes"))// USER INPUT YES
{
	
Arraysize++;

System.out.printf(" Enter Element to be Insert : ");// input inserted element


in = scan.nextInt();

newarr[k]=in;
//System.out.println("  "+newarr[k]);

arr[Arraysize-1]=in;


System.out.print("Element inserted Successfully..!!\n");



}
else
{
flag++;
// System.out.print("Now the New Array is :\n");
for(i=0; i<arr.length-1; i++)
{
if(arr[i]==0)
{

}
else
{
System.out.println(arr[i]);
}

if(Sortvalue.equalsIgnoreCase("descending")) // it sort as descending order
{ 
for(int f=0;f<arr.length-1;f++)
{
for(int j=f+1;j<arr.length-1;j++)
{
if(arr[f]>=arr[j])
{
int temp=arr[f];
arr[f]=arr[j];
arr[j]=temp;
}
}
}

System.out.println("After descending sort the elements are:");
/*for(int l=2;l<=Arraysize+1;l++)
{
System.out.println(arr[arr.length-l]);

}*/


}
if(Sortvalue.equalsIgnoreCase("ascending"))// it sort as ascending order
{
for(int f=0;f<arr.length-1;f++)
{
for(int j=f+1;j<arr.length-1;j++)
{
if(arr[f]>=arr[j])
{
int temp=arr[f];
arr[f]=arr[j];
arr[j]=temp;
}
}

}
System.out.println("After ascending sort the elements are:");
for(int l=0;l<arr.length-1;l++)
{
if(arr[l]==0)
{

}
else
{

	
	
	
	
	//	System.out.print(" "+arr[l]);
	}
//tempsize++;
}



//for ending
}//if ending
break;
}
if(Sortvalue.equalsIgnoreCase("ascending"))
{
for(int t=0;t<=arr.length-1;t++)
{
	for(int v=0;v<=arr.length-1;v++)
	{
	try
	{
		
		
			
		if(arr[t]==newarr[v])
		{
			if((arr[t]==0)&&(newarr[t]==0))
			{
				break;
			}
			else
			{
				if(arr[t]==0)
				{
					
				}
				else
				{
			System.out.print(" {"+arr[t]+"}");
			t++;
				}
			
			}
		}
		else
		{
			
		}
	
  }	catch(Exception e)
	{
		//continue;
	}
//System.out.println(arr[t]);
}
//System.out.println(newarr[0]);
if(arr[t]==0)
{
	
}
else
{
	System.out.print(" "+arr[t]);
}
}
}
else
{
		
	for(int t=arr.length-1;t>=0;t--)
	{
		for(int v=arr.length-1;v>=0;v--)
		{
		try
		{
			
			
				
			if(arr[t]==newarr[v])
			{
				if((arr[t]==0)&&(newarr[t]==0))
				{
					break;
				}
				else
				{
					if(arr[t]==0)
					{
						
					}
					else
					{
				System.out.print(" {"+arr[t]+"}");
				t--;
				
					}
				
				}
			}
			else
			{
				
			}
		
	  }	catch(Exception e)
		{
			continue;
		}
	//System.out.println(arr[t]);
	}
	//System.out.println(newarr[0]);
		

	if(arr[t]==0)
	{
		
	}
	else
	{
		System.out.print(" "+arr[t]);
	}

	}		
		
}



}//else ending for no case

if(flag>0)
{
break;
}
}
return arr;
}



}

