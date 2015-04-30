package com.client;

import javax.transaction.xa.Xid;

import org.jboss.tm.XidFactory;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.internal.jta.xa.XID;
import com.arjuna.ats.jta.xa.XidImple;
import com.configration.utilities.MySQLXAConnection;

public class Client 
{
	public static void main(String[] args) 
	{
		try
		{
			MySQLXAConnection my =new MySQLXAConnection();
			XidImple x = new XidImple(new XID());
			Uid id= new Uid();
			Xid xid = new XidImple(id);
			System.out.println(xid);
			my.mysqlXAconn(xid);
		}
		catch(Exception ex)
		{
			
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
}