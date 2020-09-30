package example;

public class test {

	public static void main(String[] args) {
		int n = 153; int rev=0;int sum=0;
	{
	
		for(int i=1;i<=10;i++)
		{
			int temp=i;
		while(i!=0)
		{
			
			int a=i%10;
			
			//System.out.println(a);
			sum=sum+(a*a*a);
			System.out.println(sum);
			 rev=rev*10+a;
			i=i/10;
			if(sum==temp)
			{
				System.out.println("Armstrong");
			}
		}	
		
		}
	System.out.println(rev);

	}}

}