package example;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
public class readandupdateDb{
public static void main(String[]args){
try{
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.123:1433;databaseName=kudheer;user=sa;password=Accu@QA");
Statement sta = conn.createStatement();
System.out.println("connection succesful");
FileInputStream fstream = new FileInputStream("D:/AccuSelenium/Ram/TEST/details.txt");
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
String FirstName="",LastName="",Address1="",City="";
for (int i = 0 ; i < splitSt.length ; i++) {
	FirstName=splitSt[0];
	LastName=splitSt[1];
	Address1=splitSt[2];
	City=splitSt[3];	
}

String Sql = "insert into  dbo.AmzadaTable (FirstName,LastName,Address1,City) values ('"+FirstName+"','"+LastName+"','"+Address1+"','"+City+"')";
ResultSet rs = sta.executeQuery(Sql);
System.out.println("successful");
}
}
catch(Exception e){}
}
}
