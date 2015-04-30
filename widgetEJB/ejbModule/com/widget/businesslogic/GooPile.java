package com.widget.businesslogic;

import java.util.List;

import javax.ejb.Stateless;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.configration.utilities.ContextConfig;
import com.entity.busniess.PileRemote;
import com.widget.entities.Goo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.TransactionManager;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import utilities.TypeDB;

/**
 * Session Bean implementation class GooPile
 */
@Stateless
@Remote(PileRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@Resource(name = "java:jboss/datasources/MySqlDS")
public class GooPile implements PileRemote {

	public static Goo gooObject;
	private int count;
	Statement stmt;
	public static Connection sqlConn = null;
	private TransactionManager manager;

	@PersistenceContext(unitName = "widget")
    private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public GooPile() {
    }
    
	public TransactionManager getManager() {
		return manager;
	}

	public void setManager(TransactionManager manager) {
		this.manager = manager;
	}

	@Override
	public int add(Object obj) 
	{
		Goo goo = (Goo) obj;
		String SQL = "INSERT INTO "+ Goo.class.getName() + "(name, code, type, price)" + "VALUES('"
				+ goo.getName() + "','" + goo.getCode() + "','" + goo.getType()
				+ "'," + goo.getPrice() + ");";
		
		String dType = TypeDB.MYSQL.toString();

		sqlConn = ContextConfig.setupGooPileXAConnection(manager, dType,
					"GooPile", "root", "anas", SQL);
			return sqlStatement(SQL);
		}

	@Override
	public int remove(Object obj) {
		// TODO Auto-generated method stub
		Goo goo = (Goo) obj;
		String SQL = "DELETE FROM "+ Goo.class.getName() +" WHERE (name='" + goo.getName()
				+ "' and code='" + goo.getCode() + "' and type='"
				+ goo.getType() + "') or ID=" + goo.getID() + ";";
		return sqlStatement(SQL);
	}

	@Override
	public Object retrieve() 
	{
		Goo gooItem = new Goo();
		String SQL = "SELECT ID,name,code, type, price FROM "+ Goo.class.getName() + " LIMIT 1;";
		try {
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			while (rs.next()) 
			{
				gooItem.setId(rs.getInt("ID"));
				gooItem.setName(rs.getString("name"));
				gooItem.setCode(rs.getString("code"));
				gooItem.setType(rs.getString("type"));
				gooItem.setPrice(rs.getInt("price"));
				return gooItem;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			gooItem = null;
		}
		return gooItem;
	}

	@Override
	public List<Object> retrieveAll() {
		List<Object> gooItems = new ArrayList<Object>();
		Goo gooItem = new Goo();
		String SQL = "SELECT ID,name,code, type, price FROM "+ Goo.class.getName();
		try {
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			//Iterate through the data in the result set and display it.
			while (rs.next()) 
			{
				gooItem.setId(rs.getInt("ID"));
				gooItem.setName(rs.getString("name"));
				gooItem.setCode(rs.getString("code"));
				gooItem.setType(rs.getString("type"));
				gooItem.setPrice(rs.getInt("price"));
				gooItems.add(gooItem);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gooItems = null;
		}
		return gooItems;
	}
	
	@Override
	public Object findObject(Object obj)
	{
		Goo goo = (Goo)obj;
		return entityManager.find(Goo.class, goo.getID());
	}
	
	@Override
	public int count() {
		List<Object> gooItems = new ArrayList<Object>();
		gooItems = retrieveAll();
		return gooItems.size();
	}
	private int executeUpdateQuery(String SQL) 
	{
		int result=0;
		try
		{
			Query query = entityManager.createQuery(SQL);
	        result = query.executeUpdate();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			result = (Integer) null;
			ex.printStackTrace();
		}
		return result;
	}
	public int sqlStatement(String SQL) 
	{
		int result = (Integer) null;  
		try
		{
			stmt = sqlConn.createStatement();
			// execute insert SQL statement
			result=  stmt.executeUpdate(SQL);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			result = (Integer) null;
		}
		return result;
	}

	public Goo getGooObject() {
		return gooObject;
	}

	public void setGooObject(Goo gooObject) {
		this.gooObject = gooObject;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
