package com.widget.entities;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import java.io.IOException;
import java.io.Serializable;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import com.arjuna.ats.txoj.Lock;
import com.arjuna.ats.txoj.LockManager;
import com.arjuna.ats.txoj.LockMode;
import com.arjuna.ats.txoj.LockResult;
import com.entity.busniess.Item;

@Resource(name = "java:jboss/datasources/MySqlDS")
@Entity
@Table(name="Widget")
public class Widget implements XAResource, Serializable, Item {
	
    @Id
    @GeneratedValue
	private int Id;
	private String name;
	private String style;
	private String code;
		
	private int state;

	public Widget(){	
	}
	
	public Widget(String name, String style, String code)
	{	
		this.name= name;
		this.style=style;
		this.code=code;
	}
	
	
	@Override
	public int calculateTotal() {
		// TODO Auto-generated method stub
		int sum=0;

		return sum;
	}
	

	public String getName() {
			return name;
		
	}

	public void setName(String name) {
			this.name = name;
		
	}

	public String getStyle() {
			return style;
		
	}
	
	public void setStyle(String style) {
			this.style = style;
		
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
        return "Widget [Widget=" + getID() + ", Widget Name=" + getName() + ", Style=" + getStyle() + "]";
    }   
	
	public String type() {
		return "/StateManager/LockManager/WidgetObject";
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
