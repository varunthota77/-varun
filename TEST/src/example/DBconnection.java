package example;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class DBconnection {
 public static Connection conn;
 public static Statement sta;
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		/*Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.123:1433;databaseName=ACv4.5.6.0_Orgunit;user=sa;password=Tecra@QA");
		System.out.println("***connection successful***");
		Statement sta = conn.createStatement();
		String Sql = "select TAccuItem.orgUnitId,TEmailQueue.eventType,TEmailQueue.subject from TAccuItem Inner join TEmailQueue ON TAccuItem.orgUnitId=31170 and TEmailQueue.eventType=789";
		ResultSet rs = sta.executeQuery(Sql);
		while (rs.next()) {
			System.out.println(rs.getString("orgunitid")+"||"+rs.getString("eventtype")+"||"+rs.getString("subject"));
		}*/
	}
	public static void DBconnect() throws ClassNotFoundException,SQLException
	{
try
{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
		 conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.123:1433;databaseName=ACv4.5.6.0_Orgunit;user=sa;password=Tecra@QA");
		 sta = conn.createStatement();
		System.out.println("***connection successful***");

}
catch(Exception e)
{
	e.printStackTrace();
}
	
	}
	public static void QueryExec(String sql) throws ClassNotFoundException,SQLException
{
	
		ResultSet rs = sta.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString("orgunitid")+"||"+rs.getString("eventtype")+"||"+rs.getString("subject"));

		}
}
	
//select TAccuItem.orgUnitId,TEmailQueue.eventType,TEmailQueue.subject from TAccuItem Inner join TEmailQueue ON TAccuItem.orgUnitId=31170 and TEmailQueue.eventType=789
}

