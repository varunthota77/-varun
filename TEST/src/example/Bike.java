package example;

class Method{  
	  //defining a method  
	public static String v="varun";
	  void run(){
		  
		  System.out.println("Vehicle is running"       );}  
	}  
	//Creating a child class  
	class Bike extends Method{  
		public static String v="varun thota";
		
	  void run()
	  {
		  System.out.println("Bike is running safely");
		//  System.out.println(super.v);
//	  super.run();
	}
	  
	
	  public static void main(String args[]){  
		  Bike obj = new Bike();  
		 Method m= new Method();
		 Method mt=new Bike();
	// Bike b=new Method();     //wrong
	  mt.run();
//	  obj.run();//calling method
	  
	  }  
	}  