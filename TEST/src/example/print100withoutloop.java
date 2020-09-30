package example;

public class print100withoutloop {

	
	public static void main(String args[])
	{
	int number=1;
	print(number);
}
	public static void print(int n)
	{
		if(n<=100)
		{
	System.out.print(n+" ");	
	print(n+1);
		}
		
	}
}
