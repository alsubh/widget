package com.transaction;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.List;

import com.operator.businesslogic.Assembler;
import com.operator.businesslogic.Labeler;
import com.widget.entities.Gadget;
import com.widget.entities.Widget;
import com.entity.busniess.TransactionRemote;

/**
 * Session Bean implementation class AssembledGadgetTransaction
 */
@Stateless
@LocalBean
public class AssembledGadgetTransaction implements TransactionRemote{

	private final Widget widget = new Widget();
	private Gadget gadet = new Gadget();

	private final Assembler assembler = new Assembler();

	private List<Widget> widgets = new ArrayList<Widget>();

	private final Labeler labeler = new Labeler();

	TransactionManager manager= null;
	
	public AssembledGadgetTransaction() {
	}

	// Get Widget (W1) from Widget Pile 1
	// Get Widget (W2) from Widget Pile 2
	@SuppressWarnings("finally")
	public List<Widget> getWidget(int numberOfComponent) 
	{
		try 
		{
			manager.begin();
			widgets = assembler.getWidgets(numberOfComponent);
			manager.commit();
		} 
		catch(SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| NotSupportedException | SystemException e)
		{	
			try
			{
				manager.rollback();
			} 
			catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
		}
		finally
		{
			// lock.readLock().unlock();
			return widgets;
		}
	}

	// Give W1 and W2 to Assembler and get a Gadget, G
	public Gadget getGadget(String name, String code, List<Widget> wids)// , int
																		// numberofWidgets)
	{
		widgets = wids;

		assembler.setName(name);
		assembler.setCode(code);
		assembler.setNumberofWidget(widgets.size());// numberofWidgets);
		
		try
		{
			manager.begin();
			gadet = (Gadget) assembler.create(widgets);
			manager.commit();
		}
		catch(SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| NotSupportedException | SystemException e) {
			
			try {
				manager.rollback();
			} catch (IllegalStateException | SecurityException
					| SystemException e1) {
				// TODO Auto-generated catch block
				gadet = null;
				e1.printStackTrace();
			}
			gadet = null;
			e.printStackTrace();
		}
		// Put Gadget G in a Gadget Pile
		return gadet;
	}

	// Have Labeler put a tag on G
	public void putTag(Gadget gad, String tag) {
		try {
			manager.begin();
			labeler.setTag(tag);
			labeler.tagging(gad);
			manager.commit();
		} 	catch(SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| NotSupportedException | SystemException e) {
			// TODO Auto-generated catch block
			try {
				manager.rollback();
			} catch (IllegalStateException | SecurityException
					| SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
}