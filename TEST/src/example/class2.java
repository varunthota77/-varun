package example;

public class class2 {
	   public void add() { // without arguments
	      int x = 30;
	      int y = 70;
	      int z = x+y;
	      System.out.println(z);
	     
	   }
	   public static void main(String args[]) {
	      class2 test = new class2();
	     test.add();
	      //System.out.println("The sum of x and y is: " + test.add());
	   }
	}
