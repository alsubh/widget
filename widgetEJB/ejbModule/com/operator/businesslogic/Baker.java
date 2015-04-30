package com.operator.businesslogic;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.processedComponent.utilities.RawWidget;
import com.processedComponent.utilities.RoughWidget;
import com.widget.businesslogic.WidgetPile;
import com.widget.entities.Widget;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.entity.busniess.OperatorRemote;

/**
 * Session Bean implementation class Baker
 */
@Stateless
public class Baker implements OperatorRemote {

	private String name;
	private String code;
	private String style;

	private WidgetPile widgetPileObject = new WidgetPile();

	private javax.transaction.TransactionManager manager = null; //com.arjuna.ats.jta.TransactionManager.transactionManager();

	public RawWidget getRawWidget() {
		Widget wid = new RawWidget();
		// select the Widget from the WidgetPile
		wid = (Widget) widgetPileObject.retrieveSpecificWidgets("Raw");
		// remove the RawWidget from the Pile.
		return (RawWidget) wid;
	}

	@Override
	public Object create(Object rawWid) {
		// TODO Auto-generated method stub
		RoughWidget roughWidget = new RoughWidget(name, code,
				(RawWidget) rawWid);
		roughWidget.setStyle("Rough");
		// update
		remove(rawWid);
		add(roughWidget);
		return roughWidget;
	}

	@Override
	public void add(Object obj) {
		// TODO Auto-generated method stub
		widgetPileObject.add(obj);
	}

	@Override
	public void remove(Object obj) {
		widgetPileObject.remove(obj);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public WidgetPile getWidgetPileObject() {
		return widgetPileObject;
	}

	public void setWidgetPileObject(WidgetPile widgetPileObject) {
		this.widgetPileObject = widgetPileObject;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public javax.transaction.TransactionManager getManager() {
		return manager;
	}

	public void setManager(javax.transaction.TransactionManager manager) {
		this.manager = manager;
	}
}
