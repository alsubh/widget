package com.widget.businesslogic;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.configration.utilities.ConnectionConfig;
import com.entity.busniess.PileRemote;
import com.processedComponent.utilities.PolishedWidget;
import com.processedComponent.utilities.RawWidget;
import com.processedComponent.utilities.RoughWidget;
import com.widget.entities.Goo;
import com.widget.entities.Widget;

/**
 * Session Bean implementation class WidgetPile
 */
@Stateless
@Remote(PileRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@Resource(name = "java:jboss/datasources/MySqlDS")
public class WidgetPile implements PileRemote {

	//Resource
	public static Widget widgetObject;
	public Xid xid = null;
	private int count;
	Statement stmt;

	public static Connection sqlConn = ConnectionConfig.setupWidgetPileConnection("Widget", "root", "anas");

	@PersistenceContext(unitName = "widget")
    private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public WidgetPile() {
    }
    
    @Override
	public int add(Object obj) 
    {
    	int result = (Integer)null;
		Widget widget = null;
		try {
			if (obj.getClass() == RawWidget.class) {
				widget = (RawWidget) obj;
			} else if (obj.getClass() == RoughWidget.class) {
				widget = (RoughWidget) obj;
			} else if (obj.getClass() == PolishedWidget.class) {
				widget = (PolishedWidget) obj;
			}
			String SQL = "INSERT INTO " + Widget.class.getName()+ " (name, code, style)" + "VALUES('"
					+ widget.getName() + "','" + widget.getCode() + "','"
					+ widget.getStyle() + "');";
			result= sqlStatement(SQL);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return (Integer) null;
		}
		return result;
	}

	@Override
	public int remove(Object obj) {
		Widget widget = null;
		int result= (Integer) null;
		try {

			if (obj.getClass() == RawWidget.class) {
				widget = (RawWidget) obj;
				sqlDeleteStatement(widget);
			} else if (obj.getClass() == RoughWidget.class) {
				widget = (RoughWidget) obj;
				sqlDeleteStatement(widget);
			} else if (obj.getClass() == PolishedWidget.class) {
				widget = (PolishedWidget) obj;
				sqlDeleteStatement(widget);
			} else if (obj.getClass() == ArrayList.class) {
				List<Widget> listOfWidgets = (List<Widget>) obj;
				int length = listOfWidgets.size();
				for (int i = 0; i < length - 1; i++) {
					result= sqlDeleteStatement(listOfWidgets.get(i));
					listOfWidgets.remove(listOfWidgets.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result= (Integer) null;
		}
		return result;
	}

	@Override
	public Object retrieve() {
		Widget WidgetItem = new Widget();
		String SQL = "SELECT ID,name,code, style FROM "+ Widget.class.getName();
		try {
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);			
			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				WidgetItem.setId(rs.getInt("ID"));
				WidgetItem.setName(rs.getString("name"));
				WidgetItem.setCode(rs.getString("code"));
				WidgetItem.setStyle(rs.getString("style"));
				return WidgetItem;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WidgetItem = null;
		}
		return WidgetItem;
	}

	@Override
	public List<Object> retrieveAll() {
		List<Object> widgetItems = new ArrayList<Object>();
		Widget widgetItem = new Widget();
		String SQL = "SELECT ID,name,code, style FROM "+ Widget.class.getName();
		try {
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			//Iterate through the data in the result set and display it.
			while (rs.next()) 
			{
				widgetItem.setId(rs.getInt("ID"));
				widgetItem.setName(rs.getString("name"));
				widgetItem.setCode(rs.getString("code"));
				widgetItem.setStyle(rs.getString("type"));
				widgetItems.add(widgetItem);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			widgetItems = null;
		}
		return widgetItems;
	}

	public List<Widget> retrieveSpecificWidgets(String style) {
		List<Widget> widgetItems = new ArrayList<Widget>();
		Widget widgetItem = new Widget();

		String SQL = "SELECT ID,name,code, style FROM " + Widget.class.getName() +" where style='"
				+ style + "';";
		try 
		{
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			// Iterate through the data in the result set and display it.
			while (rs.next()) 
			{
				widgetItem.setId(rs.getInt("ID"));
				widgetItem.setName(rs.getString("name"));
				widgetItem.setCode(rs.getString("code"));
				widgetItem.setStyle(rs.getString("style"));
				widgetItems.add(widgetItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			widgetItems = null;
		}
		return widgetItems;
	}

	@Override
	public int count() {
		String SQL = "SELECT count(name) FROM "+ Widget.class.getName();
		try {
			stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);			
			// Iterate through the data in the result set and display it.
			count = rs.getInt("count");
			System.out.println(" Number of Widgets in the WidgetPile="
					+ rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int count(String style) {
		List<Widget> list = new ArrayList<Widget>();
		list = retrieveSpecificWidgets(style);
		return list.size();
	}

	@Override
	public Object findObject(Object obj)
	{
		Widget widget = (Widget)obj;
		return entityManager.find(Widget.class, widget.getID());
	}
	
	public int sqlStatement(String SQL) throws XAException 
	{
		int result =(Integer) null;
		try {

			stmt = sqlConn.createStatement();
			// execute insert SQL statement
			result = stmt.executeUpdate(SQL);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	private int executeUpdateQuery(String SQL) {
		Query query = entityManager.createQuery(SQL);
        int result = query.executeUpdate();
        return result;
	}
	private int sqlDeleteStatement(Widget widget) throws XAException {
		String SQL = "DELETE FROM "+ Widget.class.getName() +" WHERE (name='" + widget.getName()
				+ "' and code='" + widget.getCode() + "' and style='"
				+ widget.getStyle() + "') or ID=" + widget.getID() + ";";
		return sqlStatement(SQL);
	}
}
