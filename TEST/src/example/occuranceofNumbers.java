package example;

import java.util.Scanner;

public class occuranceofNumbers {
	 int count=0;
	int temp=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
int a[]={1,2,3,4,5,1,2,4,2};
int count=0;
int temp=0, i,j;
for( i=0;i<=8;i++)
{
	for( j=0;j<=8;j++)
	{
		if(a[i]==a[j])
		{
			temp=count++;
			temp=temp+1;
		}
		
	}
	
	
	System.out.println("count of "+a[i] +" is "+temp);

	temp=0;
	count=0;
}
}
}