 package example;

import java.util.ArrayList;
import java.util.List;

public class armstrong {
	 static  int  x;
	    static String s="a*"; 
	    static int count = 0;
	public static void main(String[] args)  {  
	    int c=0,a,temp;  
	    int n=1634;
	    int num=n;//It is the number to check armstrong  
	    temp=n; 
	   
		
		while(num != 0)
        {
            // num = num/10
            num /= 10;
            ++count;
        }
        System.out.println("Number of digits: " + count);
	    
        for(int k=1;k<=count-1;k++)
	    {
	    	
	     if(k==count-1)
	     {
	    	 s=s+"a";
	     }
	     else
	     {
	   	s=s+"a*";
	     }	
	    }
        System.out.println(s);
        Integer x=Integer.parseInt(s);

        
        System.out.println(x);
	    while(n>0)  
	    {  
	    	/*if(n<10)
	    	{
	    		c=c+n;
	    		n=0;
	    		break;
	    	}*/
	    a=n%10;  
	    n=n/10;  
	    //sum  of each digits is equal to the tempNumber itself
	    
	    
	    
	   
	    
	    //int x=Integer.parseInt(s);
	    
	    System.out.println(c);
	    }  
	    if(temp==c)  
	    System.out.println("armstrong number");   
	    else  
	        System.out.println("Not armstrong number");   
	   }  
	}  

