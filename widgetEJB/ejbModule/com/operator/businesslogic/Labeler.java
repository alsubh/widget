package com.operator.businesslogic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.entity.busniess.OperatorRemote;
import com.widget.businesslogic.GadgetPile;
import com.widget.entities.Gadget;

import java.util.ArrayList;
import java.util.List;

/**
 * Session Bean implementation class Labeler
 */
@Stateless
public class Labeler{

	private String tag = "";
	private static String serialNumber = "149000";
	private final GadgetPile gadgetpileObject = new GadgetPile();
	List<Gadget> listOfGadget = new ArrayList<Gadget>();

	private javax.transaction.TransactionManager manager = null; //com.arjuna.ats.jta.TransactionManager.transactionManager();
	
	public Labeler() {

	}

	public void tagging() {
		listOfGadget = gadgetpileObject.retrieveSpecificGadgets(" ");
		for (Gadget gad : listOfGadget) {
			// Update Tag
			gadgetpileObject.updateTag(gad, generateSerial());
		}
	}

	public void tagging(Gadget gad) {
		// Update Tag
		gadgetpileObject.updateTag(gad, getTag());
	}

	public String generateSerial()// (Gadget gad)
	{
		// serialNumber= serialNumber+1;
		return serialNumber;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public javax.transaction.TransactionManager getManager() {
		return manager;
	}

	public void setManager(javax.transaction.TransactionManager manager) {
		this.manager = manager;
	}

}
