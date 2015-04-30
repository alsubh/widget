package com.configration.utilities;

//Step 1: Use interfaces from java.sql package 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class MySQLConnection
{
  //static reference to itself
  private static MySQLConnection instance = new MySQLConnection();
  public static final String URL = "jdbc:mysql://localhost:3306/widget";
  public static final String USER = "root";
  public static final String PASSWORD = "anas";
  public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
   
  //private constructor
  private MySQLConnection() 
  {
      try 
      {
          //Step 2: Load MySQL Java driver
          Class.forName(DRIVER_CLASS);
      } 
      catch (ClassNotFoundException e)
      {
          e.printStackTrace();
      }
  }
   
  private Connection createConnection() 
  {

      Connection connection = null;
      try 
      {
          //Step 3: Establish Java MySQL connection
          connection = DriverManager.getConnection(URL, USER, PASSWORD);
      } 
      catch (SQLException e)
      {
          System.out.println("ERROR: Unable to Connect to Database.");
      }
      return connection;
  }   
   
  public static Connection getConnection() {
      return instance.createConnection();
  }
  
  
  public static XAConnection testXADataSourceWithoutTX() throws Exception {
      XADataSource dataSource = (XADataSource) Class.forName(
              "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource")
              .newInstance();
      dataSource.getClass().getMethod("setURL", new Class[] { String.class })
              .invoke(dataSource,
                      new Object[] { "jdbc:mysql://localhost/widget" });
      dataSource.getClass().getMethod("setLogger",
              new Class[] { String.class }).invoke(dataSource,
              new Object[] { "com.mysql.jdbc.log.StandardLogger" });

      XAConnection xaConnection = dataSource.getXAConnection("root", "anas");
      return xaConnection;
  }
}