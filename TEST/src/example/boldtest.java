package example;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

class boldtest
{
	void m1(byte b)
	{System.out.println("the vlaue of m1 is:"+b);
	}
	void m1(short s)
	{System.out.println("the vlaue of m1 is:"+s);}
	void m1(int a)
	{System.out.println("the vlaue of m1 is:"+a);}
public static void main(String args[])
	{
	boldtest o=new boldtest();
	o.m1(10);
	o.m1(100);
	o.m1(10000);
}
} 
