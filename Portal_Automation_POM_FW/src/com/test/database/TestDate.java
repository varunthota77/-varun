package com.test.database;

import java.text.DateFormat;
import java.util.Date;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;
import com.test.pages.Claim_submission_Page;
import com.test.pages.Fund_Submit_Page;
import com.test.pages.L1_and_L2_Page;
import com.test.pages.Login_Page;

public class TestDate extends Hooks
{

	@Test
	public void init_browser() throws Exception
	{

		DB_Test.Update_Activity_Date_in_Database();
	}
}
