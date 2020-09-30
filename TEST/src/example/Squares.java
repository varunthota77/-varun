package example;

public class Squares {
	static int sum=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	int	n=10;
	
for(int i=1;i<=n;i++)
{
	int temp=i*i;	
	System.out.println(temp);
sum=sum+temp;
}
System.out.println("TOTAL SUM IS:"+sum);
	}

}
