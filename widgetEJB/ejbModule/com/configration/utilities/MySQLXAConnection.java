package com.configration.utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.util.property.Property;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.arjuna.ats.jta.common.jtaPropertyManager;
import com.entity.busniess.PileRemote;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;




//Resource Injection related
import javax.annotation.Resource;


public class MySQLXAConnection 
{
	Connection connection = null;
		
	public void mysqlXAconn(Xid xid)
	{
		Transaction transaction =null;
		TransactionManager manager = null;
		int status =0;
		Xid tid =null;
		Uid uid =null;
		XAConnection  xaConn =null;
		XAResource    xaRes = null;
		
		//@Resource(name = "java:/MySqlDS")
        XADataSource ds;
		
		try 
	    {	 
			PileRemote pile = LookingUp.doLookup("Goo");
			
	    	manager =  jtaPropertyManager.getJTAEnvironmentBean().getTransactionManager();
	    	System.out.println(" Manager Status: "+ manager.getStatus());
	    	manager.begin();
	    	transaction = manager.getTransaction();
	    	if(transaction != null)
	    	{
	    		System.out.println( "Transaction is : " + transaction);
	    	}
	        // From the DataSource, obtain an XAConnection object that
	        // contains an XAResource and a Connection object.
	        xaConn =  (XAConnection) MySQLConnection.testXADataSourceWithoutTX(); //ds.getXAConnection();
	        xaRes  = xaConn.getXAResource();
	        connection =  (Connection) xaConn.getConnection();
	
	        
	        status = transaction.getStatus();
	        System.out.println(status);
	        tid = TransactionImple.getTransaction().getTxId();
	        uid = TransactionImple.getTransaction().get_uid();
	        
	        System.out.println("Tid : " + tid);
	        System.out.println("Uid : " + uid);
	        // The connection from the XAResource can be used as any other JDBC connection.
	        Statement stmt = connection.createStatement();
	        
	         //transaction.enlistResource(xaRes);
	        // The XA resource must be notified before starting any transactional work.
	        xaRes.start(tid, XAResource.TMNOFLAGS);
	
	        // Standard JDBC work is performed.
	        int count = stmt.executeUpdate("INSERT INTO widget.goo(name, code, type, price) VALUES('Uoo1','W1','ww1',2 );");
	
	        // When the transaction work has completed, the XA resource must again be notified.
	        xaRes.end(xid, XAResource.TMNOFLAGS);
	
	        // The transaction represented by the transaction ID is prepared to be committed.
	        int rc = xaRes.prepare(xid);
	
	        // The transaction is committed through the XAResource. The JDBC Connection object is not used to commit the transaction when using JTA.
	        xaRes.commit(xid, false);
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Something has gone wrong.");
	        e.printStackTrace();

	        try 
	        {
				transaction.rollback();
				manager.rollback();
				e.printStackTrace();

			} 
	        catch (IllegalStateException | SystemException e1) 
	        {
				e1.printStackTrace();
			}
	    } 
	    finally 
	    {
	    	  try 
	    	  {
	  			//transaction.delistResource(xaRes, XAResource.TMSUCCESS);
	  			//transaction.commit();
		        manager.commit();
	    	  } 
	    	  catch (IllegalStateException | SystemException | SecurityException | RollbackException | HeuristicMixedException | HeuristicRollbackException e1) 
	    	  {
				try
				{
					manager.rollback();
				}
				catch (IllegalStateException | SecurityException | SystemException e) 
				{
					e.printStackTrace();
				}
	    		  e1.printStackTrace();
			}
		    
	        try 
	        {
	            if (connection != null)
	            	connection.close();
	        }
	        catch (SQLException e) 
	        {
	            System.out.println("Note:  Cleaup exception.");
	            e.printStackTrace();
	        }
	    }
	}
}