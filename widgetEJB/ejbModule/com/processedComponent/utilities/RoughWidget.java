package com.processedComponent.utilities;

import javax.annotation.Resource;

import com.widget.entities.Widget;


public class RoughWidget extends Widget {

	 public RoughWidget(){
		 super();
	 }

	public RoughWidget(RawWidget rawWidget) {
//		super(rawWidget.getGooList());
		super();
	}

	public RoughWidget(String name, String code, RawWidget rawWidget) {
		super(name, "Rough", "Rough" + code);
	}
}
