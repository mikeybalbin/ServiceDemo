/**
 * Jan Michael Balbin
 * 
 */
package com.base.connection;

import com.base.dataaccess.DataExtractDaoI;

public abstract class DAOFactory 
{
	/**
	 * Gets the PostgresDAOFactory instance.
	 * 
	 * @return new PostgresDAOFactory() Instance.
	 */
	
	public static DAOFactory getDAOFactory()
	{
		return new PostgresDAOFactory();
	}
	
	/**
	 * Gets ArchiveMetricsExtract interface
	 * 
	 */
	public abstract DataExtractDaoI getDataExtractDaoI();
}
