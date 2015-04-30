package com.configration.utilities;

import java.util.Properties;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import javax.activation.DataSource;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.arjuna.ats.jdbc.TransactionalDriver;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.widget.businesslogic.WidgetPile;
import com.widget.entities.Widget;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContextConfig {

	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";
	public static Context initialContext;
	
	public static Context getInitialContext() throws NamingException {
		if (initialContext == null) {
			Properties properties = new Properties();
			properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
			properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
			properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1099");
			properties.put(Context.INITIAL_CONTEXT_FACTORY,	"org.jboss.naming.remote.client.InitialContextFactory");
			properties.put(Context.PROVIDER_URL, "remote://localhost:4447");
			properties.put(Context.SECURITY_PRINCIPAL, "anas");
			properties.put(Context.SECURITY_CREDENTIALS, "a01575591~");
			properties.put("jboss.naming.client.ejb.context", true);
			// create a context passing these properties
			initialContext = new InitialContext(properties);
		}
		return initialContext;
	}

	public static void loadDrivers(String dbType) {
		// Register the driver via the system properties variable "jdbc.drivers"
		Properties property = System.getProperties();
		try {
			switch (dbType) {
			case "ORACLE":
				property.put("jdbc.drivers", "oracle.jdbc.driver.OracleDriver");
				// sun.jdbc.odbc.JdbcOdbcDriver drv = new
				// sun.jdbc.odbc.JdbcOdbcDriver();
				// DriverManager.registerDriver(drv);
				break;
			case "SQLSERVER":
				property.put("jdbc.drivers",
						"com.microsoft.sqlserver.jdbc.SQLServerDriver");
				// SQLServerDriver drv = new SQLServerDriver();
				// DriverManager.registerDriver(drv);
				break;
			case "MYSQL":
				property.put("jdbc.drivers", "com.mysql.jdbc.Driver");
				break;
			case "PGSQL":
				property.put("jdbc.drivers", "org.postgresql.Driver");
				break;
			}
			System.setProperties(property);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}

	private static Connection registerDriver(TransactionManager manager,
			String username, String password, String dbName, String sqlStatement) {
		Connection conn = null;
		Context context = null;
		try {
			TransactionalDriver JDBCDriver = new TransactionalDriver();
			DriverManager.registerDriver(JDBCDriver);

			Properties dbProps = new Properties();
			dbProps.setProperty(TransactionalDriver.userName, username);
			dbProps.setProperty(TransactionalDriver.password, password);
			dbProps.setProperty(TransactionalDriver.dynamicClass, "com.arjuna.ats.internal.jdbc.drivers.jndi");
			
			String connectString= "jdbc:arjuna:jdbc/" + dbName;
			conn = JDBCDriver.connect(connectString, dbProps);
			
			dbName = "widget";
			XADataSource ds = getxaResource(username, password, dbName);
			XAConnection c = ds.getXAConnection();
			manageXADataSource(manager, ds, c, sqlStatement);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " Cause :"+ e.getCause() );
			e.printStackTrace();
			System.exit(0);
		}
		return conn;
	}

	private static XADataSource getxaResource(String username, String password, String dbName) throws NamingException {
		
		Context context;
		
		//XADataSource ds = new SQLServerXADataSource();
		XADataSource  ds = new MysqlXADataSource();
		try {
			context = getInitialContext();
			//System.out.println(context.getClass());

			//DataSource das1 = (DataSource) context.lookup("java:jboss/datasources/MySqlDS");
			ds = (XADataSource) context.lookup("MySqlDS");// +dbname
			
			//context.bind("java:/MSSQLDS", ds);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return ds;
	}

	// JNDI: Java Naming and Directory Interface
	public static void manageXADataSource(TransactionManager manager,
			XADataSource ds, XAConnection con, String sqlStatement) {
		// Create the XA data source and XA ready connection.
		XAResource xaRes = null;

		try {
			XAConnection xaCon = ds.getXAConnection();
			//con = xaCon.getConnection();

			javax.transaction.Transaction transaction = manager.getTransaction();

			// Get the XAResource object and set the timeout value.
			WidgetPile.widgetObject = (Widget) xaCon.getXAResource();
			WidgetPile.widgetObject.setTransactionTimeout(0);
			transaction.enlistResource(WidgetPile.widgetObject);

			// Perform the XA transaction.
			//xaRes.start(getXid(), XAResource.TMNOFLAGS);
			PreparedStatement pstmt = ((Connection) xaCon).prepareStatement(sqlStatement);
			pstmt.executeUpdate();
			//xaRes.end(getXid(), XAResource.TMSUCCESS);
			// Commit the transaction.
			//xaRes.commit(getXid(), true);
			manager.getTransaction().delistResource(xaRes, XAResource.TMSUCCESS);

			// Cleanup.
			con.close();
			xaCon.close();

		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static Connection setupGooPileXAConnection(
			TransactionManager manager, String dbtype, String dbName,
			String userName, String password, String sqlStatement) {
		loadDrivers(dbtype);
		Connection con = registerDriver(manager, userName, password, dbName,
				sqlStatement);
		return con;
	}
}
