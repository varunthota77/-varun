package example;

public class anushaPricing {
	static int temp1 = 0;
	static int count = 1;
	static int temp = 3;
	static int v=(temp*count)-1;
	static String s="abcdedfegwrbrberntnrynmynnhmkymtuyk";
	static int len=s.length()-1;
	public static void main(String[] args) {
		try {
			
			//System.out.println(s.length()-1);
			for(int i=0;i<=s.length()-1;i++)
			{
				
				char c=s.charAt(i);
				if(i==v)
				{
					System.out.println(s.charAt(i));
					count++;
					v=(temp*count)-1;
					
				}
				
				else
				{
					System.out.print(s.charAt(i));
				}
				
		  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
		
		
	}

}