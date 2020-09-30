package example;

import java.util.ArrayList;

public class capitalandSmall {

	public static void main(String[] args) {
	String d="spaYDgHDz";
	//d=d.toUpperCase();
	//System.out.println(d);
	char[] f=d.toCharArray();
	ArrayList  al=new ArrayList();
	ArrayList al1=new ArrayList();
	
	for(int i=0;i<f.length;i++)
	{
				if (f[i] >= 'A' && f[i] <= 'Z') 
		{
			//ArrayList <Character> al=new ArrayList();
			al.add(f[i]);
					
		}
				else if(f[i] >= 'a' && f[i] <= 'z')
				{
					al1.add(f[i]);
				}
		}
			
		for(int j=0;j<d.length();j++)
		{
			if(j<al.size())
			{
				System.out.print(al.get(j));
			}
			if(j<al1.size())
			{
			System.out.print(al1.get(j));
			}
		}
	}
}