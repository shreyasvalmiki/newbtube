package tube.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import tube.lib.Constants;

public class ConnectDB {
	
	private static String url = Constants.DB_CONN_STRING;
	private static String user = Constants.DB_USERNAME;
	private static String password = Constants.DB_PASSWORD;
	
	
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
