package com.widget.entities;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import com.arjuna.ats.txoj.Lock;
import com.arjuna.ats.txoj.LockManager;
import com.arjuna.ats.txoj.LockMode;
import com.arjuna.ats.txoj.LockResult;
import com.entity.busniess.Item;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Resource(name = "java:jboss/datasources/MySqlDS")
@Entity
@Table(name="Gadget")
public class Gadget implements XAResource, Serializable, Item {
	
    @Id
    @GeneratedValue
	private int ID;
	private String name;
	private String code;
	private int numberOfComponent;
	private String tag="";
	
	private int state;

	
	public Gadget(){
		
	}
	
	
	public Gadget(String name, String code)
	{	
		this.name= name;
		this.code=code;

		this.tag="";
	}
	
	@Override
	public int calculateTotal() {
		int sum=0;

		return sum;
	}
	
	public String getName() {
				return name;

		}

		public void setName(String name) {
				this.name = name;

		}

		public String getTag() {
				return tag;
	
		}
		
		public void setTag(String tag) {
				this.tag = tag;
	
		}

		public String getCode() {
				return code;

		}

		public void setCode(String code) {
				this.code = code;
	
		}
		public int getID() {
				return ID;
		
		}

		public void setId(int iD) {
				this.ID = iD;
	
		}
	
		public int getNumberOfComponent() {
				return numberOfComponent;

		}

		public void setNumberOfComponent (int numberOfComponent) {
				this.numberOfComponent = numberOfComponent;

		}
		
		
    @Override
    public String toString() {
        return "Gadget [Gadget=" + getID() + ", Gadget Name=" + getName() + ", Tag=" + getTag() + "]";
    }
    


	public String type() {
		return "/StateManager/LockManager/GadgetObject";
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
