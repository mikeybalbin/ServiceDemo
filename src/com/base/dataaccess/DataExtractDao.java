/**
 * Jan Michael Balbin
 * 
 */
package com.base.dataaccess;

import com.base.connection.PostgresDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DataExtractDao implements DataExtractDaoI
{
	private static Logger logger = Logger.getLogger(DataExtractDao.class);
	
	private Connection 			conn;
	private PreparedStatement 	ps = null;
	private ResultSet 			rs = null;
  
	/**
	 * 
	 */
	public int getStoreNumber()
	{
		int iStoreNumber = 0;
		try
		{
			conn = PostgresDAOFactory.newConnection();
      
			String sql = "SELECT number FROM scf WHERE structmember = 'gstScfParam.ulLong' AND index = '12' AND description = 'Store Number'";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()){
				do{
					iStoreNumber = rs.getInt("number");
				}while(rs.next());		
			}	
		}
		catch(Exception e){ 
			e.printStackTrace();
			logger.error("Error extracting store number", e);
		}
		finally{					
			try 
			{
				conn.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
		
		return(iStoreNumber);
	}
	
	
}
