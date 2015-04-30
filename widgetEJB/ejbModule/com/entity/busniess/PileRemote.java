package com.entity.busniess;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;

//@Resource(name = "java:jboss/datasources/MySqlDS")
@Remote
public interface PileRemote 
{

	// Database Transactions
	public int add(Object obj);
	public int remove(Object obj);
	public Object retrieve();
	public List<Object> retrieveAll();
	public Object findObject(Object obj);
	public int count();

}
