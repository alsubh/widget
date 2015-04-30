package com.operator.businesslogic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.Local;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.entity.busniess.OperatorRemote;
import com.processedComponent.utilities.PolishedWidget;
import com.processedComponent.utilities.RoughWidget;
import com.widget.businesslogic.WidgetPile;
import com.widget.entities.Widget;

/**
 * Session Bean implementation class Polisher
 */
@Stateless
public class Polisher implements OperatorRemote {
	
	private String name;
	private String code;
	private String style;

	private final WidgetPile widgetPileObject = new WidgetPile();

	private javax.transaction.TransactionManager manager = null; //com.arjuna.ats.jta.TransactionManager.transactionManager();

	public RoughWidget getRoughWidget() {
		Widget wid = new Widget();
		// select the Widget from the WidgetPile
		wid = (Widget) widgetPileObject.retrieveSpecificWidgets("Rough");
		return (RoughWidget) wid;
	}

	@Override
	public Object create(Object roughWid) {
		// TODO Auto-generated method stub
		PolishedWidget polishedWidget = new PolishedWidget(name, code,
				(RoughWidget) roughWid);// getRoughWidget());
		polishedWidget.setStyle("Polished");
		// update
		remove(roughWid);
		add(polishedWidget);
		return polishedWidget;
	}

	@Override
	public void add(Object obj) {
		// TODO Auto-generated method stub
		widgetPileObject.add(obj);
	}

	@Override
	public void remove(Object obj) {
		// TODO Auto-generated method stub

		// WidgetPile.widgetPile.remove(index);
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
