package example;

public class exmple {

	public static void main(String[] args) {

	String s="tecra systems";
	String space[]=s.split(" ");
	for(int i=0;i<=space.length-1;i++)
	{
		String store=space[i];
		for(int j=store.length()-1;j>=0;j--)
		{
			System.out.print(store.charAt(j));
			
		}
		System.out.print(" ");
      }
	}
}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
/*      String reversedString = "";
		String z= "i love my country";
		String y[]=z.split(" ");
						
		for(int i=0;i<y.length;i++)
		{
			
			 String reverseWord = "";
			 //String reversestring="";
		    String d=y[i];
		    

			//System.out.println(d.length()-1);
			//int j =0;
			for(int j=d.length()-1;j>=0;j--)
			{
				reverseWord = reverseWord + d.charAt(j);
				
				
			}
			
		  reversedString = reversedString + reverseWord + " ";
		  
		 }
		
		System.out.print(reversedString+" ");
		}
	
	
	
	}*/