/**
 * Jan Michael Balbin
 * 
 */
package com.base.connection;

import com.base.dataaccess.DataExtractDao;
import com.base.dataaccess.DataExtractDaoI;
import com.base.environment.Environment;
import com.base.environment.EnvironmentConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDAOFactory extends DAOFactory
{
	
	static String driverClassName = Environment.getProperty(EnvironmentConstants.DRIVER_NAME);
	private static String hostName = Environment.getProperty(EnvironmentConstants.HOST_NAME);
	private static String databaseName = Environment.getProperty(EnvironmentConstants.DATABASE_NAME);
  
	static String connectionUrl = "jdbc:postgresql://" + hostName + ":5432/" + databaseName;
  
	static String dbUser = Environment.getProperty(EnvironmentConstants.USER_NAME);
	static String dbPwd = Environment.getProperty(EnvironmentConstants.PASSWORD);
  
	public static Connection newConnection() throws Exception
	{
		Connection conn = null;
    
		try{
			Class.forName(driverClassName);
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		
		try{
			conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		}
		catch (SQLException e){
			System.out.println("Connection Failed! Check output console.\n");
			e.printStackTrace();
		}
		return conn;
	}
  
	public DataExtractDaoI getDataExtractDaoI()
	{
		return new DataExtractDao();
	}
}
