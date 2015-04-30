package com.client;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import Tester.clientTest;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.arjuna.ats.jta.UserTransaction;
import com.processedComponent.utilities.PolishedWidget;
import com.processedComponent.utilities.RawWidget;
import com.processedComponent.utilities.RoughWidget;
import com.transaction.AssembledGadgetTransaction;
import com.transaction.CreateWidgetTransaction;
import com.widget.businesslogic.GooPile;
import com.widget.entities.Gadget;
import com.widget.entities.Goo;
import com.widget.entities.Widget;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;

/**
 * Session Bean implementation class ApplicationClient
 */
@Stateless
@LocalBean
public class ApplicationClient {

public static void main(String[] args) {
		
	ApplicationClient test = new ApplicationClient();
		test.FillGooPile();
	}
	

	public void FillGooPile() 
	{
		try
		{
				// Fill GooPile
			
			Goo goo1 = new Goo("Goo1", "B1", 10, "01");
			Goo goo2 = new Goo("Goo2", "B2", 30, "02");
			Goo goo3 = new Goo("Goo3", "B3", 10, "03");
			Goo goo4 = new Goo("Goo4", "B4", 20, "04");
	
			// add new Goo to the DB
			GooPile goopile = new GooPile();
	
			goopile.add(goo1);
			goopile.add(goo2);
			goopile.add(goo3);
			goopile.add(goo4);
	
			System.out.println("Number of Goo in the GooPile : " + goopile.count());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void t1(int numberOfGoos, String nameOfRawWidget, String codeOfRawWidget, String nameOfRoughWidget, String codeOfRoughWidget, 
			String nameOfPolishedWidget, String codeOfPolishedWidget) 
	{
		javax.transaction.UserTransaction user= UserTransaction.userTransaction();
		CreateWidgetTransaction t1 = new CreateWidgetTransaction();
		List<Goo> goos = new ArrayList<Goo>();
		
		try 
		{
			user.begin();
				
			goos = t1.getGooFromPile(numberOfGoos);
			System.out.println("Number of Goo that required for starting production: "+ goos.size());
			TransactionImple trans1 = TransactionImple.getTransaction();
			Uid TransactionUid = trans1.get_uid();
			System.out.println("Transaction Uid = "+ TransactionUid);
			Xid transactionId = trans1.getTxId();
			System.out.println("Transaction Xid = " + transactionId);
			int status = trans1.getStatus();
			int timeout= trans1.getTimeout();
			System.out.println("Current status of the " + transactionId + "is "+ status + "within timeout = " + timeout );
			
			RawWidget raw = new RawWidget();
			raw = t1.getRawWidget(nameOfRawWidget, codeOfRawWidget, goos);
	
			RoughWidget rough = new RoughWidget(raw);
			rough = t1.getRoughWidget(nameOfRoughWidget, codeOfRoughWidget, raw);
	
			PolishedWidget polished = new PolishedWidget();
			polished = t1.getPolishedWidget(nameOfPolishedWidget, codeOfPolishedWidget, rough);
			
			user.commit();
	
			// t1.putInWidgetPile(polished);
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException 
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			try 
			{
				user.rollback();
			} 
			catch (IllegalStateException | SecurityException | SystemException e1) 
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}

	public void t2(String gadgetName, String gadgetCode, String tag, int numberOfRequiredWidgets) {

		javax.transaction.UserTransaction user= UserTransaction.userTransaction();
		AssembledGadgetTransaction t2 = new AssembledGadgetTransaction();
		
		try
		{
			user.begin();
			
			List<Widget> widgets = t2.getWidget(numberOfRequiredWidgets);
			
			Gadget gadget = new Gadget();
			gadget = t2.getGadget(gadgetName, gadgetCode, widgets);
	
			t2.putTag(gadget, tag);
			
			user.commit();
			
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException 
				| HeuristicMixedException | HeuristicRollbackException e) {
			try 
			{
				user.rollback();
			} 
			catch (IllegalStateException | SecurityException | SystemException e1) 
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
