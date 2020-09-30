package example;

public class Prime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num=28;int flag=0;
		for(int i=2;i<=num;i++)
		{
			if(num%i==0)
			{
				System.out.println("not prime");
				break;
			}
			else
			{
				flag++;
				break;
			}
		}
			if(flag==1)
			{
				System.out.println("prime");
			}
		
	}
}
