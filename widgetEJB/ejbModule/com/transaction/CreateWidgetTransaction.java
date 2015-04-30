package com.transaction;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

import com.arjuna.ats.internal.jta.transaction.arjunacore.BaseTransaction;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.arjuna.ats.jta.TransactionManager;
import com.entity.busniess.TransactionRemote;
import com.operator.businesslogic.Baker;
import com.operator.businesslogic.Builder;
import com.operator.businesslogic.Polisher;
import com.processedComponent.utilities.PolishedWidget;
import com.processedComponent.utilities.RawWidget;
import com.processedComponent.utilities.RoughWidget;
import com.widget.entities.Goo;
import com.widget.entities.Widget;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 * Session Bean implementation class CreateWidgetTransaction
 */
@Stateless
@LocalBean
public class CreateWidgetTransaction implements TransactionRemote{

	public List<Goo> goos = new ArrayList<Goo>();
	RawWidget rawWidget = new RawWidget();
	Builder builder = new Builder();

	RoughWidget roughWidget;
	Baker baker = new Baker();

	PolishedWidget polishedWidget;
	Polisher polisher = new Polisher();

	public CreateWidgetTransaction() {
	}

	// Get Goo from Goo Pile
	//ST1.1 (Op 1.1)
	public List<Goo> getGooFromPile(int numberOfGoos) throws IllegalStateException, SecurityException, SystemException {
		
		try {
			builder.getManager().begin();
			//begin();
			goos = builder.getGooList(numberOfGoos);
			// delete that goo from the pile
			//commit();
			builder.getManager().commit();
		} catch (SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| NotSupportedException | SystemException e) {
			builder.getManager().rollback();
			//rollback();
			e.printStackTrace();
		}
		return goos;
	}

	// Give Goo to a Builder and get back a Raw Widget
	public RawWidget getRawWidget(String name, String code, List<Goo> goo) {
		builder.setName(name);
		builder.setCode(code);
		try {
			builder.getManager().begin();
			//begin();
			rawWidget = (RawWidget) builder.create(goo);
			//commit();
			builder.getManager().commit();

		} catch(SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| NotSupportedException | SystemException e)
		{	
			try
			{
				//rollback();
				builder.getManager().rollback();

			} 
			catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
		}
		return rawWidget;
	}

	// Give Raw Widget to a Baker and get a Rough Widget
	public RoughWidget getRoughWidget(String name, String code,
			RawWidget rawWidget) throws IllegalStateException,
			SecurityException, SystemException {
		baker.setName(name);
		baker.setCode(code);
		baker.setStyle("Rough");
		try {

			baker.getManager().begin();
			//begin();
			roughWidget = (RoughWidget) baker.create(rawWidget);
			baker.getManager().commit();
			//commit();

		} catch (NotSupportedException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| SecurityException | IllegalStateException e) {
			//baker.getManager().rollback();
			//rollback();
			builder.getManager().rollback();

			e.printStackTrace();
		}
		return roughWidget;
	}

	// Give Rough Widget to a Polisher and get a Polished Widget
	public PolishedWidget getPolishedWidget(String name, String code,
			RoughWidget roughWidget) {
		polisher.setName(name);
		polisher.setCode(code);
		polisher.setStyle("Polished");

		try {

			polisher.getManager().begin();
			//begin();
			polishedWidget = (PolishedWidget) polisher.create(roughWidget);
			polisher.getManager().commit();
			//commit();
		} catch (NotSupportedException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| SecurityException | SystemException | IllegalStateException e) {
			try {
				polisher.getManager().rollback();
				//rollback();
			} catch (IllegalStateException | SecurityException
					| SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return polishedWidget;
	}

	// Put Polish Widget in a Widget Pile
	public void putInWidgetPile(PolishedWidget pWidget) {
		try {
			polisher.getManager().begin();
			//begin();
			polisher.add(pWidget);
			polisher.getManager().commit();
			//commit();
		} catch (SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| NotSupportedException | SystemException e) {
			try {
				polisher.getManager().rollback();
				//rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}