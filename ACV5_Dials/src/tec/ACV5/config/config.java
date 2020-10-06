package tec.ACV5.config;

public class config {

	public static String url = "http://192.168.1.121/acv4.1.0.0_responsive/#/";  // Main URL 
	public static String LayoutType = "layout1";  // Classic Layout1 Layout2

	public static String browser = "GC";  // FF GC IE
																																							
	public static String ReleaseNo = "TEC-ACC-6.7.0.0";  // Release patch Number

	public static String Currency = "$"; // Currency symboClassicl
	public static String PercentageSymbol = "%";  // Percentage symbol
	
	public static int ElementWaitTime = 300;// Explicit wait time in seconds
	public static String TakeScreenShot = "YES"; // YES yes Yes NO (other than Yes it is not taken screen shots)
	
	public static String IsConsoleErrorSave = "YES";  // Yes yes YES and NO
	public static String IsAdjustMOuse = "yes";  // YES yes Yes NO
	
	public static int ExecutionStartRow = 1;      // Any number (it must be equal or less than the Execution End row value)
	public static int ExecutionEndRow = 25;       // Any number or n (IF we enter n, it indicates last row of the Data sheet) 
	public static String SelectedRow = "";  
	
	// Layout1 users Details
	public static String UserNamel1 = "userg2";
	public static String UserPwdl1 = "welcome";
	public static String AdminNamel1 = "admingp";
	public static String AdminPwdl1 = "welcome";
	}


