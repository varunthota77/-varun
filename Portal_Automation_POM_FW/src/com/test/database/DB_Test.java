package com.test.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import com.test.hooks.Hooks;
import com.test.pages.Claim_submission_Page;

public class DB_Test extends Hooks 
{

	public static String host="192.168.1.25\\mssqlserver2012";
	public static String portNumber="1433";
	public static String DBName="PM_IQense";
	public static String uname="qausernew";
	public static String pass="Quality@QA";
	
	public static String StartDate = "2020-02-16 00:00:00.000";
	public static String EndDate = "2020-02-16 00:00:00.000";
	public static String PANumber = Hooks.PA_Number;
	public static String val = "222";
	
public static ResultSet rs;
	
	
	public static Statement connectToDB() throws SQLException
	{
		
		Connection con=DriverManager.getConnection("jdbc:sqlserver://"+host+":"+portNumber+";databasename="+DBName,uname,pass);
		Statement st=con.createStatement();
		return st;
	}
	
	static String UpdateActivityDate="UPDATE PlanDetail SET StartDate = '"+StartDate+"', EndDate = '"+EndDate+"' WHERE PlanID = "+PANumber+";";
	
	public static void Update_Activity_Date_in_Database() throws SQLException 
	{
		
		Statement st= connectToDB();
		System.out.println("Database Script is Initiated");
		st.execute(UpdateActivityDate);
		System.out.println("Activity Date Is Updated from Database");
		System.out.println("Database connections are closed");
	}

	
	

	public static void Insert_ClaimNumber_in_Database() throws SQLException 
	{
		Claim_submission_Page cp=new Claim_submission_Page(driver);
		String UpdateClaimNumber ="DECLARE @orgUnitId bigint=1, @objectId bigint="+cp.Actual_ClaimNumber+",\r\n" + 
				"@objectType int=3,@eventType int=2,@workflowId bigint,\r\n" + 
				"@levelNumber int,@creationDate datetime=GETDATE(),@lastModifiedDate datetime,\r\n" + 
				"@isComplete bit=0,@UserCulture varchar(10)='en-US',@approvalprocessGroupId bigint\r\n" + 
				"INSERT INTO TObjectWorkflow \r\n" + 
				"([orgUnitId],[objectId],[objectType],[eventType],[workflowId],[levelNumber]\r\n" + 
				",[creationDate],[lastModifiedDate],[isComplete],[UserCulture],[approvalprocessGroupId])\r\n" + 
				"VALUES(@orgUnitId,@objectId,@objectType,@eventType,@workflowId,@levelNumber, \r\n" + 
				"@creationDate,@lastModifiedDate,@isComplete,@UserCulture,@approvalprocessGroupId)";
		Statement st= connectToDB();
		System.out.println("Database Script is Initiated");
		st.executeUpdate(UpdateClaimNumber);
		System.out.println("Claim Number is Inserted Database");
		
	}

	
}
