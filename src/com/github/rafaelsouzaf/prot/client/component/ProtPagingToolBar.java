package com.github.rafaelsouzaf.prot.client.component;

import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;

public class ProtPagingToolBar extends PagingToolBar {
	
	public ProtPagingToolBar(int pageSize) {
		super (pageSize);
		
		getMessages().setBeforePageText("Pagina");
		getMessages().setDisplayMsg("Mostrando {0} - {1} de {2}");
		getMessages().setAfterPageText("de {0}");
		getMessages().setEmptyMsg("Ningun dato encontrado");
		getMessages().setFirstText("Primera Pagina");
		getMessages().setLastText("Ultima Pagina");
		getMessages().setNextText("Proxima Pagina");
		getMessages().setPrevText("Pagina Anterior");
		getMessages().setRefreshText("Actualizar");
		
	}

}
