package com.test.hooks;

public class Config 
{
	
	/**
	 * @Global Inputs
	 */
	
	//QA
	public static String BrowserEnvironment ="Local"; //CBT or Local
	public static String URL ="http://192.168.1.10/PM-IQense/FMPortal/";

	//UAT
	//public static String URL ="http://192.168.1.10/Porsche-IQenseMergeUAT/FMPortal/";

	/**
	 * @EXCEL DATA SETS START ROW AND END ROW
	 */
	
	public static int Start_Row_Number=1;
	public static int End_Row_Number=2;
	
	//emulate users
	public static String Emulate_App_1_user = "Vinod Kumar";
	public static String Emulate_App_2_user = "User 1";
	
	public static String L1_user = "User 1";
	public static String L2_user  = "prudhvi raj";
	
	//Customer name in Fund Allocation
	public static String customerName = "automation automation";
	public static String Fund_allocation_amount = "1";

}