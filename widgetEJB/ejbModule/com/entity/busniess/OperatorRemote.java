package com.entity.busniess;

import javax.annotation.Resource;
import javax.ejb.Remote;

//@Resource(name = "java:jboss/datasources/MySqlDS")
@Remote
public interface OperatorRemote
{
	public Object create(Object obj);
	public void add(Object obj);
	public void remove(Object obj);	
}
