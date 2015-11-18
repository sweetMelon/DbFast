package com.tiangua.fast.db.util;

public class DBConfig
{

	public String			DB_NAME		= "fastDB";

	public int				DB_VERSION	= 1;

	public boolean			debugMode	= false;

	private static DBConfig	instance	= null;

	protected DBConfig()
	{

	}

	public static DBConfig getDBConfig()
	{
		if (instance == null)
		{
			synchronized (DBConfig.class)
			{
				instance = new DBConfig();
			}
			{

			}
		}
		return instance;
	}

}
