package com.widget.entities;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import com.arjuna.ats.txoj.Lock;
import com.arjuna.ats.txoj.LockManager;
import com.arjuna.ats.txoj.LockMode;
import com.arjuna.ats.txoj.LockResult;
import com.entity.busniess.Item;
 
@Resource(name = "java:jboss/datasources/MySqlDS")
@Entity
@Table(name="Goo")
public class Goo implements XAResource, Serializable, Item {
    private static final long serialVersionUID = 1L;
 

    @Id
    @GeneratedValue
	private int Id;
	private String name;
	private String type;
	private int price;
	private String code;
	
	private int state;
	private Uid lockUid =null;
 
    public Goo() {
        super();
    }
    
	public Goo(String name, String type, int price, String code)
	{
		this.name= name;
		this.type=type;
		this.price=price;
		this.code=code;
	}
		
	@Override
	public int calculateTotal() {
		// TODO Auto-generated method stub
		return price;
	}
	
	public String getName() 
	{
		
			return name;
		
	}

	public void setName(String name) {
			this.name = name;
			
	}

	private void printLockInfo(Uid lockInfo) {
		lockUid = lockInfo;
		System.out.println("Lock Uid = " + lockInfo);
	}

	public String getType() {
			return type;
	}
	
	public void setType(String type) {
			this.type = type;
			
	}

	public int getPrice() {
			return price;
		
	}

	public void setPrice(int price) {
			this.price = price;
		
	}

	public String getCode() {
			return code;
		
	}

	public void setCode(String code) {
			this.code = code;
		
	}
	public int getID() {

			return Id;
		
	}

	public void setId(int iD) {

			this.Id = iD;
		
	}
	
    @Override
    public String toString() {
        return "Goo [Goo=" + getID() + ", Goo name=" + getName()
                + ", Type=" + getType() + ", price=" + getPrice() + "]";
    }
    
	
		

	public String type() {
		return "/StateManager/LockManager/GooObject";
	}
	

	@Override
	public void commit(Xid arg0, boolean arg1) throws XAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end(Xid arg0, int arg1) throws XAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forget(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTransactionTimeout() throws XAException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSameRM(XAResource arg0) throws XAException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int prepare(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Xid[] recover(int arg0) throws XAException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rollback(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setTransactionTimeout(int arg0) throws XAException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void start(Xid arg0, int arg1) throws XAException {
		// TODO Auto-generated method stub
		
	}
}