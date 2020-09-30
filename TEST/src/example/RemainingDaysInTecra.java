package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class RemainingDaysInTecra extends DBconnection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection conn = DriverManager.getConnection(
				"jdbc:sqlserver://192.168.1.123:1433;databaseName=ACv4.5.6.0_Orgunit;user=sa;password=Tecra@QA");
		System.out.println("***connection successful***");
		Statement sta = conn.createStatement();
		String Sql = "select DATEDIFF(day,GETDATE(),'2021-10-15 14:19:38.573')";
		ResultSet rs = sta.executeQuery(Sql);
	}

}
