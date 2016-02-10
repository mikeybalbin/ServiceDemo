/**
 * Jan Michael Balbin
 * 
 */
package com.base.environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Environment
{
	private static final String ENV_DIRECTORY_PROPERTY = "env_dir";
	private static final String DEFAULT_ENV_DIRECTORY = "/home/tplinux/gcservice/env";
	protected static Properties properties = new Properties();
	protected static Properties roleProperties = new Properties();
	
	private static Logger logger = Logger.getLogger(Environment.class);
  
	static
	{
		String env = "gc";
		String envDir = getEnvDirectory();
		String pathForAppProps = buildEnvPath(envDir, env);
		try
		{
			loadProperties(pathForAppProps, properties);
		}
		catch (IOException e)
		{
			logger.error("IO exception encountered while loading properties from " + pathForAppProps, e);
		}
	}
  
	public static String getEnvDirectory()
	{
		String envDir = System.getProperty(Environment.ENV_DIRECTORY_PROPERTY);
		if ((envDir == null) || (envDir.equals(""))) {
			envDir = Environment.DEFAULT_ENV_DIRECTORY;
		}
		return envDir;
	}
  
	public static void loadProperties(String pathname, Properties properties) throws IOException
	{
		File file = new File(pathname);
		if (file.exists())
		{
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			in.close();
		}
		else
		{
			logger.warn("Properties file does not exist, skipping load: " + pathname);
		}
	}
  
	private static String buildEnvPath(String dir, String environment)
	{
		return dir + "/" + environment + ".properties";
	}
  
	public static String getProperty(String key)
	{
		String value = properties.getProperty(key);
		if (value == null) {
			logger.warn("Environment property not defined: " + key);
		}
		return value;
	}
}
