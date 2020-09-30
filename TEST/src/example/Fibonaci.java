package example;

public class Fibonaci {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
int a=0,b=1,n=10;
System.out.print(a+" "+b);
for(int i=1;i<=n;i++)
{
	int sum=a+b;
	System.out.print(" "+sum);
	 a=b;
	 b=sum; 
	 
}
	}

}
