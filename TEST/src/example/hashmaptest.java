package example;

import java.util.ArrayList;
import java.util.HashMap;

public class hashmaptest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> datamap = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> sampleData = new HashMap<String,String>();
    	sampleData.put("username","abc@xyz.com");
    	sampleData.put("password","Test@123");
    	System.out.println("Current data" +sampleData);
    	System.out.println(sampleData.get("username"));
    	datamap.add(sampleData);
    	System.out.println(datamap);
	}

}
