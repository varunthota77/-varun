package example;

import java.sql.SQLException;

public class DBcartitems extends DBconnection {
public static String query="select objectId from TAccuItem where cartId IS NOT NULL and orgUnitId=116 and lastCartAddDate IS NOT NULL order by lastCartAddDate desc";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
DBconnect();
QueryExec(query);

	}

}
