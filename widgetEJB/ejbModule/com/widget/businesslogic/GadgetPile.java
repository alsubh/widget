package com.widget.businesslogic;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entity.busniess.PileRemote;
import com.widget.entities.Gadget;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.configration.utilities.ConnectionConfig;

/**
 * Session Bean implementation class GadgetPile
 */
@Stateless
@Remote(PileRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@Resource(name = "java:jboss/datasources/MySqlDS")
public class GadgetPile implements PileRemote {

	public static Gadget gadgetObject;
	private int count;
	Statement stmt;
	public static Connection sqlConn = ConnectionConfig.setupGadgetPileConnection("GadgetPile", "tedi", "tedi");
	
	@PersistenceContext(unitName = "widget")
    private EntityManager entityManager;
	
	public GadgetPile() {
	}
	
	@Override
	public int add(Object obj) 
	{
		Gadget gadget = (Gadget) obj;
		String SQL = "INSERT INTO "+ Gadget.class.getName() +" (name, code, numberOfWidget, tag)"
				+ "VALUES('" + gadget.getName() + "','" + gadget.getCode()
				+ "','" + gadget.getNumberOfComponent() + "','"
				+ gadget.getTag() + "');";
		
		return sqlStatement(SQL);
	}

	@Override
	public int remove(Object obj) 
	{
		Gadget gadget = (Gadget) obj;
		String SQL = "DELETE FROM "+ Gadget.class.getName() + " WHERE (name='" + gadget.getName()
				+ "' and code='" + gadget.getCode() + "' and numberOfWidget='"
				+ gadget.getNumberOfComponent() + "')or ID=" + gadget.getID()
				+ ";";
		return sqlStatement(SQL);
	}

	public int updateTag(Gadget gadget, String tag) {
		String SQL = "UPDATE "+ Gadget.class.getName() +" SET tag='" + tag + "' WHERE ID="
				+ gadget.getID() + ";";
		return sqlStatement(SQL);
	}

	@Override
	public Object retrieve() 
	{
		Gadget gadgetItem = new Gadget();
		String SQL = "SELECT ID,name,code, numberOfWidget, tag FROM " + Gadget.class.getName();
		try 
		{
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				gadgetItem.setId(rs.getInt("ID"));
				gadgetItem.setName(rs.getString("name"));
				gadgetItem.setCode(rs.getString("code"));
				gadgetItem.setNumberOfComponent(rs.getInt("numberOfWidget"));
				gadgetItem.setTag(rs.getString("tag"));
				return gadgetItem;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			gadgetItem = null;
		}
		return gadgetItem;
	}

	@Override
	public List<Object> retrieveAll() {
		List<Object> gadgetItems = new ArrayList<Object>();
		Gadget gadgetItem = new Gadget();
		String SQL = "SELECT ID,name,code, numberOfWidget, tag FROM " + Gadget.class.getName();
		try 
		{
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				gadgetItem.setId(rs.getInt("ID"));
				gadgetItem.setName(rs.getString("name"));
				gadgetItem.setCode(rs.getString("code"));
				gadgetItem.setNumberOfComponent(rs.getInt("numberOfWidget"));
				gadgetItem.setTag(rs.getString("tag"));
				gadgetItems.add(gadgetItem);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gadgetItems = null;
		}
		return gadgetItems;
	}

	public List<Gadget> retrieveSpecificGadgets(int numberOfWids, String label) {
		List<Gadget> gadgetItems = new ArrayList<Gadget>();
		Gadget gadgetItem = new Gadget();
		String SQL = "SELECT ID,name,code, numberOfWidget, tag "
				+ "FROM "+ Gadget.class.getName() +" where numberOfWidget=" + numberOfWids + " or "
				+ " tag='" + label + "';";
		try 
		{
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				gadgetItem.setId(rs.getInt("ID"));
				gadgetItem.setName(rs.getString("name"));
				gadgetItem.setCode(rs.getString("code"));
				gadgetItem.setNumberOfComponent(rs.getInt("NumberOfWidget"));
				gadgetItem.setTag(rs.getString("tag"));
				gadgetItems.add(gadgetItem);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			gadgetItems = null;
		}
		return gadgetItems;
	}

	public List<Gadget> retrieveSpecificGadgets(String label) {
		List<Gadget> gadgetItems = new ArrayList<Gadget>();
		Gadget gadgetItem = new Gadget();
		String SQL = "SELECT ID,name,code, numberOfWidget, tag "
				+ "FROM " + Gadget.class.getName()+ " where tag='" + label + "';";
		try 
		{			
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				gadgetItem.setId(rs.getInt("ID"));
				gadgetItem.setName(rs.getString("name"));
				gadgetItem.setCode(rs.getString("code"));
				gadgetItem.setNumberOfComponent(rs.getInt("NumberOfWidget"));
				gadgetItem.setTag(rs.getString("tag"));
				gadgetItems.add(gadgetItem);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gadgetItems = null;
		}
		return gadgetItems;
	}
	
	@Override
	public Object findObject(Object obj)
	{
		Gadget gadget = (Gadget)obj;
		return entityManager.find(Gadget.class, gadget.getID());
	}	
	
	public List<Gadget> retrieveSpecificGadgets(int numberOfWids) {
		List<Gadget> gadgetItems = new ArrayList<Gadget>();
		Gadget gadgetItem = new Gadget();
		String SQL = "SELECT ID,name,code, numberOfWidget, tag "
				+ "FROM Gadget where numberOfWidget=" + numberOfWids + ";";
		try 
		{
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				gadgetItem.setId(rs.getInt("ID"));
				gadgetItem.setName(rs.getString("name"));
				gadgetItem.setCode(rs.getString("code"));
				gadgetItem.setNumberOfComponent(rs.getInt("NumberOfWidget"));
				gadgetItem.setTag(rs.getString("tag"));
				gadgetItems.add(gadgetItem);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			gadgetItems = null;
		}
		return gadgetItems;
	}

	@Override
	public int count() 
	{
		String SQL = "SELECT count(name) FROM "+ Gadget.class.getName();
		try 
		{
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			count = rs.getInt("count");
			System.out.println(" Number of Gadget in the GadgetPile="
					+ rs.getString(1));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return count;
	}

	public int count(int numOfWidgets, String label) {
		List<Gadget> list = new ArrayList<Gadget>();
		list = retrieveSpecificGadgets(numOfWidgets, label);
		return list.size();
	}

	private int executeUpdateQuery(String SQL) {
		Query query = entityManager.createQuery(SQL);
        int result = query.executeUpdate();
        return result;
	}
	
	public int sqlStatement(String SQL)
	{
		int result = (Integer) null;
		try 
		{
			stmt = sqlConn.createStatement();
			// execute insert SQL statement
			result = stmt.executeUpdate(SQL);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return result;
	}
}
