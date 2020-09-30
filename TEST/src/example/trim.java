package example;

import java.text.DecimalFormat;

public class trim {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double d = 1.23554;
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.print(df.format(d));
       
	}

}
