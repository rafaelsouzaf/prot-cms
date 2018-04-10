package com.github.rafaelsouzaf.prot.client.component;

import com.extjs.gxt.ui.client.widget.Info;

public class ProtMessage extends Info {
	
	public static void Info(String msg) {
		Info.display("", msg);
	}

	public static void Info(String title, String msg) {
		Info.display(title, msg);
	}

}
