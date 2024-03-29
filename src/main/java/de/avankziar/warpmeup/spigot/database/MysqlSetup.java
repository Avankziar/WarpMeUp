package main.java.de.avankziar.warpmeup.spigot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import main.java.de.avankziar.warpmeup.spigot.WarpMeUp;

public class MysqlSetup 
{
	private WarpMeUp plugin;
	private Connection conn = null;
	
	public MysqlSetup(WarpMeUp plugin) 
	{
		this.plugin = plugin;
		connectToDatabase();
		setupDatabaseI();
	}
	
	public void connectToDatabase() 
	{
		WarpMeUp.log.info("Connecting to the database...");
		try {
       	 	//Load Drivers
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().get().getString("mysql.user"));
            properties.setProperty("password", plugin.getYamlHandler().get().getString("mysql.password"));
            properties.setProperty("autoReconnect", plugin.getYamlHandler().get().getString("mysql.autoReconnect"));
            properties.setProperty("verifyServerCertificate", plugin.getYamlHandler().get().getString("mysql.verifyServerCertificate"));
            properties.setProperty("useSSL", plugin.getYamlHandler().get().getString("mysql.sslEnabled"));
            properties.setProperty("requireSSL", plugin.getYamlHandler().get().getString("mysql.sslEnabled"));
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().get().getString("mysql.host") 
            		+ ":" + plugin.getYamlHandler().get().getString("mysql.port") + "/" + plugin.getYamlHandler().get().getString("mysql.databaseName"), properties);
           
          } catch (ClassNotFoundException e) {
        	  WarpMeUp.log.severe("Could not locate drivers for mysql! Error: " + e.getMessage());
            return;
          } catch (SQLException e) {
        	  WarpMeUp.log.severe("Could not connect to mysql database! Error: " + e.getMessage());
            return;
          }
		WarpMeUp.log.info("Database connection successful!");
	}
	
	public void setupDatabaseI() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getMysqlInterface().tableNameI 
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " player_uuid char(36) NOT NULL UNIQUE,"
		        		+ " player_name varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,"
		        		+ " "
		        		+ " );";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        WarpMeUp.log.severe("Error creating tables! Error: " + e.getMessage());
		      } finally {
		    	  try {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
	}
	
	public Connection getConnection() 
	{
		checkConnection();
		return conn;
	}
	
	public void checkConnection() 
	{
		try {
			if (conn == null) {
				WarpMeUp.log.warning("Connection failed. Reconnecting...");
				reConnect();
			}
			if (!conn.isValid(3)) 
			{
				WarpMeUp.log.warning("Connection is idle or terminated. Reconnecting...");
				reConnect();
			}
			if (conn.isClosed() == true) 
			{
				WarpMeUp.log.warning("Connection is closed. Reconnecting...");
				reConnect();
			}
		} catch (Exception e) 
		{
			WarpMeUp.log.severe("Could not reconnect to Database! Error: " + e.getMessage());
		}
	}
	
	public boolean reConnect() 
	{
		try 
		{            
            long start = 0;
			long end = 0;
			
		    start = System.currentTimeMillis();
		    WarpMeUp.log.info("Attempting to establish a connection to the MySQL server!");
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().get().getString("mysql.user"));
            properties.setProperty("password", plugin.getYamlHandler().get().getString("mysql.password"));
            properties.setProperty("autoReconnect", "true");
            properties.setProperty("verifyServerCertificate", "false");
            properties.setProperty("useSSL", plugin.getYamlHandler().get().getString("mysql.sslEnabled"));
            properties.setProperty("requireSSL", plugin.getYamlHandler().get().getString("mysql.sslEnabled"));
            //properties.setProperty("useUnicode", "true");
            //properties.setProperty("characterEncoding", "utf8");
            //properties.setProperty("characterSetResults", "utf8");
            //properties.setProperty("connectionCollation", "utf8mb4_unicode_ci");
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().get().getString("mysql.host") + ":" 
            		+ plugin.getYamlHandler().get().getString("mysql.port") + "/" + plugin.getYamlHandler().get().getString("mysql.databaseName"), properties);
		    end = System.currentTimeMillis();
		    WarpMeUp.log.info("Connection to MySQL server established!");
		    WarpMeUp.log.info("Connection took " + ((end - start)) + "ms!");
            return true;
		} catch (Exception e) {
			WarpMeUp.log.severe("Error re-connecting to the database! Error: " + e.getMessage());
			return false;
		}
	}
	
	public void closeConnection() 
	{
		try 
		{
			WarpMeUp.log.info("Closing database connection...");
			conn.close();
			conn = null;
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
