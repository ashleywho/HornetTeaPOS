package Database_Package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Written by Amirul Asyraaf Bin Asri B032110030

public class Database 
{
		private String databaseName = "ht_db";
		private String username = "root";
		private String password = "";
		private String connectionPath = "jdbc:mysql://localhost:3306/"+ databaseName;

		public Connection getConnection() throws ClassNotFoundException, SQLException 
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(connectionPath,username,password);
			
			return connection;
		}
}
