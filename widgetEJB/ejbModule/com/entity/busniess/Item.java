package com.entity.busniess;

import javax.annotation.Resource;
import javax.ejb.Remote;

//@Resource(name = "java:jboss/datasources/MySqlDS")
@Remote
public interface Item 
{
	public int calculateTotal();
}