package example;


	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.sql.Statement;
	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.io.BufferedReader;
		import java.io.DataInputStream;
		import java.io.File;
		import java.io.FileInputStream;
		import java.io.FileWriter;
		import java.io.IOException;
		import java.io.InputStreamReader;
		import java.sql.Connection;
	//import java.sql.Date;
	import java.sql.DriverManager;
		import java.sql.ResultSet;
		import java.util.ArrayList;
		import java.util.Iterator;
		public class lavan{
			 

		public static void main(String[]args){
		try{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.123:1433;databaseName=kudheer;user=sa;password=Accu@QA");
		Statement sta = conn.createStatement();
		System.out.println("connection succesful");
		FileInputStream fstream = new FileInputStream("C:\\Users\\Admin\\Desktop\\mydatafile.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		ArrayList list=new ArrayList();
		while ((strLine = br.readLine()) != null){
		list.add(strLine);
		System.out.println(strLine);
		}
		Iterator itr;
		for (itr=list.iterator(); itr.hasNext(); ){
		String str=itr.next().toString();	
		String [] splitSt =str.split(" ");
		String FirstName="",LastName="",email="",Address1="",City="",date1="";
		for (int i = 0 ; i < splitSt.length ; i++) {
			FirstName=splitSt[0];
			LastName=splitSt[1];
			Address1=splitSt[2];
			City=splitSt[3];	
			email=splitSt[4];
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_h-mm-ss_a");
		Date date = new Date();
		date1=dateFormat.format(date);
		System.out.println(dateFormat.format(date));
		try
		{
		String Sql = "insert into  dbo.AmzadaTable (FirstName,LastName,Address1,City,email,DATE) values ('"+FirstName+"','"+LastName+"','"+Address1+"','"+City+"','"+email+"','"+date1+"')";
		ResultSet rs = sta.executeQuery(Sql);
		System.out.println("successful");
		}
		catch(Exception e)
		{
			
		}
		}
		}
		catch(Exception e){}
		
	}}