package com.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;

import com.configration.utilities.JNDILookupClass;
import com.configration.utilities.LookingUp;
import com.entity.busniess.PileRemote;
import com.widget.businesslogic.GadgetPile;
import com.widget.businesslogic.GooPile;
import com.widget.businesslogic.WidgetPile;
import com.widget.entities.Gadget;
import com.widget.entities.Goo;

public class EJBApplicationClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//PileRemote gooRemote = doLookup("goo");
		//PileRemote widgetRemote = doLookup("widget");
		//PileRemote gadgetRemote = doLookup("gadget");
		
		EJBApplicationClient client = new EJBApplicationClient();
		client.FillGooPile();
		
	}
	
	public void FillGooPile() 
	{
		PileRemote gooRemote = LookingUp.doLookup("goo");
	
		
		try
		{
			// Fill GooPile
			Goo goo1 = new Goo();
			goo1.setName("Goo1");
			goo1.setType("B1");
			goo1.setPrice(10);
			goo1.setCode("01");
			
			gooRemote.add(goo1);
			//goo1.release();
			
			Goo goo2 = new Goo();
			goo2.setName("Goo2");
			goo2.setType("B2");
			goo2.setPrice(30);
			goo2.setCode("02");
			gooRemote.add(goo2);
			//goo2.release();
			
			Goo goo3 = new Goo();
			goo3.setName("Goo3");
			goo3.setType("B3");
			goo3.setPrice(10);
			goo3.setCode("03");
			
			gooRemote.add(goo3);
			//goo3.release();
			
			Goo goo4 = new Goo();
			goo4.setName("Goo4");
			goo4.setType("B4");
			goo4.setPrice(40);
			goo4.setCode("04");
			gooRemote.add(goo4);
			//goo4.release();

	
			System.out.println("Number of Goo in the GooPile : " + gooRemote.count());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}