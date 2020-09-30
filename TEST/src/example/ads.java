package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ads {
	static int temp=0;
	static int temp1;
	static int temp2;
	static int flag=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
 Integer  a[]={1,2,3,2,1,3,132,111,51,68,99,5};
 
// System.out.println(a.length);
 List l=new ArrayList<>();
for(int i=0;i<=a.length-1;i++)
{
	for(int j=i+1;j<=a.length-1;j++)
	{
		if(a[i]<a[j])
		{
			l.add(a[i]);
			
			
		}
		else
		{
			if(a[i-1]<a[j-1])
			{
				l.add(a[i]);	
			}
			temp=Arrays.asList(a).indexOf(a[i]);
			break;
		}
		break;
	}
}
//System.out.println(l);
for(int x=0;x<=a.length-1;x++)
{
if(temp!=0)	
{
	for(int i=temp+1;i<=a.length-1;i++)
	{
		for(int j=i+1;j<=a.length-1;j++)
		{
			if(a[i]<a[j])
			{
				l.add(a[i]);
				
				temp=0;
			}
			else
			{
				if(a[i-1]<a[j-1])
				{
					l.add(a[i]);	
				}
				temp=Arrays.asList(a).indexOf(a[i]);
				break;
			}
			break;
		}
	}
	}
}
	
System.out.println(l);

int index;
for(int i=0;i<=l.size();i++)
{
	for(int j=i+1;j<=l.size();j++)
	{
		 temp1=(int) l.get(i);
		 temp2=(int) l.get(i);
		
		 if(temp1<temp2)
		  {
			 index=l.indexOf(temp1);
			 
			 System.out.println(l.subList(0, 3));
			 flag++;
			 break;
		  }
		else
		{
			break;
		}
	
	
	}
	if(flag==1)
	{
		break;
	
	}
		}
	
}
}

	

