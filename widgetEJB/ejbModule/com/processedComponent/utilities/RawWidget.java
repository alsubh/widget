package com.processedComponent.utilities;

import java.util.List;

import javax.annotation.Resource;

import com.widget.entities.Goo;
import com.widget.entities.Widget;

public class RawWidget extends Widget {

	public RawWidget() {
		super();
	}
//
//	public RawWidget(List<Goo> goolist) {
//		super(goolist);
//	}

	public RawWidget(String name, String code) {
		super(name, "Raw", "Raw" + code);
	}

}
