package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// This class is used for making connection to DB
	//static Connection:singleton
	private static Connection con;
	
	public static void openConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver"); // reflection
		System.out.println("Driver Loaded...");
		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root123");
		System.out.println("Connected to DB......");
	}
	
	public static Connection getCon() {
		return con;
	}
	
	public static void closeConnection() throws SQLException
	{
		if(con!=null) {
			con.close();
		}
	}
}
