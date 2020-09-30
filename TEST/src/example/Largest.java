package example;

import java.util.Scanner;

public class Largest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
int a[]={23,435,643,7544,24,634,2,6666,5};
for(int i=0;i<a.length-1;i++)
{
	for(int j=i+1;j<a.length-1;j++)
	{
	if(a[i]<=a[j])
	{
		int temp=a[i];
		a[i]=a[j];
		a[j]=temp;
	}
	}
}
System.out.println(a[0]);
	}

}
