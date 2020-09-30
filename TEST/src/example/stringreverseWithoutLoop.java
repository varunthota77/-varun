package example;

public class stringreverseWithoutLoop {
static String s="varu f h t";
static int l=s.length();
static int i=0;
public static void main(String[] args) 
	{
		//System.out.print("Reverse :");
		//StringReverse();
		StringLength();
		//removeSpacesFromString();
		//StringReverse()	;
	}
	
	public static void StringReverse()	
	{
	if(l!=0)
		{
		System.out.print(s.charAt(l-1));
		l--;
		StringReverse();
		}
	}
	
	
	public static void StringLength()
	{
	try
	{
		if(s.charAt(i)!=' '||s.charAt(i+1)!=' ')
		{
			i++;
			StringLength();
			//System.out.println(i);
		}
	}
	catch(StringIndexOutOfBoundsException e)
	   {
			System.out.println();
			System.out.print("Length is:"+i);
	   }
	}
	public static void removeSpacesFromString()
	{
	for(int i=0;i<=l-1;i++)
	{
		if(s.charAt(i)==' ')
		{
			
		}
		else
		{
			System.out.print(s.charAt(i));
		}
	}
	}
}
