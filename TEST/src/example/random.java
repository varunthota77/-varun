package example;
import java.util.Random;
import java.util.Scanner;

public class random {
	static long randomNo;
	public static void main(String args[])
	{
		
		System.out.println("enter your payment amount:");
		Scanner sc=new Scanner(System.in);
		int store=sc.nextInt();
		Random rnd = new Random();
		if(store<=100)
		{
			System.out.println("recharge should be greater than 100");
		}
		else if(store>=100 && store<=1000)
		{
			 randomNo =rnd.nextInt(10);
			System.out.println(" You Won $" + randomNo+" cashback");
			if(randomNo==0)
			{
				System.out.println("Better Luck Next Time");
			}
		}
		else if(store>=1000 && store<=10000)
		{
			 r();
			 while(randomNo<=100)
			 {
				 r();
			 }
			System.out.println(" You Won $" + randomNo+" cashback");
			if(randomNo==0)
			{
				System.out.println("Better Luck Next Time");
			}
		}
		else
		{
			System.out.println("No cashback recieved");
		}
	
	}
	public static void r()
	{
		Random rnd = new Random();
		randomNo =rnd.nextInt(1000);
	}
}
