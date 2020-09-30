package example;
import java.applet.*;  
import java.awt.*;
import java.util.Scanner;  
public class app extends Applet{
	Scanner s=new Scanner(System.in);
	String store=	s.nextLine();
		public void paint (Graphics g) 
		{
		Font myFont = new Font("Courier", Font.BOLD,20);		
		g.setFont(myFont);
		g.drawString(store,150,150);  
		}  
	
	
		
		} 
	


